package ru.netology.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.APIHelper;
import ru.netology.data.DataHelper;
import ru.netology.data.DataHelper.Card;
import ru.netology.data.SQLHelper;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DataHelper.*;
import static ru.netology.data.SQLHelper.*;

public class TransferTest {
    int firstCardBalance;
    int secondCardBalance;

    @AfterEach
    void tearDown() {
        cleanAuthCodes();
    }

    @AfterAll
    static void tearDownAll() {
        cleanDataBase();
    }

    @Test
    void shouldTransferMoneyBetweenOwnCards() {
        var authInfo = getAuthIfoWithTestData();
        APIHelper.login(authInfo);
        String verificationCode = SQLHelper.getVerificationCode();
        var verification = getVerifiCode(verificationCode);
        String token = APIHelper.getToken(verification);
        Card[] cardsInfo = APIHelper.getCards(token);
        firstCardBalance = cardsInfo[0].getBalance();
        secondCardBalance = cardsInfo[1].getBalance();
        var amount = DataHelper.generateValidAmount(firstCardBalance);
        var transfer = getTransInfo(getFullCardNumber(cardsInfo[0].getNumber()), getFullCardNumber(cardsInfo[1].getNumber()),amount);
        APIHelper.transfer(transfer, token);
        cardsInfo = APIHelper.getCards(token);
        var actualFirstCardBalance = cardsInfo[0].getBalance();
        var actualSecondCardBalance = cardsInfo[1].getBalance();
        assertAll(() -> assertEquals(firstCardBalance - amount, actualFirstCardBalance),
                () -> assertEquals(secondCardBalance + amount, actualSecondCardBalance));
    }

    @Test
    void shouldNotTransferMoreMoneyThanIsInTheAccount() {
        var authInfo = getAuthIfoWithTestData();
        APIHelper.login(authInfo);
        String verificationCode = SQLHelper.getVerificationCode();
        var verification = getVerifiCode(verificationCode);
        String token = APIHelper.getToken(verification);
        Card[] cardsInfo = APIHelper.getCards(token);
        firstCardBalance = cardsInfo[0].getBalance();
        secondCardBalance = cardsInfo[1].getBalance();
        var transfer = getTransInfo(getFullCardNumber(cardsInfo[0].getNumber()), getFullCardNumber(cardsInfo[1].getNumber()),
                firstCardBalance + 1);
        APIHelper.transfer(transfer, token);
        cardsInfo = APIHelper.getCards(token);
        var actualFirstCardBalance = cardsInfo[0].getBalance();
        var actualSecondCardBalance = cardsInfo[1].getBalance();
        assertAll(() -> assertEquals(firstCardBalance, actualFirstCardBalance),
                () -> assertEquals(secondCardBalance, actualSecondCardBalance));
    }
}
