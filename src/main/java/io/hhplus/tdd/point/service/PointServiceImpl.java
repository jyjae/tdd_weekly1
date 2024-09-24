package io.hhplus.tdd.point.service;


import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.point.domain.UserPoint;
import io.hhplus.tdd.point.controller.FindUserPointCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PointServiceImpl implements PointService {

    private final UserPointTable userPointTable;

    @Autowired
    public PointServiceImpl(UserPointTable userPointTable) {
        this.userPointTable = userPointTable;
    }


    @Override
    public UserPoint getPoint(FindUserPointCommand command) {

        return userPointTable.selectById(command.getId());
    }
}
