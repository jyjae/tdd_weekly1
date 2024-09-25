package io.hhplus.tdd.point.service;

import io.hhplus.tdd.common.ClockHolder;
import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.point.controller.AddPointHistoryCommand;
import io.hhplus.tdd.point.controller.FindPointHistoryCommand;
import io.hhplus.tdd.point.domain.PointHistory;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public PointHistory insert(AddPointHistoryCommand command) {
        return pointHistoryTable.insert(command.getUserId(), command.getAmount(), command.getType(), clockHolder.getMillis());
    }

    @Override
    public List<PointHistory> selectAll(FindPointHistoryCommand command) {
        return pointHistoryTable.selectAllByUserId(command.getId());
    }


}
