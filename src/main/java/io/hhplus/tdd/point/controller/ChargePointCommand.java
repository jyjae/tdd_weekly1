package io.hhplus.tdd.point.controller;

import io.hhplus.tdd.exception.InvalidRequest;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ChargePointCommand {
    private Long id;
    private Long amount;

    @Builder
    public ChargePointCommand(Long id, Long amount) {
        if (id == null) {
            throw new InvalidRequest("ID는 null일 수 없습니다.");
        }
        if (id < 0) {
            throw new InvalidRequest("ID는 0이상입니다.");
        }
        if (amount == null) {
            throw new InvalidRequest("amount는 null일 수 없습니다.");
        }
        if (Long.MAX_VALUE - amount < 0 ) {
            throw new InvalidRequest("amount값이 너무 큽니다.");
        }
        this.id = id;
        this.amount = amount;
    }
}