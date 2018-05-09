package fwzl.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import fwzl.entity.User;
import fwzl.service.RentOrderService;
import fwzl.util.StringUtils;
import fwzl.util.WebUtils;
import fwzl.vo.JsonResult;
import fwzl.vo.PageObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * 账目管理controller
 * @author 马亮
 */
@Controller
@RequestMapping("/zmgl/")
public class ZmglController {

    @Autowired
    @Qualifier("rentOrderService")
    private RentOrderService rentOrderService;

    private static final Log LOG = LogFactory.getLog(ZmglController.class);

    @RequestMapping("zmgl.do")
    public String zmglUI() {
        return "sys/zmgl";
    }

    @RequestMapping("zmglEditUI.do")
    public String zmglEditUI() {
        return "sys/zmglEdit";
    }

    @RequestMapping("findZmxx.do")
    @ResponseBody
    public String findZmxx(HttpServletRequest request) {
        String result = WebUtils.ERROR_MSG;
        Map<String, Object> map = WebUtils.getRequestParams(request);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        try {
            PageObject pageObject = rentOrderService.findZmxx(map);
            result = mapper.writeValueAsString(pageObject);
        } catch (Exception e) {
            LOG.error("查询失败", e);
        }
        return result;
    }

    @RequestMapping("findZmxxById.do")
    @ResponseBody
    public String findZmxxById(String id) {
        String result = WebUtils.ERROR_MSG;
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        try {
            Map<String, Object> map = rentOrderService.findZmxxById(StringUtils.trim(id));
            result = mapper.writeValueAsString(new JsonResult(map));
        } catch (Exception e) {
            LOG.error("查询账目信息失败", e);
        }
        return result;
    }

    @RequestMapping("doSaveZmxx.do")
    @ResponseBody
    public String doSaveZmxx(HttpServletRequest request) {
        String result = WebUtils.ERROR_MSG;
        Map<String, Object> map = WebUtils.getRequestParams(request);
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            map.put("username", user.getUsername());
        }
        try {
            rentOrderService.saveRentOrder(map);
            result = new ObjectMapper().writeValueAsString(new JsonResult());
        } catch (Exception e) {
            LOG.error("保存信息失败", e);
        }
        return result;
    }

    @RequestMapping("deleteZmxx.do")
    @ResponseBody
    public String deleteZmxx(String orderId, String houseId) {
        String result = WebUtils.ERROR_MSG;
        try {
            rentOrderService.deleteRentOrder(StringUtils.trim(orderId), StringUtils.trim(houseId));
            result = new ObjectMapper().writeValueAsString(new JsonResult());
        } catch (Exception e) {
            LOG.error("删除账目信息失败", e);
        }
        return result;
    }

}
