package com.jechoi.a.lock.ui;


import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class Scheduler {
    private static final String ONE_MIN = "PT1M"; // 1분동안 Lock
    private static final String SEC_59 = "PT59S"; // 59초동안 Lock

//    //20초 마다 실행
//    @Scheduled(cron = "0/20 * * * * ?")
//    // lockAtLeastFor: 실행시간이 주기보다 짧을 경우, lock을 유지할 시간
//    // lockAtMostFor: 실행시간이 주기보다 길 경우, lock을 유지할 경우 (주기가 되었는데, 실행중인 스케줄러가 있다면 해당 주기의 실행을 생략한다.)
//    @SchedulerLock(name = "testShedLockJob", lockAtLeastFor = "PT19S", lockAtMostFor = "PT45S")
//    public void testShedLockJob() throws Exception {
//        log.info("================task has been stared===============");
//        log.info("================Server Port : " + "8090 " + LocalDateTime.now().toString() + " ===============");
//        //  1) 실행시간이 주기 2번까지 길경우, 2번까지 건너뜀
////        for (int i = 0; i < 50; i++) {
////            log.info(i + "");
////            Thread.sleep(1000);
////        }
//        // 2) 실행시간이 실행주기 1번보다 길경우, 1번 주기 생략하고, 다음껄로 실행
////        for (int i = 0; i < 30; i++) {
////            log.info(i + "");
////            Thread.sleep(1000);
////        }
//        // 3) 실행시간이 실행주기보다 짧은 경우, 주기마다 실행
////        for (int i = 0; i < 10; i++) {
////            log.info(i + "");
////            Thread.sleep(1000);
////        }
//        throw new Exception("dfdf");
////        log.info("================task has been ended" + LocalDateTime.now().toString() + "===============");
//    }

    //20초 마다 실행
    @Scheduled(cron = "0 * * * * ?")
    // lockAtLeastFor: 실행시간이 주기보다 짧을 경우, lock을 유지할 시간
    // lockAtMostFor: 실행시간이 주기보다 길 경우, lock을 유지할 경우 (주기가 되었는데, 실행중인 스케줄러가 있다면 해당 주기의 실행을 생략한다.)
    @SchedulerLock(name = "testShedLockJob", lockAtLeastFor = "PT1M", lockAtMostFor = "PT3M")
    public void testShedLockJob() throws Exception {
        log.info("================task has been stared===============");
        log.info("================Server Port : " + "8090 " + LocalDateTime.now().toString() + " ===============");
        //  1) 실행시간이 주기 2번까지 길경우, 2번까지 건너뜀
        for (int i = 0; i < 10; i++) {
            log.info(i + "");
            Thread.sleep(1000);
        }
        log.info("================task has been ended" + LocalDateTime.now().toString() + "===============");
    }

    //20초 마다 실행
    @Scheduled(cron = "0 * * * * ?")
    // lockAtLeastFor: 실행시간이 주기보다 짧을 경우, lock을 유지할 시간
    // lockAtMostFor: 실행시간이 주기보다 길 경우, lock을 유지할 경우 (주기가 되었는데, 실행중인 스케줄러가 있다면 해당 주기의 실행을 생략한다.)
    @SchedulerLock(name = "testShedLockJob2", lockAtLeastFor = "PT1M", lockAtMostFor = "PT3M")
    public void testShedLockJob2() throws Exception {
        log.info("================(2)task has been stared===============");
        log.info("================(2)Server Port : " + "8090 " + LocalDateTime.now().toString() + " ===============");
        //  1) 실행시간이 주기 2번까지 길경우, 2번까지 건너뜀
        for (int i = 0; i < 10; i++) {
            log.info(i + "");
            Thread.sleep(1000);
        }
        log.info("================(2)task has been ended" + LocalDateTime.now().toString() + "===============");
    }

    @Scheduled(cron = "0 * * * * ?")
    // lockAtLeastFor: 실행시간이 주기보다 짧을 경우, lock을 유지할 시간
    // lockAtMostFor: 실행시간이 주기보다 길 경우, lock을 유지할 경우 (주기가 되었는데, 실행중인 스케줄러가 있다면 해당 주기의 실행을 생략한다.)
    @SchedulerLock(name = "testShedLockJob3", lockAtLeastFor = "PT1M", lockAtMostFor = "PT3M")
    public void testShedLockJob3() throws Exception {
        log.info("================(3)task has been stared===============");
        log.info("================(3)Server Port : " + "8090 " + LocalDateTime.now().toString() + " ===============");
        //  1) 실행시간이 주기 2번까지 길경우, 2번까지 건너뜀
        for (int i = 0; i < 10; i++) {
            log.info(i + "");
            Thread.sleep(1000);
        }
        log.info("================(3)task has been ended" + LocalDateTime.now().toString() + "===============");
    }
}
