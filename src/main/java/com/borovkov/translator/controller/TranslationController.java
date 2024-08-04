package com.borovkov.translator.controller;

import com.borovkov.translator.model.Translation;
import com.borovkov.translator.service.TranslationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/translate")
public class TranslationController {

    private final TranslationService service;

    @PostMapping
    public String translate(
            @RequestParam String text,
            @RequestParam String sourceLanguage,
            @RequestParam String targetLanguage,
            Model model,
            HttpServletRequest request) {

        String ipAddress = request.getRemoteAddr();

        Translation translation = service.save(text, sourceLanguage, targetLanguage, ipAddress);

        model.addAttribute("sourceLanguage", translation.getSourceLanguage());
        model.addAttribute("targetLanguage", translation.getTargetLanguage());
        model.addAttribute("sourceText", translation.getSourceText());
        model.addAttribute("translatedText", translation.getTranslatedText());

        model.addAttribute("sourceLanguages", new String[] {"en", "ru"});
        model.addAttribute("targetLanguages", new String[] {"en", "ru"});

        return "index";
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("sourceLanguages", new String[] {"en", "ru"});
        model.addAttribute("targetLanguages", new String[] {"en", "ru"});
        return "index";
    }
}
