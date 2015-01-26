package com.covisint.papi.sample.portlet.service.persistence;

import com.covisint.papi.sample.portlet.model.Invoice;

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

/**
 * The persistence utility for the invoice service. This utility wraps {@link InvoicePersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Nitin R. Khobragade
 * @see InvoicePersistence
 * @see InvoicePersistenceImpl
 * @generated
 */
public class InvoiceUtil {
    private static InvoicePersistence _persistence;

    /*
     * NOTE FOR DEVELOPERS:
     *
     * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
     */

    /**
     * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
     */
    public static void clearCache() {
        getPersistence().clearCache();
    }

    /**
     * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(com.liferay.portal.model.BaseModel)
     */
    public static void clearCache(Invoice invoice) {
        getPersistence().clearCache(invoice);
    }

    /**
     * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
     */
    public long countWithDynamicQuery(DynamicQuery dynamicQuery)
        throws SystemException {
        return getPersistence().countWithDynamicQuery(dynamicQuery);
    }

    /**
     * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
     */
    public static List<Invoice> findWithDynamicQuery(DynamicQuery dynamicQuery)
        throws SystemException {
        return getPersistence().findWithDynamicQuery(dynamicQuery);
    }

    /**
     * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
     */
    public static List<Invoice> findWithDynamicQuery(
        DynamicQuery dynamicQuery, int start, int end)
        throws SystemException {
        return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
    }

    /**
     * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
     */
    public static List<Invoice> findWithDynamicQuery(
        DynamicQuery dynamicQuery, int start, int end,
        OrderByComparator orderByComparator) throws SystemException {
        return getPersistence()
                   .findWithDynamicQuery(dynamicQuery, start, end,
            orderByComparator);
    }

    /**
     * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
     */
    public static Invoice update(Invoice invoice, boolean merge)
        throws SystemException {
        return getPersistence().update(invoice, merge);
    }

    /**
     * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
     */
    public static Invoice update(Invoice invoice, boolean merge,
        ServiceContext serviceContext) throws SystemException {
        return getPersistence().update(invoice, merge, serviceContext);
    }

    /**
    * Caches the invoice in the entity cache if it is enabled.
    *
    * @param invoice the invoice
    */
    public static void cacheResult(
        com.covisint.papi.sample.portlet.model.Invoice invoice) {
        getPersistence().cacheResult(invoice);
    }

    /**
    * Caches the invoices in the entity cache if it is enabled.
    *
    * @param invoices the invoices
    */
    public static void cacheResult(
        java.util.List<com.covisint.papi.sample.portlet.model.Invoice> invoices) {
        getPersistence().cacheResult(invoices);
    }

    /**
    * Creates a new invoice with the primary key. Does not add the invoice to the database.
    *
    * @param invoiceId the primary key for the new invoice
    * @return the new invoice
    */
    public static com.covisint.papi.sample.portlet.model.Invoice create(
        long invoiceId) {
        return getPersistence().create(invoiceId);
    }

    /**
    * Removes the invoice with the primary key from the database. Also notifies the appropriate model listeners.
    *
    * @param invoiceId the primary key of the invoice
    * @return the invoice that was removed
    * @throws com.covisint.papi.sample.portlet.NoSuchInvoiceException if a invoice with the primary key could not be found
    * @throws SystemException if a system exception occurred
    */
    public static com.covisint.papi.sample.portlet.model.Invoice remove(
        long invoiceId)
        throws com.covisint.papi.sample.portlet.NoSuchInvoiceException,
            com.liferay.portal.kernel.exception.SystemException {
        return getPersistence().remove(invoiceId);
    }

    public static com.covisint.papi.sample.portlet.model.Invoice updateImpl(
        com.covisint.papi.sample.portlet.model.Invoice invoice, boolean merge)
        throws com.liferay.portal.kernel.exception.SystemException {
        return getPersistence().updateImpl(invoice, merge);
    }

    /**
    * Returns the invoice with the primary key or throws a {@link com.covisint.papi.sample.portlet.NoSuchInvoiceException} if it could not be found.
    *
    * @param invoiceId the primary key of the invoice
    * @return the invoice
    * @throws com.covisint.papi.sample.portlet.NoSuchInvoiceException if a invoice with the primary key could not be found
    * @throws SystemException if a system exception occurred
    */
    public static com.covisint.papi.sample.portlet.model.Invoice findByPrimaryKey(
        long invoiceId)
        throws com.covisint.papi.sample.portlet.NoSuchInvoiceException,
            com.liferay.portal.kernel.exception.SystemException {
        return getPersistence().findByPrimaryKey(invoiceId);
    }

    /**
    * Returns the invoice with the primary key or returns <code>null</code> if it could not be found.
    *
    * @param invoiceId the primary key of the invoice
    * @return the invoice, or <code>null</code> if a invoice with the primary key could not be found
    * @throws SystemException if a system exception occurred
    */
    public static com.covisint.papi.sample.portlet.model.Invoice fetchByPrimaryKey(
        long invoiceId)
        throws com.liferay.portal.kernel.exception.SystemException {
        return getPersistence().fetchByPrimaryKey(invoiceId);
    }

    /**
    * Returns all the invoices where consumerId = &#63;.
    *
    * @param consumerId the consumer ID
    * @return the matching invoices
    * @throws SystemException if a system exception occurred
    */
    public static java.util.List<com.covisint.papi.sample.portlet.model.Invoice> findByInvoiceForConsumer(
        long consumerId)
        throws com.liferay.portal.kernel.exception.SystemException {
        return getPersistence().findByInvoiceForConsumer(consumerId);
    }

    /**
    * Returns a range of all the invoices where consumerId = &#63;.
    *
    * <p>
    * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
    * </p>
    *
    * @param consumerId the consumer ID
    * @param start the lower bound of the range of invoices
    * @param end the upper bound of the range of invoices (not inclusive)
    * @return the range of matching invoices
    * @throws SystemException if a system exception occurred
    */
    public static java.util.List<com.covisint.papi.sample.portlet.model.Invoice> findByInvoiceForConsumer(
        long consumerId, int start, int end)
        throws com.liferay.portal.kernel.exception.SystemException {
        return getPersistence().findByInvoiceForConsumer(consumerId, start, end);
    }

    /**
    * Returns an ordered range of all the invoices where consumerId = &#63;.
    *
    * <p>
    * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
    * </p>
    *
    * @param consumerId the consumer ID
    * @param start the lower bound of the range of invoices
    * @param end the upper bound of the range of invoices (not inclusive)
    * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
    * @return the ordered range of matching invoices
    * @throws SystemException if a system exception occurred
    */
    public static java.util.List<com.covisint.papi.sample.portlet.model.Invoice> findByInvoiceForConsumer(
        long consumerId, int start, int end,
        com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
        throws com.liferay.portal.kernel.exception.SystemException {
        return getPersistence()
                   .findByInvoiceForConsumer(consumerId, start, end,
            orderByComparator);
    }

    /**
    * Returns the first invoice in the ordered set where consumerId = &#63;.
    *
    * @param consumerId the consumer ID
    * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
    * @return the first matching invoice
    * @throws com.covisint.papi.sample.portlet.NoSuchInvoiceException if a matching invoice could not be found
    * @throws SystemException if a system exception occurred
    */
    public static com.covisint.papi.sample.portlet.model.Invoice findByInvoiceForConsumer_First(
        long consumerId,
        com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
        throws com.covisint.papi.sample.portlet.NoSuchInvoiceException,
            com.liferay.portal.kernel.exception.SystemException {
        return getPersistence()
                   .findByInvoiceForConsumer_First(consumerId, orderByComparator);
    }

    /**
    * Returns the first invoice in the ordered set where consumerId = &#63;.
    *
    * @param consumerId the consumer ID
    * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
    * @return the first matching invoice, or <code>null</code> if a matching invoice could not be found
    * @throws SystemException if a system exception occurred
    */
    public static com.covisint.papi.sample.portlet.model.Invoice fetchByInvoiceForConsumer_First(
        long consumerId,
        com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
        throws com.liferay.portal.kernel.exception.SystemException {
        return getPersistence()
                   .fetchByInvoiceForConsumer_First(consumerId,
            orderByComparator);
    }

    /**
    * Returns the last invoice in the ordered set where consumerId = &#63;.
    *
    * @param consumerId the consumer ID
    * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
    * @return the last matching invoice
    * @throws com.covisint.papi.sample.portlet.NoSuchInvoiceException if a matching invoice could not be found
    * @throws SystemException if a system exception occurred
    */
    public static com.covisint.papi.sample.portlet.model.Invoice findByInvoiceForConsumer_Last(
        long consumerId,
        com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
        throws com.covisint.papi.sample.portlet.NoSuchInvoiceException,
            com.liferay.portal.kernel.exception.SystemException {
        return getPersistence()
                   .findByInvoiceForConsumer_Last(consumerId, orderByComparator);
    }

    /**
    * Returns the last invoice in the ordered set where consumerId = &#63;.
    *
    * @param consumerId the consumer ID
    * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
    * @return the last matching invoice, or <code>null</code> if a matching invoice could not be found
    * @throws SystemException if a system exception occurred
    */
    public static com.covisint.papi.sample.portlet.model.Invoice fetchByInvoiceForConsumer_Last(
        long consumerId,
        com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
        throws com.liferay.portal.kernel.exception.SystemException {
        return getPersistence()
                   .fetchByInvoiceForConsumer_Last(consumerId, orderByComparator);
    }

    /**
    * Returns the invoices before and after the current invoice in the ordered set where consumerId = &#63;.
    *
    * @param invoiceId the primary key of the current invoice
    * @param consumerId the consumer ID
    * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
    * @return the previous, current, and next invoice
    * @throws com.covisint.papi.sample.portlet.NoSuchInvoiceException if a invoice with the primary key could not be found
    * @throws SystemException if a system exception occurred
    */
    public static com.covisint.papi.sample.portlet.model.Invoice[] findByInvoiceForConsumer_PrevAndNext(
        long invoiceId, long consumerId,
        com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
        throws com.covisint.papi.sample.portlet.NoSuchInvoiceException,
            com.liferay.portal.kernel.exception.SystemException {
        return getPersistence()
                   .findByInvoiceForConsumer_PrevAndNext(invoiceId, consumerId,
            orderByComparator);
    }

    /**
    * Returns all the invoices.
    *
    * @return the invoices
    * @throws SystemException if a system exception occurred
    */
    public static java.util.List<com.covisint.papi.sample.portlet.model.Invoice> findAll()
        throws com.liferay.portal.kernel.exception.SystemException {
        return getPersistence().findAll();
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
    public static java.util.List<com.covisint.papi.sample.portlet.model.Invoice> findAll(
        int start, int end)
        throws com.liferay.portal.kernel.exception.SystemException {
        return getPersistence().findAll(start, end);
    }

    /**
    * Returns an ordered range of all the invoices.
    *
    * <p>
    * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
    * </p>
    *
    * @param start the lower bound of the range of invoices
    * @param end the upper bound of the range of invoices (not inclusive)
    * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
    * @return the ordered range of invoices
    * @throws SystemException if a system exception occurred
    */
    public static java.util.List<com.covisint.papi.sample.portlet.model.Invoice> findAll(
        int start, int end,
        com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
        throws com.liferay.portal.kernel.exception.SystemException {
        return getPersistence().findAll(start, end, orderByComparator);
    }

    /**
    * Removes all the invoices where consumerId = &#63; from the database.
    *
    * @param consumerId the consumer ID
    * @throws SystemException if a system exception occurred
    */
    public static void removeByInvoiceForConsumer(long consumerId)
        throws com.liferay.portal.kernel.exception.SystemException {
        getPersistence().removeByInvoiceForConsumer(consumerId);
    }

    /**
    * Removes all the invoices from the database.
    *
    * @throws SystemException if a system exception occurred
    */
    public static void removeAll()
        throws com.liferay.portal.kernel.exception.SystemException {
        getPersistence().removeAll();
    }

    /**
    * Returns the number of invoices where consumerId = &#63;.
    *
    * @param consumerId the consumer ID
    * @return the number of matching invoices
    * @throws SystemException if a system exception occurred
    */
    public static int countByInvoiceForConsumer(long consumerId)
        throws com.liferay.portal.kernel.exception.SystemException {
        return getPersistence().countByInvoiceForConsumer(consumerId);
    }

    /**
    * Returns the number of invoices.
    *
    * @return the number of invoices
    * @throws SystemException if a system exception occurred
    */
    public static int countAll()
        throws com.liferay.portal.kernel.exception.SystemException {
        return getPersistence().countAll();
    }

    public static InvoicePersistence getPersistence() {
        if (_persistence == null) {
            _persistence = (InvoicePersistence) PortletBeanLocatorUtil.locate(com.covisint.papi.sample.portlet.service.ClpSerializer.getServletContextName(),
                    InvoicePersistence.class.getName());

            ReferenceRegistry.registerReference(InvoiceUtil.class,
                "_persistence");
        }

        return _persistence;
    }

    /**
     * @deprecated
     */
    public void setPersistence(InvoicePersistence persistence) {
    }
}
