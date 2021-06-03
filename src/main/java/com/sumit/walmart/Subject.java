package com.sumit.walmart;

import lombok.Builder;

@Builder
public class Subject {

    private final int number;
    private final int level;

    public Subject(int number) {
        this(number, -1);
    }

    public Subject(int number, int level) {
        this.number = number;
        this.level = level;
    }

    public int getNumber() {
        return number;
    }

    public int getLevel() {
        return level;
    }
}
