package com.sumit.walmart.helper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Random;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RandomNumberGenerator {

    private final static Random random = new Random();

    public static int randomNumber(int low, int high) {
        return random.nextInt(high - low) + low;
    }
}
