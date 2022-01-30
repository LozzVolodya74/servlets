package com.learn.service.impl;

import com.learn.service.XssFixer;

/**
 * Class содержит метд для защиты от Xss - атак
 *
 * @author Volodymyr Lopachak
 * @version 1.0 26 September 2021
 */
public class XssFixerImpl implements XssFixer {

    /**
     * Метод заменяет значения "<" и ">" на альтернативные
     *
     * @param content - строковое представление поля
     * @return - модифицированную строку
     */
    @Override
    public String fix(String content) {
        return content.replace("<", "&lt;").replace(">", "&gt;");
    }
}
