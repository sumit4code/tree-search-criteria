package com.sumit.walmart.criteria;

import com.sumit.walmart.Criteria;
import com.sumit.walmart.Subject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ElementDividedBySevenImpl implements Criteria {

    public static final String IDENTIFIER = "id4";

    private int count = 0;

    @Override
    public void apply(Subject subject) {
        if (subject.getNumber() % 7 == 0) {
            count++;
        }
    }

    @Override
    public void display() {
        log.info("Total number for digit divided by 7 {}", count);
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
