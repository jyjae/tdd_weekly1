package io.hhplus.tdd.point.controller;

import io.hhplus.tdd.point.service.PointService;
import io.hhplus.tdd.point.domain.UserPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/point")
public class PointController {

    private static final Logger log = LoggerFactory.getLogger(PointController.class);

    private final PointService pointService;

    public PointController(PointService pointService) {
        this.pointService = pointService;
    }

    /**
     * 특정 유저의 포인트를 조회하는 API
     * @param id - 유저 ID
     * @return - 유저의 포인트 정보를 담은 ResponseEntity
     */
    @GetMapping("{id}")
    public ResponseEntity<UserPoint> point(
            @PathVariable long id
    ) {
        log.info("Received request to fetch points for user ID: {}", id);
        return ResponseEntity.ok(pointService.getPoint(FindPointCommand.toDto(id)));
    }

}
