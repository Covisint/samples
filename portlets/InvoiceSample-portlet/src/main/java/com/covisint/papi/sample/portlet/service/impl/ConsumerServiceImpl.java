package com.covisint.papi.sample.portlet.service.impl;

import com.covisint.papi.sample.portlet.NoSuchConsumerException;
import com.covisint.papi.sample.portlet.model.Consumer;
import com.covisint.papi.sample.portlet.service.ConsumerLocalServiceUtil;
import com.covisint.papi.sample.portlet.service.base.ConsumerServiceBaseImpl;
import com.liferay.portal.kernel.exception.SystemException;

/**
 * The implementation of the consumer remote service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are
 * added, rerun ServiceBuilder to copy their definitions into the
 * {@link com.covisint.papi.sample.portlet.service.ConsumerService} interface.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have
 * security checks based on the propagated JAAS credentials because this service
 * can be accessed remotely.
 * </p>
 *
 * @author Nitin R. Khobragade
 * @see com.covisint.papi.sample.portlet.service.base.ConsumerServiceBaseImpl
 * @see com.covisint.papi.sample.portlet.service.ConsumerServiceUtil
 */
public class ConsumerServiceImpl extends ConsumerServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 * 
	 * Never reference this interface directly. Always use {@link
	 * com.covisint.papi.sample.portlet.service.ConsumerServiceUtil} to access
	 * the consumer remote service.
	 */
	public Consumer getConsumer(String consumerId)
			throws NoSuchConsumerException, SystemException {
		return ConsumerLocalServiceUtil.getConsumer(consumerId);
	}

}
