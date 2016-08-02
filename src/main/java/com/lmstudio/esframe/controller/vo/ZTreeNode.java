/**
* TODO
* @Project: esframe
* @Title: ZTreeNode.java
* @Package com.lmstudio.esframe.controller
* @author jason
* @Date 2016年8月1日 上午11:24:31
* @Copyright
* @Version 
*/
package com.lmstudio.esframe.controller.vo;

/**
* TODO
* @ClassName: ZTreeNode
* @author jason
*/
public class ZTreeNode {
	
	public String id;
	public String name;
	public String pId;
	
	public ZTreeNode(String id, String name, String pId) {
		super();
		this.id = id;
		this.name = name;
		this.pId = pId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	
	
}
