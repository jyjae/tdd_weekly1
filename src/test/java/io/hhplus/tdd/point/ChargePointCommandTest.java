package io.hhplus.tdd.point;

import io.hhplus.tdd.exception.InvalidRequest;
import io.hhplus.tdd.point.controller.ChargePointCommand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChargePointCommandTest {


    @DisplayName("유효한 ChargePointCommand 생성")
    @Test
    void testValidId() {
        // Given
        Long validId = 1L;
        Long validAmount = 1000L;

        // When
        ChargePointCommand command = ChargePointCommand.builder()
                .id(validId)
                .amount(validAmount)
                .build();

        // Then
        assertNotNull(command);
        assertEquals(validId, command.getId());
        assertEquals(validAmount, command.getAmount());
    }

    @DisplayName("음수 id로 ChargePointCommand 생성")
    @Test
    void testInvalidIdThrowsException() {
        // Given
        Long invalidId = -1L;
        Long validAmount = 1000L;

        // When

        // Then
        InvalidRequest exception = assertThrows(InvalidRequest.class, () -> {
            ChargePointCommand.builder().id(invalidId).amount(validAmount).build();
        });
        assertEquals("ID는 0이상입니다.", exception.getMessage());
    }

    @DisplayName("null id로 ChargePointCommand 생성")
    @Test
    void testNullIdThrowsException() {
        // Given
        Long validAmount = 1000L;

        // When

        // Then
        InvalidRequest exception = assertThrows(InvalidRequest.class, () -> {
            ChargePointCommand.builder().id(null).amount(validAmount).build();
        });
        assertEquals("ID는 null일 수 없습니다.", exception.getMessage());
    }

    @DisplayName("null amount로 ChargePointCommand 생성")
    @Test
    void testNullAmountThrowsException() {
        // Given
        Long validId = 1L;

        // When

        // Then
        InvalidRequest exception = assertThrows(InvalidRequest.class, () -> {
            ChargePointCommand.builder().id(validId).amount(null).build();
        });
        assertEquals("amount는 null일 수 없습니다.", exception.getMessage());
    }
    @DisplayName("Long 최대값 초과된 amount로 ChargePointCommand 생성")
    @Test
    void testAmountThrowsException() {
        // Given
        Long validId = 1L;
        Long inValidAmount = Long.MAX_VALUE+1L;

        // When

        // Then
        InvalidRequest exception = assertThrows(InvalidRequest.class, () -> {
            ChargePointCommand.builder().id(validId).amount(inValidAmount).build();
        });
        assertEquals("amount값이 너무 큽니다.", exception.getMessage());
    }
}
