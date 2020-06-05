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
    //todo write a method which returns reversed value of given integer;
    //example 1234 % 10 = 4
    // example 1234 / 10 = 123
    // reverse = 4, 43, 432, 4321
}
