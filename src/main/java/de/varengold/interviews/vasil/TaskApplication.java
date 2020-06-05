package de.varengold.interviews.vasil;

import de.varengold.interviews.vasil.reader.FileReader;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class TaskApplication {

  public static void main(String[] args) {
    ApplicationContext applicationContext = new SpringApplicationBuilder()
        .sources(TaskApplication.class)
        .run(args);

//    System.out.println(reverse(1234));

    FileReader fileReader = applicationContext.getBean(FileReader.class);
    fileReader.readFile();

  }

}
