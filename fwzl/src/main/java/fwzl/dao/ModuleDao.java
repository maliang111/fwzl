package fwzl.dao;

import fwzl.entity.Module;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 模块dao
 * @author 马亮
 */
@Repository
public interface ModuleDao {

    /**
     * 根据用户id获取可操作的菜单
     * @param userId
     * @return
     */
    List<Module> findModuleByUserId(Integer userId);


    /**
     * 根据角色id获取可操作的菜单
     * @param roleId
     * @return
     */
    List<Module> findModuleByRoleId(Integer roleId);


    /**
     * 根据用户的角色获取可操作的菜单
     * @param userId
     * @return
     */
    List<Module> findModuleByUserRole(Integer userId);

    /**
     * 根据id查询
     * @param moduleId
     * @return
     */
    Module findModuleById(@Param("moduleId") Integer moduleId);

}
