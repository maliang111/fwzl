package fwzl.service;

import fwzl.vo.PageObject;

import java.util.List;
import java.util.Map;

/**
 * 账目service接口
 * @author 马亮
 */
public interface RentOrderService {

    /**
     * 查询所有账目信息
     * @param map
     * @return
     */
    PageObject findZmxx(Map<String, Object> map);


    /**
     * 根据id查询账目信息
     * @param id
     * @return
     */
    Map<String, Object> findZmxxById(String id);

    /**
     * 插入或更新账目信息
     * @param map
     */
    void saveRentOrder(Map<String, Object> map);


    /**
     * 删除账目信息
     * @param orderId
     * @param houseId
     */
    void deleteRentOrder(String orderId, String houseId);

}
