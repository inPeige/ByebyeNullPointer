package com.sina.byenull;

import com.sina.compiler.annotation.ByeNullField;

public class VideoInfo {
    @ByeNullField
    private TagInfo mTag;
    @ByeNullField
    private CommInfo mComm;
    @ByeNullField
    private TagList mTagList;

    public TagInfo getTag() {
        return mTag;
    }

    public void setTag(TagInfo mTag) {
        this.mTag = mTag;
    }

    public CommInfo getComm() {
        return mComm;
    }

    public void setComm(CommInfo mComm) {
        this.mComm = mComm;
    }
}
