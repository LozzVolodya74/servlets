package com.learn.tags;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

/**
 * Класс, обрабатывающий тег, получающий возраст пользователя
 *
 * @author Volodymyr Lopachak
 * @version 1.0 27 November 2021
 */
public class AgeTag extends TagSupport {

    private Date age;

    public void setAge(Date age) {
        this.age = age;
    }

    public int doStartTag() {
        JspWriter out = pageContext.getOut();
        try {
            out.print(getAge(age));
        } catch (IOException e) {
        }
        return SKIP_BODY;
    }

    private int getAge(Date date) {
        Calendar born = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        born.setTime(date);
        return now.get(Calendar.YEAR) - born.get(Calendar.YEAR);
    }
}
