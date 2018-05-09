package fwzl.service;

import fwzl.entity.UserPicture;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户图片服务层接口
 * @author 马亮
 */
public interface UserPictureService {


    /**
     * 从数据库中获取图片
     * @param userId
     * @return
     */
    UserPicture getPicture(Integer userId);

    /**
     * 读取图片
     * @param request
     * @param pictureName
     * @return
     */
    byte[] getPicture(HttpServletRequest request, String pictureName);


    /**
     * 保存图片
     * @param request
     * @param file
     * @return
     */
    String savePicture(HttpServletRequest request, MultipartFile file);


}
