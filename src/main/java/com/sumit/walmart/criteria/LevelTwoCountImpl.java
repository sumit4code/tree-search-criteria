package com.sumit.walmart.criteria;

import com.sumit.walmart.Subject;
import com.sumit.walmart.Criteria;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LevelTwoCountImpl implements Criteria {

    public static final String IDENTIFIER = "id2";

    private int count = 0;

    @Override
    public void apply(Subject subject) {
        if (subject.getLevel() == 2) {
            count++;
        }
    }

    @Override
    public void display() {
        log.info("Level two count {}", count);
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
