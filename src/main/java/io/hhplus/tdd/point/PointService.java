package io.hhplus.tdd.point;

import io.hhplus.tdd.PointValidation;
import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.database.UserPointTable;

import java.util.List;

public class PointService {

    private final UserPointTable userPointTable;
    private final PointHistoryTable pointHistoryTable;
    private final PointValidation pointValidation;

    public PointService(UserPointTable userPointTable, PointHistoryTable pointHistoryTable, PointValidation pointValidation) {
        this.userPointTable = userPointTable;
        this.pointHistoryTable = pointHistoryTable;
        this.pointValidation = pointValidation;
    }

    public UserPoint userPoint(long userId) throws Exception {
        pointValidation.validateUserId(userId);

        return userPointTable.selectById(userId);
    }

    public List<PointHistory> pointHistory(long userId) throws Exception {
        pointValidation.validateUserId(userId);

        return pointHistoryTable.selectAllByUserId(userId);
    }

    public UserPoint chargePoint(long userId, long amount) throws Exception {
//        pointValidation.validateUserId(userId);
//        pointValidation.validateChargeAmount(amount);

        UserPoint userInfo = userPoint(userId);
        long updatePoint = userInfo.point() + amount;

        UserPoint updateUserPoint = userPointTable.insertOrUpdate(userId, updatePoint);

        pointHistoryTable.insert(userId, amount, TransactionType.CHARGE, System.currentTimeMillis());

        return updateUserPoint;
    }

    public UserPoint usePoint(long userId, long amount) throws Exception {
//        pointValidation.validateUserId(userId);
//        pointValidation.validateUseAmount(userId, amount);

        UserPoint userInfo = userPoint(userId);
        long updatePoint = userInfo.point() - amount;

        UserPoint updateUserPoint = userPointTable.insertOrUpdate(userId, updatePoint);
        pointHistoryTable.insert(userId, amount, TransactionType.USE, System.currentTimeMillis());

        return updateUserPoint;
    }

}