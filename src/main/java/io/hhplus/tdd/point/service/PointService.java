package io.hhplus.tdd.point.service;

import io.hhplus.tdd.point.domain.UserPoint;
import io.hhplus.tdd.point.controller.FindPointCommand;

public interface PointService {
    UserPoint getPoint(FindPointCommand command);

    UserPoint getPointById(Long id);
}
