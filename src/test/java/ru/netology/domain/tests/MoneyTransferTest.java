package ru.netology.domain.tests;

import com.codeborne.selenide.Configuration;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.netology.domain.data.DataHelper;
import ru.netology.domain.pages.DashboardPage;
import ru.netology.domain.pages.LoginPage;
import ru.netology.domain.pages.TransferPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {
    @Nested
    @DisplayName("Login & Verification")
    class LoginAndVerification {
        @BeforeEach
        void setup() {
            Configuration.holdBrowserOpen = true;
            open("http://localhost:9999");
        }

        @Test
        void shouldLoginByHappyPath() {
            var loginPage = new LoginPage();
            var autoInfo = DataHelper.getAuthInfo();
            var verificationPage = loginPage.validLogin(autoInfo);
            var verificationCode = DataHelper.getVerificationCodeFor(autoInfo);
            verificationPage.validVerify(verificationCode);
        }

        @Test
        void shouldLoginWithOther() {
            var loginPage = new LoginPage();
            var authInfo = DataHelper.getOtherAuthInfo();
            var verificationPage = loginPage.validLogin(authInfo);
            var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
            verificationPage.validVerify(verificationCode);
        }

        @Test
        void shouldLoginWithWrongVerificationCode() {
            var loginPage = new LoginPage();
            var autoInfo = DataHelper.getAuthInfo();
            var verificationPage = loginPage.validLogin(autoInfo);
            var verificationCode = DataHelper.getWrongVerificationCodeFor(autoInfo);
            verificationPage.invalidVerify(verificationCode);
        }

        @Test
        void shouldSetWrongLogin() {
            var loginPage = new LoginPage();
            var autoInfo = DataHelper.getWrongAuthInfo();
            loginPage.invalidLogin(autoInfo);
            loginPage.ErrorElement();
        }
    }

    @Nested
    @DisplayName("Different Transfers Between Own Cards")
    class MoneyTransferring {
        @BeforeEach
        public void setUp() {
            Configuration.holdBrowserOpen = true;
            open("http://localhost:9999");
            var loginPage = new LoginPage();
            var autoInfo = DataHelper.getAuthInfo();
            var verificationPage = loginPage.validLogin(autoInfo);
            var verificationCode = DataHelper.getVerificationCodeFor(autoInfo);
            verificationPage.validVerify(verificationCode);
        }

        @Test
        void shouldTransferMoneyFromSecondToFirst() {
            var dashboardPage = new DashboardPage();
            int amount = 100;
            int firstBalanceCard = dashboardPage.getFirstCardBalance();
            int secondBalanceCard = dashboardPage.getSecondCardBalance();
            val transferPage = new TransferPage();
            dashboardPage.topUpFirstCard();
            transferPage.validTransfer(amount, DataHelper.getSecondCardNumber());
            val secondBalanceCardResult = secondBalanceCard - amount;
            val firstBalanceCardResult = firstBalanceCard + amount;
            assertEquals(firstBalanceCardResult, dashboardPage.getFirstCardBalance());
            assertEquals(secondBalanceCardResult, dashboardPage.getSecondCardBalance());
        }

        @Test
        void shouldTransferMoneyFromFirstToSecond() {
            var dashboardPage = new DashboardPage();
            int amount = 1900;
            int firstBalanceCard = dashboardPage.getFirstCardBalance();
            int secondBalanceCard = dashboardPage.getSecondCardBalance();
            val transferPage = new TransferPage();
            dashboardPage.topUpSecondCard();
            transferPage.validTransfer(amount, DataHelper.getFirstCardNumber());
            val firstBalanceCardResult = firstBalanceCard - amount;
            val secondBalanceCardResult = secondBalanceCard + amount;
            assertEquals(firstBalanceCardResult, dashboardPage.getFirstCardBalance());
            assertEquals(secondBalanceCardResult, dashboardPage.getSecondCardBalance());
        }

        @Test
        void shouldTransferMoreThanAvailable() {
            var dashboardPage = new DashboardPage();
            int amount = 100000;
            int firstBalanceCard = dashboardPage.getFirstCardBalance();
            int secondBalanceCard = dashboardPage.getSecondCardBalance();
            val transferPage = new TransferPage();
            dashboardPage.topUpSecondCard();
            transferPage.validTransfer(amount, DataHelper.getFirstCardNumber());
            val firstBalanceCardResult = firstBalanceCard - amount;
            val secondBalanceCardResult = secondBalanceCard + amount;
            assertEquals(firstBalanceCardResult, dashboardPage.getFirstCardBalance());
            assertEquals(secondBalanceCardResult, dashboardPage.getSecondCardBalance());
        }

        @Test
        void shouldTransferMoneyFromNonExistentCard() {
            var dashboardPage = new DashboardPage();
            int amount = 6000;
            int firstBalanceCard = dashboardPage.getFirstCardBalance();
            int secondBalanceCard = dashboardPage.getSecondCardBalance();
            val transferPage = new TransferPage();
            dashboardPage.topUpSecondCard();
            transferPage.invalidTransfer(amount, DataHelper.getNonExistentCardNumber());
        }

        @Test
        void shouldTransferNegativeAmount() {
            var dashboardPage = new DashboardPage();
            int amount = -9999;
            int firstBalanceCard = dashboardPage.getFirstCardBalance();
            int secondBalanceCard = dashboardPage.getSecondCardBalance();
            val transferPage = new TransferPage();
            dashboardPage.topUpSecondCard();
            transferPage.validTransfer(amount, DataHelper.getFirstCardNumber());
            val firstBalanceCardResult = firstBalanceCard + amount;
            val secondBalanceCardResult = secondBalanceCard - amount;
            assertEquals(firstBalanceCardResult, dashboardPage.getFirstCardBalance());
            assertEquals(secondBalanceCardResult, dashboardPage.getSecondCardBalance());
        }

        @Test
        void shouldTransferDecimalAmount() {
            var dashboardPage = new DashboardPage();
            double amount = 500.5;
            double firstBalanceCard = dashboardPage.getFirstCardBalance();
            double secondBalanceCard = dashboardPage.getSecondCardBalance();
            val transferPage = new TransferPage();
            dashboardPage.topUpSecondCard();
            transferPage.decimalTransfer(amount, DataHelper.getFirstCardNumber());
            val firstBalanceCardResult = firstBalanceCard - amount;
            val secondBalanceCardResult = secondBalanceCard + amount;
            assertEquals(firstBalanceCardResult, dashboardPage.getFirstCardBalance());
            assertEquals(secondBalanceCardResult, dashboardPage.getSecondCardBalance());
        }

        @Test
        void shouldTransferOnTheSameCard() {
            var dashboardPage = new DashboardPage();
            int amount = 5000;
            int firstBalanceCard = dashboardPage.getFirstCardBalance();
            int secondBalanceCard = dashboardPage.getSecondCardBalance();
            val transferPage = new TransferPage();
            dashboardPage.topUpSecondCard();
            transferPage.validTransfer(amount, DataHelper.getSecondCardNumber());
            val firstBalanceCardResult = firstBalanceCard;
            val secondBalanceCardResult = secondBalanceCard;
            assertEquals(firstBalanceCardResult, dashboardPage.getFirstCardBalance());
            assertEquals(secondBalanceCardResult, dashboardPage.getSecondCardBalance());
        }
    }
}