package fwzl.dao;

import fwzl.entity.UserPicture;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 用户图片dao
 * @author 马亮
 */
@Repository
public interface UserPictureDao {


    /**
     * 保存用户头像
     * @param userPicture
     * @return
     */
    int saveUserPicture(@Param("userPicture") UserPicture userPicture);


    /**
     * 查询用户图片
     * @param userId
     * @return
     */
    UserPicture findUserPicture(@Param("userId") Integer userId);


    /**
     * 更新用户照片
     * @param userPicture
     * @return
     */
    int updateUserPicture(@Param("userPicture") UserPicture userPicture);


    /**
     * 删除用户头像
     * @param id
     * @return
     */
    int deleteUserPicture(@Param("id") Integer id);

    /**
     * 批量删除用户图片
     * @param ids
     * @return
     */
    int deleteUserPictures(@Param("ids") Integer[] ids);

}
