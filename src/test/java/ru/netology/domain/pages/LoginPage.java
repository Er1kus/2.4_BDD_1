package ru.netology.domain.pages;

import com.codeborne.selenide.SelenideElement;
import lombok.Value;
import ru.netology.domain.data.DataHelper;
import ru.netology.domain.pages.exceptions.LoginPageError;

import static com.codeborne.selenide.Selenide.$;

@Value

public class LoginPage {
    private SelenideElement loginField = $("[data-test-id='login'] input");
    private SelenideElement passwordField = $("[data-test-id='password'] input");
    private SelenideElement loginButton = $("[data-test-id='action-login']");
    private SelenideElement errorMassage = $("[data-test-id='error-notification']");

    public void loginAs(String login, String password) {
        loginField.setValue(login);
        passwordField.setValue(password);
        loginButton.click();
    }

    public VerificationPage validLogin(DataHelper.AuthInfo info) {
        loginAs(info.getLogin(), info.getPassword());
        return new VerificationPage();
    }

    public LoginPageError invalidLogin(DataHelper.AuthInfo info) {
        loginAs(info.getLogin(), info.getPassword());
        return new LoginPageError();
    }
}
