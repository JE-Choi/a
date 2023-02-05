package com.jechoi.a.a.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@Getter
@ToString
@RequiredArgsConstructor
public class Element {
    /**
     * 식별자
     */
    @Id
    private final UUID id;

    /**
     * 요소명
     */
    private final String name;

    /**
     * 활성화
     */
    private final Status status;

    public enum Status {
        SUCCESS,
        PROCESSING,
        FAIL,
        NEED_PROCESSING,
    }
}
