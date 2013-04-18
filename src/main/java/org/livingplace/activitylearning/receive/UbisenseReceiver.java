package org.livingplace.activitylearning.receive;

import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.joda.time.DateTime;
import org.livingplace.activitylearning.data.IData;
import org.livingplace.activitylearning.data.UbisenseData;
import org.livingplace.scriptsimulator.Helper;
import org.livingplace.scriptsimulator.script.entry.UbisenseMockupData;

import com.google.gson.Gson;

public class UbisenseReceiver extends AbstractDataReceiver{

	public UbisenseReceiver(String activeIP, String mongoIP, Gson gson)
	{
		super(activeIP, mongoIP, gson, Helper.UBISENSE_ENTRY_TOPIC_NAME);
	}
	
	public IData receive() {
		try {
			TextMessage message = (TextMessage) subscriber.receive();
			
			UbisenseMockupData data = gson.fromJson(message.getText(), UbisenseMockupData.class);

			UbisenseData ubidata = new UbisenseData(data.getTime(),data.getPosition());
			
//			System.out.println(ubidata.getPoint());
			return ubidata;
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}


}
