package fwzl.service;

import fwzl.vo.PageObject;

import java.util.List;
import java.util.Map;

/**
 * 房屋service
 * @author 马亮
 */
public interface HouseService {


    /**
     * 查询房屋信息
     * @param params
     * @return
     */
    PageObject findHouses(Map<String, Object> params);


    /**
     * 查询房屋详细信息
     * @param houseId
     * @return
     */
    Map<String, Object> getHouseById(String houseId);


    /**
     * 删除房屋
     * @param houseId
     */
    void deleteHouse(String houseId);

    /**
     * 批量删除
     * @param ids
     */
    void deleteHouses(String ids);

    /**
     * 保存房屋信息
     * @param map
     */
    void saveHouse(Map<String, Object> map);


    /**
     * 根据拥有者分组查询
     * @return
     */
    List<Map<String, Object>> findHouseGroupByOwner();


    /**
     * 根据发布时间查询房屋数量
     * @return
     */
    List<Map<String, Object>> findHouseGroupByLeaseTime();
}
