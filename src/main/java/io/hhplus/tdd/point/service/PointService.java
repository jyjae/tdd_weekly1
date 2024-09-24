package io.hhplus.tdd.point.service;

import io.hhplus.tdd.point.domain.UserPoint;
import io.hhplus.tdd.point.controller.FindUserPointCommand;

public interface PointService {
    UserPoint getPoint(FindUserPointCommand command);
}
