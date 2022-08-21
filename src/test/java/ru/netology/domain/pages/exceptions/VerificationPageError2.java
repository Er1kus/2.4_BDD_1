package ru.netology.domain.pages.exceptions;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPageError2 {
    private SelenideElement errorMassage = $("[data-test-id='error-notification']");

    public VerificationPageError2() {
        errorMassage.shouldHave(text("Ошибка " + "Ошибка! Превышено количество попыток ввода кода!"));
    }
}

