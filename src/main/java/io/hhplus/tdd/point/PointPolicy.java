package io.hhplus.tdd.point;

import org.springframework.stereotype.Component;

@Component
public class PointPolicy {

    private final long maxPointLimit;

    public PointPolicy() {
        this.maxPointLimit = 10000L;
    }

    public long getMaxPointLimit() {
        return maxPointLimit;
    }

}
