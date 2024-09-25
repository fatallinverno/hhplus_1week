package io.hhplus.tdd.point.Service;

import io.hhplus.tdd.point.PointHistory;
import io.hhplus.tdd.point.UserPoint;

import java.util.List;

public interface PointService {

    UserPoint userPoint(long userId);

    List<PointHistory> pointHistory(long userId);

    UserPoint chargePoint(long userId, long amount);

    UserPoint usePoint(long userId, long amount);

}