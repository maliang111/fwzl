package fwzl.service.impl;

import fwzl.common.Constant;
import fwzl.dao.HouseDao;
import fwzl.dao.HousePictureDao;
import fwzl.entity.House;
import fwzl.entity.HousePicture;
import fwzl.service.HouseService;
import fwzl.util.StringUtils;
import fwzl.vo.PageObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 房屋service
 * @author 马亮
 */
@Service("houseService")
public class HouseServiceImpl implements HouseService{

    @Autowired
    @Qualifier("houseDao")
    private HouseDao houseDao;

    @Autowired
    @Qualifier("housePictureDao")
    private HousePictureDao housePictureDao;

    @Override
    public PageObject findHouses(Map<String, Object> params) {
        Integer ownerId = Integer.parseInt(StringUtils.convertEmptyToZero(StringUtils.trim(String.valueOf(params.get("ownerId")))));
        params.put("ownerId", ownerId);
        Double rent1 = Double.parseDouble(StringUtils.convertEmptyToZero(StringUtils.trim((String) params.get("rent1"))));
        params.put("rent1", rent1);
        Double rent2 = Double.parseDouble(StringUtils.convertEmptyToZero(StringUtils.trim((String) params.get("rent2"))));
        params.put("rent2", rent2);
        String residence = StringUtils.trim((String) params.get("residence"));
        params.put("residence", residence);
        String address = StringUtils.trim((String) params.get("address"));
        params.put("address", address);
        Integer start = Integer.parseInt(StringUtils.trim((String) params.get("start")));
        params.put("start", start);
        Integer limit = Integer.parseInt(StringUtils.trim((String) params.get("limit")));
        params.put("limit", limit);
        String ownerName = StringUtils.trim((String) params.get("ownerName"));
        params.put("ownerName", ownerName);
        String houseCode = StringUtils.trim((String) params.get("houseCode"));
        params.put("houseCode", houseCode);
        Date leaseTime1 = StringUtils.convertStrToDate(StringUtils.trim((String) params.get("leaseTime1")), "");
        params.put("leaseTime1", leaseTime1);
        Date leaseTime2 = StringUtils.convertStrToDate(StringUtils.trim((String) params.get("leaseTime2")), "");
        params.put("leaseTime2", leaseTime2);

        String isLeased = StringUtils.trim((String) params.get("isLeased"));
        if (!StringUtils.isEmply(isLeased)) {
            params.put("isLeased", Constant.NOT_LEASED);
        }

        PageObject pageObject = new PageObject();
        List<Map<String, Object>> houseMap = houseDao.findHouses(params);
        Iterator<Map<String, Object>> it = houseMap.iterator();


        while (it.hasNext()) {
            Map<String, Object> map = it.next();
            Integer pictureId = (Integer)map.get("pictureId");
            if (pictureId == null) {
                pictureId = 0;
            }
            map.put("pictureId", pictureId);
            String pictureName = (String) map.get("pictureName");
            map.put("pictureName", StringUtils.trim(pictureName));
            map.put("leaseTime", map.get("leaseTime"));
        }

        int totalCount = houseDao.getHouseCount(params);
        pageObject.setData(houseMap);
        pageObject.setTotalCount(totalCount);
        return pageObject;
    }
    @Override
    public Map<String, Object> getHouseById(String houseId) {
        return houseDao.findHouseById(Integer.parseInt(houseId));
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteHouse(String houseId) {
        Integer id = Integer.parseInt(StringUtils.convertEmptyToZero(houseId));

        //删除房屋的所有图片
        int n = housePictureDao.deleteHousePicture(id);
        if (n < 0) {
            throw new RuntimeException("删除房屋图片失败");
        }
        //删除房屋
        n = houseDao.deleteHouse(id);
        if (n < 0) {
            throw new RuntimeException("删除房屋失败");
        }
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteHouses(String ids) {
        String[] _ids = ids.split(",");
        Integer[] houseIds = new Integer[_ids.length];
        for (int i = 0; i < _ids.length; i++) {
            houseIds[i] = Integer.parseInt(StringUtils.trim(_ids[i]));
        }
        houseDao.deleteHouses(houseIds);
        housePictureDao.deleteHousePictures(houseIds);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveHouse(Map<String, Object> map) {

        Integer houseId = Integer.parseInt(StringUtils.convertEmptyToZero(StringUtils.trim((String) map.get("houseId"))));
        String address = StringUtils.trim((String) map.get("address"));
        String residence = StringUtils.trim((String) map.get("residence"));
        String size = StringUtils.trim((String) map.get("size"));
        Date leaseTime = StringUtils.convertStrToDate(StringUtils.trim((String) map.get("leaseTime")), "");
        String rent = StringUtils.trim((String) map.get("rent"));
        String note = StringUtils.trim((String) map.get("note"));
        String isLeased = StringUtils.trim((String) map.get("isLeased"));
        String modifiedUser = StringUtils.trim((String) map.get("modifiedUser"));

        Map<String, Object> house = null;
        int result;
        if (houseId != 0) {
            house = houseDao.findHouseById(houseId);
            house.put("address", address);
            house.put("residence", residence);
            house.put("size", size);
            house.put("leaseTime", leaseTime);
            house.put("rent", rent);
            house.put("note", note);
            house.put("isLeased", isLeased);
            house.put("modifiedUser", modifiedUser);
            result = houseDao.updateHouse(house);
        } else {
            house = map;
            house.put("houseId", houseId);
            Integer sort = houseDao.getMaxSort();
            if (sort == null) {
                sort = 0;
            }
            sort++;
            house.put("ownerId", map.get("ownerId"));
            house.put("sort", sort);
            house.put("houseCode", StringUtils.generateHouseCode(leaseTime, sort));
            house.put("createdUser", modifiedUser);
            result = houseDao.saveHouse(house);
            houseId = (Integer) house.get("houseId");

        }
        if (result <= 0) {
            throw new RuntimeException("保存房屋信息失败");
        }
        String pictureStr = StringUtils.trim((String) map.get("pictures"));
        String[] pictureNames = pictureStr.split(",");
        //空串split后长度为1，需要排除
        if (pictureNames.length == 1 && StringUtils.isEmply(pictureNames[0])) {
            return;
        }
        //保存图片
        Integer pictureId = housePictureDao.getMaxPictureId(houseId);
        if (pictureId == null) {
            pictureId = 0;
        }
        List<HousePicture> housePictureList = new LinkedList<HousePicture>();
        for (int i = 0; i < pictureNames.length; i++) {
            pictureId++;
            HousePicture housePicture = new HousePicture();
            housePicture.setHouseId(houseId);
            housePicture.setPictureId(pictureId);
            housePicture.setPictureName(pictureNames[i]);
            housePicture.setPictureType(StringUtils.getExtName(pictureNames[i]));
            housePictureList.add(housePicture);
        }
        if (!housePictureList.isEmpty()) {
            result = housePictureDao.saveHousePictures(housePictureList);
            if (result <= 0) {
                throw new RuntimeException("保存图片失败");
            }
        }
    }

    @Override
    public List<Map<String, Object>> findHouseGroupByLeaseTime() {
        return houseDao.findHouseGroupByLeaseTime();
    }

    @Override
    public List<Map<String, Object>> findHouseGroupByOwner() {
        return houseDao.findHouseGroupByOwner();
    }
}
