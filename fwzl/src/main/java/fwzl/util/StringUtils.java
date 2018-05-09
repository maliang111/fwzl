package fwzl.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import fwzl.common.Constant;
import org.apache.commons.codec.digest.DigestUtils;
import sun.misc.BASE64Encoder;
import sun.security.provider.MD5;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * String工具类
 * @author 马亮
 */
public class StringUtils {

    public static final String NULL = "null";

    /**
     * 清除字符串的空白
     * @param str
     * @return
     */
    public static String trim(String str) {
        if (str == null || NULL.equals(str) || Constant.UNDEFINED.equals(str)) {
            return "";
        }
        return str.trim();
    }

    public static String convertEmptyToZero(String str) {
        return isEmply(str) ? "0" : trim(str);
    }

    public static boolean isEmply(String str) {
        return (str == null || "".equals(str) || NULL.equals(str));
    }


    /**
     * 生成房屋编号
     * @return
     */
    public static String generateHouseCode(Date leaseTime, Integer sort) {
        String code = "FW" + StringUtils.formatDate(leaseTime, "yyyyMMdd") + "_" + sort;
        return code;
    }

    /**
     * 将json字符串转换为map
     * @param json
     * @return
     */
    public static Map<String, String> jsonStrToObject(String json) {
        try {
            if (StringUtils.isEmply(StringUtils.trim(json))) {
                return null;
            }
            return new ObjectMapper().readValue(json, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("转换为map失败");
        }
    }


    public static String getExtName(String pictureName) {
        if (pictureName.lastIndexOf(Constant.DOT) >= 0) {
            return pictureName.substring(pictureName.lastIndexOf(Constant.DOT) + 1);
        } else {
            return "";
        }
    }


    public static String formatDate(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }

    /**
     * 对字符串进行加密
     * @param code
     * @param salt
     * @return
     */
    public static String digest(String code, String salt) {

        byte[] bytes = null;
        try {
            bytes = (code + salt).getBytes("utf-8");
            return (new BASE64Encoder()).encode(bytes);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("编码失败", e);
        }
    }

    public static String getUUID() {
        return String.valueOf(UUID.randomUUID()).replace("-", "");
    }


    /**
     * 将日期字符串转为指定格式，第二个参数为空默认yyyy-MM-dd
     * @param date
     * @param format
     * @return
     */
    public static Date convertStrToDate(String date, String format) {
        if (StringUtils.isEmply(format)) {
            format = "yyyy-MM-dd";
        }
        try {
            if (isEmply(date)) {
                return null;
            }
            return new SimpleDateFormat(format).parse(date);
        } catch (ParseException e) {
            throw new RuntimeException("转换日期失败", e);
        }
    }

    public static void main(String[] args) {
        String salt = getUUID();
        System.out.println(salt);
        System.out.println(digest("123456", salt));
        Integer i = null;
        System.out.println("".split(",").length);
        System.out.println(new Timestamp(System.currentTimeMillis()).getTime());
    }

}
