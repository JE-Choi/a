package com.jechoi.a.thread;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.function.Consumer;


@Slf4j
class QueueCapacityTest {

    @Test
    void test() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setQueueCapacity(3);
        executor.setMaxPoolSize(10);
        executor.setThreadNamePrefix("ASYNC-");
        executor.initialize();

        final Consumer<Integer> r = (v) -> {
            try {
                log.info(Thread.currentThread().getName() + "(" + v + ")" + "start ...");
                Thread.sleep(5000);
                log.info(String.format("pool size: %d, active count: %d, queue size: %d", executor.getPoolSize(), executor.getActiveCount(), executor.getThreadPoolExecutor().getQueue().size()));
                log.info(Thread.currentThread().getName() + "(" + v + ")" + "end ...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        for (int i = 0; i < 13; i++) {
            final int finalI = i;
            executor.execute(() -> r.accept(finalI));
        }

        /**
         * 비동기 작업 모두 끊날때까지 대기
         */
        try {
            Thread.sleep(10000 * 15);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        /**
         * 실행결과
         * -- QueueCapacity 세팅한 값까지 큐에 쌓이고 대기한다, 큐에 쌓였던 작업의 다음! 작업이 쓰레드가 maxPool만큼 생성되면서 처리되고
         * -- idle인 쓰레드가 생겨야 큐에 있는 작업을 꺼내와서 작업한다.
         */
        // 첫시도
        // 일단 실행됨:  4, 1, 3, 0, 8, 2, 9, 10, 11, 12
        // 나중에 실행됨: 6, 7, 5

        // 두번째 시도
        // 일단 실행됨:  1, 2, 0, 3, 4, 8, 9, 10, 11, 12
        // 나중에 실행됨: 5, 6, 7

        // 세번째 시도
        // 일단 실행됨: 2, 8, 1, 4, 3, 0, 9, 10, 11, 12
        // 나중에 실행됨: 5, 6, 7
    }
}
