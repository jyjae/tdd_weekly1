package io.hhplus.tdd;

import io.hhplus.tdd.common.ClockHolder;

import java.time.Clock;

public class TestClockHolder implements ClockHolder {
    private Clock clock;

    public TestClockHolder(Clock clock) {
        this.clock = clock;
    }

    @Override
    public long getMillis() {
        return clock.millis();
    }


}