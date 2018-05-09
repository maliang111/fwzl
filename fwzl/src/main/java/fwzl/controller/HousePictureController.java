package fwzl.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fwzl.entity.HousePicture;
import fwzl.service.HousePictureService;
import fwzl.util.StringUtils;
import fwzl.util.WebUtils;
import fwzl.vo.JsonResult;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.annotations.Param;
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
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

/**
 * 房屋图片controller
 * @author 马亮
 */
@Controller
@CrossOrigin
public class HousePictureController {

    @Autowired
    @Qualifier("housePictureService")
    private HousePictureService housePictureService;

    private static final Log LOG = LogFactory.getLog(HousePictureController.class);

    @RequestMapping(value = "getHousePictures.do", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String getHousePicture(String houseId) {
        String result = WebUtils.ERROR_MSG;
        try {
            String[] housePictureList =
                    housePictureService.findHousePicture(StringUtils.trim(houseId));
            result = new ObjectMapper().writeValueAsString(new JsonResult(housePictureList));
        } catch (Exception e) {
            LOG.error("查询图片失败", e);
        }
        return result;
    }

    @RequestMapping("housePictureUpload.do")
    @ResponseBody
    public String housePictureUpload(HttpServletRequest request, @RequestParam("file") MultipartFile file) {
        String result = WebUtils.ERROR_MSG;
        try {
            String pictureName = housePictureService.savePicture(request, file);
            result = new ObjectMapper().writeValueAsString(new JsonResult(pictureName));
        } catch (Exception e) {
            LOG.error("图片保存失败", e);
        }
        return result;
    }


    @RequestMapping("getPicture.do")
    @ResponseBody
    public void getHousePictureByName(HttpServletRequest request, HttpServletResponse response) {

        String pictureName = StringUtils.trim(request.getParameter("pictureName"));
        byte[] picture = housePictureService.getPicture(request, pictureName);
        try {
            if (picture != null) {
                response.getOutputStream().write(picture);
            } else {
                String path = request.getSession().getServletContext().getRealPath("/");
                String picturePath = path + File.separator + "resource" + File.separator + "no_picture.png";
                BufferedImage image = ImageIO.read(new File(picturePath));
                ImageIO.write(image, "png", response.getOutputStream());
            }
        } catch (Exception e) {
            LOG.error("输出图片失败");
        }
    }



}
