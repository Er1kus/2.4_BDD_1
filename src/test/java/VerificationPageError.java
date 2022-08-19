//import com.codeborne.selenide.SelenideElement;
//import ru.netology.domain.data.DataHelper;
//import ru.netology.domain.pages.DashboardPage;
//
//import static com.codeborne.selenide.Condition.text;
//import static com.codeborne.selenide.Selenide.$;
//
//public class VerificationPageError {
//    private SelenideElement codeField = $("[data-test-id='code'] input");
//    private SelenideElement verifyButton = $("[data-test-id='action-verify']");
//    private SelenideElement errorMassage = $("[data-test-id='error-notification']");
//
//    public VerificationPageError() {
//        errorMassage.shouldHave(text("Неверно указан код! Попробуйте ещё раз."));
//    }
//
//    public VerificationPageError invalidVerify(DataHelper.VerificationCode verificationCode) {
//        codeField.setValue(verificationCode.getCode());
//        verifyButton.click();
//        return new VerificationPageError();
//    }
//}