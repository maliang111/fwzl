package fwzl.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import fwzl.common.Constant;
import fwzl.entity.User;
import fwzl.service.UserService;
import fwzl.util.StringUtils;
import fwzl.util.WebUtils;
import fwzl.vo.JsonResult;
import fwzl.vo.PageObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户controller
 * @author 马亮
 */
@Controller
@CrossOrigin
@RequestMapping("/user/")
public class UserController {

    private static final Log LOG = LogFactory.getLog(UserController.class);

    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @RequestMapping("yhxx.do")
    public String userInfoUI() {
        return "user/userInfo";
    }

    @RequestMapping("userEditUI.do")
    public String userEditInfo(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        String id = StringUtils.trim(request.getParameter("id"));
        //设置调用该页面的来源，如果是从主页面调用，则将当前登录的管理员id传递到页面上，否则不显示
        //防止在账目管理页面当没有账目信息时下方会显示管理员的情况，以及传入id为空时主页面的修改用户页面无法查询
        String source = StringUtils.trim(request.getParameter("source"));
        if (!StringUtils.isEmply(source)) {
            id = String.valueOf(user.getId());
        }
        request.setAttribute("id", id);
        return "user/userEdit";
    }

    @RequestMapping(value = "getUser.do", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String getUser(HttpSession session) {
        String result = WebUtils.ERROR_MSG;
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        try {
            User user = (User) session.getAttribute("user");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("username", user.getUsername());
            map.put("realName", user.getRealName());
            map.put("email", user.getEmail());
            map.put("gender", user.getGender());
            map.put("birthday", user.getBirthday());
            map.put("mobile", user.getMobile());
            result = mapper.writeValueAsString(new JsonResult(map));
        } catch (Exception e) {
            LOG.error("获取用户失败", e);
        }
        return result;
    }


    @RequestMapping("findUserById.do")
    @ResponseBody
    public String findUserById(String id, HttpSession session) {
        String result = WebUtils.ERROR_MSG;
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        try {
            User user = null;
            if (StringUtils.isEmply(id)) {
                user = (User) session.getAttribute("user");
                Map<String, Object> map = new HashMap<>();
                map.put("id", user.getId());
                map.put("username", user.getUsername());
                map.put("realName", user.getRealName());
                map.put("email", user.getEmail());
                map.put("mobile", user.getMobile());
                map.put("birthday", user.getBirthday());
                map.put("gender", user.getGender());
                result = mapper.writeValueAsString(new JsonResult(map));
            } else {
             user = userService.findUserById(StringUtils.trim(id));
                result = mapper.writeValueAsString(new JsonResult(user));
            }
        } catch (Exception e) {
            LOG.error("获取用户失败", e);
        }
        return result;
    }


    @RequestMapping("register.do")
    @ResponseBody
    public String register(HttpServletRequest request) {
        String result = WebUtils.ERROR_MSG;
        Map<String, Object> map = WebUtils.getRequestParams(request);
        try {
            userService.saveUser(map);
            result = new ObjectMapper().writeValueAsString(new JsonResult());
        } catch (Exception e) {
            LOG.error("注册失败", e);
        }
        return result;
    }

    @RequestMapping("updateUser.do")
    @ResponseBody
    public String updateUser(HttpServletRequest request) {
        String result = WebUtils.ERROR_MSG;
        try {
            Map<String, Object> map = WebUtils.getRequestParams(request);
            User user = (User) request.getSession().getAttribute("user");
            if (StringUtils.isEmply(String.valueOf(map.get("userId")))) {
                map.put("userId", user.getId());
                map.put("username", user.getUsername());
            }
            User user2 = userService.updateUser(map);
            //若修改的是当前的用户，根据id判断，则将新的值覆盖到session中
            if (user.getId().equals(user2.getId())) {
                user2.setPassword(user.getPassword());
                user2.setSalt(user.getSalt());
                user2.setSort(user.getSort());
                user2.setCreatedTime(user.getCreatedTime());
                user2.setCreatedUser(user.getCreatedUser());
                request.getSession().setAttribute("user", user2);
            }
            result = new ObjectMapper().writeValueAsString(new JsonResult());
        } catch (Exception e) {
            LOG.error("更新失败", e);
        }
        return result;
    }

    @RequestMapping(value = "updatePassword.do", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String updatePassword(HttpServletRequest request) {
        String result = WebUtils.ERROR_MSG;
        try {
            Map<String, Object> map = WebUtils.getRequestParams(request);
            User user = (User) request.getSession().getAttribute("user");
            if (user != null) {
                map.put("userId", user.getId());
                map.put("password", user.getPassword());
                map.put("salt", user.getSalt());
            }
            userService.updatePassword(map);
            user.setPassword(StringUtils.digest(String.valueOf(map.get("password")), user.getSalt()));
            result = new ObjectMapper().writeValueAsString(new JsonResult());
        } catch (Exception e) {
            LOG.error("更新失败", e);
            Map error = StringUtils.jsonStrToObject(result);
            error.put("msg", e.getMessage());
            try {
                result = new ObjectMapper().writeValueAsString(error);
            } catch (JsonProcessingException e1) {
                LOG.error("转换失败", e);
            }
        }
        return result;
    }


    @RequestMapping("findUserByUsername.do")
    @ResponseBody
    public String findUserByUsername(String username) {
        String result = WebUtils.ERROR_MSG;
        try {
            if (userService.findUserByUsername(username) == Constant.NOT_FOUND) {
                result = new ObjectMapper().writeValueAsString(new JsonResult());
            }
        } catch (Exception e) {
            LOG.error("查询失败", e);
        }
        return result;
    }


    @RequestMapping("findUsers.do")
    @ResponseBody
    public String findUsers(HttpServletRequest request) {

        String result = WebUtils.ERROR_MSG;
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        try {
            Map<String, Object> map = WebUtils.getRequestParams(request);
            PageObject page = userService.getUsersByPage(map);
            result = mapper.writeValueAsString(page);
        } catch (Exception e) {
            LOG.error("查询用户失败", e);
        }
        return result;
    }

    @RequestMapping("deleteUser.do")
    @ResponseBody
    public String deleteUser(String id) {
        String result = WebUtils.ERROR_MSG;
        try {
            userService.deleteUser(StringUtils.trim(id));
            result = new ObjectMapper().writeValueAsString(new JsonResult());
        } catch (Exception e) {
            LOG.error("删除失败", e);
        }
        return result;
    }

    @RequestMapping("deleteUsers.do")
    @ResponseBody
    public String deleteUsers(String ids) {
        String result = WebUtils.ERROR_MSG;
        try {
            userService.deleteUsers(ids);
            result = new ObjectMapper().writeValueAsString(new JsonResult());
        } catch (Exception e) {
            LOG.error("批量删除失败");
        }
        return result;
    }

}
