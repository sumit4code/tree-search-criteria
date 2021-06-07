package com.sumit.walmart.criteria;

import com.sumit.walmart.Criteria;
import com.sumit.walmart.Subject;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class ElementDividedBySevenImpl implements Criteria {

    public static final String IDENTIFIER = "id4";

    @Override
    public boolean apply(Subject subject) {
        return subject.getNumber() % 7 == 0;
    }

    @Override
    public String getId() {
        return IDENTIFIER;
    }
}
