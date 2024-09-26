package io.hhplus.tdd.point;

import io.hhplus.tdd.PointValidation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class PointValidationTest {

    private final PointValidation pointValidation = new PointValidation();

    @DisplayName("아이디 Null 체크")
    @Test
    void testIdNullChk() {
        //Given
        long userId = 1L;

        //When & Then
        assertDoesNotThrow(() -> pointValidation.validateUserId(userId));
    }

    @DisplayName("충전 금액 체크")
    @Test
    void testChargeAmountChk() {
        //Given
        long amount = 100L;

        //When & Then
        assertDoesNotThrow(() -> pointValidation.validateChargeAmount(amount));
    }

    @DisplayName("사용 포인트 체크")
    @Test
    void testUseAmountChk() {
        //Given
        long baseAmount = 100L;
        long useAmount = 50L;

        //When & Then
        assertDoesNotThrow(() -> pointValidation.validateUseAmount(baseAmount, useAmount));

    }

    @DisplayName("충전 포인트 맥스")
    @Test
    void testMaxPointChk() {
        //Given
        long baseAmount = 9000L;
        long chargeAmount = 3000L;
        long sumAmouunt = baseAmount + chargeAmount;
        long maxPointAmount = 10000L;

        //When & Then
        assertDoesNotThrow(() -> pointValidation.validateMaxPoint(sumAmouunt, maxPointAmount));
    }

}
