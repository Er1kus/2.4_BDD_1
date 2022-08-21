package ru.netology.domain.pages.exceptions;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class TransferPageError2 {
    private SelenideElement errorPopup = $("[data-test-id='error-notification']");

    public TransferPageError2() {
        errorPopup.shouldHave(text("Ошибка " +
                "Ошибка! Перевод не может быть осуществлен. Недостаточно средств"));
    }
}
