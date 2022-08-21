package ru.netology.domain.pages;

import com.codeborne.selenide.SelenideElement;
import ru.netology.domain.data.DataHelper;
import ru.netology.domain.pages.exceptions.TransferPageError;
import ru.netology.domain.pages.exceptions.TransferPageError2;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private SelenideElement heading = $("[data-test-id='dashboard']");
    private SelenideElement sumForTransfer = $("[data-test-id='amount'] input");
    private SelenideElement fromCardNumber = $("[data-test-id='from'] input");
    private SelenideElement toCardNumber = $("[data-test-id='to']");
    private SelenideElement transferButton = $("[data-test-id='action-transfer']");
    private SelenideElement cancelButton = $("[data-test-id='action-cancel']");
    private SelenideElement errorPopup = $("[data-test-id='error-notification']");

    public TransferPage() {
        heading.shouldBe(visible);
    }

    public void transferAs(int amount, String number) {
        sumForTransfer.setValue(String.valueOf(amount));
        fromCardNumber.setValue(number);
        transferButton.click();
    }

    public DashboardPage validTransfer(int amount, DataHelper.TransferCode code) {
        transferAs(Integer.parseInt(String.valueOf(amount)), code.getNumber());
        return new DashboardPage();
    }

    public TransferPageError invalidTransfer(int amount, DataHelper.TransferCode code) {
        transferAs(Integer.parseInt(String.valueOf(amount)), code.getNumber());
        return new TransferPageError();
    }

    public TransferPageError2 excessTransfer(int amount, DataHelper.TransferCode code) {
        transferAs(Integer.parseInt(String.valueOf(amount)), code.getNumber());
        return new TransferPageError2();
    }

    public DashboardPage decimalTransfer(double amount, DataHelper.TransferCode code) {
        sumForTransfer.setValue(String.valueOf(amount));
        fromCardNumber.setValue(code.getNumber());
        transferButton.click();
        return new DashboardPage();
    }
}


