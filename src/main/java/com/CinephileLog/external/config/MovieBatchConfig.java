package com.CinephileLog.external.config;

import com.CinephileLog.external.batch.MovieIdRangePartitioner;
import com.CinephileLog.external.batch.MovieIdReader;
import com.CinephileLog.external.service.TmdbApiClient;
import com.CinephileLog.movie.domain.Movie;
import com.CinephileLog.movie.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.skip.AlwaysSkipItemSkipPolicy;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class MovieBatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final MovieRepository movieRepository;
    private final TmdbApiClient tmdbApiClient;
    private final MovieIdRangePartitioner partitioner;

    @Bean
    public Job movieFetchJob(JobLauncher jobLauncher) {
        return new JobBuilder("movieFetchJob", jobRepository)
                .start(partitionedStep())
                .build();
    }

    @Bean
    public Step partitionedStep() {
        return new StepBuilder("partitionedStep", jobRepository)
                .partitioner("movieStep", partitioner)
                .step(delegateStep())
                .gridSize(10)
                .taskExecutor(new SimpleAsyncTaskExecutor())
                .build();
    }
    public Step delegateStep() {
        return new Step() {
            @Override
            public String getName() {
                return "movieStepDelegate";
            }

            @Override
            public boolean isAllowStartIfComplete() {
                return false;
            }

            @Override
            public int getStartLimit() {
                return Integer.MAX_VALUE;
            }

            @Override
            public void execute(StepExecution stepExecution) throws JobInterruptedException {
                Step dynamicStep = movieStepDynamic(stepExecution);
                dynamicStep.execute(stepExecution);
            }
        };
    }

    public Step movieStepDynamic(StepExecution stepExecution) {
        ExecutionContext ctx = stepExecution.getExecutionContext();
        int startId = ctx.getInt("startId");
        int endId = ctx.getInt("endId");
        String apiKey = ctx.getString("apiKey");

        System.out.println("🔁 파티션 실행: startId=" + startId + ", endId=" + endId + ", apiKey=" + apiKey);

        ItemReader<Integer> reader = new MovieIdReader(startId, endId);

        ItemProcessor<Integer, Movie> processor = id -> {
            Optional<Movie> movie = tmdbApiClient.fetchMovieById(id, apiKey);
            if (movie.isPresent()) {
                System.out.println("✅ 저장 대상 영화 ID: " + id);
            }
            return movie.orElse(null);
        };

        return new StepBuilder("movieStep-" + startId + "-" + endId, jobRepository)
                .<Integer, Movie>chunk(10, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(movies -> {
                    movieRepository.saveAll(movies);
                    System.out.println("💾 저장 완료 - count: " + movies.size());
                })
                .faultTolerant()
                .skipPolicy(new AlwaysSkipItemSkipPolicy())
                .build();
    }

}
