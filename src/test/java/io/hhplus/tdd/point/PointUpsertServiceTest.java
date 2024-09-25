package io.hhplus.tdd.point;

import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.point.constant.TransactionType;
import io.hhplus.tdd.point.controller.AddPointHistoryCommand;
import io.hhplus.tdd.point.controller.ChargePointCommand;
import io.hhplus.tdd.point.domain.UserPoint;
import io.hhplus.tdd.point.service.PointHistoryService;
import io.hhplus.tdd.point.service.PointHistoryServiceImpl;
import io.hhplus.tdd.point.service.PointUpsertServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PointUpsertServiceTest {

    @InjectMocks
    private PointUpsertServiceImpl pointUpsertService;
    @Mock
    private UserPointTable userPointTable;
    @Mock
    private PointHistoryServiceImpl pointHistoryService;

    @DisplayName("point값과 amount값을 합칠때 Long.MAX_VALUE 초과할 경우")
    @Test
    void 충전할때_point값과_amount값을_합칠때_초과할_경우() {
        // Given
        Long validId = 1L;
        Long validAmount = Long.MAX_VALUE;

        ChargePointCommand command = ChargePointCommand.builder()
                .id(validId)
                .amount(validAmount)
                .build();

        // When

        // Then
        assertThrows(RuntimeException.class, () -> pointUpsertService.charge(command));
    }

    @DisplayName("포인트 충전 성공")
    @Test
    void 포인트_충전_성공() {
        // Given
        Long validId = 1L;
        Long validAmount = 100L;

        ChargePointCommand command = ChargePointCommand.builder()
                .id(validId)
                .amount(validAmount)
                .build();

        when(userPointTable.selectById(validId)).thenReturn(new UserPoint(validId, 100L, System.currentTimeMillis()));

        // When
        pointUpsertService.charge(command);

        // Then
        verify(userPointTable).insertOrUpdate(validId, 200L);
        verify(pointHistoryService).insert(org.mockito.ArgumentMatchers.any(AddPointHistoryCommand.class));
    }

}
