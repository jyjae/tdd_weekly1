package io.hhplus.tdd.point.service;

import io.hhplus.tdd.common.ClockHolder;
import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.point.controller.AddPointHistoryCommand;
import org.springframework.stereotype.Service;

@Service
public class PointHistoryServiceImpl implements PointHistoryService {

    private final PointHistoryTable pointHistoryTable;
    private final ClockHolder clockHolder;

    public PointHistoryServiceImpl(
            PointHistoryTable pointHistoryTable,
            ClockHolder clockHolder
    ) {
        this.pointHistoryTable = pointHistoryTable;
        this.clockHolder = clockHolder;
    }

    @Override
    public void insert(AddPointHistoryCommand command) {
        pointHistoryTable.insert(command.getUserId(), command.getAmount(), command.getType(), clockHolder.getMillis());
    }


}
