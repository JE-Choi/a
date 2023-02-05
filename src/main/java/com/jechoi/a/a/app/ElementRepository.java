package com.jechoi.a.a.app;

import com.jechoi.a.a.domain.Element;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Table("element")
@Repository
public interface ElementRepository extends PagingAndSortingRepository<Element, UUID> {


}
