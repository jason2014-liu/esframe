/**
* TODO
* @Project: esframe
* @Title: Medicine.java
* @Package com.lmstudio.esframe.lucene
* @author jason
* @Date 2016年8月5日 上午11:02:24
* @Copyright
* @Version 
*/
package com.lmstudio.esframe.lucene;

/**
 * TODO
 * 
 * @ClassName: Medicine
 * @author jason
 */
public class Medicine {

	private Integer id;
	private String name;
	private String function;

	public Medicine() {

	}

	public Medicine(Integer id, String name, String function) {
		super();
		this.id = id;
		this.name = name;
		this.function = function;
	}


	public String toString() {
		return this.id + "," + this.name + "," + this.function;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}
}
