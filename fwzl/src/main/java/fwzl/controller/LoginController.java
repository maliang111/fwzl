package fwzl.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fwzl.common.Constant;
import fwzl.entity.User;
import fwzl.service.UserService;
import fwzl.util.StringUtils;
import fwzl.util.WebUtils;
import fwzl.vo.JsonResult;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * 用户登录controller
 *
 * @author 马亮
 */
@Controller
@CrossOrigin
public class LoginController {

    private static final Log LOG = LogFactory.getLog(LoginController.class);


    @Autowired
    @Qualifier("userService")
    private UserService userService;

    /**
     * 用户登录
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("login.do")
    @ResponseBody
    public String login(HttpServletRequest request, HttpServletResponse response) {
        String result = WebUtils.ERROR_MSG;
        try {
            String username = StringUtils.trim(request.getParameter("username"));
            String password = StringUtils.trim(request.getParameter("password"));
            User user = userService.login(username, password);
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            result = new ObjectMapper().writeValueAsString(new JsonResult());
        } catch (Exception e) {
            LOG.error("登录失败", e);
        }
        return result;
    }


    /**
     * 注销
     * @param session
     * @return
     */
    @RequestMapping("logout.do")
    @ResponseBody
    public String logout(HttpSession session) {
        String result = WebUtils.ERROR_MSG;
        try {
            session.removeAttribute("user");
            result = new ObjectMapper().writeValueAsString(new JsonResult());
        } catch (Exception e) {
            LOG.error("注销失败", e);
        }
        return result;
    }

    /**
     * 主页面映射
     * @param request
     * @return
     */
    @RequestMapping("indexUI.do")
    public String indexUI(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        if (user == null) {
            return "redirect:loginUI.do";
        }
        boolean isAdmin = false;
        List<Map<String, Object>> roleList = userService.getUserRole(user.getId());
        for (Map<String, Object> role : roleList) {
            if (Constant.ROLE_ADMIN.equals(role.get("roleName"))) {
                isAdmin = true;
                break;
            }
        }

        if (isAdmin) {
            //管理员
            return "adminIndex";
        } else {
            //普通用户
            return "userIndex";
        }
    }

    /**
     * 登录页面映射
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("loginUI.do")
    public String loginUI(HttpServletRequest request, HttpServletResponse response) {
        return "login";
    }

}
