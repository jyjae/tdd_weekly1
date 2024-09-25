package io.hhplus.tdd.common;

import org.springframework.stereotype.Component;

@Component
public class SystemClockHolder implements ClockHolder {
    @Override
    public long getMillis() {
        return System.currentTimeMillis();
    }
}