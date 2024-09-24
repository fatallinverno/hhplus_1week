package io.hhplus.tdd;

import io.hhplus.tdd.point.PointService;
import io.hhplus.tdd.point.UserPoint;

public class PointValidation {

    private PointService pointService;

    public void validateUserId(long userId) throws Exception {
        if (userId <= 0) {
            throw new Exception("존재 하지 않는" + userId + "입니다.");
        }
    }

    public void validateChargeAmount(long amount) throws Exception {
        if (amount < 0) {
            throw new Exception("충전 금액이 0보다 커야됩니다.");
        }
    }

    public void validateUseAmount(long userId, long amount) throws Exception {
        UserPoint userInfo = pointService.userPoint(userId);
        if(userInfo.point() < amount) {
            throw new Exception("사용하시는 포인트보다 적습니다. 포인트를 충전하시기 바랍니다.");
        }
    }

}
