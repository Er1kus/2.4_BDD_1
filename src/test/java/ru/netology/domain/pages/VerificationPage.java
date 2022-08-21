package ru.netology.domain.pages;

import com.codeborne.selenide.SelenideElement;
import ru.netology.domain.data.DataHelper;
import ru.netology.domain.pages.exceptions.VerificationPageError;
import ru.netology.domain.pages.exceptions.VerificationPageError2;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private SelenideElement codeField = $("[data-test-id='code'] input");
    private SelenideElement verifyButton = $("[data-test-id='action-verify']");
    private SelenideElement errorMassage = $("[data-test-id='error-notification']");

    public VerificationPage() {
        codeField.shouldBe(visible);
    }

    public void verifyAs(String code) {
        codeField.setValue(code);
        verifyButton.click();
    }

    public DashboardPage validVerify(DataHelper.VerificationCode verificationCode) {
        verifyAs(verificationCode.getCode());
        return new DashboardPage();
    }

    public VerificationPageError invalidVerify(DataHelper.VerificationCode verificationCode) {
        verifyAs(verificationCode.getCode());
        return new VerificationPageError();
    }

    public VerificationPageError2 invalidVerifyMore3Times(DataHelper.VerificationCode verificationCode) {
        verifyAs(verificationCode.getCode());
        return new VerificationPageError2();
    }
}
