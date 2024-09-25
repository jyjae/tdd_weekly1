package io.hhplus.tdd.point;

import io.hhplus.tdd.exception.InvalidRequestException;
import io.hhplus.tdd.point.controller.ChargePointCommand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ChargePointCommandTest {


    @DisplayName("유효한 ChargePointCommand 생성")
    @Test
    void shouldSucceedWhenCreatingValidChargePointCommand() {
        // Given
        Long validId = 1L;
        Long validAmount = 1000L;

        // When
        ChargePointCommand command = ChargePointCommand.toDto(validId, validAmount);

        // Then
        assertThat(command.getId()).isEqualTo(validId);
        assertThat(command.getAmount()).isEqualTo(validAmount);
    }

    @DisplayName("음수 id로 ChargePointCommand 생성")
    @Test
    void shouldFailWhenCreatingChargePointCommandWithNegativeId() {
        // Given
        Long invalidId = -1L;
        Long validAmount = 1000L;

        // When & Then
        assertThatThrownBy(() -> {
            ChargePointCommand.toDto(invalidId, validAmount);
        })
                .isInstanceOf(InvalidRequestException.class)
                .hasMessage("ID는 0이상입니다.");
    }

    @DisplayName("null id로 ChargePointCommand 생성")
    @Test
    void shouldFailWhenCreatingChargePointCommandWithNullId() {
        // Given
        Long validAmount = 1000L;

        // When & Then
        assertThatThrownBy(() -> {
            ChargePointCommand.toDto(null, validAmount);
        })
                .isInstanceOf(InvalidRequestException.class)
                .hasMessage("ID는 null일 수 없습니다.");
    }

    @DisplayName("null amount로 ChargePointCommand 생성")
    @Test
    void shouldFailWhenCreatingChargePointCommandWithNullAmount() {
        // Given
        Long validId = 1L;

        // When & Then
        assertThatThrownBy(() -> {
            ChargePointCommand.toDto(validId, null);
        })
                .isInstanceOf(InvalidRequestException.class)
                .hasMessage("amount는 null일 수 없습니다.");
    }
    @DisplayName("Long 최대값 초과된 amount로 ChargePointCommand 생성")
    @Test
    void shouldFailWhenCreatingChargePointCommandWithAmountExceedingLongMaxValue() {
        // Given
        Long validId = 1L;
        Long inValidAmount = Long.MAX_VALUE+1L;

        // When & Then
        assertThatThrownBy(() -> {
            ChargePointCommand.toDto(validId, inValidAmount);
        })
                .isInstanceOf(InvalidRequestException.class)
                .hasMessage("amount값이 너무 큽니다.");

    }
}
