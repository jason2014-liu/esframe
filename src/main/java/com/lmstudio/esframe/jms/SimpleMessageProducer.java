/**
* TODO
* @Project: gsyth
* @Title: SimpleMessageProducer.java
* @Package com.wondersgroup.esf.jms
* @author jason.liu
* @Date 2016年5月19日 下午4:20:14
* @Copyright
* @Version 
*/
package com.lmstudio.esframe.jms;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
 * TODO for test
 * 
 * @ClassName: SimpleMessageProducer
 * @author jason.liu
 */
public class SimpleMessageProducer {

	private JmsTemplate jmsTemplate;
	
	private Destination responseQueue;
	
	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}
	
	public Destination getResponseQueue() {
		return responseQueue;
	}

	public void setResponseQueue(Destination responseQueue) {
		this.responseQueue = responseQueue;
	}

	public void sendMessage(Destination destination, final String message) {
		
		jmsTemplate.send(destination, new MessageCreator() {
			
			public Message createMessage(Session session) throws JMSException {
				
				TextMessage txtMessage =  session.createTextMessage(message);
				txtMessage.setJMSReplyTo(responseQueue);//设置回复消息队列
				return txtMessage;
			}
		});
	}

	/**
	 * TODO
	 * 
	 * @Title: main
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
