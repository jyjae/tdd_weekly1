package io.hhplus.tdd.point.service;

import io.hhplus.tdd.UserLockManager;
import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.exception.PointCommandException;
import io.hhplus.tdd.point.constant.TransactionType;
import io.hhplus.tdd.point.controller.AddPointHistoryCommand;
import io.hhplus.tdd.point.controller.ChargePointCommand;
import io.hhplus.tdd.point.controller.UsePointCommand;
import io.hhplus.tdd.point.domain.UserPoint;
import org.springframework.stereotype.Service;

@Service
public class PointUpsertServiceImpl implements PointUpsertService {

    private final UserPointTable userPointTable;
    private final PointHistoryService pointHistoryService;
    private final UserLockManager userLockManager;

    public PointUpsertServiceImpl(
            UserPointTable userPointTable,
            PointHistoryService pointHistoryService,
            UserLockManager userLockManager
    ) {
        this.userPointTable = userPointTable;
        this.pointHistoryService = pointHistoryService;
        this.userLockManager = userLockManager;
    }

    @Override
    public UserPoint charge(ChargePointCommand command) {
            // 유저별 Lock을 사용하여 요청 처리
            return userLockManager.executeWithLock(command.getId(), () -> {
                UserPoint userPoint = userPointTable.selectById(command.getId());
                userPoint = userPointTable.insertOrUpdate(command.getId(), userPoint.addPoint(command.getAmount()));

                AddPointHistoryCommand historyCommand = AddPointHistoryCommand.builder()
                        .type(TransactionType.CHARGE)
                        .amount(command.getAmount())
                        .userId(command.getId()).build();

                pointHistoryService.insert(historyCommand);

                return userPoint;
            });
    }

    @Override
    public UserPoint use(UsePointCommand command) {
            // 유저별 Lock을 사용하여 요청 처리
            return userLockManager.executeWithLock(command.getId(), () -> {
                UserPoint userPoint = userPointTable.selectById(command.getId());
                userPoint = userPointTable.insertOrUpdate(command.getId(), userPoint.usePoint(command.getAmount()));

                AddPointHistoryCommand historyCommand = AddPointHistoryCommand.builder()
                        .type(TransactionType.USE)
                        .amount(command.getAmount())
                        .userId(command.getId()).build();

                pointHistoryService.insert(historyCommand);

                return userPoint;
            });
    }
}
