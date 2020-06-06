package de.varengold.interviews.vasil.runner;

import de.varengold.interviews.vasil.reader.SheetExtractor;
import de.varengold.interviews.vasil.service.ReverseNumberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * TaskApplicationRunner is used to log the final result to the console.
 * It has FileReader and ReverseNumberService as a dependencies.
 */
@Component
@Slf4j
public class TaskApplicationRunner implements CommandLineRunner {

    private final SheetExtractor sheetExtractor;
    private final ReverseNumberService reverseNumberService;

    public TaskApplicationRunner(SheetExtractor sheetExtractor, ReverseNumberService reverseNumberService) {
        this.sheetExtractor = sheetExtractor;
        this.reverseNumberService = reverseNumberService;
    }

    @Override
    public void run(String... args) {
        long extractedValueFromSheet = sheetExtractor.extractValue();
        long reversedExtractedValue = reverseNumberService.reverseNumber(extractedValueFromSheet);
        log.info("Reversed value: {}", reversedExtractedValue);
    }
}
