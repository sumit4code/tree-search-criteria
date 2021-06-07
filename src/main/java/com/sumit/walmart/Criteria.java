package com.sumit.walmart;

public interface Criteria {

    boolean apply(Subject subject);

    String getId();

}
