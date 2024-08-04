package com.borovkov.translator.controller;

import com.borovkov.translator.model.Language;
import com.borovkov.translator.model.Translation;
import com.borovkov.translator.service.LanguageService;
import com.borovkov.translator.service.TranslationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/translate")
public class TranslationController {

    private final TranslationService translationService;
    private final LanguageService languageService;

    @PostMapping
    public String translate(
            @RequestParam String text,
            @RequestParam String sourceLanguage,
            @RequestParam String targetLanguage,
            Model model,
            HttpServletRequest request) {

        String ipAddress = request.getRemoteAddr();

        Translation translation = translationService.save(text, sourceLanguage, targetLanguage, ipAddress);

        model.addAttribute("sourceLanguage", translation.getSourceLanguage());
        model.addAttribute("targetLanguage", translation.getTargetLanguage());
        model.addAttribute("sourceText", translation.getSourceText());
        model.addAttribute("translatedText", translation.getTranslatedText());

        List<Language> languages = languageService.getAllLanguages();
        model.addAttribute("languages", languages);

        return "translator";
    }

    @GetMapping
    public String index(Model model) {

        List<Language> languages = languageService.getAllLanguages();
        model.addAttribute("languages", languages);

        return "translator";
    }
}
