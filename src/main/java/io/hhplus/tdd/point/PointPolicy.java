package io.hhplus.tdd.point;

public class PointPolicy {

    private final long maxPointLimit;

    public PointPolicy(long maxPointLimit) {
        this.maxPointLimit = 10000L;
    }

    public long getMaxPointLimit() {
        return maxPointLimit;
    }

}
