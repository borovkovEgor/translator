package com.borovkov.translator.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Translation {

    private Long id;
    private String ipAddress;
    private String sourceText;
    private String translatedText;
    private String sourceLanguage;
    private String targetLanguage;
    @JsonProperty("destination-text")
    private String destinationText;
}
