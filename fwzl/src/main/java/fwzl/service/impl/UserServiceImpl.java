package fwzl.service.impl;

import fwzl.common.Constant;
import fwzl.dao.*;
import fwzl.entity.User;
import fwzl.entity.UserPicture;
import fwzl.service.UserService;
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
 * 用户sercice
 * @author 马亮
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    @Qualifier("userDao")
    private UserDao userDao;

    @Autowired
    @Qualifier("userPictureDao")
    private UserPictureDao userPictureDao;

    @Autowired
    @Qualifier("houseDao")
    private HouseDao houseDao;


    @Autowired
    @Qualifier("housePictureDao")
    private HousePictureDao housePictureDao;

    @Autowired
    @Qualifier("roleDao")
    private RoleDao roleDao;

    @Autowired
    @Qualifier("rentOrderDao")
    private RentOrderDao rentOrderDao;

    @Override
    public User login(String username, String password) {

        User user = userDao.findUserByName(username);
        if (user == null) {
            throw new RuntimeException("该用户不存在");
        }
        String salt = user.getSalt();
        //对密码进行加密
        password = StringUtils.digest(password, salt);
        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("密码错误");
        }
        return user;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public User updateUser(Map<String, Object> map) {

        User user = new User();
        user.setId(Integer.parseInt(StringUtils.trim(String.valueOf(map.get("userId")))));
        user.setRealName(StringUtils.trim((String) map.get("realName")));
        user.setBirthday(StringUtils.convertStrToDate(StringUtils.trim((String) map.get("birthday")), ""));
        user.setEmail(StringUtils.trim((String) map.get("email")));
        user.setGender(StringUtils.trim((String) map.get("gender")));
        user.setMobile(StringUtils.trim((String) map.get("mobile")));
        user.setModifiedTime(new Date());
        user.setModifiedUser(user.getUsername());
        int rtn = userDao.updateUser(user);
        if (rtn < 0) {
            throw new RuntimeException("更新用户信息失败");
        }

        //保存照片
        String userPictureName = StringUtils.trim((String) map.get("pictureName"));
        if (!StringUtils.isEmply(userPictureName)) {
            boolean isExists = true;
            UserPicture userPicture = userPictureDao.findUserPicture(user.getId());
            if (userPicture == null) {
                isExists = false;
                userPicture = new UserPicture();
            }
            userPicture.setUserId(user.getId());
            userPicture.setPictureName(userPictureName);
            userPicture.setPictureType(StringUtils.getExtName(userPictureName));

            if (isExists) {
                userPicture.setModifiedTime(new Date());
                userPicture.setModifiedUser(StringUtils.trim((String) map.get("username")));
                rtn= userPictureDao.updateUserPicture(userPicture);
            } else {
                userPicture.setCreatedTime(new Date());
                userPicture.setCreatedUser(StringUtils.trim((String) map.get("username")));
                rtn = userPictureDao.saveUserPicture(userPicture);
            }
            if (rtn <= 0) {
                throw new RuntimeException("保存图片失败");
            }
        }
        return user;
    }

    @Override
    public User findUserById(String id) {
        return userDao.getUser(Integer.parseInt(id));
    }


    @Override
    public int findUserByUsername(String username) {

        User user = userDao.findUserByName(username);
        if (user == null) {
            return Constant.NOT_FOUND;
        }
        return Constant.FOUND;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePassword(Map<String, Object> map) {
        String oldPassword = StringUtils.trim((String) map.get("oldPassword"));
        String newPassword = StringUtils.trim((String) map.get("newPassword"));
        String password = StringUtils.trim((String) map.get("password"));
        String salt = StringUtils.trim((String) map.get("salt"));
        oldPassword = StringUtils.digest(oldPassword, salt);
        if (!password.equals(oldPassword)) {
            throw new RuntimeException("旧密码输入错误");
        }

        newPassword = StringUtils.digest(newPassword, salt);
        Integer id = Integer.parseInt(StringUtils.trim(String.valueOf(map.get("userId"))));
        int rtn = userDao.updatePassword(id, newPassword);
        if (rtn < 0) {
            throw new RuntimeException("更新失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUser(Map<String, Object> map) {
        User user = new User();
        user.setUsername(StringUtils.trim((String) map.get("user")));
        String password = StringUtils.trim((String) map.get("passwd"));
        user.setRealName(StringUtils.trim((String) map.get("realName")));
        String uuid = StringUtils.getUUID();
        user.setSalt(uuid);
        //编码
        password = StringUtils.digest(password, uuid);
        user.setPassword(password);
        user.setEmail(StringUtils.trim((String) map.get("email")));
        Integer sort = userDao.getMaxSort();
        if (sort == null) {
            sort = 0;
        }
        sort++;
        user.setSort(sort);
        int rtn = userDao.saveUser(user);
        if (rtn <= 0) {
            throw new RuntimeException("保存用户失败");
        }
        //设置新注册用户的角色，默认为角色用户
        //查询普通用户的角色id
        Integer roleId = roleDao.findRoleByRoleName(Constant.ROLE_USER);
        if (roleId == null || roleId <= 0) {
            throw new RuntimeException("查询用户角色失败");
        }
        //保存用户的角色
        rtn = userDao.saveUserRole(user.getId(), new Integer[]{roleId});
        if (rtn <= 0) {
            throw new RuntimeException("保存用户角色失败");
        }

        //获取普通用户拥有的权限，将其设置为用户的权限
        Integer[] auths = roleDao.findRoleAuthByRoleId(roleId);

        rtn = userDao.saveUserAuth(user.getId(), auths);

        if (rtn <= 0) {
            throw new RuntimeException("保存用户权限失败");
        }
    }

    @Override
    public List<Map<String, Object>> getUserRole(int userId) {
        List<Map<String, Object>> listRoles = roleDao.findRoleByUserId(userId);
        return listRoles;
    }


    @Override
    public PageObject getUsersByPage(Map<String, Object> map) {
        map.put("birthday", StringUtils.convertStrToDate((String) map.get("birthday"), ""));
        map.put("start", Integer.parseInt((String) map.get("start")));
        map.put("limit", Integer.parseInt((String) map.get("limit")));
        List<User> userList = userDao.getUsers(map);
        for (User user : userList) {
            String gender = user.getGender();
            if (Constant.GENDER_MALE.equals(gender)) {
                user.setGender("男");
            } else {
                user.setGender("女");
            }
        }

        PageObject page = new PageObject();
        page.setData(userList);
        page.setTotalCount(userDao.getUserCounts(map));
        return page;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteUser(String id) {

        int n = userDao.deleteUserAuth(Integer.parseInt(id));

        if (n < 0) {
            throw new RuntimeException("删除用户权限失败");
        }

        n = userDao.deleteUserRole(Integer.parseInt(id));

        if (n < 0) {
            throw new RuntimeException("删除用户角色失败");
        }

        n = userPictureDao.deleteUserPicture(Integer.parseInt(id));
        if (n < 0) {
            throw new RuntimeException("删除头像失败");
        }
        Integer _id = Integer.parseInt(id);
        List<Integer> ids = houseDao.findHouseIdByOwnerId(_id);
        if (ids.size() > 0) {
            Integer[] _ids = ids.toArray(new Integer[]{});
            rentOrderDao.deleteZmxxByHouseId(_ids);
            if (n < 0) {
                throw new RuntimeException("删除房屋账目失败");
            }
            n = housePictureDao.deleteHousePictures(_ids);
            if (n < 0) {
                throw new RuntimeException("删除房屋图片失败");
            }
        }

        n = rentOrderDao.deleteZmxxByUserId(Integer.parseInt(id));
        if (n < 0) {
            throw new RuntimeException("删除账目失败");
        }

        n = houseDao.deleteHouseByOwner(Integer.parseInt(id));
        if (n < 0) {
            throw new RuntimeException("删除失败");
        }
        n = userDao.deleteUser(Integer.parseInt(id));
        if (n < 0) {
            throw new RuntimeException("删除用户失败");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteUsers(String ids) {

        String[] idArray = ids.split(",");
        Integer[] _ids = new Integer[idArray.length];
        for (int i = 0; i < idArray.length; i++) {
//            _ids[i] = Integer.parseInt(StringUtils.trim(idArray[i]));
            deleteUser(StringUtils.trim(idArray[i]));
        }

//        int rtn = userDao.deleteUserAuths(_ids);
//        if (rtn < 0) {
//            throw new RuntimeException("删除权限失败");
//        }
//
//        rtn = userDao.deleteUserRoles(_ids);
//
//        if (rtn < 0) {
//            throw new RuntimeException("删除角色失败");
//        }
//
//        rtn = userPictureDao.deleteUserPictures(_ids);
//
//        if (rtn < 0) {
//            throw new RuntimeException("删除图片失败");
//        }
//
//
//        List<Integer> _houseIds = houseDao.findHouseIdByOwnerIds(_ids);
//
//        Integer[] houseIds = _houseIds.toArray(new Integer[]{});
//
//        rtn = rentOrderDao.deleteZmxxByHouseId(houseIds);
//
//        if (rtn < 0) {
//            throw new RuntimeException("删除账目失败");
//        }
//
//        rtn = housePictureDao.deleteHousePictures(houseIds);
//        if (rtn < 0) {
//            throw new RuntimeException("删除房屋图片失败");
//        }
//
//        rtn = houseDao.deleteHouses(houseIds);
//        if (rtn < 0) {
//            throw new RuntimeException("删除房屋失败");
//        }
//
//        for (int i = 0; i < _ids.length; i++) {
//            rtn = rentOrderDao.deleteZmxxByUserId(_ids[i]);
//            if (rtn < 0) {
//                throw new RuntimeException("删除账目失败");
//            }
//
//        }
//        rtn = userDao.deleteUsers(_ids);
//        if (rtn < 0) {
//            throw new RuntimeException("删除用户失败");
//        }

    }
}
