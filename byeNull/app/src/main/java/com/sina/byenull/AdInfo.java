package com.sina.byenull;

import com.sina.compiler.annotation.ByeNull;
import com.sina.compiler.annotation.ByeNullField;
@ByeNull
public class AdInfo {
    @ByeNullField
    VideoInfo mVideoInfo;
    @ByeNullField
    TagInfo mTagInfo;
}
