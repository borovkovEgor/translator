package com.borovkov.translator.service;

import com.borovkov.translator.dao.LanguageDAO;
import com.borovkov.translator.model.Language;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LanguageService {

    private final LanguageDAO languageDAO;

    @Cacheable("languages")
    public List<Language> getAllLanguages() {
        return languageDAO.getAllLanguages();
    }
}
