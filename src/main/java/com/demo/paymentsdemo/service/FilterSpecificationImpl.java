package com.demo.paymentsdemo.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import com.demo.paymentsdemo.dto.SearchRequestDto;
import com.demo.paymentsdemo.dto.RequestDto.GlobalOperator;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
public class FilterSpecificationImpl<T> implements FilterSpecification<T>{

    @Override
    public Specification<T> getSearchSpecification(SearchRequestDto searchRequestDto) {

        return new Specification<T>() {

            @Override
            @Nullable
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                return criteriaBuilder.equal(root.get(searchRequestDto.getColumn()), searchRequestDto.getValue());
                
            }
        };
    }

    @Override
    public Specification<T> getSearchSpecification(List<SearchRequestDto> searchRequestDtos, GlobalOperator globalOperator) {
        
        return new Specification<T>(){

            @Override
            @Nullable
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                
                List<Predicate> predicates = new ArrayList<>();
                for(SearchRequestDto it:searchRequestDtos)
                {
                    switch(it.getOperation())
                    {
                        case EQUAL:
                            Predicate equal = criteriaBuilder.equal(root.get(it.getColumn()), it.getValue());
                            predicates.add(equal);
                            break;

                        case LIKE:
                            Predicate like = criteriaBuilder.like(root.get(it.getColumn()), "%" + it.getValue() + "%");
                            predicates.add(like);
                            break;

                        case IN:
                            String[] split = it.getValue().split(",");
                            Predicate in = root.get(it.getColumn()).in(Arrays.asList(split));
                            predicates.add(in);
                            break;
                        
                        case LESS_THAN:
                            Predicate lessthan = criteriaBuilder.lessThan(root.get(it.getColumn()), it.getValue());
                            predicates.add(lessthan);
                            break;
                        
                        case GREATER_THAN:
                            Predicate greaterthan = criteriaBuilder.greaterThan(root.get(it.getColumn()), it.getValue());
                            predicates.add(greaterthan);
                            break;
                        
                        case BETWEEN:
                            String[] split1 = it.getValue().split(",");
                            Predicate between = criteriaBuilder.between(root.get(it.getColumn()), split1[0], split1[1]);
                            predicates.add(between);
                            break;

                        case JOIN:
                            Predicate join = criteriaBuilder.equal(root.join(it.getJoinTable()).get(it.getColumn()), it.getValue());
                            predicates.add(join);
                            break;
                        
                        default:
                            throw new IllegalAccessError("Unexpected Value:");
                    }
                    
                    
                }
                if(globalOperator.equals(GlobalOperator.AND)){
                    return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
                }
                else{
                    return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
                }
                
            }
        
        };
    }
    
    


    
}
