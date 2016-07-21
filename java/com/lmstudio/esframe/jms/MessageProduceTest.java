/**
* TODO
* @Project: gsyth
* @Title: MessageProduceTest.java
* @Package com.wondersgroup.esf.jms
* @author jason.liu
* @Date 2016年5月19日 下午5:08:31
* @Copyright
* @Version 
*/
package com.lmstudio.esframe.jms;

import javax.jms.Destination;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
* TODO
* @ClassName: MessageProduceTest
* @author jason.liu
*/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:config/ext/applicationContext-jms.xml")   
public class MessageProduceTest {

	@Autowired
	private SimpleMessageProducer simpleMessageProducer;
	
	@Autowired
	private Destination weblogicTestQueue;
	
	@Test
	public void sendTest(){
		simpleMessageProducer.sendMessage(weblogicTestQueue, "同步数据ID:100");}
}
