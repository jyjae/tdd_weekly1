package io.hhplus.tdd.point;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.hhplus.tdd.exception.InvalidRequestException;
import io.hhplus.tdd.point.controller.FindPointCommand;
import io.hhplus.tdd.point.controller.FindPointHistoryCommand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FindPointHistoryCommandTest {

    @DisplayName("유효한 id로 FindPointHistoryCommand 생성")
    @Test
    void shouldSucceedWhenCreatingFindPointHistoryCommandWithValidId() {
        // Given
        Long validId = 1L;

        // When
        FindPointHistoryCommand command = FindPointHistoryCommand.toDto(validId);

        // Then
        assertNotNull(command);
        assertEquals(validId, command.getId());
    }

    @DisplayName("음수 id로 FindPointHistoryCommand 생성")
    @Test
    void shouldFailWhenCreatingFindPointHistoryCommandWithNegativeId() {
        // Given
        Long invalidId = -1L;

        // When
        InvalidRequestException exception = assertThrows(InvalidRequestException.class, () -> {
            FindPointHistoryCommand.toDto(invalidId);
        });

        // Then
        assertEquals("ID는 0이상입니다.", exception.getMessage());
    }

    @DisplayName("null id로 FindPointHistoryCommand 생성")
    @Test
    void shouldFailWhenCreatingFindPointHistoryCommandWithNullId() {
        // When
        InvalidRequestException exception = assertThrows(InvalidRequestException.class, () -> {
            FindPointHistoryCommand.toDto(null);
        });

        // Then
        assertEquals("ID는 null일 수 없습니다.", exception.getMessage());
    }
}
