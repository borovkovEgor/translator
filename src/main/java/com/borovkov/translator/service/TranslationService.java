package com.borovkov.translator.service;

import com.borovkov.translator.dao.TranslationDAO;
import com.borovkov.translator.model.Translation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.std.CollectionSerializer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
public class TranslationService {

    private final RestTemplate restTemplate;
    private final TranslationDAO translationDAO;
    private final ObjectMapper objectMapper;
    public Translation save(String text, String sourceLanguage, String targetLanguage, String ipAddress) {

        String translatedText = translationText(text, sourceLanguage, targetLanguage);

        Translation translation = new Translation();
        translation.setIpAddress(ipAddress);
        translation.setSourceText(text);
        translation.setTranslatedText(translatedText);
        translationDAO.save(translation);

        return translation;
    }

    public String translationText(String text, String sourceLanguage, String targetLanguage) {
        String[] words = text.split("\\s+");
        Map<Integer, String> wordsMap = Collections.synchronizedMap(new HashMap<>());

        CountDownLatch count = new CountDownLatch(words.length);
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < words.length; i ++) {
            final int index = i;
            executorService.execute(() -> {
                try {
                    String translatedWord = translateWord(words[index], sourceLanguage, targetLanguage);
                    wordsMap.put(index, translatedWord);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                } finally {
                    count.countDown();
                }
            });
        }

        try {
            count.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        StringBuilder translatedText = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            translatedText.append(wordsMap.get(i)).append(" ");
        }

        return translatedText.toString().trim();
    }

    private String translateWord(String text, String sourceLanguage, String targetLanguage) throws JsonProcessingException {
        String apiUrl = "https://ftapi.pythonanywhere.com/translate?sl=" + sourceLanguage + "&dl=" + targetLanguage + "&text=" + text;
        String response = restTemplate.getForObject(apiUrl, String.class);
        Translation translation = objectMapper.readValue(response, Translation.class);
        return translation.getDestinationText();
    }
}