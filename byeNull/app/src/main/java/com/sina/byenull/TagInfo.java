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

    public ActionLog getActionLog() {
        return actionLog;
    }

    public void setActionLog(ActionLog actionLog) {
        this.actionLog = actionLog;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
