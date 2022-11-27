package cn.luckycurve.avdc.util;

import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LuckyCurve
 */
public class CookieUtil {
    public static final String COOKIE_SPLIT = "; ";

    public static final String ITEM_SPLIT = "=";

    public static Map<String, String> cookieParse(String cookie) {
        HashMap<String, String> result = Maps.newHashMap();
        String[] items = cookie.split(COOKIE_SPLIT);
        for (String item : items) {
            String[] split = item.split(ITEM_SPLIT);
            result.put(split[0], split[1]);
        }

        return result;
    }
}
