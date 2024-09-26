package io.hhplus.tdd.point.Service;

import io.hhplus.tdd.point.PointHistory;
import io.hhplus.tdd.point.UserPoint;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface PointService {

    UserPoint userPoint(long userId);

    List<PointHistory> pointHistory(long userId);

    CompletableFuture<UserPoint> chargePointAsync(long userId, long amount);

    CompletableFuture<UserPoint> usePointAsync(long userId, long amount);

}