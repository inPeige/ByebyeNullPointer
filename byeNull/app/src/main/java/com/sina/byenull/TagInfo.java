package com.sina.byenull;

import com.sina.compiler.annotation.ByeNullField;

public class TagInfo {
    private String mTagName;
    @ByeNullField
    private ActionLog actionLog;
    @ByeNullField
    private UserInfo userInfo;
    public String getTagName() {
        return mTagName;
    }

    public void setTagName(String mTagName) {
        this.mTagName = mTagName;
    }
}
