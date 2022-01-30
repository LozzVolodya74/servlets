package com.learn.exception;

/**
 * Class для описания ошибки, которая возникла на уровне DAO
 *
 * @author Volodymyr Lopachak
 * @version 1.0 28 October 2021
 */
public class MySQLException extends Exception {
    private String massage;

    public MySQLException(String massage) {
        this.massage = massage;
    }

    public String getMassage() {
        return massage;
    }
}
