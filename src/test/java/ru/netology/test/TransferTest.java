package ru.netology.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.APIHelper;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.SQLHelper.cleanAuthCodes;
import static ru.netology.data.SQLHelper.cleanDataBase;

public class TransferTest {
    LoginPage loginPage;

//    @AfterEach
//    void tearDown() {
//        cleanAuthCodes();
//    }
//
//    @AfterAll
//    static void tearDownAll() {
//        cleanDataBase();
//    }

    @Test
    void transfer() {
        var authInfo = DataHelper.getAuthIfoWithTestData();
        APIHelper.login(authInfo);
        String verificationCode = SQLHelper.getVerificationCode();
        var verification = DataHelper.getVerifiCode(verificationCode);
        String token = APIHelper.getToken(verification);

//        verificationPage.verifyVerificationPage();
//        var verificationCode = SQLHelper.getVerificationCode();
//        verificationPage.validVerify(verificationCode);

    }
}
