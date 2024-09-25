package io.hhplus.tdd.point.controller;

import io.hhplus.tdd.point.service.PointService;
import io.hhplus.tdd.point.domain.UserPoint;
import io.hhplus.tdd.point.service.PointUpsertService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/point")
public class PointUpsertController {

    private static final Logger log = LoggerFactory.getLogger(PointUpsertController.class);

    private final PointUpsertService pointUpsertService;

    public PointUpsertController(PointUpsertService pointUpsertService) {
        this.pointUpsertService = pointUpsertService;
    }

    /**
     * TODO - 특정 유저의 포인트를 충전하는 기능을 작성해주세요.
     */
    @PatchMapping("{id}/charge")
    public ResponseEntity<UserPoint> charge(
            @PathVariable long id,
            @RequestBody long amount
    ) {

        ChargePointCommand command = ChargePointCommand.builder()
                .id(id)
                .amount(amount)
                .build();

        return ResponseEntity.ok(pointUpsertService.charge(command));
    }

    /**
     * TODO - 특정 유저의 포인트를 사용하는 기능을 작성해주세요.
     */
    @PatchMapping("{id}/use")
    public ResponseEntity<UserPoint> use(
            @PathVariable long id,
            @RequestBody long amount
    ) {

        UsePointCommand command = UsePointCommand.builder()
                .id(id)
                .amount(amount)
                .build();

        return ResponseEntity.ok(pointUpsertService.use(command));
    }
}
