package io.hhplus.tdd.point;

import io.hhplus.tdd.TestClockHolder;
import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.point.constant.TransactionType;
import io.hhplus.tdd.point.controller.AddPointHistoryCommand;
import io.hhplus.tdd.point.service.PointHistoryServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PointHistoryServiceTest {

    @Mock
    private PointHistoryTable pointHistoryTable;

    @DisplayName("포인트 히스토리 저장 성공")
    @Test
    void shouldSucceedWhenSavingPointHistoryOnPointCharge() {

        // Given
        Long validId = 1L;
        Long validAmount = 100L;

        Clock clock = Clock.fixed(Instant.parse("2000-01-01T00:00:00.00Z"), ZoneId.of("UTC"));
        PointHistoryServiceImpl pointHistoryService = new PointHistoryServiceImpl(pointHistoryTable, new TestClockHolder(clock));


        AddPointHistoryCommand command = AddPointHistoryCommand.builder()
                .userId(validId)
                .amount(validAmount)
                .type(TransactionType.CHARGE)
                .build();

        // When
        pointHistoryService.insert(command);

        // Then
        verify(pointHistoryTable).insert(command.getUserId(), command.getAmount(), command.getType(),  946684800000L);
    }
}
