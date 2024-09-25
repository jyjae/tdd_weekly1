package io.hhplus.tdd.point.service;

import io.hhplus.tdd.common.ClockHolder;
import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.point.controller.AddPointHistoryCommand;
import io.hhplus.tdd.point.controller.FindPointHistoryCommand;
import io.hhplus.tdd.point.controller.PointHistoryController;
import io.hhplus.tdd.point.domain.PointHistory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PointHistoryServiceImpl implements PointHistoryService {

    private static final Logger log = LoggerFactory.getLogger(PointHistoryServiceImpl.class);
    private final PointHistoryTable pointHistoryTable;
    private final ClockHolder clockHolder;

    public PointHistoryServiceImpl(
            PointHistoryTable pointHistoryTable,
            ClockHolder clockHolder
    ) {
        this.pointHistoryTable = pointHistoryTable;
        this.clockHolder = clockHolder;
    }

    /**
     * 포인트 이력 삽입 로직
     * @param command - 포인트 이력 추가 명령을 담은 객체
     * @return - 삽입된 포인트 이력 정보
     */
    @Override
    public PointHistory insert(AddPointHistoryCommand command) {
        long currentTimeMillis = clockHolder.getMillis();
        PointHistory pointHistory = pointHistoryTable.insert(command.getUserId(), command.getAmount(), command.getType(), currentTimeMillis);

        log.info("Successfully inserted point history for user ID: {}, at time: {}",
            command.getUserId(), currentTimeMillis);

        return pointHistory;
    }

    /**
     * 특정 유저의 모든 포인트 이력을 조회하는 로직
     * @param command - 포인트 이력 조회 명령을 담은 객체
     * @return - 조회된 포인트 이력 리스트
     */
    @Override
    public List<PointHistory> selectAll(FindPointHistoryCommand command) {
        return pointHistoryTable.selectAllByUserId(command.getId());
    }


}
