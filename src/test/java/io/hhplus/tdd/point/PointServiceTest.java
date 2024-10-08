package io.hhplus.tdd.point;

import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.point.controller.FindPointCommand;
import io.hhplus.tdd.point.domain.UserPoint;
import io.hhplus.tdd.point.service.PointServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PointServiceTest {

    @InjectMocks
    private PointServiceImpl pointService;
    @Mock
    private UserPointTable userPointTable;

    @DisplayName("포인트 조회")
    @Test
    void shouldSucceedWhenRetrievingPoints() {
        // given
        FindPointCommand command = FindPointCommand.toDto(1L);

        // when
        when(userPointTable.selectById(command.getId())).thenReturn(UserPoint.empty(command.getId()));
        UserPoint userPoint = pointService.getPoint(command);

        // then
        assertNotNull(userPoint);
        assertEquals(1L, userPoint.id());
        assertEquals(0L, userPoint.point());
    }


}

