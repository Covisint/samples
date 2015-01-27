package com.covisint.papi.sample.portlet.service;

import com.liferay.portal.service.ServiceWrapper;

/**
 * <p>
 * This class is a wrapper for {@link InvoiceLocalService}.
 * </p>
 *
 * @author    Nitin R. Khobragade
 * @see       InvoiceLocalService
 * @generated
 */
public class InvoiceLocalServiceWrapper implements InvoiceLocalService,
    ServiceWrapper<InvoiceLocalService> {
    private InvoiceLocalService _invoiceLocalService;

    public InvoiceLocalServiceWrapper(InvoiceLocalService invoiceLocalService) {
        _invoiceLocalService = invoiceLocalService;
    }

    /**
    * Adds the invoice to the database. Also notifies the appropriate model listeners.
    *
    * @param invoice the invoice
    * @return the invoice that was added
    * @throws SystemException if a system exception occurred
    */
    public com.covisint.papi.sample.portlet.model.Invoice addInvoice(
        com.covisint.papi.sample.portlet.model.Invoice invoice)
        throws com.liferay.portal.kernel.exception.SystemException {
        return _invoiceLocalService.addInvoice(invoice);
    }

    /**
    * Creates a new invoice with the primary key. Does not add the invoice to the database.
    *
    * @param invoiceId the primary key for the new invoice
    * @return the new invoice
    */
    public com.covisint.papi.sample.portlet.model.Invoice createInvoice(
        long invoiceId) {
        return _invoiceLocalService.createInvoice(invoiceId);
    }

    /**
    * Deletes the invoice with the primary key from the database. Also notifies the appropriate model listeners.
    *
    * @param invoiceId the primary key of the invoice
    * @return the invoice that was removed
    * @throws PortalException if a invoice with the primary key could not be found
    * @throws SystemException if a system exception occurred
    */
    public com.covisint.papi.sample.portlet.model.Invoice deleteInvoice(
        long invoiceId)
        throws com.liferay.portal.kernel.exception.PortalException,
            com.liferay.portal.kernel.exception.SystemException {
        return _invoiceLocalService.deleteInvoice(invoiceId);
    }

    /**
    * Deletes the invoice from the database. Also notifies the appropriate model listeners.
    *
    * @param invoice the invoice
    * @return the invoice that was removed
    * @throws SystemException if a system exception occurred
    */
    public com.covisint.papi.sample.portlet.model.Invoice deleteInvoice(
        com.covisint.papi.sample.portlet.model.Invoice invoice)
        throws com.liferay.portal.kernel.exception.SystemException {
        return _invoiceLocalService.deleteInvoice(invoice);
    }

    public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
        return _invoiceLocalService.dynamicQuery();
    }

    /**
    * Performs a dynamic query on the database and returns the matching rows.
    *
    * @param dynamicQuery the dynamic query
    * @return the matching rows
    * @throws SystemException if a system exception occurred
    */
    @SuppressWarnings("rawtypes")
    public java.util.List dynamicQuery(
        com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
        throws com.liferay.portal.kernel.exception.SystemException {
        return _invoiceLocalService.dynamicQuery(dynamicQuery);
    }

    /**
    * Performs a dynamic query on the database and returns a range of the matching rows.
    *
    * <p>
    * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
    * </p>
    *
    * @param dynamicQuery the dynamic query
    * @param start the lower bound of the range of model instances
    * @param end the upper bound of the range of model instances (not inclusive)
    * @return the range of matching rows
    * @throws SystemException if a system exception occurred
    */
    @SuppressWarnings("rawtypes")
    public java.util.List dynamicQuery(
        com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
        int end) throws com.liferay.portal.kernel.exception.SystemException {
        return _invoiceLocalService.dynamicQuery(dynamicQuery, start, end);
    }

    /**
    * Performs a dynamic query on the database and returns an ordered range of the matching rows.
    *
    * <p>
    * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
    * </p>
    *
    * @param dynamicQuery the dynamic query
    * @param start the lower bound of the range of model instances
    * @param end the upper bound of the range of model instances (not inclusive)
    * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
    * @return the ordered range of matching rows
    * @throws SystemException if a system exception occurred
    */
    @SuppressWarnings("rawtypes")
    public java.util.List dynamicQuery(
        com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
        int end,
        com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
        throws com.liferay.portal.kernel.exception.SystemException {
        return _invoiceLocalService.dynamicQuery(dynamicQuery, start, end,
            orderByComparator);
    }

    /**
    * Returns the number of rows that match the dynamic query.
    *
    * @param dynamicQuery the dynamic query
    * @return the number of rows that match the dynamic query
    * @throws SystemException if a system exception occurred
    */
    public long dynamicQueryCount(
        com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
        throws com.liferay.portal.kernel.exception.SystemException {
        return _invoiceLocalService.dynamicQueryCount(dynamicQuery);
    }

    public com.covisint.papi.sample.portlet.model.Invoice fetchInvoice(
        long invoiceId)
        throws com.liferay.portal.kernel.exception.SystemException {
        return _invoiceLocalService.fetchInvoice(invoiceId);
    }

    /**
    * Returns the invoice with the primary key.
    *
    * @param invoiceId the primary key of the invoice
    * @return the invoice
    * @throws PortalException if a invoice with the primary key could not be found
    * @throws SystemException if a system exception occurred
    */
    public com.covisint.papi.sample.portlet.model.Invoice getInvoice(
        long invoiceId)
        throws com.liferay.portal.kernel.exception.PortalException,
            com.liferay.portal.kernel.exception.SystemException {
        return _invoiceLocalService.getInvoice(invoiceId);
    }

    public com.liferay.portal.model.PersistedModel getPersistedModel(
        java.io.Serializable primaryKeyObj)
        throws com.liferay.portal.kernel.exception.PortalException,
            com.liferay.portal.kernel.exception.SystemException {
        return _invoiceLocalService.getPersistedModel(primaryKeyObj);
    }

    /**
    * Returns a range of all the invoices.
    *
    * <p>
    * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
    * </p>
    *
    * @param start the lower bound of the range of invoices
    * @param end the upper bound of the range of invoices (not inclusive)
    * @return the range of invoices
    * @throws SystemException if a system exception occurred
    */
    public java.util.List<com.covisint.papi.sample.portlet.model.Invoice> getInvoices(
        int start, int end)
        throws com.liferay.portal.kernel.exception.SystemException {
        return _invoiceLocalService.getInvoices(start, end);
    }

    /**
    * Returns the number of invoices.
    *
    * @return the number of invoices
    * @throws SystemException if a system exception occurred
    */
    public int getInvoicesCount()
        throws com.liferay.portal.kernel.exception.SystemException {
        return _invoiceLocalService.getInvoicesCount();
    }

    /**
    * Updates the invoice in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
    *
    * @param invoice the invoice
    * @return the invoice that was updated
    * @throws SystemException if a system exception occurred
    */
    public com.covisint.papi.sample.portlet.model.Invoice updateInvoice(
        com.covisint.papi.sample.portlet.model.Invoice invoice)
        throws com.liferay.portal.kernel.exception.SystemException {
        return _invoiceLocalService.updateInvoice(invoice);
    }

    /**
    * Updates the invoice in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
    *
    * @param invoice the invoice
    * @param merge whether to merge the invoice with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
    * @return the invoice that was updated
    * @throws SystemException if a system exception occurred
    */
    public com.covisint.papi.sample.portlet.model.Invoice updateInvoice(
        com.covisint.papi.sample.portlet.model.Invoice invoice, boolean merge)
        throws com.liferay.portal.kernel.exception.SystemException {
        return _invoiceLocalService.updateInvoice(invoice, merge);
    }

    /**
    * Returns the Spring bean ID for this bean.
    *
    * @return the Spring bean ID for this bean
    */
    public java.lang.String getBeanIdentifier() {
        return _invoiceLocalService.getBeanIdentifier();
    }

    /**
    * Sets the Spring bean ID for this bean.
    *
    * @param beanIdentifier the Spring bean ID for this bean
    */
    public void setBeanIdentifier(java.lang.String beanIdentifier) {
        _invoiceLocalService.setBeanIdentifier(beanIdentifier);
    }

    public java.lang.Object invokeMethod(java.lang.String name,
        java.lang.String[] parameterTypes, java.lang.Object[] arguments)
        throws java.lang.Throwable {
        return _invoiceLocalService.invokeMethod(name, parameterTypes, arguments);
    }

    public java.util.List<com.covisint.papi.sample.portlet.model.Invoice> getInvoicesForUser(
        long userId) throws com.liferay.portal.kernel.exception.SystemException {
        return _invoiceLocalService.getInvoicesForUser(userId);
    }

    /**
     * @deprecated Renamed to {@link #getWrappedService}
     */
    public InvoiceLocalService getWrappedInvoiceLocalService() {
        return _invoiceLocalService;
    }

    /**
     * @deprecated Renamed to {@link #setWrappedService}
     */
    public void setWrappedInvoiceLocalService(
        InvoiceLocalService invoiceLocalService) {
        _invoiceLocalService = invoiceLocalService;
    }

    public InvoiceLocalService getWrappedService() {
        return _invoiceLocalService;
    }

    public void setWrappedService(InvoiceLocalService invoiceLocalService) {
        _invoiceLocalService = invoiceLocalService;
    }
}
