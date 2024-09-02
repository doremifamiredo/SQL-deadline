package ru.netology.test;

import org.junit.jupiter.api.Test;

import static ru.netology.data.SQLHelper.cleanDataBase;

public class ClearDataBase {
    @Test
    void tearDownAll1() {
        cleanDataBase();
    }
}
