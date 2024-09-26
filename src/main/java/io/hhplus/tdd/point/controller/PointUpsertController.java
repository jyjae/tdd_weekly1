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
   * 특정 유저의 포인트를 충전하는 API
   * @param id - 유저 ID
   * @param amount - 충전할 포인트 금액
   * @return - 충전된 포인트 정보
   */
  @PatchMapping("{id}/charge")
  public ResponseEntity<UserPoint> charge(
      @PathVariable long id,
      @RequestBody long amount
  ) {
    log.info("Charging points for user with ID: {}, amount: {}", id, amount);
    return ResponseEntity.ok(pointUpsertService.charge(ChargePointCommand.toDto(id, amount)));
  }

  /**
   * 특정 유저의 포인트를 사용하는 API
   * @param id - 유저 ID
   * @param amount - 사용할 포인트 금액
   * @return - 사용된 포인트 정보
   */
  @PatchMapping("{id}/use")
  public ResponseEntity<UserPoint> use(
      @PathVariable long id,
      @RequestBody long amount
  ) {
    log.info("Using points for user with ID: {}, amount: {}", id, amount);
    return ResponseEntity.ok(pointUpsertService.use(UsePointCommand.toDto(id, amount)));
  }
}
