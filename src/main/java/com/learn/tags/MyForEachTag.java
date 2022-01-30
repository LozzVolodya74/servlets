package com.learn.tags;

import java.util.Iterator;
import java.util.List;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Класс, обрабатывающий тег итерации по колекции
 *
 * @author Volodymyr Lopachak
 * @version 1.0 08 September 2021
 */
public class MyForEachTag extends TagSupport {

    private Iterator<?> iterator;

    private String var;

    @Override
    public int doStartTag() {
        return handleIterationTag(EVAL_BODY_INCLUDE);
    }

    @Override
    public int doAfterBody() {
        return handleIterationTag(EVAL_BODY_AGAIN);
    }

    private int handleIterationTag(int continueResult) {
        if (iterator.hasNext()) {
            final Object object = iterator.next();
            pageContext.setAttribute(var, object);
            return continueResult;
        } else {
            return SKIP_BODY;
        }
    }

    public void setItems(List<?> items) {
        this.iterator = items.iterator();
    }

    public void setVar(String var) {
        this.var = var;
    }
}
