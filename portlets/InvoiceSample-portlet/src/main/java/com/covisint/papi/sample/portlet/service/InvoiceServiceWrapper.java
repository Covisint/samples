package com.covisint.papi.sample.portlet.service;

import com.liferay.portal.service.ServiceWrapper;

/**
 * <p>
 * This class is a wrapper for {@link InvoiceService}.
 * </p>
 *
 * @author    Nitin R. Khobragade
 * @see       InvoiceService
 * @generated
 */
public class InvoiceServiceWrapper implements InvoiceService,
    ServiceWrapper<InvoiceService> {
    private InvoiceService _invoiceService;

    public InvoiceServiceWrapper(InvoiceService invoiceService) {
        _invoiceService = invoiceService;
    }

    /**
    * Returns the Spring bean ID for this bean.
    *
    * @return the Spring bean ID for this bean
    */
    public java.lang.String getBeanIdentifier() {
        return _invoiceService.getBeanIdentifier();
    }

    /**
    * Sets the Spring bean ID for this bean.
    *
    * @param beanIdentifier the Spring bean ID for this bean
    */
    public void setBeanIdentifier(java.lang.String beanIdentifier) {
        _invoiceService.setBeanIdentifier(beanIdentifier);
    }

    public java.lang.Object invokeMethod(java.lang.String name,
        java.lang.String[] parameterTypes, java.lang.Object[] arguments)
        throws java.lang.Throwable {
        return _invoiceService.invokeMethod(name, parameterTypes, arguments);
    }

    public java.util.List<com.covisint.papi.sample.portlet.model.Invoice> getInvoicesForConsumer(
        long consumerId)
        throws com.liferay.portal.kernel.exception.SystemException {
        return _invoiceService.getInvoicesForConsumer(consumerId);
    }

    /**
     * @deprecated Renamed to {@link #getWrappedService}
     */
    public InvoiceService getWrappedInvoiceService() {
        return _invoiceService;
    }

    /**
     * @deprecated Renamed to {@link #setWrappedService}
     */
    public void setWrappedInvoiceService(InvoiceService invoiceService) {
        _invoiceService = invoiceService;
    }

    public InvoiceService getWrappedService() {
        return _invoiceService;
    }

    public void setWrappedService(InvoiceService invoiceService) {
        _invoiceService = invoiceService;
    }
}
