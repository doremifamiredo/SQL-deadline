package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.Locale;

public class DataHelper {
    private static Faker faker = new Faker(new Locale("en"));

    private DataHelper() {
    }

    public static AuthInfo getAuthIfoWithTestData() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static VerifiC getVerifiCode(String code) {
        return new VerifiC("vasya", code);
    }

    public static AuthInfo generateRandomUser() {
        return new AuthInfo(faker.name().username(), faker.internet().password());
    }

    public static VerificationCode genRandomCode() {
        return new VerificationCode(faker.numerify("######"));
    }

    @Value
    public static class AuthInfo {
        String login;
        String password;
    }

    @Value
    public static class VerifiC {
        String login;
        String code;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VerificationCode {
        String code;
    }
}
