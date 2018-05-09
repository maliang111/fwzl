package fwzl.service;

import fwzl.entity.User;
import fwzl.vo.PageObject;

import java.util.List;
import java.util.Map;

/**
 * 用户service层接口
 * @author 马亮
 */
public interface UserService {
    /**
     * 用户登录
     * @param username 用户账号
     * @param password 用户口令
     * @return
     */
    User login(String username, String password);


    /**
     * 更新用户
     * @param map
     * @return
     */
    User updateUser(Map<String, Object> map);

    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    User findUserById(String id);

    /**
     * 查询用户是否存在
     * @param username
     * @return
     */
    int findUserByUsername(String username);

    /**
     * 更新密码
     * @param map
     */
    void updatePassword(Map<String, Object> map);
    /**
     * 保存用户
     * @param map
     */
    void saveUser(Map<String, Object> map);


    /**
     * 获取用户的角色
     * @param userId
     * @return
     */
    List<Map<String, Object>> getUserRole(int userId);

    /**
     * 获得用户的分页数据
     * @param map
     * @return
     */
    PageObject getUsersByPage(Map<String, Object> map);


    /**
     * 删除用户
     * @param id
     */
    void deleteUser(String id);


    /**
     * 批量删除用户
     * @param ids
     */
    void deleteUsers(String ids);

}
