package fwzl.dao;

import fwzl.entity.HousePicture;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 房屋图片dao
 * @author 马亮
 */
@Repository
public interface HousePictureDao {

    /**
     * 获取房屋的图片
     * @param houseId
     * @return
     */
    String[] findHousePicture(@Param("houseId") Integer houseId);

    /**
     * 获取最大的图片id
     * @param houseId
     * @return
     */
    Integer getMaxPictureId(@Param("houseId") Integer houseId);


    /**
     * 保存图片
     * @param housePicture
     */
    void saveHousePicture(@Param("housePicture") HousePicture housePicture);

    /**
     * 批量保存图片
     * @param housePictures
     * @return
     */
    int saveHousePictures(@Param("housePictures") List<HousePicture> housePictures);

    /**
     * 删除房屋图片
     * @param houseId
     * @return
     */
    int deleteHousePicture(@Param("houseId") Integer houseId);

    /**
     * 批量删除照片
     * @param houseIds
     * @return
     */
    int deleteHousePictures(@Param("ids") Integer[] houseIds);



}
