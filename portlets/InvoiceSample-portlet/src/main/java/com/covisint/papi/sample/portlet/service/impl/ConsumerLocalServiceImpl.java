package com.covisint.papi.sample.portlet.service.impl;

import com.covisint.papi.sample.portlet.NoSuchConsumerException;
import com.covisint.papi.sample.portlet.model.Consumer;
import com.covisint.papi.sample.portlet.service.base.ConsumerLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.SystemException;

/**
 * The implementation of the consumer local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are
 * added, rerun ServiceBuilder to copy their definitions into the
 * {@link com.covisint.papi.sample.portlet.service.ConsumerLocalService}
 * interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security
 * checks based on the propagated JAAS credentials because this service can only
 * be accessed from within the same VM.
 * </p>
 *
 * @author Nitin R. Khobragade
 * @see com.covisint.papi.sample.portlet.service.base.ConsumerLocalServiceBaseImpl
 * @see com.covisint.papi.sample.portlet.service.ConsumerLocalServiceUtil
 */
public class ConsumerLocalServiceImpl extends ConsumerLocalServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 * 
	 * Never reference this interface directly. Always use {@link
	 * com.covisint.papi.sample.portlet.service.ConsumerLocalServiceUtil} to
	 * access the consumer local service.
	 */
	public String getAuthenticationToken() {
		return null;
	}

	public Consumer getConsumer(String consumerId)
			throws NoSuchConsumerException, SystemException {
		return consumerPersistence.findByConsumerName(consumerId);
	}

	public boolean isDefaultDataPopulated() {
		return false;
	}

	public void populateDefaultData() {

	}
}
