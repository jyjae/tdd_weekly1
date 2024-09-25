package io.hhplus.tdd.point;

import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.exception.LongOverflowException;
import io.hhplus.tdd.exception.NotEnoughPointsException;
import io.hhplus.tdd.point.controller.AddPointHistoryCommand;
import io.hhplus.tdd.point.controller.ChargePointCommand;
import io.hhplus.tdd.point.controller.UsePointCommand;
import io.hhplus.tdd.point.domain.UserPoint;
import io.hhplus.tdd.point.service.PointHistoryServiceImpl;
import io.hhplus.tdd.point.service.PointUpsertServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PointUpsertServiceTest {

    @InjectMocks
    private PointUpsertServiceImpl pointUpsertService;
    @Mock
    private UserPointTable userPointTable;
    @Mock
    private PointHistoryServiceImpl pointHistoryService;

    @DisplayName("point값과 amount값을 합칠때 Long.MAX_VALUE 초과할 경우 충전 실패")
    @Test
    void shouldFailWhenSumOfPointAndAmountExceedsLongMaxValue() {
        // Given
        Long validId = 1L;
        Long validAmount = Long.MAX_VALUE;

        ChargePointCommand command = ChargePointCommand.builder()
                .id(validId)
                .amount(validAmount)
                .build();

        // When
        when(userPointTable.selectById(validId)).thenReturn(new UserPoint(validId, 100L, System.currentTimeMillis()));

        // Then
        assertThatThrownBy(() -> {
            pointUpsertService.charge(command);
        })
                .isInstanceOf(LongOverflowException.class)
                .hasMessage("long 값 범위를 초과했습니다.");

        verify(userPointTable, times(0)).insertOrUpdate(validId, 200L);
    }

    @DisplayName("포인트 충전 성공")
    @Test
    void shouldSucceedWhenChargingPoints() {
        // Given
        Long validId = 1L;
        Long validAmount = 100L;

        ChargePointCommand command = ChargePointCommand.builder()
                .id(validId)
                .amount(validAmount)
                .build();

        when(userPointTable.selectById(validId)).thenReturn(new UserPoint(validId, 100L, System.currentTimeMillis()));
        when(userPointTable.insertOrUpdate(anyLong(), anyLong())).thenReturn(new UserPoint(validId, 200L, System.currentTimeMillis()));

        // When
        UserPoint userPoint = pointUpsertService.charge(command);

        // Then
        assertThat(userPoint.point()).isEqualTo(200L);

        verify(userPointTable, times(1)).insertOrUpdate(validId, 200L);
        verify(pointHistoryService).insert(any(AddPointHistoryCommand.class));
    }

    @DisplayName("유저 포인트보다 사용할 포인트가 더 클 경우 포인트 사용 실패")
    @Test
    void shouldFailWhenUsingMorePointsThanUserHas() {
        // Given
        Long validId = 1L;
        Long validAmount = 100L;

        UsePointCommand command = UsePointCommand.builder()
                .id(validId)
                .amount(validAmount)
                .build();

        // When
        when(userPointTable.selectById(validId)).thenReturn(new UserPoint(validId, 0L, System.currentTimeMillis()));

        // Then
        assertThatThrownBy(() -> {
            pointUpsertService.use(command);
        })
                .isInstanceOf(NotEnoughPointsException.class)
                .hasMessage("포인트가 부족합니다.");

        verify(userPointTable, times(0)).insertOrUpdate(validId, 900L);
    }

    @DisplayName("유저 포인트보다 사용할 포인트가 더 작을 경우 포인트 사용 성공")
    @Test
    void shouldSucceedWhenUsingMorePointsThanUserHas() {
        // Given
        Long validId = 1L;
        Long validAmount = 100L;

        UsePointCommand command = UsePointCommand.builder()
                .id(validId)
                .amount(validAmount)
                .build();

        when(userPointTable.selectById(validId)).thenReturn(new UserPoint(validId, 1000L, System.currentTimeMillis()));
        when(userPointTable.insertOrUpdate(anyLong(), anyLong())).thenReturn(new UserPoint(validId, 900L, System.currentTimeMillis()));

        // When
        UserPoint userPoint = pointUpsertService.use(command);

        // Then
        assertThat(userPoint.point()).isEqualTo(900L);

        verify(userPointTable, times(1)).insertOrUpdate(validId, 900L);
        verify(pointHistoryService).insert(any(AddPointHistoryCommand.class));
    }

}
