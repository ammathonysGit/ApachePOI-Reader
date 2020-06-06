package de.varengold.interviews.vasil.properties;

import lombok.Getter;
import lombok.Setter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * This class is responsible for holding the properties which are injected from the application.properties file.
 */
@Component
@Getter
@Setter
@ConfigurationProperties("app")
public class ExcelProperties {

    private String fileName;
    private String sheetName;
    private String columnName;
}
