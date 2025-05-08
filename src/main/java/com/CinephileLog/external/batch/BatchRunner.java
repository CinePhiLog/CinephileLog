package com.CinephileLog.external.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BatchRunner implements CommandLineRunner {

    private final JobLauncher jobLauncher;
    private final Job movieFetchJob;

    @Value("${movie.data.init:false}")
    private boolean init;

    @Override
    public void run(String... args) throws Exception {
        if (!init) {
            System.out.println("영화 데이터 배치 실행이 비활성화되어 있습니다");
            return;
        }

        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("timestamp", System.currentTimeMillis())
                .toJobParameters();

        try {
            JobExecution execution = jobLauncher.run(movieFetchJob, jobParameters);
            System.out.println("✅ 배치 작업 상태: " + execution.getStatus());
        } catch (Exception e) {
            System.err.println("❌ 배치 작업 실행 실패: " + e.getMessage());
        }
    }
}
