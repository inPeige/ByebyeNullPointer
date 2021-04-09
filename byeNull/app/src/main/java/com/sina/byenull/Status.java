package com.sina.byenull;


import com.sina.compiler.annotation.ByeNull;
import com.sina.compiler.annotation.ByeNullField;

/**
 * class Status$Con{
 *     public String MVIDEOINFO$MTAG$MADINFO="mVideoInfo$mTag";
 *     public String MVIDEOINFO$MCOMM"mTagInfo";
 * }
 */
@ByeNull
public class Status {

    private String mid;
    @ByeNullField
    private VideoInfo mVideoInfo;
    @ByeNullField
    private TagInfo mTagInfo;

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public VideoInfo getVideoInfo() {
        return mVideoInfo;
    }

    public void setVideoInfo(VideoInfo mVideoInfo) {
        this.mVideoInfo = mVideoInfo;
    }

    public TagInfo getTagInfo() {
        return mTagInfo;
    }

    public void setTagInfo(TagInfo mTagInfo) {
        this.mTagInfo = mTagInfo;
    }
}

