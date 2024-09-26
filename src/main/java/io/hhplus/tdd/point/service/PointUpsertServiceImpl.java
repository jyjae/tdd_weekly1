package io.hhplus.tdd.point.service;

import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.point.constant.TransactionType;
import io.hhplus.tdd.point.controller.AddPointHistoryCommand;
import io.hhplus.tdd.point.controller.ChargePointCommand;
import io.hhplus.tdd.point.controller.PointUpsertController;
import io.hhplus.tdd.point.controller.UsePointCommand;
import io.hhplus.tdd.point.domain.UserPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PointUpsertServiceImpl implements PointUpsertService {

    private static final Logger log = LoggerFactory.getLogger(PointUpsertServiceImpl.class);
    private final UserPointTable userPointTable;
    private final PointHistoryService pointHistoryService;

    public PointUpsertServiceImpl(
            UserPointTable userPointTable,
            PointHistoryService pointHistoryService
    ) {
        this.userPointTable = userPointTable;
        this.pointHistoryService = pointHistoryService;
    }

    /**
     * 유저의 포인트 충전 로직
     * @param command - 충전 관련 정보가 담긴 Command 객체
     * @return - 업데이트된 유저 포인트 정보
     */
    @Override
    public UserPoint charge(ChargePointCommand command) {
        // 유저 포인트 조회
        UserPoint userPoint = userPointTable.selectById(command.getId());

        // 포인트 추가 및 업데이트
        userPoint = userPointTable.insertOrUpdate(command.getId(), userPoint.addPoint(command.getAmount()));

        // 충전 이력 생성 및 저장
        pointHistoryService.insert(AddPointHistoryCommand.toDtoForCharge(command));

        // 포인트 업데이트 후 상태 기록
        log.debug("Updated user point balance: {} for user ID: {}", userPoint.point(), command.getId());

        return userPoint;
    }

    /**
     * 유저의 포인트 사용 로직
     * @param command - 사용 관련 정보가 담긴 Command 객체
     * @return - 업데이트된 유저 포인트 정보
     */
    @Override
    public UserPoint use(UsePointCommand command) {
        // 유저 포인트 조회
        UserPoint userPoint = userPointTable.selectById(command.getId());

        // 포인트 사용 및 업데이트
        userPoint = userPointTable.insertOrUpdate(command.getId(), userPoint.usePoint(command.getAmount()));

        // 사용 이력 생성 및 저장
        pointHistoryService.insert(AddPointHistoryCommand.toDtoForUse(command));

        // 포인트 업데이트 후 상태 기록
        log.debug("Updated user point balance: {} for user ID: {}", userPoint.point(), command.getId());

        return userPoint;
    }
}
