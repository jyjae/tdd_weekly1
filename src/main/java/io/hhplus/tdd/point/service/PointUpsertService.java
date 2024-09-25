package io.hhplus.tdd.point.service;

import io.hhplus.tdd.point.controller.ChargePointCommand;
import io.hhplus.tdd.point.domain.UserPoint;

public interface PointUpsertService {
    UserPoint charge(ChargePointCommand command);
}
