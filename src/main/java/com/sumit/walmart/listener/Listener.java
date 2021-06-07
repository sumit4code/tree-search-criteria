package com.sumit.walmart.listener;

import com.sumit.walmart.Criteria;
import com.sumit.walmart.domain.Context;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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
            criteriaList.parallelStream()
                    .filter(criteria -> criteria.apply(context.getSubject())).forEach(criteria -> updateResultantMap(context, criteria));
        } else {
            criteriaList.parallelStream()
                    .filter(criteria -> isMatching(criteria, context.getFilteredCriteria()))
                    .filter(criteria -> criteria.apply(context.getSubject())).forEach(criteria -> updateResultantMap(context, criteria));
        }
    }

    private void updateResultantMap(Context context, Criteria criteria) {
        AtomicInteger integer = context.getResultMap().get(criteria.getId());
        if (integer == null) {
            context.getResultMap().put(criteria.getId(), new AtomicInteger(1));
        } else {
            integer.getAndIncrement();
            context.getResultMap().put(criteria.getId(), integer);
        }
    }

    private boolean isMatching(Criteria criteria, List<String> filteredCriteria) {
        return filteredCriteria.contains(criteria.getId());
    }
}
