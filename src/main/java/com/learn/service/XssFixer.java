package com.learn.service;

/**
 * Interface содержит метд для защиты от Xss - атак
 *
 * @author Volodymyr Lopachak
 * @version 1.0 26 September 2021
 */
public interface XssFixer {

    /**
     * Метод заменяет значения "<" и ">" на альтернативные
     *
     * @param content - строковое представление поля формы
     * @return - обработанное поле формы
     */
    String fix(String content);
}