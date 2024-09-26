package io.hhplus.tdd.point;

import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.point.controller.ChargePointCommand;
import io.hhplus.tdd.point.controller.UsePointCommand;
import io.hhplus.tdd.point.service.PointUpsertService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PointUpsertServiceConcurrencyTest {

    @Autowired
    private PointUpsertService pointUpsertService;
    @Autowired
    private UserPointTable userPointTable;

    @DisplayName("동시성 포인트 충전 성공")
    @Test
    public void testConcurrentPointCharging_SucceedWithoutConcurrencyControl() throws ExecutionException, InterruptedException {
        // 유저 ID 목록과 처리할 요청 수
        List<Long> userIds = List.of(1L, 2L, 3L, 4L, 5L);
        int numberOfRequestsPerUser = 10;

        // 병렬로 실행할 CompletableFuture 목록
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        // 각 유저별로 비동기 요청을 생성 (각 유저에게 100개의 요청을 병렬로 실행)
        for (Long userId : userIds) {
            for (int i = 0; i < numberOfRequestsPerUser; i++) {
                CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                    ChargePointCommand command = ChargePointCommand.builder()
                            .id(userId)
                            .amount(10L)
                            .build();
                    pointUpsertService.charge(command); // 각 요청마다 10포인트 충전
                });
                futures.add(future);
            }
        }

        // 모든 비동기 작업이 완료될 때까지 대기
        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        allOf.get(); // 모든 작업이 끝날 때까지 기다림

        // 검증: 각 유저의 100개의 요청이 각각 10 포인트씩 충전되었는지 확인
        for (Long userId : userIds) {
            assertEquals(100, userPointTable.selectById(userId).point()); // 100 * 10 = 1000 포인트
        }
    }

    @DisplayName("동시성 포인트 사용 성공")
    @Test
    public void testConcurrentPointUsing_WithConcurrencyControl() throws ExecutionException, InterruptedException {
        // 유저 ID 목록과 처리할 요청 수
        List<Long> userIds = List.of(6L, 7L, 8L, 9L, 10L);
        int numberOfRequestsPerUser = 10;

        // 각 유저에게 1000 포인트 충전
        for (Long userId : userIds) {
            pointUpsertService.charge(ChargePointCommand.builder().id(userId).amount(1000L).build());
        }

        // 병렬로 실행할 CompletableFuture 목록
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        // 각 유저별로 비동기 요청을 생성 (각 유저에게 10개의 요청을 병렬로 실행)
        for (Long userId : userIds) {
            for (int i = 0; i < numberOfRequestsPerUser; i++) {
                CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                    UsePointCommand command = UsePointCommand.builder()
                            .id(userId)
                            .amount(10L)
                            .build();
                    pointUpsertService.use(command); // 각 요청마다 10포인트 사용
                });
                futures.add(future);
            }
        }

        // 모든 비동기 작업이 완료될 때까지 대기
        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        allOf.get(); // 모든 작업이 끝날 때까지 기다림

        // 검증: 각 유저의 10개의 요청이 각각 10 포인트씩 사용되었는지 확인
        for (Long userId : userIds) {
            assertEquals(900L, userPointTable.selectById(userId).point()); // 1000 - (10 * 10 요청) = 900 포인트
        }
    }
}
