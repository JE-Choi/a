package com.jechoi.a.thread;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadEx1 {
    public static void main(String[] args) {
        ShowThread showThread1 = new ShowThread("1번 쓰레드");
        ShowThread showThread2 = new ShowThread("2번 쓰레드");
        showThread1.start();
        showThread2.start();
    }

    @RequiredArgsConstructor
    static class ShowThread extends Thread {
        public ShowThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                System.out.println("name = " + super.getName());
                try {
                    sleep(100);
                } catch (final InterruptedException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }
}
