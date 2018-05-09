package fwzl.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import fwzl.entity.House;
import fwzl.entity.User;
import fwzl.service.HouseService;
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
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 房屋controller
 * @author 马亮
 */
@CrossOrigin
@Controller
@RequestMapping("/house/")
public class HouseController {

    @Autowired
    @Qualifier("houseService")
    private HouseService houseService;

    private static final Log LOG = LogFactory.getLog(HouseController.class);

    @RequestMapping("houseInfo.do")
    public String houseInfoUI() {
        return "house/houseInfo";
    }

    @RequestMapping("houseForm.do")
    public String houseForm() {
        return "house/houseForm";
    }

    @RequestMapping(value = "findHouses.do", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String findHouses(HttpServletRequest request) {
        String result = WebUtils.ERROR_MSG;
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        try {
           Map<String, Object> map = WebUtils.getRequestParams(request);
           if (!StringUtils.isEmply((String) map.get("username"))) {
               User user = (User) request.getSession().getAttribute("user");
               map.put("ownerId", user.getId());
           }
            PageObject pageObject =
                    houseService.findHouses(map);
            result = mapper.writeValueAsString(pageObject);
        } catch (Exception e) {
            LOG.error("查询房屋信息失败", e);
        }
        return result;
    }

    @RequestMapping("deleteHouses.do")
    @ResponseBody
    public String deleteHouses(String ids) {
        String result = WebUtils.ERROR_MSG;
        try {
            houseService.deleteHouses(StringUtils.trim(ids));
            result = new ObjectMapper().writeValueAsString(new JsonResult());
        } catch (Exception e) {
            LOG.error("批量删除失败", e);
        }
        return result;
    }

    @RequestMapping("saveHouse.do")
    @ResponseBody
    public String saveHouse(HttpServletRequest request) {
        String result = WebUtils.ERROR_MSG;
        Map<String, Object> map = WebUtils.getRequestParams(request);
        User user = (User) request.getSession().getAttribute("user");
        map.put("ownerId", user.getId());
        map.put("modifiedUser", user.getUsername());
        try {
            houseService.saveHouse(map);
            result = new ObjectMapper().writeValueAsString(new JsonResult());
        } catch (Exception e) {
            LOG.error("保存房屋信息失败", e);
        }
        return result;
    }

    @RequestMapping(value = "findHouseById.do", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String findHouseById(String houseId) {
        String result = WebUtils.ERROR_MSG;
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        try {
            Map<String, Object> houseMap = houseService.getHouseById(houseId);
            result = mapper.writeValueAsString(new JsonResult(houseMap));
        } catch (Exception e) {
            LOG.error("查询房屋信息失败", e);
        }
        return result;
    }

    @RequestMapping("deleteHouse.do")
    @ResponseBody
    public String deleteHouse(String houseId) {
        String result = WebUtils.ERROR_MSG;
        try {
            houseService.deleteHouse(StringUtils.trim(houseId));
            result = new ObjectMapper().writeValueAsString(new JsonResult());
        } catch (Exception e) {
            LOG.error("删除房屋信息失败", e);
        }
        return result;
    }

}
