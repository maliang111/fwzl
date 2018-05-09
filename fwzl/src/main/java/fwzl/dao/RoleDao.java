package fwzl.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 角色dao
 * @author 马亮
 */
@Repository
public interface RoleDao {

    /**
     * 查询用户所拥有的角色
     * @param userId
     * @return
     */
    List<Map<String, Object>> findRoleByUserId(@Param("userId") Integer userId);


    /**
     * 根据角色id获取该角色拥有的权限
     * @param roleId
     * @return
     */
    Integer[] findRoleAuthByRoleId(@Param("roleId") Integer roleId);


    /**
     * 根据角色名查询角色id
     * @param roleName
     * @return
     */
    Integer findRoleByRoleName(@Param("roleName") String roleName);

}
