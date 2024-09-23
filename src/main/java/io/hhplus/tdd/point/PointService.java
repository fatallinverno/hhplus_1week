package io.hhplus.tdd.point;

import java.util.ArrayList;
import java.util.List;

public class PointService {
    private final List<UserPoint> userPoints = new ArrayList<>();
    private final List<PointHistory> pointHistories = new ArrayList<>();

    // 1. 포인트 조회
    public UserPoint getPoint(long userId) {
        return userPoints.stream()
                .filter(userPoint -> userPoint.id() == userId)
                .findFirst()
                .orElse(UserPoint.empty(userId));
    }

    // 2. 포인트 충전 내역 조회
    public List<PointHistory> getPointHistories(long userId) {
        return pointHistories.stream()
                .filter(history -> history.userId() == userId)
                .toList();
    }

    // 3. 포인트 충전
    public UserPoint chargePoints(long userId, long amount) {
        UserPoint userPoint = getPoint(userId);
        long updatedPoints = userPoint.point() + amount;
        UserPoint updatedUserPoint = new UserPoint(userId, updatedPoints, System.currentTimeMillis());

        // 포인트 업데이트
        userPoints.removeIf(up -> up.id() == userId);
        userPoints.add(updatedUserPoint);

        // 히스토리 추가
        pointHistories.add(new PointHistory(System.currentTimeMillis(), userId, amount, TransactionType.CHARGE, System.currentTimeMillis()));

        return updatedUserPoint;
    }

    // 4. 포인트 사용
    public UserPoint usePoints(long userId, long amount) throws Exception {
        UserPoint userPoint = getPoint(userId);
        if (userPoint.point() < amount) {
            throw new Exception("Not enough points");
        }

        long updatedPoints = userPoint.point() - amount;
        UserPoint updatedUserPoint = new UserPoint(userId, updatedPoints, System.currentTimeMillis());

        // 포인트 업데이트
        userPoints.removeIf(up -> up.id() == userId);
        userPoints.add(updatedUserPoint);

        // 히스토리 추가
        pointHistories.add(new PointHistory(System.currentTimeMillis(), userId, amount, TransactionType.USE, System.currentTimeMillis()));

        return updatedUserPoint;
    }
}
