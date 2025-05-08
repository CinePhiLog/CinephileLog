package com.CinephileLog.external.batch;

import org.springframework.batch.item.ItemReader;

public class MovieIdReader implements ItemReader<Integer> {

    private int current;
    private final int end;

    public MovieIdReader(int start, int end) {
        this.current = start;
        this.end = end;
    }

    @Override
    public Integer read() {
        return (current <= end) ? current++ : null;
    }
}
