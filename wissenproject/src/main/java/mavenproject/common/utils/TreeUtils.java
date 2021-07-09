package mavenproject.common.utils;

import mavenproject.modules.sys.model.dto.TreeNode;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * Tree工具类
 * </p>
 *
 * @author jinghong
 */
public abstract class TreeUtils {

    /**
     * 递归查找子节点
     *
     * @param treeNodes
     * @return
     */
    public static <T extends TreeNode> T findChildren(T treeNode, List<T> treeNodes) {
        treeNodes.stream().filter(e -> Objects.equals(treeNode.getId(), e.getPid()))
                .forEach(e -> treeNode.getSubMenus().add(findChildren(e, treeNodes)));
        return treeNode;
    }
}
