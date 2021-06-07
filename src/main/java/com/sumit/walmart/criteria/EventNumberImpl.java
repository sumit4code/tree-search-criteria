package com.sumit.walmart.criteria;

import com.sumit.walmart.Subject;
import com.sumit.walmart.Criteria;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EventNumberImpl implements Criteria {

    public static final String IDENTIFIER = "id1";


    @Override
    public boolean apply(Subject subject) {
        return subject.getNumber() % 2 == 0;
    }

    @Override
    public String getId() {
        return IDENTIFIER;
    }

}
