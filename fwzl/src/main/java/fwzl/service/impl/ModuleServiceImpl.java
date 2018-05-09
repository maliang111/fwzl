package fwzl.service.impl;

import fwzl.dao.ModuleDao;
import fwzl.entity.Module;
import fwzl.service.ModuleService;
import fwzl.util.StringUtils;
import fwzl.vo.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 模块service
 * @author 马亮
 */
@Service("moduleService")
public class ModuleServiceImpl implements ModuleService {

    @Autowired
    @Qualifier("moduleDao")
    private ModuleDao moduleDao;



    @Override
    public List<TreeNode> findModuleByUserId(Integer userId, String style) {
        List<Module> allModule = moduleDao.findModuleByUserRole(userId);
        List<Module> moduleList = moduleDao.findModuleByUserId(userId);
        Iterator<Module> it = moduleList.iterator();
        while (it.hasNext()) {
            Module module = it.next();
            if (!allModule.contains(module)) {
                it.remove();
            }
        }
        return convertToTreeNode("0", style, moduleList);
    }

    @Override
    public String getModuleUrl(String id, String style) {
        String url = "";
        if (StringUtils.isEmply(id)) {
            return url;
        }
        Module module = null;
        module = moduleDao.findModuleById(Integer.parseInt(id));
        Map<String, String> urlMap = StringUtils.jsonStrToObject(module.getUrl());
        if (urlMap != null) {
            url = urlMap.get(style);
        }
        return url;
    }

    /**
     * 将模块对象转换为树对象
     * @param parentId
     * @param modules
     * @return
     */
    private List<TreeNode> convertToTreeNode(String parentId, String style, List<Module> modules) {

        List<TreeNode> treeNodes = new LinkedList<TreeNode>();
        for (int i = 0; i < modules.size(); i++) {
            Module module = modules.get(i);
            if (parentId.equals(String.valueOf(module.getParentId()))) {
                TreeNode treeNode = new TreeNode();
                treeNode.setId(String.valueOf(module.getModuleId()));
                treeNode.setText(module.getModuleName());
                Map<String, String> urlMap = StringUtils.jsonStrToObject(module.getUrl());
                if (urlMap != null) {
                    treeNode.setUrl(urlMap.get(style));
                }
                List<TreeNode> childNodes = convertToTreeNode(String.valueOf(module.getModuleId()), style, modules);
                if (childNodes.size() == 0) {
                    treeNode.setLeaf(true);
                } else {
                    treeNode.setLeaf(false);
                    treeNode.setChildren(childNodes);
                }
                treeNodes.add(treeNode);
            }
        }
        return treeNodes;
    }

}
