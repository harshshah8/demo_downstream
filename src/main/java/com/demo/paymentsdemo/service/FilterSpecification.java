package com.demo.paymentsdemo.service;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.demo.paymentsdemo.dto.RequestDto;
import com.demo.paymentsdemo.dto.SearchRequestDto;

public interface FilterSpecification<T> {

    public Specification<T> getSearchSpecification(SearchRequestDto searchRequestDto);
    public Specification<T> getSearchSpecification(List<SearchRequestDto> searchRequestDtos, RequestDto.GlobalOperator globalOperator);
    
}
