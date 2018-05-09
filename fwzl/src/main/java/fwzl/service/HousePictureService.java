package fwzl.service;


import fwzl.entity.HousePicture;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 房屋图片service
 * @author 马亮
 */
public interface HousePictureService {

    /**
     * 获取房屋的图片
     * @param houseId
     * @return
     */
    String[] findHousePicture(String houseId);


    /**
     * 获取图片
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
