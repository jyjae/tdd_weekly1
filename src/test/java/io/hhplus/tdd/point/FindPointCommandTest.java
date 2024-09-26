package io.hhplus.tdd.point;

import io.hhplus.tdd.exception.InvalidRequestException;
import io.hhplus.tdd.point.controller.FindPointCommand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FindPointCommandTest {

    @DisplayName("유효한 id로 FindUserPointCommand 생성")
    @Test
    void shouldSucceedWhenCreatingFindUserPointCommandWithValidId() {
        // Given
        Long validId = 1L;

        // When
        FindPointCommand command = FindPointCommand.toDto(validId);

        // Then
        assertNotNull(command);
        assertEquals(validId, command.getId());
    }

    @DisplayName("음수 id로 FindUserPointCommand 생성")
    @Test
    void shouldFailWhenCreatingFindUserPointCommandWithNegativeId() {
        // Given
        Long invalidId = -1L;

        // When
        InvalidRequestException exception = assertThrows(InvalidRequestException.class, () -> {
            FindPointCommand.toDto(invalidId);
        });

        // Then
        assertEquals("ID는 0이상입니다.", exception.getMessage());
    }

    @DisplayName("null id로 FindUserPointCommand 생성")
    @Test
    void shouldFailWhenCreatingFindUserPointCommandWithNullId() {
        // When
        InvalidRequestException exception = assertThrows(InvalidRequestException.class, () -> {
            FindPointCommand.toDto(null);
        });

        // Then
        assertEquals("ID는 null일 수 없습니다.", exception.getMessage());
    }
}
