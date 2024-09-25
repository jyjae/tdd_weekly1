package io.hhplus.tdd.point.controller;

import io.hhplus.tdd.point.constant.TransactionType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AddPointHistoryCommand {
    private Long userId;
    private Long amount;
    private TransactionType type;

    @Builder
    public AddPointHistoryCommand(Long userId, Long amount, TransactionType type) {
        this.userId = userId;
        this.amount = amount;
        this.type = type;
    }
}
