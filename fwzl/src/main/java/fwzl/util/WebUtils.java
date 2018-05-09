package fwzl.util;

import fwzl.common.Constant;
import fwzl.entity.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * web工具类
 * @author 马亮
 */
public class WebUtils {


    public static final String ERROR_MSG  = "{\"msg\": \"error\", \"success\" : 0, \"data\" : null}";


    /**
     * 获取请求参数
     * @param request
     * @return
     */
    public static Map<String, Object> getRequestParams(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        Enumeration enumeration = request.getParameterNames();
        while (enumeration.hasMoreElements()) {
            String name = (String) enumeration.nextElement();
            map.put(name, StringUtils.trim(request.getParameter(name)));
        }
        return map;
    }


    /**
     * 获取服务器的图片资源路径
     * @param request
     * @return
     */
    public static String getServerPath(HttpServletRequest request) {
        return request.getSession().getServletContext().getRealPath("picture");
    }

    /**
     * 获取完整的请求路径
     * @param request
     * @return
     */
    public static String getContextPath(HttpServletRequest request) {
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName()
                + ":" + request.getServerPort() + path;
        return basePath;
    }


}
