package ru.netology.domain.data;

import lombok.Value;

public class DataHelper {
    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getOtherAuthInfo() {
        return new AuthInfo("petya", "123qwerty");
    }

    public static AuthInfo getWrongAuthInfo() {
        return new AuthInfo("sasha", "qwerty123");
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {
        return new VerificationCode("12345");
    }

    public static VerificationCode getWrongVerificationCodeFor(AuthInfo authInfo) {
        return new VerificationCode("54321");

    }

    @Value
    public static class TransferCode {
        private String number;
    }

    public static TransferCode getFirstCardNumber() {
        return new TransferCode("5559 0000 0000 0001");
    }

    public static TransferCode getSecondCardNumber() {
        return new TransferCode("5559 0000 0000 0002");
    }

    public static TransferCode getNonExistentCardNumber() {
        return new TransferCode("5559 0000 0000 0005");
    }
}
