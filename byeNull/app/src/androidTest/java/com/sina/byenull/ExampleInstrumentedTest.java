package com.sina.byenull;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.sina.byenull", appContext.getPackageName());
        Status status1 = new Status();
        Status status2 = new Status();
        VideoInfo videoInfo = new VideoInfo();
        TagInfo tagInfo = new TagInfo();
        tagInfo.setActionLog(new ActionLog());
        videoInfo.setTag(new TagInfo());
        status1.setVideoInfo(videoInfo);
        status2.getTagInfo().getTagName();
        if (status2.getTagInfo() != null &&status2.getTagInfo().getActionLog()!=null) {
            //too do
        }
        boolean isNull1 = ByNullUtils.isNull(status1, Status$Consts.MVIDEOINFO$MTAG$ACTIONLOG);
        boolean isNull2 = ByNullUtils.isNull(status2, Status$Consts.MVIDEOINFO$MTAG$ACTIONLOG);
        Object value = ByNullUtils.getValue(status1, Status$Consts.MVIDEOINFO, null);
        if(value!=null){
            Log.i("ByeNull", ((VideoInfo)value).toString());
        }
        Log.i("ByeNull", String.valueOf(isNull1));
        Log.i("ByeNull", String.valueOf(isNull2));
    }
}