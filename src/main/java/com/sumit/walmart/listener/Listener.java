package com.sumit.walmart.observer;

import com.sumit.walmart.Criteria;
import com.sumit.walmart.domain.Context;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Observer {

    private final List<Criteria> criteriaList;

    public Observer(List<Criteria> criteriaList) {
        this.criteriaList = criteriaList;
    }

    public Observer() {
        this(new ArrayList<>());
    }

    public void addCriteria(Criteria criteria) {
        log.info("Adding criteria {}", criteria.getId());
        criteriaList.add(criteria);
    }

    public void notify(Context context) {
        if (context.getFilteredCriteria().isEmpty()) {
            criteriaList.forEach(criteria -> criteria.apply(context.getSubject()));
        } else {
            criteriaList.stream().filter(criteria -> isMatching(criteria, context.getFilteredCriteria()))
                    .forEach(criteria -> criteria.apply(context.getSubject()));
        }
    }

    private boolean isMatching(Criteria criteria, List<String> filteredCriteria) {
        return filteredCriteria.contains(criteria.getId());
    }

    public void reset() {
        criteriaList.forEach(Criteria::reset);
    }

    public List<Criteria> getCriteriaList() {
        return criteriaList;
    }
}
