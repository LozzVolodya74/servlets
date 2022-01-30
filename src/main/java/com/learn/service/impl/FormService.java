package com.learn.service.impl;

import com.learn.form.UserForm;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Class содержит поля для работы с данными формы
 *
 * @author Volodymyr Lopachak
 */
public class FormService {

    /**
     * Метод заполняет форму данными, внесёнными пользователем
     *
     * @param form - форма
     * @param req  - HttpServletRequest
     */
    public void fillForm(UserForm form, final HttpServletRequest req) {
        if (form.getLogin() == null) {
            form.setLogin(req.getParameter("login").trim());
        }
        if (form.getPassword() == null) {
            form.setPassword(req.getParameter("password").trim());
        }
        if (form.getPassword_again() == null) {
            form.setPassword_again(req.getParameter("passwordAgain").trim());
        }
        if (form.getEmail() == null) {
            form.setEmail(req.getParameter("email").trim());
        }
        if (form.getFirstName() == null) {
            form.setFirstName(req.getParameter("firstName").trim());
        }
        if (form.getLastName() == null) {
            form.setLastName(req.getParameter("lastName").trim());
        }
        if (form.getDob() == null) {
            form.setDob(req.getParameter("birthday").trim());
        }
    }

    /**
     * Метод валидирует данные, внесённые пользователем
     *
     * @param form   - форма с данными
     * @param errors - карта ошибок
     * @return - заполненую карту ошибок кара ошибок в формате ключ/сообщение
     */
    public Map<String, String> validForm(UserForm form, Map<String, String> errors) {
        UserFormValidator validator = new UserFormValidator();
        validator.validateLogin(form, errors);
        validator.validatePasswords(form, errors);
        validator.validateEmail(form, errors);
        validator.validateFirstNames(form, errors);
        validator.validateLastNames(form, errors);
        validator.validateDate(form, errors);
        return errors;
    }

    // вносятся невалидные поля для отображения на форме

    /**
     * Метод заполняет холдеры для отображения невалидных полей
     *
     * @param req    - HttpServletRequest
     * @param form   - форма
     * @param errors - кара ошибок в формате ключ/сообщение
     * @return
     */
    public HttpServletRequest filErrorAttribute(HttpServletRequest req, UserForm form, Map<String, String> errors) {
        if (!errors.containsKey("login")) {
            req.setAttribute("log_ph", form.getLogin());
        }
        if (!errors.containsKey("password")) {
            req.setAttribute("pas_ph", form.getPassword());
        }
        if (!errors.containsKey("email")) {
            req.setAttribute("email_ph", form.getEmail());
        }
        if (!errors.containsKey("first")) {
            req.setAttribute("first_ph", form.getFirstName());
        }
        if (!errors.containsKey("last")) {
            req.setAttribute("last_ph", form.getLastName());
        }
        if (!errors.containsKey("dob")) {
            req.setAttribute("dob_ph", form.getDob());
        }
        return req;
    }
}
