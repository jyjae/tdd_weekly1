package io.hhplus.tdd.point.controller;

import io.hhplus.tdd.exception.InvalidRequestException;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UsePointCommand {
    private Long id;
    private Long amount;

    @Builder
    public UsePointCommand(Long id, Long amount) {
        if (id == null) {
            throw new InvalidRequestException("ID는 null일 수 없습니다.");
        }
        if (id < 0) {
            throw new InvalidRequestException("ID는 0이상입니다.");
        }
        if (amount == null) {
            throw new InvalidRequestException("amount는 null일 수 없습니다.");
        }
        if (Long.MAX_VALUE - amount < 0 ) {
            throw new InvalidRequestException("amount값이 너무 큽니다.");
        }
        this.id = id;
        this.amount = amount;
    }
}