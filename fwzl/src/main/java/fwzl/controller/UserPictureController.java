package fwzl.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fwzl.entity.User;
import fwzl.entity.UserPicture;
import fwzl.service.UserPictureService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * 用户图片controller
 * @author 马亮
 */
@CrossOrigin
@Controller
public class UserPictureController {

    @Autowired
    @Qualifier("userPictureService")
    private UserPictureService userPictureService;

    private static final Log LOG = LogFactory.getLog(UserPictureController.class);

    @RequestMapping("getUserPicture.do")
    @ResponseBody
    public String getUserPicture(HttpServletRequest request, HttpSession session) {
        String result = WebUtils.ERROR_MSG;
        try {
            String id = request.getParameter("id");
            if (StringUtils.isEmply(id)) {
                User user = (User) session.getAttribute("user");
                if (user != null) {
                    UserPicture picture = userPictureService.getPicture(user.getId());
                    result = new ObjectMapper().writeValueAsString(new JsonResult(picture));
                }
            } else {
                UserPicture picture = userPictureService.getPicture(Integer.parseInt(id));
                result = new ObjectMapper().writeValueAsString(new JsonResult(picture));
            }

        } catch (Exception e) {
            LOG.error("读取用户头像失败", e);
        }
        return result;
    }


    @RequestMapping("showUserPicture.do")
    public void showUserPicture(HttpServletRequest request, HttpServletResponse response) {

        String pictureName = StringUtils.trim(request.getParameter("pictureName"));
        byte[] picture = userPictureService.getPicture(request, pictureName);
        try {
            if (picture != null) {
                response.getOutputStream().write(picture);
            } else {
                String path = request.getSession().getServletContext().getRealPath("/");
                String picturePath = path + File.separator + "resource" + File.separator + "no_userpicture.png";
                BufferedImage image = ImageIO.read(new File(picturePath));
                ImageIO.write(image, "png", response.getOutputStream());
            }
        } catch (Exception e) {
            LOG.error("输出图片失败");
        }
    }

    @RequestMapping("userPictureUpload.do")
    @ResponseBody
    public String userPictureUpload(HttpServletRequest request, @RequestParam("file") MultipartFile file) {
        String result = WebUtils.ERROR_MSG;
        String style = StringUtils.trim(request.getParameter("style"));
        try {
            String pictureName = userPictureService.savePicture(request, file);
            result = new ObjectMapper().writeValueAsString(new JsonResult(pictureName));
        } catch (Exception e) {
            LOG.error("图片保存失败", e);
        }
        if (!StringUtils.isEmply(style)) {
            result = "<script>parent.uploadResult('" + result + "')</script>";
        }
        return result;
    }
}
