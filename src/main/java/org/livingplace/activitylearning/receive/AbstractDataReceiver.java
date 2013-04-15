package org.livingplace.activitylearning.receive;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.livingplace.activitylearning.data.IData;
import com.google.gson.Gson;

/**
 * 
 * @author Andreas Basener
 *
 */
public abstract class AbstractDataReceiver {

	private String activeIP;
	private String mongoIP;
	private String topicName;
	protected Gson gson;
	

	private ActiveMQConnectionFactory connectionFactory;
	private TopicConnection connection;
	private boolean transacted = false;

	protected TopicSubscriber subscriber;
	
	public AbstractDataReceiver(String activeIP, String mongoIP, Gson gson, String topicName)
	{
		this.activeIP = activeIP;
		this.mongoIP = mongoIP;
		this.topicName = topicName;
		this.gson = gson;
	}
	
	public void init()
	{


		connectionFactory = new ActiveMQConnectionFactory(activeIP);
		try
		{
			connection = connectionFactory.createTopicConnection();
			connection.start();
			
			TopicSession newSession = connection.createTopicSession(transacted,
																	Session.AUTO_ACKNOWLEDGE);
			Topic topic = newSession.createTopic(topicName);

			// Setup a message publisher to send messages to the topic
			subscriber = newSession.createSubscriber(topic);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	public IData receive()
	{
		try {
			TextMessage message = (TextMessage) subscriber.receive();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	/**
	 * @return the activeIP
	 */
	public String getActiveIP() {
		return activeIP;
	}

	/**
	 * @param activeIP the activeIP to set
	 */
	public void setActiveIP(String activeIP) {
		this.activeIP = activeIP;
	}

	/**
	 * @return the mongoIP
	 */
	public String getMongoIP() {
		return mongoIP;
	}

	/**
	 * @param mongoIP the mongoIP to set
	 */
	public void setMongoIP(String mongoIP) {
		this.mongoIP = mongoIP;
	}

	/**
	 * @return the topic
	 */
	public String getTopic() {
		return topicName;
	}

	/**
	 * @param topic the topic to set
	 */
	public void setTopic(String topic) {
		this.topicName = topic;
	}

	/**
	 * @return the gson
	 */
	public Gson getGson() {
		return gson;
	}

	/**
	 * @param gson the gson to set
	 */
	public void setGson(Gson gson) {
		this.gson = gson;
	}
	
}
