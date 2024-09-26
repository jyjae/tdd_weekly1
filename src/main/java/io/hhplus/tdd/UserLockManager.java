package io.hhplus.tdd;

import io.hhplus.tdd.exception.PointCommandException;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class UserLockManager {

    // 유저별로 Lock 객체를 저장하는 ConcurrentHashMap
    private final Map<Long, Lock> userLocks = new ConcurrentHashMap<>();

    // 유저별 Lock을 관리
    public Lock getLockForUser(Long userId) {
        // 유저별로 고유한 Lock을 제공
        return userLocks.computeIfAbsent(userId, id -> new ReentrantLock());
    }

    // Callable<T>을 사용하여 리턴 값을 처리할 수 있게 수정
    public <T> T executeWithLock(Long userId, Callable<T> task) {
        Lock lock = getLockForUser(userId);
        lock.lock();
        try {
            return task.call();  // 락이 걸린 상태에서 요청 처리 및 리턴 값 반환
        } catch (Exception e) {
            // 예외 발생 시 로깅 또는 추가 처리
            throw new PointCommandException("작업 실패");  // 예외를 래핑해서 던질 수도 있음
        } finally {
            lock.unlock();  // 락 해제
        }
    }
}

