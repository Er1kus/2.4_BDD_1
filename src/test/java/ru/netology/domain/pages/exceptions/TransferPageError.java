package ru.netology.domain.pages.exceptions;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class TransferPageError {
    private SelenideElement errorPopup = $("[data-test-id='error-notification']");

    public TransferPageError() {
        errorPopup.shouldHave(text("Ошибка " +
                "Ошибка! Произошла ошибка"));
    }
}

