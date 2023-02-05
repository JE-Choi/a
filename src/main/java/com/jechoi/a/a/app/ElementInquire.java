package com.jechoi.a.a.app;

import com.jechoi.a.a.domain.Element;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ElementInquire {
    private final ElementRepository repository;

    public Page<Element> run(){
        return this.repository.findAll(Pageable.ofSize(10));
    }
}
