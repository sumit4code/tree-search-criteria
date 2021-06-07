package com.sumit.walmart.criteria;

import com.sumit.walmart.Subject;
import com.sumit.walmart.Criteria;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class LevelTwoCountImpl implements Criteria {

    public static final String IDENTIFIER = "id2";

    @Override
    public boolean apply(Subject subject) {
        return subject.getLevel() == 2;
    }

    @Override
    public String getId() {
        return IDENTIFIER;
    }
}
