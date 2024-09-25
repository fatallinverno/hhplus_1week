package io.hhplus.tdd.point.Service;

import io.hhplus.tdd.PointValidation;
import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.point.PointHistory;
import io.hhplus.tdd.point.TransactionType;
import io.hhplus.tdd.point.UserPoint;

import java.util.List;

public class PointServiceImpl implements PointService {

    private final UserPointTable userPointTable;
    private final PointHistoryTable pointHistoryTable;
    private final PointValidation pointValidation;

    public PointServiceImpl(UserPointTable userPointTable, PointHistoryTable pointHistoryTable, PointValidation pointValidation) {
        this.userPointTable = userPointTable;
        this.pointHistoryTable = pointHistoryTable;
        this.pointValidation = pointValidation;
    }

    public UserPoint userPoint(long userId) {
        pointValidation.validateUserId(userId);

        return userPointTable.selectById(userId);
    }

    public List<PointHistory> pointHistory(long userId) {
        pointValidation.validateUserId(userId);

        return pointHistoryTable.selectAllByUserId(userId);
    }

    public UserPoint chargePoint(long userId, long amount) {
        pointValidation.validateUserId(userId);
        pointValidation.validateChargeAmount(amount);

        UserPoint userInfo = userPoint(userId);
        long updatePoint = userInfo.point() + amount;

        UserPoint updateUserPoint = userPointTable.insertOrUpdate(userId, updatePoint);

        pointHistoryTable.insert(userId, amount, TransactionType.CHARGE, 1L);

        return updateUserPoint;
    }

    public UserPoint usePoint(long userId, long amount) {
        pointValidation.validateUserId(userId);

        UserPoint userInfo = userPoint(userId);

        pointValidation.validateUseAmount(userInfo.point(), amount);

        long updatePoint = userInfo.point() - amount;

        UserPoint updateUserPoint = userPointTable.insertOrUpdate(userId, updatePoint);
        pointHistoryTable.insert(userId, amount, TransactionType.USE, 1L);

        return updateUserPoint;
    }

}
