package com.jechoi.a.lock.ui;


import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Scheduler {

    @Scheduled(cron = "* * * * * ?")
    // lockAtLeastFor 락을 유지하는 시간(스케줄러 주기보다 약간 짧게 지정하는 것이 좋다.)
    // lockAtMostFor 락이 소멸할 경우 유지되는 시간 (실제 작업에 소요되는 시간보다 길어야 함.)
    @SchedulerLock(name = "taskName")
    public void task() throws InterruptedException {
        //
        log.info("start task...");
        for (int i = 0; i < 10; i++) {
            log.info(i + "");
            Thread.sleep(1000);
        }
        log.info("end task...");
    }
}
