# 동시성 제어 방식 분석 보고서

## 1. 동시성 제어의 필요성

포인트 충전 및 사용 기능은 유저별로 별도의 동시성 관리가 필요합니다. 여러 유저가 동시에 포인트를 충전하거나 사용할 경우, 다음과 같은 문제가 발생할 수 있습니다.

- **데이터 일관성 문제**: 동일한 유저가 동시에 여러 요청을 처리할 때 데이터가 잘못 업데이트되는 문제.
- **경쟁 조건**: 다수의 스레드가 동일한 자원에 접근하면서 발생하는 예상치 못한 결과.

따라서 유저별로 락을 적용하여 해당 유저의 요청이 순차적으로 처리되도록 하는 것이 중요합니다.

---

## 2. 구현 방식

본 프로젝트에서는 **`ReentrantLock`**을 이용해 유저별 동시성 문제를 해결했습니다. 이를 위한 핵심 클래스는 `UserLockManager`와 `PointUpsertServiceImpl`입니다.

### 2.1. `UserLockManager`

`UserLockManager` 클래스는 유저별로 고유한 락을 제공하며, 락을 관리하는 역할을 합니다.

- **`ConcurrentHashMap<Long, Lock> userLocks`**: 유저 ID를 키로 하는 `ReentrantLock` 객체를 저장하는 맵. 이를 통해 유저별로 고유한 락을 제공.
- **`executeWithLock`**: 락을 획득한 후, 지정된 작업을 수행하는 메서드. 락은 명시적으로 해제되며, 예외가 발생하더라도 `finally` 블록에서 락이 해제됩니다.

### 2.2. `PointUpsertServiceImpl`

이 클래스는 실제 포인트 충전 및 사용 비즈니스 로직을 처리하는 서비스 계층입니다. 여기서도 유저별로 `UserLockManager`를 통해 동시성을 제어합니다.

- **포인트 충전 (`charge`)**: 특정 유저에 대해 포인트를 충전하는 비즈니스 로직을 수행하며, 유저별 락을 이용해 동시성을 관리.
- **포인트 사용 (`use`)**: 유저의 포인트 사용 요청을 처리하며, 락을 통해 여러 요청이 중복되지 않도록 합니다.

이러한 방식은 **동시성 제어**를 통해 하나의 유저가 여러 요청을 동시에 보내더라도 포인트 충전 및 사용이 안전하게 처리되도록 보장합니다.

---

## 3. 테스트 및 검증

### 3.1. 테스트 환경

- **동시성 포인트 충전 성공 테스트**
  - 여러 유저가 동시에 10개의 포인트 충전 요청을 보냅니다.
  - 각 유저의 100개의 요청이 각각 10포인트씩 충전되는지 검증.
  - 기대 결과: 유저당 1000포인트 충전.

- **동시성 포인트 사용 성공 테스트**
  - 각 유저에게 1000포인트를 충전한 후, 10개의 포인트 사용 요청을 동시에 처리합니다.
  - 기대 결과: 각 유저의 포인트가 900포인트로 정확히 감소.

### 3.2. 테스트 결과

- **포인트 충전 테스트**: 각 유저당 100개의 포인트 충전 요청이 정확하게 1000포인트씩 누적되었음을 확인.
- **포인트 사용 테스트**: 1000포인트에서 각각 10포인트씩 10번 사용된 후 900포인트로 정확하게 감소함을 확인.

---

## 4. `synchronized`와 `ReentrantLock` 비교

`ReentrantLock`은 `synchronized`에 비해 여러 장점이 있습니다. 본 프로젝트에서 `ReentrantLock`을 선택한 이유는 다음과 같습니다.

- **락 해제의 유연성**: `ReentrantLock`은 명시적으로 락을 해제할 수 있어, 복잡한 비즈니스 로직에서 더욱 세밀한 제어가 가능합니다.
- **공정성**: `ReentrantLock`은 락을 요청한 순서대로 처리하는 공정성 옵션을 제공할 수 있습니다.
- **타임아웃 설정**: `ReentrantLock`을 사용하면 락 획득 시 타임아웃을 설정할 수 있어, 무한 대기 상태를 방지할 수 있습니다.
- **조건 대기**: `ReentrantLock`은 조건 대기(Condition)를 지원하여, 복잡한 동시성 흐름을 더욱 쉽게 관리할 수 있습니다.

---

## 5. 결론

본 프로젝트에서 적용된 동시성 제어 방식은 `ReentrantLock`을 이용해 유저별로 고유한 락을 제공함으로써 동시성 문제를 해결하였습니다.

