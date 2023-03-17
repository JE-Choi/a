package com.jechoi.a.thread;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

class Sum {
    private int value = 0;

    public void add(int v) {
        value += v;
    }

    public int get() {
        return value;
    }
}

@RequiredArgsConstructor
class AdderThread extends Sum implements Runnable {
    private final int start, end;

    @Override
    public void run() {
        for (int i = start; i <= end; i++) {
            super.add(i);
        }
    }
}

@Slf4j
public class ThreadEx2 {
    public static void main(String[] args) {
        final AdderThread adderThread1 = new AdderThread(1, 50); //1225
        final AdderThread adderThread2 = new AdderThread(51, 100);
        Thread thread1 = new Thread(adderThread1);
        Thread thread2 = new Thread(adderThread2);
        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (final InterruptedException e) {
            log.error(e.getMessage(), e);
            Thread.currentThread().interrupt();
        }
        log.info(String.format("1~100까지의 합 : %d", adderThread1.get() + adderThread2.get()));
    }
}
