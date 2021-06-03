package com.sumit.walmart;

public interface Criteria {

    void apply(Subject subject);

    void display();

    String getId();

    void reset();
}
