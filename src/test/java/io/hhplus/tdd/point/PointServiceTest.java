package io.hhplus.tdd.point;

import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.database.UserPointTable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PointServiceTest {

    @Mock
    private UserPointTable userPointTable;

    @Mock
    private PointHistoryTable pointHistoryTable;

    @InjectMocks
    private PointService pointService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("유저 포인트 조회")
    @Test
    void testGetUserPoint() throws Exception {
        long userId = 1L;
        UserPoint testUserPoint = new UserPoint(userId, 100L, System.currentTimeMillis());

        when(pointService.userPoint(userId)).thenReturn(testUserPoint);

        UserPoint result = pointService.userPoint(userId);

        assertNotNull(result);
        assertEquals(1, result.id());

        verify(userPointTable).selectById(userId);
    }

    @DisplayName("포인트 충전/사용 내역 조회")
    @Test
    void testGetPointHistory() throws Exception {
        long userId = 1L;
        PointHistory testPointHistory = new PointHistory(1L, userId, 100L, TransactionType.CHARGE, System.currentTimeMillis());

        when(pointService.pointHistory(userId)).thenReturn(List.of(testPointHistory));

        List<PointHistory> result = pointService.pointHistory(userId);
        assertNotNull(result);
        assertEquals(1, result.size());

        verify(pointHistoryTable).selectAllByUserId(userId);
    }

    @DisplayName("포인트 충전")
    @Test
    void testChangeUserPoint() throws Exception {
        long userId = 1L;
        long chargeAmount = 100L;

        UserPoint testUserPoint = new UserPoint(userId, 100L, System.currentTimeMillis());

        when(pointService.chargePoint(userId, chargeAmount)).thenReturn(testUserPoint);
        when(userPointTable.insertOrUpdate(userId, testUserPoint.point()+chargeAmount)).thenReturn(new UserPoint(userId, 200L, System.currentTimeMillis()));

        UserPoint result = pointService.chargePoint(userId, chargeAmount);

        assertNotNull(result);
        assertEquals(200L, result.point());

        verify(userPointTable).insertOrUpdate(userId, testUserPoint.point()+chargeAmount);
        verify(pointHistoryTable.insert(userId, chargeAmount, TransactionType.CHARGE, result.updateMillis()));
    }

    @DisplayName("포인트 사용")
    @Test
    void testUsePoint() throws Exception {
        long userId = 1L;
        long useAmount = 100L;

        UserPoint testUserPoint = new UserPoint(userId, 200L, System.currentTimeMillis());

        when(userPointTable.selectById(userId)).thenReturn(testUserPoint);
        when(userPointTable.insertOrUpdate(userId, useAmount)).thenReturn(new UserPoint(userId, useAmount, System.currentTimeMillis()));

        UserPoint result = pointService.usePoint(userId, useAmount);

        assertNotNull(result);
        assertEquals(useAmount, result.point());

        verify(userPointTable).insertOrUpdate(userId, useAmount);
        verify(pointHistoryTable).insert(userId, useAmount, TransactionType.CHARGE, result.updateMillis());
    }

}
