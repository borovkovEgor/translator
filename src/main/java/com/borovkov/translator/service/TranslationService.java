package com.borovkov.translator.service;

import com.borovkov.translator.dao.TranslationDAO;
import com.borovkov.translator.model.Translation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TranslationService {

    private final RestTemplate restTemplate;
    private final TranslationDAO translationDAO;
    private final ObjectMapper objectMapper;
    public Translation save(String text, String sourceLanguage, String targetLanguage, String ipAddress) throws JsonProcessingException {

        String translatedText = translationText(text, sourceLanguage, targetLanguage);

        Translation translation = new Translation();
        translation.setIpAddress(ipAddress);
        translation.setSourceText(text);
        translation.setTranslatedText(translatedText);
        translationDAO.save(translation);

        return translation;
    }

    public String translationText(String text, String sourceLanguage, String targetLanguage) throws JsonProcessingException {
        String[] words = text.split("\\s+");
        List<String> translatedWords = new ArrayList<>();

        for (String word : words) {
            translatedWords.add(translateWord(word, sourceLanguage, targetLanguage));
        }

        return String.join(" ", translatedWords);
    }

    private String translateWord(String text, String sourceLanguage, String targetLanguage) throws JsonProcessingException {
        String apiUrl = "https://ftapi.pythonanywhere.com/translate?sl=" + sourceLanguage + "&dl=" + targetLanguage + "&text=" + text;
        String response = restTemplate.getForObject(apiUrl, String.class);
        Translation translation = objectMapper.readValue(response, Translation.class);
        return translation.getDestinationText();
    }
}