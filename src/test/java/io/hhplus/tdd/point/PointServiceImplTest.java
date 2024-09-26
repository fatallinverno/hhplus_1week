package io.hhplus.tdd.point;

import io.hhplus.tdd.PointValidation;
import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.point.Service.PointServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PointServiceImplTest {

    @Autowired
    private PointServiceImpl pointService;

    @Autowired
    private PointValidation pointValidation;

    @Autowired
    private PointPolicy pointPolicy;

    @Autowired
    private UserPointTable userPointTable;

    @Autowired
    private PointHistoryTable pointHistoryTable;

    @Test
    public void testChargePointAsync() throws ExecutionException, InterruptedException {
        long userId = 1L;
        long chargeAmount = 500L;
        long currentPoint = 1000L;
        long maxPoint = 5000L;

        UserPoint userPoint = new UserPoint(userId, currentPoint, 1L);
        userPointTable.insertOrUpdate(userId, currentPoint);

        CompletableFuture<UserPoint> future = pointService.chargePointAsync(userId, chargeAmount);
        UserPoint updatedUserPoint = future.get();

        assertEquals(currentPoint + chargeAmount, updatedUserPoint.point());

        UserPoint dbUserPoint = userPointTable.selectById(userId);
        assertEquals(currentPoint + chargeAmount, dbUserPoint.point());
    }

    @Test
    public void testUsePointAsync() throws ExecutionException, InterruptedException {
        long userId = 1L;
        long useAmount = 200L;
        long currentPoint = 1000L;

        UserPoint userPoint = new UserPoint(userId, currentPoint, 1L);
        userPointTable.insertOrUpdate(userId, currentPoint);

        CompletableFuture<UserPoint> future = pointService.usePointAsync(userId, useAmount);
        UserPoint updatedUserPoint = future.get();

        assertEquals(currentPoint - useAmount, updatedUserPoint.point());

        UserPoint dbUserPoint = userPointTable.selectById(userId);
        assertEquals(currentPoint - useAmount, dbUserPoint.point());

    }

}
