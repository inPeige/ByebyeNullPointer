package com.sina.compiler;

import java.util.ArrayList;

/**
 * 从注解中解析出来的待处理参数
 * public class ClassName&Consts.CALSS_CONSTS{
 * public static final String A$B$C$D="a$b$c$d";
 * public static final String C$B$C$D="a$b$c$d";
 * public static final String D$B$C$D="a$b$c$d";
 * public static final String E$B$C$D="a$b$c$d";
 * }
 */
public class ClassParam {
    private String className;
    private ArrayList<ParamTree> mFieldValue;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public ArrayList<ParamTree> getFieldValue() {
        return mFieldValue;
    }

    public void setFieldValue(ArrayList<ParamTree> mFieldValue) {
        this.mFieldValue = mFieldValue;
    }

    @Override
    public String toString() {
        return "ClassParam{" +
                "className='" + className + '\'' +
                ", mFieldValue=" + mFieldValue +
                '}';
    }
}
