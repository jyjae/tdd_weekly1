package io.hhplus.tdd.point.service;

import io.hhplus.tdd.point.controller.AddPointHistoryCommand;

public interface PointHistoryService {
    void insert(AddPointHistoryCommand command);
}
