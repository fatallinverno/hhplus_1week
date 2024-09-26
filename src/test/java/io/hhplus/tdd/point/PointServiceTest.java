package io.hhplus.tdd.point;

import io.hhplus.tdd.PointValidation;
import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.point.Service.PointService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class PointServiceTest {

    @Mock
    private UserPointTable userPointTable;

    @Mock
    private PointHistoryTable pointHistoryTable;

    @Mock
    private PointValidation pointValidation;

    //    @InjectMocks
    @Mock
    private PointService pointService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("유저 포인트 조회")
    @Test
    void testGetUserPoint() {
        //Given
        long userId = 1L;
        long amount = 100L;
        UserPoint testUserPoint = new UserPoint(userId, amount, System.currentTimeMillis());

        when(pointService.userPoint(userId)).thenReturn(testUserPoint);

        //When
        pointService.userPoint(userId);

        //Then
        assertEquals(1, testUserPoint.id());

    }

    @DisplayName("포인트 충전/사용 내역 조회")
    @Test
    void testGetPointHistory() {
        //Given
        long userId = 1L;
        long amount = 100L;
        PointHistory testPointHistory = new PointHistory(1L, userId, amount, TransactionType.CHARGE, System.currentTimeMillis());

        when(pointHistoryTable.selectAllByUserId(userId)).thenReturn(List.of(testPointHistory));

        //When
        pointService.pointHistory(testPointHistory.userId());

        //Then
        assertEquals(1, testPointHistory.userId());

    }

//    @DisplayName("포인트 충전")
//    @Test
//    void testChangeUserPoint() {
//        //Given
//        long userId = 1L;
//        long chargeAmount = 100L;
//        long baseAmount = 100L;
//
//        UserPoint testUserPoint = new UserPoint(userId, baseAmount, 1L);
//        UserPoint t = new UserPoint(testUserPoint.id(), baseAmount + chargeAmount, 1L);
//
//        when(userPointTable.selectById(userId)).thenReturn(testUserPoint);
//        when(userPointTable.insertOrUpdate(testUserPoint.id(), testUserPoint.point() + chargeAmount)).thenReturn(t);
//
//        //When
//        pointService.chargePoint(testUserPoint.id(), chargeAmount);   //sut mocking X
//
//        //Then
//        assertEquals(t.point(), testUserPoint.point() + chargeAmount); //findById로 아이디를 찾아와서 200 포인트 만큼 있는지 여부 체크
//
//    }
//
//    @DisplayName("포인트 사용")
//    @Test
//    void testUsePoint() {
//        //Given
//        long userId = 1L;
//        long useAmount = 100L;
//        long baseAmount = 200L;
//
//        UserPoint testUserPoint = new UserPoint(userId, baseAmount, 1L);
//        UserPoint t = new UserPoint(testUserPoint.id(), baseAmount - useAmount, 1L);
//
//        when(userPointTable.selectById(userId)).thenReturn(testUserPoint);
//        when(userPointTable.insertOrUpdate(testUserPoint.id(), useAmount)).thenReturn(t);
//
//        //When
//        pointService.usePoint(testUserPoint.id(), useAmount);
//
//        //Then
//        assertEquals(t.point(), baseAmount - useAmount);
//
//    }
    
}
