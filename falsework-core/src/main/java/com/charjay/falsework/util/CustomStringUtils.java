package com.charjay.falsework.util;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public final class CustomStringUtils {

    private CustomStringUtils() {
    }

    /**
     * 类名转为驼峰式<br>
     * TERMINAL --> Terminal<br>
     * LBS_TERMINAL_PHONE --> LbsTerminalPhone<br>
     * lbs_terminal_phone --> LbsTerminalPhone<br>
     * LBs_TERMInal_phonE --> LbsTerminalPhone<br>
     *
     * @param className
     * @return
     */
    public static String camelCase4Class( String className ) {
        if (className == null) {
            return null;
        }

        return camelCase( className, true );
    }

    /**
     * 字段名转为驼峰式<br>
     * NAME --> name<br>
     * showName  --> showname(友情提醒！)<br>
     * SHOW_NAME --> showName<br>
     * show_name --> showName<br>
     * SHoW_nAmE --> showName<br>
     *
     * @param fieldName
     * @return
     */
    public static String camelCase4Field( String fieldName ) {
        if (fieldName == null) {
            return null;
        }

        return camelCase( fieldName, false );
    }

    private static String camelCase( String str, boolean upperCaseFirstWord ) {
        char[] cs = str.toCharArray();
        StringBuilder sb = new StringBuilder( cs.length );
        boolean sign = upperCaseFirstWord;

        for (char c : cs) {
            if (c == '_') {
                sign = true;
                continue;
            }
            sb.append( sign ? Character.toUpperCase( c ) : Character.toLowerCase( c ) );
            sign = false;
        }

        return sb.toString();
    }

}

