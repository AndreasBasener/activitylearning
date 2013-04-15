package org.livingplace.activitylearning.sensor;

import java.io.FileNotFoundException;
import java.io.PrintWriter;


import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.livingplace.scriptsimulator.Helper;
import org.livingplace.scriptsimulator.script.entry.UbisenseMockupData;

import com.google.gson.Gson;

/**
 * 
 * @author Andreas Basener
 *
 */
public class Sensor implements ISensor
{
	protected static ActiveMQConnectionFactory connectionFactory;
	protected static TopicConnection connection;
	protected static boolean transacted = false;

	protected TopicSubscriber subscriber;
	
	protected String name;
	
	protected Gson gson;
	
	public Sensor(String name, Gson gson)
	{
		this.name = name;
		this.gson = gson;
		
		connectToActiveMQ();
	}
	
	public String getName() 
	{
		return this.name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	public String getType() 
	{
		return this.getClass().getSimpleName();
	}
	
	public void setGson(Gson gson)
	{
		this.gson = gson;
	}
	
	public static void connectToActiveMQ()
	{

		if(connectionFactory == null)
		{
			connectionFactory = new ActiveMQConnectionFactory(Helper.URL);
			try
			{
				connection = connectionFactory.createTopicConnection();
				connection.start();
			}
			catch(Exception e)
			{
				
			}
		}
	}
	
	public void connectToTopic(String topicName)
	{

		try{
			TopicSession newSession = connection.createTopicSession(transacted,
																	Session.AUTO_ACKNOWLEDGE);
			Topic topic = newSession.createTopic(topicName);

			// Setup a message publisher to send messages to the topic
			subscriber = newSession.createSubscriber(topic);
		}
		catch(Exception e)
		{
			
		}
	}
	
	public void readFromActiveMQ(boolean continuous)
	{
		System.out.println("beginne");

		PrintWriter writer = null;
		try {
			writer = new PrintWriter(name +".csv");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		if (writer != null) do
		{
			try 
			{
				TextMessage message =(TextMessage) subscriber.receive();
				String text = message.getText();
				String out;
//				System.out.println(text);
				UbisenseMockupData data = gson.fromJson(text, UbisenseMockupData.class);
				
				out = data.getPosition().getX() + " " + data.getPosition().getY() + " " + this.name;
				
//				System.out.println(data.toJson());
				System.out.println(out);

				writer.println(out);
				writer.flush();
			} 
			catch (JMSException e) 
			{
				e.printStackTrace();
			}
		}
		while(continuous);
	}

}
