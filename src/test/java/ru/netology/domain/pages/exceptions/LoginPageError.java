package ru.netology.domain.pages.exceptions;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class LoginPageError {
    private SelenideElement errorMassage = $("[data-test-id='error-notification']");

    public LoginPageError() {
        errorMassage.shouldHave(text(" Ошибка! Неверно указан логин или пароль"));
    }
}
