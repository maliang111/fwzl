package fwzl.service.impl;

import fwzl.dao.UserPictureDao;
import fwzl.entity.UserPicture;
import fwzl.service.UserPictureService;
import fwzl.util.StringUtils;
import fwzl.util.WebUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


/**
 * 用户图片服务层实现类
 * @author 马亮
 */
@Service("userPictureService")
public class UserPictureServiceImpl implements UserPictureService {

    @Autowired
    @Qualifier("userPictureDao")
    private UserPictureDao userPictureDao;

    private static final Log LOG = LogFactory.getLog(UserPictureServiceImpl.class);

    @Override
    public UserPicture getPicture(Integer userId) {
        return userPictureDao.findUserPicture(userId);
    }

    @Override
    public byte[] getPicture(HttpServletRequest request, String pictureName) {
        String filePath = WebUtils.getServerPath(request);
        String fullPath = filePath + File.separator + "userPicture" + File.separator + pictureName;
        File picture = new File(fullPath);
        if (!picture.isFile() || !picture.exists()) {
            return null;
        }
        byte[] result = new byte[(int) picture.length()];
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(picture);
            fis.read(result);
        } catch (java.io.IOException e) {
            LOG.error("读取图片失败", e);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    LOG.error("关闭文件流失败", e);
                }
            }
        }
        return result;
    }

    @Override
    public String savePicture(HttpServletRequest request, MultipartFile file) {

        //写入文件
        String extName = StringUtils.getExtName(file.getOriginalFilename());
        String filePath = WebUtils.getServerPath(request) + File.separator + "userPicture";
        File path = new File(filePath);
        if (!path.exists()) {
            path.mkdirs();
        }
        String pictureName = StringUtils.getUUID() + "." + extName;
        String fullName = filePath + File.separator + pictureName;
        File picture = new File(fullName);
        try {
            file.transferTo(picture);
        } catch (Exception e) {
            LOG.error("写入文件失败", e);
            throw new RuntimeException("文件写入失败");
        }
        return pictureName;
    }
}
