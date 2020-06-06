package de.varengold.interviews.vasil.runner;

import de.varengold.interviews.vasil.reader.FileReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * TaskApplicationRunner is used to log the final result to the console.
 * It has FileReader as a dependency.
 */
@Component
@Slf4j
public class TaskApplicationRunner implements CommandLineRunner {

    private final FileReader fileReader;

    public TaskApplicationRunner(FileReader fileReader) {
        this.fileReader = fileReader;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Reversed value: " + fileReader.extractValueFromSheet());
    }
}
