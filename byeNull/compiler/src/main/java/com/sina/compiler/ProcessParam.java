package com.sina.compiler;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

/**
 * 从注解中解析出来的待处理参数
 * public class ClassName&Consts.CALSS_CONSTS{
 *      public static final String B="d";
 *      public static final String C="e";
 *      public static final String E="e";
 * }
 *
 *
 */
public class ProcessParam {
    private Element element;
    private String fieldValue;

    public ProcessParam(Element element, String fieldValue) {
        this.element = element;
        this.fieldValue = fieldValue;
    }

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    @Override
    public String toString() {
        return "ProcessParam{" +
                "element=" + element +
                ", fieldValue='" + fieldValue + '\'' +
                '}';
    }
}
