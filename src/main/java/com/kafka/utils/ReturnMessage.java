package com.kafka.utils;

import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Description: 约定返回格式
 * Version: 1.0
 * Create Date Time: 2018/9/20 16:19.
 *
 * @see
 */
public class ReturnMessage {

    public static Map<String, Object> jsonData(boolean result, Object data, Object errcode, Object msg) {
        Map<String, Object> retMap = new HashMap<>();
        retMap.put("result", result);
        retMap.put("data", data);
        retMap.put("errcode", errcode);
        if (!StringUtils.isEmpty(msg)) {
            retMap.put("msg", msg);
        }

        return retMap;
    }

    public static Map<String, Object> jsonData(boolean result, Object msg) {
        Map<String, Object> retMap = new HashMap<>();
        retMap.put("result", result);
        retMap.put("data", null);
        retMap.put("errcode", null);
        if (!StringUtils.isEmpty(msg)) {
            retMap.put("msg", msg);
        }

        return retMap;
    }

    public static Map<String, Object> jsonData(boolean result) {
        Map<String, Object> retMap = new HashMap<>();
        retMap.put("result", result);
        retMap.put("data", null);
        retMap.put("errcode", null);
        if (result)
            retMap.put("msg", Constants.SUCCESS_MSG);
        else
            retMap.put("msg", Constants.FAIL_MSG);

        return retMap;
    }
}
