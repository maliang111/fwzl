package fwzl.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import fwzl.service.HouseService;
import fwzl.util.WebUtils;
import fwzl.vo.JsonResult;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * 统计分析控制器
 * @author 马亮
 */
@Controller
@RequestMapping("/tjfx/")
public class TjfxController {

    @Autowired
    @Qualifier("houseService")
    private HouseService houseService;

    private static final Log LOG = LogFactory.getLog(TjfxController.class);

    @RequestMapping("tjfx.do")
    public String tjfxUI() {
        return "sys/tjfx";
    }

    @RequestMapping("houseDailyChart.do")
    public String houseDailyChart() {
        return "chart/houseDailyChart";
    }


    @RequestMapping("personChart.do")
    public String personChart() {
        return "chart/personChart";
    }




    @RequestMapping("getHouseDailyCount.do")
    @ResponseBody
    public String getHouseDailyCount() {

        String result = WebUtils.ERROR_MSG;
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        try {
            List<Map<String, Object>> mapList = houseService.findHouseGroupByLeaseTime();
            result = mapper.writeValueAsString(new JsonResult(mapList));
        } catch (Exception e) {
            LOG.error("查询每日房屋发布数失败");
        }
        return result;
    }


    @RequestMapping("getPersonalCount.do")
    @ResponseBody
    public String getPersonalCount() {
        String result = WebUtils.ERROR_MSG;
        try {
            List<Map<String, Object>> mapList = houseService.findHouseGroupByOwner();
            result = new ObjectMapper().writeValueAsString(new JsonResult(mapList));
        } catch (Exception e) {
            LOG.error("查询每人发布数失败");
        }
        return result;
    }

}
