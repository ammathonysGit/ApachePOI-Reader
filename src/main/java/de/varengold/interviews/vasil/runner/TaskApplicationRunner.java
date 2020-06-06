package de.varengold.interviews.vasil.runner;

import de.varengold.interviews.vasil.reader.FileReader;
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

    private final FileReader fileReader;
    private final ReverseNumberService reverseNumberService;

    public TaskApplicationRunner(FileReader fileReader, ReverseNumberService reverseNumberService) {
        this.fileReader = fileReader;
        this.reverseNumberService = reverseNumberService;
    }

    @Override
    public void run(String... args) {
        long extractedValueFromSheet = fileReader.extractValueFromSheet();
        long reversedExtractedValue = reverseNumberService.reverseNumber(extractedValueFromSheet);
        log.info("Reversed value: {}", reversedExtractedValue);
    }
}
