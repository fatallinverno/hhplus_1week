package io.hhplus.tdd.point.Service;

import io.hhplus.tdd.PointValidation;
import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.point.PointHistory;
import io.hhplus.tdd.point.PointPolicy;
import io.hhplus.tdd.point.TransactionType;
import io.hhplus.tdd.point.UserPoint;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class PointServiceImpl implements PointService {

    private final UserPointTable userPointTable;
    private final PointHistoryTable pointHistoryTable;
    private final PointValidation pointValidation;
    private final PointPolicy pointPolicy;

    public PointServiceImpl(PointValidation pointValidation, PointPolicy pointPolicy, UserPointTable userPointTable, PointHistoryTable pointHistoryTable) {
        this.userPointTable = userPointTable;
        this.pointHistoryTable = pointHistoryTable;
        this.pointValidation = pointValidation;
        this.pointPolicy = pointPolicy;
    }

    public UserPoint userPoint(long userId) {
        pointValidation.validateUserId(userId);

        return userPointTable.selectById(userId);
    }

    public List<PointHistory> pointHistory(long userId) {
        pointValidation.validateUserId(userId);

        return pointHistoryTable.selectAllByUserId(userId);
    }

    public CompletableFuture<UserPoint> chargePointAsync(long userId, long amount) {
        return CompletableFuture.supplyAsync(() -> {
            pointValidation.validateChargeAmount(amount);

            UserPoint userInfo = userPoint(userId);
            long maxPoint = pointPolicy.getMaxPointLimit();
            long sumPoint = userInfo.point() + amount;

            pointValidation.validateMaxPoint(sumPoint, maxPoint);

            UserPoint updateUserPoint = userPointTable.insertOrUpdate(userId, sumPoint);
            pointHistoryTable.insert(userId, amount, TransactionType.CHARGE, 1L);

            return updateUserPoint;
        });
    }

    public CompletableFuture<UserPoint> usePointAsync(long userId, long amount) {
        return CompletableFuture.supplyAsync(() -> {

            UserPoint userInfo = userPoint(userId);
            pointValidation.validateUseAmount(userInfo.point(), amount);

            long updatePoint = userInfo.point() - amount;

            UserPoint updateUserPoint = userPointTable.insertOrUpdate(userId, updatePoint);
            pointHistoryTable.insert(userId, amount, TransactionType.USE, 1L);

            return updateUserPoint;
        });
    }

}
