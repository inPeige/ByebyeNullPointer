package com.sina.byenull;


import com.sina.compiler.annotation.ByeNull;
import com.sina.compiler.annotation.ByeNullField;

/**
 * class Status$Con{
 *     public String MVIDEOINFO$MTAG$MADINFO="mVideoInfo$mTag$mAdInfo";
 *     public String MVIDEOINFO$MCOMM"mVideoInfo$mComm";
 * }
 */
@ByeNull
public class Status {

    private String mid;
    @ByeNullField
    private VideoInfo mVideoInfo;
    @ByeNullField
    private TagInfo mTagInfo;
}

