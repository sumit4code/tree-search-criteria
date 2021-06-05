package com.sumit.walmart.listener;

import com.sumit.walmart.Criteria;
import com.sumit.walmart.domain.Context;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Listener {

    private final List<Criteria> criteriaList;

    public Listener(List<Criteria> criteriaList) {
        this.criteriaList = criteriaList;
    }

    public Listener() {
        this(new ArrayList<>());
    }

    public void addCriteria(Criteria criteria) {
        log.info("Adding criteria {}", criteria.getId());
        criteriaList.add(criteria);
    }

    public void notify(Context context) {
        if (context.getFilteredCriteria().isEmpty()) {
            criteriaList.parallelStream().forEach(criteria -> criteria.apply(context.getSubject()));
        } else {
            criteriaList.parallelStream().filter(criteria -> isMatching(criteria, context.getFilteredCriteria()))
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
