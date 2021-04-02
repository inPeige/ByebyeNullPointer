package com.sina.byenull;


import com.sina.compiler.annotation.ByeNull;
import com.sina.compiler.annotation.ByeNullField;

/**
 * class Status$Con{
 *     public String status_abc_def_jgp_zfs
 *
 *   boolean a=  Utils.value(obj,Status$Con.status_abc_def_jgp_zfs);
 * }
 */
@ByeNull
public class Status {

    private String mid;
    @ByeNullField
    private Object abc;
}

