package com.learn.service.impl;

import com.learn.form.UserForm;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Class содержит методя для валидации данных. введённых пользователем
 *
 * @author Volodymyr Lopachak
 */
public class UserFormValidator {
    public static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public static final Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    /**
     * Метод для валидации логина
     * @param form - форма  с данными. введёнными пользователем
     * @param errors - карта ошибок
     */
    public void validateLogin(UserForm form, Map<String, String> errors) {
        if (form.getLogin() == null || form.getLogin().trim().isEmpty()) {
            errors.put("login", "incorrect login");
            form.setLogin(null);
        }
    }

    /**
     * Метод для валидации пароля
     * @param form - форма  с данными. введёнными пользователем
     * @param errors - карта ошибок
     */
    public void validatePasswords(UserForm form, Map<String, String> errors) {
        if (form.getPassword().length() == 0 || form.getPassword_again().length() == 0) {
            form.setPassword(null);
            form.setPassword_again(null);
            errors.put("password", "Password is required");
            return;
        }
        if (form.getPassword() == null || form.getPassword_again() == null) {
            errors.put("password", "Password is required");
            form.setPassword(null);
            form.setPassword_again(null);
            return;
        }
        if (!form.getPassword().equals(form.getPassword_again())) {
            errors.put("password", "Password again must be equal to password!");
            form.setPassword(null);
            form.setPassword_again(null);
        }
    }

    /**
     * Метод для валидации пошты
     * @param form - форма  с данными. введёнными пользователем
     * @param errors - карта ошибок
     */
    public void validateEmail(UserForm form, Map<String, String> errors) {
        if (form.getEmail() == null || form.getEmail().isEmpty()) {
            errors.put("email", "Email is required");
            form.setEmail(null);
            return;
        }
        if (!emailPattern.matcher(form.getEmail()).matches()) {
            errors.put("email", "Email has invalid format");
            form.setEmail(null);
        }
    }

    /**
     * Метод для валидации имени
     * @param form - форма  с данными. введёнными пользователем
     * @param errors - карта ошибок
     */
    public void validateFirstNames(UserForm form, Map<String, String> errors) {
        if (form.getFirstName() == null || form.getFirstName().trim().isEmpty()) {
            errors.put("first", "First name is required");
            form.setFirstName(null);
        }
    }

    /**
     * Метод для валидации фамилии
     * @param form - форма  с данными. введёнными пользователем
     * @param errors - карта ошибок
     */
    public void validateLastNames(UserForm form, Map<String, String> errors) {
        if (form.getLastName() == null || form.getLastName().trim().isEmpty()) {
            errors.put("last", "last name is required");
            form.setLastName(null);
        }
    }

    /**
     * Метод для валидации даты
     * @param form - форма  с данными. введёнными пользователем
     * @param errors - карта ошибок
     */
    public void validateDate(UserForm form, Map<String, String> errors) {
        if (form.getDob() == null) {
            errors.put("dob", "Birthday is required");
            return;
        }
        try {
            DateFormat df = new SimpleDateFormat(DATE_FORMAT);
            df.setLenient(false);
            df.parse(form.getDob());
        } catch (ParseException e) {
            errors.put("dob", "Wrong date format");
            form.setDob(null);
        }
    }
}
