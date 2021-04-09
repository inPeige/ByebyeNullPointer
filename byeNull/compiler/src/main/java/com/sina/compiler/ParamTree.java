package com.sina.compiler;

import java.util.LinkedList;

public class ParamTree {
    private String mClassName;
    private String mValue;
    private LinkedList<ParamTree> mLinked=new LinkedList<>();

    public String getClassName() {
        return mClassName;
    }

    public void setClassName(String mClassName) {
        this.mClassName = mClassName;
    }

    public String getValue() {
        return mValue;
    }

    public void setValue(String mValue) {
        this.mValue = mValue;
    }

    public LinkedList<ParamTree> getLinked() {
        return mLinked;
    }

    @Override
    public String toString() {
        return "ParamTree{" +
                "mValue='" + mValue + '\'' +
                ", mLinked=" + mLinked +
                '}';
    }
}
