package io.hhplus.tdd.point.service;

import io.hhplus.tdd.point.controller.FindPointCommand;
import io.hhplus.tdd.point.domain.UserPoint;
import org.springframework.stereotype.Service;

@Service
public class PointManageService {
    private final PointService pointService;
    private final PointUpsertService pointUpsertService;


    public PointManageService(PointService pointService, PointUpsertService pointUpsertService) {
        this.pointService = pointService;
        this.pointUpsertService = pointUpsertService;
    }

    public UserPoint getPoint(Long id) {
        FindPointCommand command = FindPointCommand.builder()
                .id(id)
                .build();

        return pointService.getPoint(command);
    }


}
