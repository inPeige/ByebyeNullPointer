package com.sina.byenull;

import android.text.TextUtils;

import java.lang.reflect.Field;

public class ByNullUtils {

    public static boolean isNull(Object mObj,String path){
        if (TextUtils.isEmpty(path) || mObj == null) {
            return false;
        }
        String[] fieldArray = parsPath(path);
        if(fieldArray.length<=0){
            return false;
        }
        Object mFieldObj=mObj;
        try {
            for (String s : fieldArray) {
                mFieldObj = isFieldNull(mFieldObj, s);
                if(mFieldObj==null){
                    return false;
                }
            }
            return true;
        }catch ( NoSuchFieldException|IllegalAccessException e){
            return false;
        }
    }

    private static Object isFieldNull(Object mObj, String mField) throws NoSuchFieldException, IllegalAccessException {
        Class<?> mClass = mObj.getClass();
        Field mClassField =  mClass.getDeclaredField(mField);
        mClassField.setAccessible(true);
        return mClassField.get(mObj);
    }
    /**
     *
     */
    private static String[] parsPath(String path){
       return path.split("\\$");
    }
}
