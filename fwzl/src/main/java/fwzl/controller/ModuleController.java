package fwzl.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fwzl.entity.User;
import fwzl.service.ModuleService;
import fwzl.util.StringUtils;
import fwzl.util.WebUtils;
import fwzl.vo.JsonResult;
import fwzl.vo.TreeNode;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 模块controller
 * @author 马亮
 */
@Controller
@CrossOrigin
@RequestMapping("/module/")
public class ModuleController {

    private static final Log LOG = LogFactory.getLog(ModuleController.class);

    @Autowired
    @Qualifier("moduleService")
    private ModuleService moduleService;

    @RequestMapping(value = "getModules.do", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String getModules(HttpSession session, String style) {

        String result = WebUtils.ERROR_MSG;
        try {
            style = StringUtils.isEmply(StringUtils.trim(style)) ? "ext" : StringUtils.trim(style);
            User user = (User) session.getAttribute("user");
            List<TreeNode> treeNodes = moduleService.findModuleByUserId(user.getId(), style);
            result = new ObjectMapper().writeValueAsString(treeNodes);
        } catch (Exception e) {
            LOG.error("加载模块失败", e);
        }
        return result;
    }

    @RequestMapping("getModuleUrl.do")
    @ResponseBody
    public String getModuleUrl(String id, String style) {

        String result = WebUtils.ERROR_MSG;
        try {
            style = StringUtils.isEmply(StringUtils.trim(style)) ? "ext" : StringUtils.trim(style);
            String url = moduleService.getModuleUrl(StringUtils.trim(id), style);
            result = new ObjectMapper().writeValueAsString(new JsonResult(url));
        } catch (Exception e) {
            LOG.error("查询url失败", e);
        }
        return result;
    }

}
