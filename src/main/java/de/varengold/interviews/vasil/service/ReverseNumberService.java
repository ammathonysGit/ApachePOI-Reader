package de.varengold.interviews.vasil.service;

import org.springframework.stereotype.Service;

/**
 * ReverseNumberService is used to reverse a given numeric value
 */
@Service
public class ReverseNumberService {

    /**
     * Reverses the given value.
     * Example: 1234 it will return 4321
     * @param value
     * @return returns the reversed value.
     */
    public long reverseNumber(long value) {
        long reversedNumber = 0;
        while (value != 0) {
            reversedNumber = reversedNumber * 10 + (value % 10);
            value /= 10;
        }
        return reversedNumber;
    }
}
