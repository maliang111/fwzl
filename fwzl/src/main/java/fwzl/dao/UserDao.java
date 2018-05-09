package fwzl.dao;

import fwzl.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 用户dao对象
 * @author 马亮
 */
@Repository
public interface UserDao {


    /**
     * 根据用户姓名和用户口令查询用户
     * @param username
     * @return
     */
    User findUserByName(@Param("username") String username);


    /**
     * 更新密码
     * @param userId
     * @param password
     * @return
     */
    int updatePassword(@Param("userId") Integer userId, @Param("password") String password);

    /**
     * 保存用户拥有的角色
     * @param userId
     * @param roleIds
     * @return
     */
    int saveUserRole(@Param("userId") Integer userId, @Param("roleIds") Integer[] roleIds);


    /**
     * 保存用户权限
     * @param userId
     * @param moduleIds
     * @return
     */
    int saveUserAuth(@Param("userId") Integer userId, @Param("moduleIds") Integer[] moduleIds);


    /**
     * 获取最大的排序号
     * @return
     */
    Integer getMaxSort();

    /**
     * 保存用户
     * @param user
     * @return
     */
    int saveUser(@Param("user") User user);


    /**
     * 根据用户id获取用户
     * @param userId
     * @return
     */
    User getUser(Integer userId);

    /**
     * 更新用户
     * @param user
     * @return
     */
    int updateUser(@Param("user") User user);


    /**
     * 分页查询用户信息
     * @param params
     * @return
     */
    List<User> getUsers(@Param("params") Map<String, Object> params);


    /**
     * 获得用户数
     * @param params
     * @return
     */
    int getUserCounts(@Param("params") Map<String, Object> params);

    /**
     * 删除用户
     * @param id
     * @return
     */
    int deleteUser(@Param("id") Integer id);


    /**
     * 批量删除用户
     * @param ids
     * @return
     */
    int deleteUsers(@Param("ids") Integer[] ids);



    /**
     * 删除用户角色
     * @param id
     * @return
     */
    int deleteUserRole(@Param("id") Integer id);

    /**
     * 批量删除用户角色
     * @param ids
     * @return
     */
    int deleteUserRoles(@Param("ids") Integer[] ids);


    /**
     * 删除用户权限
     * @param id
     * @return
     */
    int deleteUserAuth(@Param("id") Integer id);


    /**
     * 批量删除用户权限
     * @param ids
     * @return
     */
    int deleteUserAuths(@Param("ids") Integer[] ids);

}

