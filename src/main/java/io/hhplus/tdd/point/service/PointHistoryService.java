package io.hhplus.tdd.point.service;

import io.hhplus.tdd.point.controller.AddPointHistoryCommand;
import io.hhplus.tdd.point.controller.FindPointHistoryCommand;
import io.hhplus.tdd.point.domain.PointHistory;

import java.util.List;

public interface PointHistoryService {
    PointHistory insert(AddPointHistoryCommand command);


    List<PointHistory> selectAll(FindPointHistoryCommand command);
}
