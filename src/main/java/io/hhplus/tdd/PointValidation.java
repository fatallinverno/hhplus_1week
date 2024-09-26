package io.hhplus.tdd;

public class PointValidation {

    public void validateUserId(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("존재 하지 않는 [ " + userId + " ] 입니다.");
        }
    }

    public void validateChargeAmount(long amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("충전 금액이 0보다 커야됩니다.");
        }
    }

    public void validateUseAmount(long baseAmount, long useAmount) {
        if (baseAmount < useAmount) {
            throw new IllegalArgumentException("사용하시는 포인트보다 적습니다. 포인트를 충전하시기 바랍니다.");
        }
    }

    public void validateMaxPoint(long sumPoint, long maxPointLimit) {
        if (sumPoint > maxPointLimit) {
            throw new IllegalArgumentException("최대 포인트 한도를 초과했습니다. 한도: " + maxPointLimit);
        }
    }

}
