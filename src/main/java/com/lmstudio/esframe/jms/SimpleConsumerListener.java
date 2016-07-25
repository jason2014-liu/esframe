/**
* TODO
* @Project: esframe
* @Title: SimleConsumerListener.java
* @Package com.wondersgroup.esf.jms
* @author jason.liu
* @Date 2016年5月20日 上午11:07:21
* @Copyright
* @Version 
*/
package com.lmstudio.esframe.jms;

/**
 * TODO
 * 
 * @ClassName: SimleConsumerListener
 * @author jason.liu
 */
public class SimpleConsumerListener {

	public String receiveMessage(String message) {
			// 这里我们知道生产者发送的就是一个纯文本消息，所以这里可以直接进行强制转换，或者直接把onMessage方法的参数改成Message的子类TextMessage
				System.out.println("从接收队列接收到的消息内容是：" + message);
				return "确认收到信息:"+message;
		
		
	}

}
