package com.covisint.papi.sample.portlet.service.impl;

import java.util.List;

import com.covisint.papi.sample.portlet.model.Invoice;
import com.covisint.papi.sample.portlet.service.base.InvoiceLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.SystemException;

/**
 * The implementation of the invoice local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.covisint.papi.sample.portlet.service.InvoiceLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Nitin R. Khobragade
 * @see com.covisint.papi.sample.portlet.service.base.InvoiceLocalServiceBaseImpl
 * @see com.covisint.papi.sample.portlet.service.InvoiceLocalServiceUtil
 */
public class InvoiceLocalServiceImpl extends InvoiceLocalServiceBaseImpl {
    /*
     * NOTE FOR DEVELOPERS:
     *
     * Never reference this interface directly. Always use {@link com.covisint.papi.sample.portlet.service.InvoiceLocalServiceUtil} to access the invoice local service.
     */
	public List<Invoice> getInvoicesForConsumer(long consumerId) throws SystemException {
		return invoicePersistence.findByInvoiceForConsumer(consumerId);
	}
}
