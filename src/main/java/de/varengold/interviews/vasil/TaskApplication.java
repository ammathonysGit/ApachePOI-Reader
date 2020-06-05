package de.varengold.interviews.vasil;


import de.varengold.interviews.vasil.reader.FileReader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class TaskApplication {

  public static void main(String[] args) {
    SpringApplication.run(TaskApplication.class, args);
  }

  @Component
  public class MyRunner implements CommandLineRunner {

    private final FileReader fileReader;

    public MyRunner(FileReader fileReader) {
      this.fileReader = fileReader;
    }

    @Override
    public void run(String... args) throws Exception {
       System.out.println("Reversed value: " + fileReader.extractValueFromSheet());
    }
  }
}
