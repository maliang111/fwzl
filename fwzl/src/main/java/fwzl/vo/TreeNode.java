package fwzl.vo;

import java.io.Serializable;
import java.util.List;

/**
 * extjs tree节点对象
 * @author 马亮
 */
public class TreeNode implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4443716524867352949L;

	
	/**
	 * 树节点id
	 */
	private String id;
	
	/**
	 * 树节点显示的文本
	 */
	private String text;
	
	/**
	 * 是否为叶子节点
	 */
	private boolean leaf;


	/**
	 * 子节点
	 */
	private List<TreeNode> children;

	private String url;

	/**
	 * 返回id
	 * @return id 
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置id
	 * @param id 要设置的id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 返回text
	 * @return text 
	 */
	public String getText() {
		return text;
	}

	/**
	 * 设置text
	 * @param text 要设置的text
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * 返回leaf
	 * @return leaf 
	 */
	public boolean getLeaf() {
		return leaf;
	}

	/**
	 * 设置leaf
	 * @param leaf 要设置的leaf
	 */
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}


	public List<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
