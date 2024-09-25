package io.hhplus.tdd.point.controller;

import io.hhplus.tdd.point.domain.PointHistory;
import io.hhplus.tdd.point.service.PointHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/point")
public class PointHistoryController {
    private static final Logger log = LoggerFactory.getLogger(PointHistoryController.class);
    private final PointHistoryService pointHistoryService;

    public PointHistoryController(PointHistoryService pointHistoryService) {
        this.pointHistoryService = pointHistoryService;
    }

    /**
     * 특정 유저의 포인트 충전/이용 내역을 조회하는 API
     * @param id - 유저 ID
     * @return - 포인트 내역 리스트를 담은 ResponseEntity
     */
    @GetMapping("{id}/histories")
    public ResponseEntity<List<PointHistory>> history(
            @PathVariable long id
    ) {
        log.info("Received request to fetch point histories for user ID: {}", id);

        return ResponseEntity.ok(pointHistoryService.selectAll(FindPointHistoryCommand.toDto(id)));
    }
}
