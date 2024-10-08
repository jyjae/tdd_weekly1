package io.hhplus.tdd.point.controller;

import io.hhplus.tdd.exception.InvalidRequestException;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FindPointCommand {
    private Long id;

    @Builder
    public FindPointCommand(Long id) {
        if (id == null) {
            throw new InvalidRequestException("ID는 null일 수 없습니다.");
        }
        if (id < 0) {
            throw new InvalidRequestException("ID는 0이상입니다.");
        }
        this.id = id;
    }

    public static FindPointCommand toDto(Long id) {
        return FindPointCommand.builder()
                .id(id)
                .build();
    }
}