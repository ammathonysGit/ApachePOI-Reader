package de.varengold.interviews.vasil.service;

import org.springframework.stereotype.Service;

@Service
public class ReverseNumberService {

    public long reverseNumber(long number) {
        long reversedNumber = 0;
        while (number != 0) {
            reversedNumber = reversedNumber * 10 + (number % 10);
            number /= 10;
        }
        return reversedNumber;
    }
}
