package io.hhplus.tdd.point;

import io.hhplus.tdd.TestClockHolder;
import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.point.constant.TransactionType;
import io.hhplus.tdd.point.controller.AddPointHistoryCommand;
import io.hhplus.tdd.point.controller.FindPointHistoryCommand;
import io.hhplus.tdd.point.domain.PointHistory;
import io.hhplus.tdd.point.service.PointHistoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PointHistoryServiceTest {

    @Mock
    private PointHistoryTable pointHistoryTable;

    private PointHistoryServiceImpl pointHistoryService;
    private Clock clock;
    private List<PointHistory> expectedHistories;

    @BeforeEach
    void setUp() {
        clock = Clock.fixed(Instant.parse("2000-01-01T00:00:00.00Z"), ZoneId.of("UTC"));
        pointHistoryService = new PointHistoryServiceImpl(pointHistoryTable, new TestClockHolder(clock));

        Long validId = 1L;
        Long validAmount1 = 100L;
        Long validAmount2 = 200L;

        // 여러 개의 PointHistory 생성
        PointHistory history1 = new PointHistory(1L, validId, validAmount1, TransactionType.CHARGE, 946684800000L);
        PointHistory history2 = new PointHistory(2L, validId, validAmount2, TransactionType.CHARGE, 946684800100L);

        expectedHistories = Arrays.asList(history1, history2);
    }

    @DisplayName("포인트 히스토리 저장 성공")
    @Test
    void shouldSucceedWhenSavingPointHistoryOnPointCharge() {
        // Given
        Long validId = 1L;
        Long validAmount = 100L;

        AddPointHistoryCommand command = AddPointHistoryCommand.builder()
                .userId(validId)
                .amount(validAmount)
                .type(TransactionType.CHARGE)
                .build();

        when(pointHistoryTable.insert(validId, validAmount, TransactionType.CHARGE, 946684800000L)).thenReturn(expectedHistories.get(0));

        // When
        PointHistory pointHistory = pointHistoryService.insert(command);

        // Then
        assertThat(pointHistory.id()).isEqualTo(1L);
        assertThat(pointHistory.userId()).isEqualTo(validId);
        assertThat(pointHistory.amount()).isEqualTo(validAmount);
        assertThat(pointHistory.type()).isEqualTo(TransactionType.CHARGE);

        verify(pointHistoryTable).insert(command.getUserId(), command.getAmount(), command.getType(),  946684800000L);
    }

    @DisplayName("포인트 히스토리 조회")
    @Test
    void shouldSucceedWhenGetPointHistory() {
        // Given
        Long validId = 1L;

        FindPointHistoryCommand command = FindPointHistoryCommand.builder()
                .id(validId)
                .build();
        when(pointHistoryTable.selectAllByUserId(validId)).thenReturn(expectedHistories);

        // When
        List<PointHistory> pointHistories = pointHistoryService.selectAll(command);

        // Then
        assertThat(pointHistories).hasSize(2);
        assertThat(pointHistories).containsExactlyInAnyOrderElementsOf(expectedHistories);

        verify(pointHistoryTable).selectAllByUserId(validId);
    }
}
