package de.varengold.interviews.vasil.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


class ReverseNumberServiceTest {

    private final ReverseNumberService reverseNumberService = new ReverseNumberService();

    @Test
    void reverseNumberPositive() {
        long testNumber = 1234567;
        long expectedNumber = 7654321;
        assertEquals(reverseNumberService.reverseNumber(testNumber), expectedNumber);
    }

    @Test
    void reverseNumberNegative() {
        long testNumber = -1234567;
        long expectedNumber = -7654321;
        assertEquals(reverseNumberService.reverseNumber(testNumber), expectedNumber);
    }
}
