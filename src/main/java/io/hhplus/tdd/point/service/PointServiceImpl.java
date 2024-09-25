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


    @Override
    public UserPoint getPoint(FindPointCommand command) {

        return userPointTable.selectById(command.getId());
    }

    @Override
    public UserPoint getPointById(Long id) {

        return userPointTable.selectById(id);
    }
}
