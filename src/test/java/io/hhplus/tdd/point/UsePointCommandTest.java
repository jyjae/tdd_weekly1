package io.hhplus.tdd.point;

import io.hhplus.tdd.exception.InvalidRequestException;
import io.hhplus.tdd.point.controller.ChargePointCommand;
import io.hhplus.tdd.point.controller.UsePointCommand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class UsePointCommandTest {


    @DisplayName("유효한 UsePointCommand 생성")
    @Test
    void shouldSucceedWhenCreatingValidUsePointCommand() {
        // Given
        Long validId = 1L;
        Long validAmount = 1000L;

        // When
        UsePointCommand command = UsePointCommand.builder()
                .id(validId)
                .amount(validAmount)
                .build();

        // Then
        assertThat(command.getId()).isEqualTo(validId);
        assertThat(command.getAmount()).isEqualTo(validAmount);
    }

    @DisplayName("음수 id로 UsePointCommand 생성")
    @Test
    void shouldFailWhenCreatingUsePointCommandWithNegativeId() {
        // Given
        Long invalidId = -1L;
        Long validAmount = 1000L;

        // When & Then
        assertThatThrownBy(() -> {
            UsePointCommand.builder().id(invalidId).amount(validAmount).build();
        })
                .isInstanceOf(InvalidRequestException.class)
                .hasMessage("ID는 0이상입니다.");
    }

    @DisplayName("null id로 UsePointCommand 생성")
    @Test
    void shouldFailWhenCreatingUsePointCommandWithNullId() {
        // Given
        Long validAmount = 1000L;

        // When & Then
        assertThatThrownBy(() -> {
            UsePointCommand.builder().id(null).amount(validAmount).build();
        })
                .isInstanceOf(InvalidRequestException.class)
                .hasMessage("ID는 null일 수 없습니다.");
    }

    @DisplayName("null amount로 UsePointCommand 생성")
    @Test
    void shouldFailWhenCreatingUsePointCommandWithNullAmount() {
        // Given
        Long validId = 1L;

        // When & Then
        assertThatThrownBy(() -> {
            UsePointCommand.builder().id(validId).amount(null).build();
        })
                .isInstanceOf(InvalidRequestException.class)
                .hasMessage("amount는 null일 수 없습니다.");
    }
    @DisplayName("Long 최대값 초과된 amount로 UsePointCommand 생성")
    @Test
    void shouldFailWhenCreatingUsePointCommandWithAmountExceedingLongMaxValue() {
        // Given
        Long validId = 1L;
        Long inValidAmount = Long.MAX_VALUE+1L;

        // When & Then
        assertThatThrownBy(() -> {
            UsePointCommand.builder().id(validId).amount(inValidAmount).build();
        })
                .isInstanceOf(InvalidRequestException.class)
                .hasMessage("amount값이 너무 큽니다.");

    }
}
