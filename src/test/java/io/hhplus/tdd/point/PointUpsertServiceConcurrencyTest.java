//package io.hhplus.tdd.point;
//
//
//import io.hhplus.tdd.database.UserPointTable;
//import io.hhplus.tdd.point.controller.ChargePointCommand;
//import io.hhplus.tdd.point.service.PointService;
//import io.hhplus.tdd.point.service.PointUpsertService;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.CompletableFuture;
//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.atomic.AtomicInteger;
//import java.util.concurrent.atomic.AtomicLong;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@SpringBootTest
//public class PointUpsertServiceConcurrencyTest {
//
//    @Autowired
//    private PointUpsertService pointUpsertService;
//    @Autowired
//    private UserPointTable userPointTable;
//
//    @DisplayName("동시성 제어없을때 포인트 충전 실패")
//    @Test
//    public void testConcurrentPointCharging_FailsWithoutConcurrencyControl() throws ExecutionException, InterruptedException {
//        // 유저 ID와 처리할 요청 수
//        Long userId = 1L;
//        int numberOfRequests = 100;
//
//        // 병렬로 실행할 CompletableFuture 목록
//        List<CompletableFuture<Void>> futures = new ArrayList<>();
//
//        // 여러 개의 비동기 요청을 생성 (100개의 요청을 병렬로 실행)
//        for (int i = 0; i < numberOfRequests; i++) {
//            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
//                ChargePointCommand command = ChargePointCommand.builder()
//                        .id(userId)
//                        .amount(10L)
//                        .build();
//                pointUpsertService.charge(command); // 각 요청마다 10포인트 충전
//            });
//            futures.add(future);
//        }
//
//        // 모든 비동기 작업이 완료될 때까지 대기
//        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
//        allOf.get(); // 모든 작업이 끝날 때까지 기다림
//
//        // 검증: 100개의 요청이 각각 10 포인트씩 충전되었는지 확인
//        assertEquals(1000, userPointTable.selectById(userId).point());// 100 * 10 = 1000 포인트
//    }
//}
