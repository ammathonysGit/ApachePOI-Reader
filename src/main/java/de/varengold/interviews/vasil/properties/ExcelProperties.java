package de.varengold.interviews.vasil.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties("app")
public class ExcelProperties {

    private String fileName;
    private String sheetName;
    private String columnName;
}
