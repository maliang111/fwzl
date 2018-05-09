package fwzl.service;

import fwzl.vo.TreeNode;

import java.util.List;

/**
 * module service层接口
 * @author 马亮
 */
public interface ModuleService {


    /**
     * 根据用户id查询模块
     * @param userId
     * @param style
     * @return
     */
    List<TreeNode> findModuleByUserId(Integer userId, String style);

    /**
     * 根据moduleId获取module对应的url
     * @param style
     * @param id
     * @return
     */
    String getModuleUrl(String id, String style);

}
