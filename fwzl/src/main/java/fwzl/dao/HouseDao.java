package fwzl.dao;

import fwzl.entity.House;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author 马亮
 */
@Repository
public interface HouseDao {


    /**
     * 根据id查询房屋信息
     * @param houseId
     * @return
     */
    Map<String, Object> findHouseById(@Param("houseId") Integer houseId);


    /**
     * 分页查询每页房屋信息
     * @param params
     * @return
     */
    List<Map<String, Object>> findHouses(@Param("params") Map<String, Object> params);


    /**
     * 查询房屋数量
     * @param params
     * @return
     */
    int getHouseCount(@Param("params") Map<String, Object> params);


    /**
     * 删除房子
     * @param houseId
     * @return
     */
    int deleteHouse(@Param("houseId") Integer houseId);

    /**
     * 批量删除房屋信息
     * @param houseIds
     * @return
     */
    int deleteHouses(@Param("ids") Integer[] houseIds);


    /**
     * 获取最大的排序号
     * @return
     */
    Integer getMaxSort();

    /**
     * 更新房屋信息
     * @param house
     * @return
     */
    int updateHouse(@Param("house") Map<String, Object> house);


    /**
     * 添加房屋信息
     * @param house
     * @return
     */
    int saveHouse(@Param("house") Map<String, Object> house);


    /**
     * 修改出租状态
     * @param houseId
     * @param state
     * @return
     */
    int updateHouseRentState(@Param("houseId") Integer houseId, @Param("state") String state);


    /**
     * 根据所有者删除房屋
     * @param ownerId
     * @return
     */
    int deleteHouseByOwner(@Param("ownerId") Integer ownerId);


    /**
     * 根据拥有者查询房屋信息
     * @param ownerId
     * @return
     */
    List<Integer> findHouseIdByOwnerId(@Param("ownerId") Integer ownerId);


    /**
     * 批量查找房屋
     * @param ownerIds
     * @return
     */
    List<Integer> findHouseIdByOwnerIds(@Param("ids") Integer[] ownerIds);


    /**
     * 按发布时间分组，查询房屋数量
     * @return
     */
    List<Map<String, Object>> findHouseGroupByLeaseTime();


    /**
     * 按发布人分组
     * @return
     */
    List<Map<String, Object>> findHouseGroupByOwner();

}
