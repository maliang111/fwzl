package fwzl.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 *
 * 租赁订单dao
 * @author 马亮
 */
@Repository
public interface RentOrderDao {

    /**
     * 查询账目信息
     * @param params
     * @return
     */
    List<Map<String, Object>> findZmxx(@Param("params") Map<String, Object> params);


    /**
     * 查询账目信息个数
     * @param params
     * @return
     */
    Integer findZmxxCount(@Param("params") Map<String, Object> params);


    /**
     * 根据id查询账目信息
     * @param orderId
     * @return
     */
    Map<String, Object> findZmxxById(@Param("orderId") Integer orderId);


    /**
     * 查询最大排序号
     * @return
     */
    Integer findMaxSort();


    /**
     * 保存账目信息
     * @param params
     * @return
     */
    Integer saveRentOrder(@Param("params") Map<String,Object> params);


    /**
     * 更新账目信息
     * @param params
     * @return
     */
    Integer updateRentOrder(@Param("params") Map<String, Object> params);


    /**
     * 删除账目信息
     * @param orderId
     * @return
     */
    Integer deleteZmxx(@Param("orderId") Integer orderId);



    Integer deleteZmxxByHouseId(@Param("ids") Integer[] houseIds);


    Integer deleteZmxxByUserId(@Param("userId") Integer userId);
}
