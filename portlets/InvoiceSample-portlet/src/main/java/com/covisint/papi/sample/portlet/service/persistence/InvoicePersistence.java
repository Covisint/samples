package com.covisint.papi.sample.portlet.service.persistence;

import com.covisint.papi.sample.portlet.model.Invoice;

import com.liferay.portal.service.persistence.BasePersistence;

/**
 * The persistence interface for the invoice service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Nitin R. Khobragade
 * @see InvoicePersistenceImpl
 * @see InvoiceUtil
 * @generated
 */
public interface InvoicePersistence extends BasePersistence<Invoice> {
    /*
     * NOTE FOR DEVELOPERS:
     *
     * Never modify or reference this interface directly. Always use {@link InvoiceUtil} to access the invoice persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
     */

    /**
    * Caches the invoice in the entity cache if it is enabled.
    *
    * @param invoice the invoice
    */
    public void cacheResult(
        com.covisint.papi.sample.portlet.model.Invoice invoice);

    /**
    * Caches the invoices in the entity cache if it is enabled.
    *
    * @param invoices the invoices
    */
    public void cacheResult(
        java.util.List<com.covisint.papi.sample.portlet.model.Invoice> invoices);

    /**
    * Creates a new invoice with the primary key. Does not add the invoice to the database.
    *
    * @param invoiceId the primary key for the new invoice
    * @return the new invoice
    */
    public com.covisint.papi.sample.portlet.model.Invoice create(long invoiceId);

    /**
    * Removes the invoice with the primary key from the database. Also notifies the appropriate model listeners.
    *
    * @param invoiceId the primary key of the invoice
    * @return the invoice that was removed
    * @throws com.covisint.papi.sample.portlet.NoSuchInvoiceException if a invoice with the primary key could not be found
    * @throws SystemException if a system exception occurred
    */
    public com.covisint.papi.sample.portlet.model.Invoice remove(long invoiceId)
        throws com.covisint.papi.sample.portlet.NoSuchInvoiceException,
            com.liferay.portal.kernel.exception.SystemException;

    public com.covisint.papi.sample.portlet.model.Invoice updateImpl(
        com.covisint.papi.sample.portlet.model.Invoice invoice, boolean merge)
        throws com.liferay.portal.kernel.exception.SystemException;

    /**
    * Returns the invoice with the primary key or throws a {@link com.covisint.papi.sample.portlet.NoSuchInvoiceException} if it could not be found.
    *
    * @param invoiceId the primary key of the invoice
    * @return the invoice
    * @throws com.covisint.papi.sample.portlet.NoSuchInvoiceException if a invoice with the primary key could not be found
    * @throws SystemException if a system exception occurred
    */
    public com.covisint.papi.sample.portlet.model.Invoice findByPrimaryKey(
        long invoiceId)
        throws com.covisint.papi.sample.portlet.NoSuchInvoiceException,
            com.liferay.portal.kernel.exception.SystemException;

    /**
    * Returns the invoice with the primary key or returns <code>null</code> if it could not be found.
    *
    * @param invoiceId the primary key of the invoice
    * @return the invoice, or <code>null</code> if a invoice with the primary key could not be found
    * @throws SystemException if a system exception occurred
    */
    public com.covisint.papi.sample.portlet.model.Invoice fetchByPrimaryKey(
        long invoiceId)
        throws com.liferay.portal.kernel.exception.SystemException;

    /**
    * Returns all the invoices where userId = &#63;.
    *
    * @param userId the user ID
    * @return the matching invoices
    * @throws SystemException if a system exception occurred
    */
    public java.util.List<com.covisint.papi.sample.portlet.model.Invoice> findByInvoiceForUser(
        long userId) throws com.liferay.portal.kernel.exception.SystemException;

    /**
    * Returns a range of all the invoices where userId = &#63;.
    *
    * <p>
    * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
    * </p>
    *
    * @param userId the user ID
    * @param start the lower bound of the range of invoices
    * @param end the upper bound of the range of invoices (not inclusive)
    * @return the range of matching invoices
    * @throws SystemException if a system exception occurred
    */
    public java.util.List<com.covisint.papi.sample.portlet.model.Invoice> findByInvoiceForUser(
        long userId, int start, int end)
        throws com.liferay.portal.kernel.exception.SystemException;

    /**
    * Returns an ordered range of all the invoices where userId = &#63;.
    *
    * <p>
    * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
    * </p>
    *
    * @param userId the user ID
    * @param start the lower bound of the range of invoices
    * @param end the upper bound of the range of invoices (not inclusive)
    * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
    * @return the ordered range of matching invoices
    * @throws SystemException if a system exception occurred
    */
    public java.util.List<com.covisint.papi.sample.portlet.model.Invoice> findByInvoiceForUser(
        long userId, int start, int end,
        com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
        throws com.liferay.portal.kernel.exception.SystemException;

    /**
    * Returns the first invoice in the ordered set where userId = &#63;.
    *
    * @param userId the user ID
    * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
    * @return the first matching invoice
    * @throws com.covisint.papi.sample.portlet.NoSuchInvoiceException if a matching invoice could not be found
    * @throws SystemException if a system exception occurred
    */
    public com.covisint.papi.sample.portlet.model.Invoice findByInvoiceForUser_First(
        long userId,
        com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
        throws com.covisint.papi.sample.portlet.NoSuchInvoiceException,
            com.liferay.portal.kernel.exception.SystemException;

    /**
    * Returns the first invoice in the ordered set where userId = &#63;.
    *
    * @param userId the user ID
    * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
    * @return the first matching invoice, or <code>null</code> if a matching invoice could not be found
    * @throws SystemException if a system exception occurred
    */
    public com.covisint.papi.sample.portlet.model.Invoice fetchByInvoiceForUser_First(
        long userId,
        com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
        throws com.liferay.portal.kernel.exception.SystemException;

    /**
    * Returns the last invoice in the ordered set where userId = &#63;.
    *
    * @param userId the user ID
    * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
    * @return the last matching invoice
    * @throws com.covisint.papi.sample.portlet.NoSuchInvoiceException if a matching invoice could not be found
    * @throws SystemException if a system exception occurred
    */
    public com.covisint.papi.sample.portlet.model.Invoice findByInvoiceForUser_Last(
        long userId,
        com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
        throws com.covisint.papi.sample.portlet.NoSuchInvoiceException,
            com.liferay.portal.kernel.exception.SystemException;

    /**
    * Returns the last invoice in the ordered set where userId = &#63;.
    *
    * @param userId the user ID
    * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
    * @return the last matching invoice, or <code>null</code> if a matching invoice could not be found
    * @throws SystemException if a system exception occurred
    */
    public com.covisint.papi.sample.portlet.model.Invoice fetchByInvoiceForUser_Last(
        long userId,
        com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
        throws com.liferay.portal.kernel.exception.SystemException;

    /**
    * Returns the invoices before and after the current invoice in the ordered set where userId = &#63;.
    *
    * @param invoiceId the primary key of the current invoice
    * @param userId the user ID
    * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
    * @return the previous, current, and next invoice
    * @throws com.covisint.papi.sample.portlet.NoSuchInvoiceException if a invoice with the primary key could not be found
    * @throws SystemException if a system exception occurred
    */
    public com.covisint.papi.sample.portlet.model.Invoice[] findByInvoiceForUser_PrevAndNext(
        long invoiceId, long userId,
        com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
        throws com.covisint.papi.sample.portlet.NoSuchInvoiceException,
            com.liferay.portal.kernel.exception.SystemException;

    /**
    * Returns all the invoices.
    *
    * @return the invoices
    * @throws SystemException if a system exception occurred
    */
    public java.util.List<com.covisint.papi.sample.portlet.model.Invoice> findAll()
        throws com.liferay.portal.kernel.exception.SystemException;

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
    public java.util.List<com.covisint.papi.sample.portlet.model.Invoice> findAll(
        int start, int end)
        throws com.liferay.portal.kernel.exception.SystemException;

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
    public java.util.List<com.covisint.papi.sample.portlet.model.Invoice> findAll(
        int start, int end,
        com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
        throws com.liferay.portal.kernel.exception.SystemException;

    /**
    * Removes all the invoices where userId = &#63; from the database.
    *
    * @param userId the user ID
    * @throws SystemException if a system exception occurred
    */
    public void removeByInvoiceForUser(long userId)
        throws com.liferay.portal.kernel.exception.SystemException;

    /**
    * Removes all the invoices from the database.
    *
    * @throws SystemException if a system exception occurred
    */
    public void removeAll()
        throws com.liferay.portal.kernel.exception.SystemException;

    /**
    * Returns the number of invoices where userId = &#63;.
    *
    * @param userId the user ID
    * @return the number of matching invoices
    * @throws SystemException if a system exception occurred
    */
    public int countByInvoiceForUser(long userId)
        throws com.liferay.portal.kernel.exception.SystemException;

    /**
    * Returns the number of invoices.
    *
    * @return the number of invoices
    * @throws SystemException if a system exception occurred
    */
    public int countAll()
        throws com.liferay.portal.kernel.exception.SystemException;
}
