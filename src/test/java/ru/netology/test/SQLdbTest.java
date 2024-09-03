package ru.netology.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.netology.data.APIHelper;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import static ru.netology.data.DataHelper.getAuthIfoWithTestData;
import static ru.netology.data.SQLHelper.*;

public class SQLdbTest {

    @Test
    void tearDownAll1() {
        cleanDataBase();
    }

    @Test
    void checkCodeCreationTime() {
        var authInfo = getAuthIfoWithTestData();
        APIHelper.login(authInfo);
        Timestamp expectedTime = new Timestamp(System.currentTimeMillis());
        Timestamp actualTimeInDb = getTimeOfCodeCreation();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd.MM.yy");
        Assertions.assertEquals(dateFormat.format(expectedTime), dateFormat.format(actualTimeInDb));
    }
}

