package io.hhplus.tdd.point.domain;

import io.hhplus.tdd.exception.LongOverflowException;
import io.hhplus.tdd.exception.NotEnoughPointsException;

public record UserPoint(
        long id,
        long point,
        long updateMillis
) {

    public static UserPoint empty(long id) {
        return new UserPoint(id, 0, System.currentTimeMillis());
    }

    public long addPoint(long amount) {
        if (point > 0 && amount > Long.MAX_VALUE - point) {
            throw new LongOverflowException("long 값 범위를 초과했습니다.");
        }

        return point + amount;
    }

    public long usePoint(Long amount) {
        if (point < amount) {
            throw new NotEnoughPointsException("포인트가 부족합니다.");
        }

        return point - amount;
    }
}
