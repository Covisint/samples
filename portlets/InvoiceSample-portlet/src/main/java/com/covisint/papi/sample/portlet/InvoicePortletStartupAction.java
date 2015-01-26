package com.covisint.papi.sample.portlet;

import com.covisint.papi.sample.portlet.model.Consumer;
import com.covisint.papi.sample.portlet.service.ConsumerLocalServiceUtil;
import com.covisint.papi.sample.portlet.service.persistence.ConsumerUtil;
import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.SimpleAction;
import com.liferay.portal.kernel.exception.SystemException;

public class InvoicePortletStartupAction extends SimpleAction {

	@Override
	public void run(String[] ids) throws ActionException {
		if (!isDefaultDataPresent()) {
			createDefaultData();
		}
	}

	private void createDefaultData() {
		try {
			long consumerId = CounterLocalServiceUtil.increment();
			Consumer consumer = ConsumerUtil.create(consumerId);
			consumer.setConsumerName("developer@covisint.com");
			consumer.setPassword("developer");
			ConsumerLocalServiceUtil.addConsumer(consumer);
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private boolean isDefaultDataPresent() {
		int totalConsumers = 0;
		try {
			totalConsumers = ConsumerLocalServiceUtil.getConsumersCount();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return totalConsumers > 0;
	}

}
