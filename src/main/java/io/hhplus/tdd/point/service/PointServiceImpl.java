package io.hhplus.tdd.point.service;


import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.point.domain.UserPoint;
import io.hhplus.tdd.point.controller.FindPointCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PointServiceImpl implements PointService {

    private final UserPointTable userPointTable;

    @Autowired
    public PointServiceImpl(UserPointTable userPointTable) {
        this.userPointTable = userPointTable;
    }

    /**
     * 특정 유저의 포인트 정보를 조회하는 메서드
     * @param command - 유저 ID가 포함된 FindPointCommand 객체
     * @return - 조회된 유저 포인트 정보 (UserPoint 객체)
     */
    @Override
    public UserPoint getPoint(FindPointCommand command) {

        return userPointTable.selectById(command.getId());
    }

    /**
     * 특정 유저 ID를 기반으로 포인트 정보를 조회하는 메서드
     * @param id - 유저 ID
     * @return - 조회된 유저 포인트 정보 (UserPoint 객체)
     */
    @Override
    public UserPoint getPointById(Long id) {

        return userPointTable.selectById(id);
    }
}
