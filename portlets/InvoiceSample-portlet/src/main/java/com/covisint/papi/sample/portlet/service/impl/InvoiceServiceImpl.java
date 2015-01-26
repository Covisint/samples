package com.covisint.papi.sample.portlet.service.impl;

import java.util.List;

import com.covisint.papi.sample.portlet.model.Invoice;
import com.covisint.papi.sample.portlet.service.InvoiceLocalServiceUtil;
import com.covisint.papi.sample.portlet.service.base.InvoiceServiceBaseImpl;
import com.liferay.portal.kernel.exception.SystemException;

/**
 * The implementation of the invoice remote service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.covisint.papi.sample.portlet.service.InvoiceService} interface.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Nitin R. Khobragade
 * @see com.covisint.papi.sample.portlet.service.base.InvoiceServiceBaseImpl
 * @see com.covisint.papi.sample.portlet.service.InvoiceServiceUtil
 */
public class InvoiceServiceImpl extends InvoiceServiceBaseImpl {
    /*
     * NOTE FOR DEVELOPERS:
     *
     * Never reference this interface directly. Always use {@link com.covisint.papi.sample.portlet.service.InvoiceServiceUtil} to access the invoice remote service.
     */
	public List<Invoice> getInvoicesForConsumer(long consumerId) throws SystemException {
		return InvoiceLocalServiceUtil.getInvoicesForConsumer(consumerId);
	}
}
