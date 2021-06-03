package com.sumit.walmart.criteria;

import com.sumit.walmart.Subject;
import com.sumit.walmart.Criteria;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EventNumberImpl implements Criteria {

    public static final String IDENTIFIER = "id1";

    private int count = 0;

    @Override
    public void apply(Subject subject) {
        if (subject.getNumber() % 2 == 0) {
            count++;
        }
    }

    @Override
    public void display() {
        log.info("Total number of even count {}", count);
    }

    @Override
    public String getId() {
        return IDENTIFIER;
    }

    @Override
    public void reset() {
        count = 0;
    }
}
