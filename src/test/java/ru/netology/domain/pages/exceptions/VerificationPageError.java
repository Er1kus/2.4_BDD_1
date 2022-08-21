package ru.netology.domain.pages.exceptions;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPageError {
    private SelenideElement errorMassage = $("[data-test-id='error-notification']");

    public VerificationPageError() {
        errorMassage.shouldHave(text("Ошибка " + "Ошибка! Неверно указан код! Попробуйте ещё раз."));
    }
}
