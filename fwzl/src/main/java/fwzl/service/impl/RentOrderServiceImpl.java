package fwzl.service.impl;

import fwzl.common.Constant;
import fwzl.dao.HouseDao;
import fwzl.dao.RentOrderDao;
import fwzl.service.RentOrderService;
import fwzl.util.StringUtils;
import fwzl.vo.PageObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 账目service
 * @author 马亮
 */
@Service("rentOrderService")
public class RentOrderServiceImpl implements RentOrderService {

    @Autowired
    @Qualifier("rentOrderDao")
    private RentOrderDao rentOrderDao;

    @Autowired
    @Qualifier("houseDao")
    private HouseDao houseDao;

    @Override
    public PageObject findZmxx(Map<String, Object> map) {
        PageObject pageObject = new PageObject();

        map.put("leaseTime1", StringUtils.convertStrToDate((String) map.get("leaseTime1"), ""));
        map.put("leaseTime2", StringUtils.convertStrToDate((String) map.get("leaseTime2"), ""));
        map.put("start", Integer.parseInt((String) map.get("start")));
        map.put("limit", Integer.parseInt((String) map.get("limit")));
        Integer num = rentOrderDao.findZmxxCount(map);
        if (num == null) {
            num = 0;
        }
        pageObject.setTotalCount(num);
        List<Map<String, Object>> resultList = rentOrderDao.findZmxx(map);
        pageObject.setData(resultList);
        return pageObject;
    }

    @Override
    public Map<String, Object> findZmxxById(String id) {
        return rentOrderDao.findZmxxById(Integer.parseInt(StringUtils.trim(id)));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveRentOrder(Map<String, Object> map) {
        String id = StringUtils.trim((String) map.get("orderId"));
        if (!StringUtils.isEmply(id)) {
            map.put("orderId", Integer.parseInt(id));
        }
        map.put("houseId", Integer.parseInt((String) map.get("houseId")));
        map.put("ownerId", Integer.parseInt((String) map.get("ownerId")));
        map.put("tenantId", Integer.parseInt((String) map.get("tenantId")));
        map.put("leaseTime",
                StringUtils.convertStrToDate((String) map.get("leaseTime"), ""));
        map.put("leaseLength", Integer.parseInt(StringUtils.convertEmptyToZero((String) map.get("leaseLength"))));
        map.put("deposit", Double.parseDouble(StringUtils.convertEmptyToZero((String) map.get("deposit"))));
        map.put("yjje", Double.parseDouble(StringUtils.convertEmptyToZero((String) map.get("yjje"))));
        map.put("sjje", Double.parseDouble(StringUtils.convertEmptyToZero((String) map.get("sjje"))));
        map.put("wjje", Double.parseDouble(StringUtils.convertEmptyToZero((String) map.get("wjje"))));
        map.put("dueDate", StringUtils.convertStrToDate((String) map.get("dueDate"), ""));

        int result;

        //页面传入的id为空，则为添加
        if (StringUtils.isEmply(id)) {
            Integer sort = rentOrderDao.findMaxSort();
            if (sort == null) {
                sort = 0;
            }
            sort++;
            map.put("sort", sort);
            map.put("createdTime", new Date());
            map.put("createdUser", map.get("username"));
           result = rentOrderDao.saveRentOrder(map);
        } else {    //否则为修改
            Map<String, Object> rentOrder = rentOrderDao.findZmxxById(Integer.parseInt(id));
            rentOrder.put("tenantId", map.get("tenantId"));
            rentOrder.put("leaseTime", map.get("leaseTime"));
            rentOrder.put("leaseLength", map.get("leaseLength"));
            rentOrder.put("deposit", map.get("deposit"));
            rentOrder.put("yjje", map.get("yjje"));
            rentOrder.put("sjje", map.get("sjje"));
            rentOrder.put("wjje", map.get("wjje"));
            rentOrder.put("dueDate", map.get("dueDate"));
            rentOrder.put("modifiedTime", new Date());
            rentOrder.put("modifiedUser", map.get("username"));
            result = rentOrderDao.updateRentOrder(rentOrder);
        }
        if (result <= 0) {
            throw new RuntimeException("保存失败");
        }

        result = houseDao.updateHouseRentState((Integer) map.get("houseId"), Constant.LEASED);

        if (result <= 0) {
            throw new RuntimeException("保存时修改房屋状态失败");
        }

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteRentOrder(String orderId, String houseId) {

        Integer _orderId = Integer.parseInt(StringUtils.trim(orderId));
        Integer _houseId = Integer.parseInt(StringUtils.trim(houseId));

        int result = rentOrderDao.deleteZmxx(_orderId);
        if (result < 0) {
            throw new RuntimeException("删除失败");
        }

        result = houseDao.updateHouseRentState(_houseId, Constant.NOT_LEASED);

        if (result <= 0) {
            throw new RuntimeException("删除时修改房屋状态失败");
        }

    }
}
