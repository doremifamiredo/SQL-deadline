package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;

public class SQLHelper {
    private static QueryRunner QUERY_RUNNER = new QueryRunner();

    private SQLHelper() {
    }

    private static Connection getConn() throws SQLException {
        return DriverManager.getConnection(System.getProperty("app.url"), "app", "pass");
    }

    @SneakyThrows
    public static String getVerificationCode() {
        var codeSQL = "SELECT code FROM auth_codes ORDER BY created DESC LIMIT 1";
        var conn = getConn();
        return QUERY_RUNNER.query(conn, codeSQL, new ScalarHandler<>());
    }

    @SneakyThrows
    public static String getFullCardNumber(String cardNumber) {
        String shortNumber = cardNumber.substring(15);
        var codeSQL = String.format("SELECT number FROM cards WHERE number LIKE '%%%s'", shortNumber);
        var conn = getConn();
        return QUERY_RUNNER.query(conn, codeSQL, new ScalarHandler<>());
    }

    @SneakyThrows
    public static Timestamp getTimeOfCodeCreation() {
        var codeCreated = "SELECT created FROM auth_codes ORDER BY created DESC LIMIT 1";
        var conn = getConn();
        return QUERY_RUNNER.query(conn, codeCreated, new ScalarHandler<>());
    }

    @SneakyThrows
    public static void cleanDataBase() {
        var connection = getConn();
        QUERY_RUNNER.execute(connection, "DELETE FROM auth_codes");
        QUERY_RUNNER.execute(connection, "DELETE FROM card_transactions");
        QUERY_RUNNER.execute(connection, "DELETE FROM cards");
        QUERY_RUNNER.execute(connection, "DELETE FROM users");
    }

    @SneakyThrows
    public static void cleanAuthCodes() {
        var connection = getConn();
        QUERY_RUNNER.execute(connection, "DELETE FROM auth_codes");
    }
}
