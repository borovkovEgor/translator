package com.borovkov.translator;

import com.borovkov.translator.service.TranslationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TranslatorApplicationTests {

    @Autowired
    private TranslationService translationService;
    @Test
    public void translateTest() {
        String sourceText = "Во всякой книге предисловие есть первая и вместе с тем последняя вещь; оно или служит объяснением цели сочинения, или оправданием и ответом на критики.";
        String expectedTranslatedText = "In any book preface There is first And together With those last thing; it or serves explanation goals essays, or justification And answer on critics.";

        String translatedText = translationService.translationText(sourceText, "ru", "en");

        Assertions.assertEquals(translatedText, expectedTranslatedText);
    }

}
