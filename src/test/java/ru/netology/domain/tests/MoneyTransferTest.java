package ru.netology.domain.tests;

import com.codeborne.selenide.Configuration;
import lombok.val;
import org.junit.jupiter.api.*;
import ru.netology.domain.data.DataHelper;
import ru.netology.domain.pages.DashboardPage;
import ru.netology.domain.pages.LoginPage;
import ru.netology.domain.pages.TransferPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MoneyTransferTest {
    @Nested
    @DisplayName("Negative Login & Verification")
    class LoginAndVerification {
        @BeforeEach
        void setup() {
            Configuration.holdBrowserOpen = true;
            open("http://localhost:9999");
        }

        @Order(9)
        @RepeatedTest(3)
        void shouldLoginWithWrongVerificationCode() {
            var loginPage = new LoginPage();
            var autoInfo = DataHelper.getAuthInfo();
            var verificationPage = loginPage.validLogin(autoInfo);
            var verificationCode = DataHelper.getWrongVerificationCodeFor(autoInfo);
            verificationPage.invalidVerify(verificationCode);
        }

        @Order(10)
        @Test()
        void shouldLoginWithWrongVerificationCodeMoreThan3TimesInARow() {
            var loginPage = new LoginPage();
            var autoInfo = DataHelper.getAuthInfo();
            var verificationPage = loginPage.validLogin(autoInfo);
            var verificationCode = DataHelper.getWrongVerificationCodeFor(autoInfo);
            verificationPage.invalidVerifyMore3Times(verificationCode);
        }

        @Order(8)
        @Test
        void shouldSetWrongLogin() {
            var loginPage = new LoginPage();
            var autoInfo = DataHelper.getWrongAuthInfo();
            loginPage.invalidLogin(autoInfo);
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

        @Order(1)
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

        @Order(2)
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

        @Order(3)
        @Test
        void shouldTransferMoreThanAvailable() {
            var dashboardPage = new DashboardPage();
            int amount = 100000;
            int firstBalanceCard = dashboardPage.getFirstCardBalance();
            int secondBalanceCard = dashboardPage.getSecondCardBalance();
            val transferPage = new TransferPage();
            dashboardPage.topUpSecondCard();
            transferPage.excessTransfer(amount, DataHelper.getFirstCardNumber());
            val firstBalanceCardResult = firstBalanceCard;
            val secondBalanceCardResult = secondBalanceCard;
            assertEquals(firstBalanceCardResult, dashboardPage.getFirstCardBalance());
            assertEquals(secondBalanceCardResult, dashboardPage.getSecondCardBalance());
        }

        @Order(4)
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

        @Order(5)
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

        @Order(6)
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

        @Order(7)
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

