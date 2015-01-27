package com.covisint.papi.sample.portlet.service.persistence;

import com.covisint.papi.sample.portlet.NoSuchInvoiceException;
import com.covisint.papi.sample.portlet.model.Invoice;
import com.covisint.papi.sample.portlet.model.impl.InvoiceImpl;
import com.covisint.papi.sample.portlet.model.impl.InvoiceModelImpl;
import com.covisint.papi.sample.portlet.service.persistence.InvoicePersistence;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the invoice service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Nitin R. Khobragade
 * @see InvoicePersistence
 * @see InvoiceUtil
 * @generated
 */
public class InvoicePersistenceImpl extends BasePersistenceImpl<Invoice>
    implements InvoicePersistence {
    /*
     * NOTE FOR DEVELOPERS:
     *
     * Never modify or reference this class directly. Always use {@link InvoiceUtil} to access the invoice persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
     */
    public static final String FINDER_CLASS_NAME_ENTITY = InvoiceImpl.class.getName();
    public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
        ".List1";
    public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
        ".List2";
    public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_INVOICEFORUSER =
        new FinderPath(InvoiceModelImpl.ENTITY_CACHE_ENABLED,
            InvoiceModelImpl.FINDER_CACHE_ENABLED, InvoiceImpl.class,
            FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByInvoiceForUser",
            new String[] {
                Long.class.getName(),
                
            "java.lang.Integer", "java.lang.Integer",
                "com.liferay.portal.kernel.util.OrderByComparator"
            });
    public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_INVOICEFORUSER =
        new FinderPath(InvoiceModelImpl.ENTITY_CACHE_ENABLED,
            InvoiceModelImpl.FINDER_CACHE_ENABLED, InvoiceImpl.class,
            FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByInvoiceForUser",
            new String[] { Long.class.getName() },
            InvoiceModelImpl.USERID_COLUMN_BITMASK);
    public static final FinderPath FINDER_PATH_COUNT_BY_INVOICEFORUSER = new FinderPath(InvoiceModelImpl.ENTITY_CACHE_ENABLED,
            InvoiceModelImpl.FINDER_CACHE_ENABLED, Long.class,
            FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByInvoiceForUser",
            new String[] { Long.class.getName() });
    public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(InvoiceModelImpl.ENTITY_CACHE_ENABLED,
            InvoiceModelImpl.FINDER_CACHE_ENABLED, InvoiceImpl.class,
            FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
    public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(InvoiceModelImpl.ENTITY_CACHE_ENABLED,
            InvoiceModelImpl.FINDER_CACHE_ENABLED, InvoiceImpl.class,
            FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
    public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(InvoiceModelImpl.ENTITY_CACHE_ENABLED,
            InvoiceModelImpl.FINDER_CACHE_ENABLED, Long.class,
            FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
    private static final String _SQL_SELECT_INVOICE = "SELECT invoice FROM Invoice invoice";
    private static final String _SQL_SELECT_INVOICE_WHERE = "SELECT invoice FROM Invoice invoice WHERE ";
    private static final String _SQL_COUNT_INVOICE = "SELECT COUNT(invoice) FROM Invoice invoice";
    private static final String _SQL_COUNT_INVOICE_WHERE = "SELECT COUNT(invoice) FROM Invoice invoice WHERE ";
    private static final String _FINDER_COLUMN_INVOICEFORUSER_USERID_2 = "invoice.userId = ?";
    private static final String _ORDER_BY_ENTITY_ALIAS = "invoice.";
    private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No Invoice exists with the primary key ";
    private static final String _NO_SUCH_ENTITY_WITH_KEY = "No Invoice exists with the key {";
    private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = GetterUtil.getBoolean(PropsUtil.get(
                PropsKeys.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE));
    private static Log _log = LogFactoryUtil.getLog(InvoicePersistenceImpl.class);
    private static Invoice _nullInvoice = new InvoiceImpl() {
            @Override
            public Object clone() {
                return this;
            }

            @Override
            public CacheModel<Invoice> toCacheModel() {
                return _nullInvoiceCacheModel;
            }
        };

    private static CacheModel<Invoice> _nullInvoiceCacheModel = new CacheModel<Invoice>() {
            public Invoice toEntityModel() {
                return _nullInvoice;
            }
        };

    @BeanReference(type = InvoicePersistence.class)
    protected InvoicePersistence invoicePersistence;
    @BeanReference(type = ResourcePersistence.class)
    protected ResourcePersistence resourcePersistence;
    @BeanReference(type = UserPersistence.class)
    protected UserPersistence userPersistence;

    /**
     * Caches the invoice in the entity cache if it is enabled.
     *
     * @param invoice the invoice
     */
    public void cacheResult(Invoice invoice) {
        EntityCacheUtil.putResult(InvoiceModelImpl.ENTITY_CACHE_ENABLED,
            InvoiceImpl.class, invoice.getPrimaryKey(), invoice);

        invoice.resetOriginalValues();
    }

    /**
     * Caches the invoices in the entity cache if it is enabled.
     *
     * @param invoices the invoices
     */
    public void cacheResult(List<Invoice> invoices) {
        for (Invoice invoice : invoices) {
            if (EntityCacheUtil.getResult(
                        InvoiceModelImpl.ENTITY_CACHE_ENABLED,
                        InvoiceImpl.class, invoice.getPrimaryKey()) == null) {
                cacheResult(invoice);
            } else {
                invoice.resetOriginalValues();
            }
        }
    }

    /**
     * Clears the cache for all invoices.
     *
     * <p>
     * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
     * </p>
     */
    @Override
    public void clearCache() {
        if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
            CacheRegistryUtil.clear(InvoiceImpl.class.getName());
        }

        EntityCacheUtil.clearCache(InvoiceImpl.class.getName());

        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
    }

    /**
     * Clears the cache for the invoice.
     *
     * <p>
     * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
     * </p>
     */
    @Override
    public void clearCache(Invoice invoice) {
        EntityCacheUtil.removeResult(InvoiceModelImpl.ENTITY_CACHE_ENABLED,
            InvoiceImpl.class, invoice.getPrimaryKey());

        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
    }

    @Override
    public void clearCache(List<Invoice> invoices) {
        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

        for (Invoice invoice : invoices) {
            EntityCacheUtil.removeResult(InvoiceModelImpl.ENTITY_CACHE_ENABLED,
                InvoiceImpl.class, invoice.getPrimaryKey());
        }
    }

    /**
     * Creates a new invoice with the primary key. Does not add the invoice to the database.
     *
     * @param invoiceId the primary key for the new invoice
     * @return the new invoice
     */
    public Invoice create(long invoiceId) {
        Invoice invoice = new InvoiceImpl();

        invoice.setNew(true);
        invoice.setPrimaryKey(invoiceId);

        return invoice;
    }

    /**
     * Removes the invoice with the primary key from the database. Also notifies the appropriate model listeners.
     *
     * @param invoiceId the primary key of the invoice
     * @return the invoice that was removed
     * @throws com.covisint.papi.sample.portlet.NoSuchInvoiceException if a invoice with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public Invoice remove(long invoiceId)
        throws NoSuchInvoiceException, SystemException {
        return remove(Long.valueOf(invoiceId));
    }

    /**
     * Removes the invoice with the primary key from the database. Also notifies the appropriate model listeners.
     *
     * @param primaryKey the primary key of the invoice
     * @return the invoice that was removed
     * @throws com.covisint.papi.sample.portlet.NoSuchInvoiceException if a invoice with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    @Override
    public Invoice remove(Serializable primaryKey)
        throws NoSuchInvoiceException, SystemException {
        Session session = null;

        try {
            session = openSession();

            Invoice invoice = (Invoice) session.get(InvoiceImpl.class,
                    primaryKey);

            if (invoice == null) {
                if (_log.isWarnEnabled()) {
                    _log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
                }

                throw new NoSuchInvoiceException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
                    primaryKey);
            }

            return remove(invoice);
        } catch (NoSuchInvoiceException nsee) {
            throw nsee;
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }
    }

    @Override
    protected Invoice removeImpl(Invoice invoice) throws SystemException {
        invoice = toUnwrappedModel(invoice);

        Session session = null;

        try {
            session = openSession();

            BatchSessionUtil.delete(session, invoice);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }

        clearCache(invoice);

        return invoice;
    }

    @Override
    public Invoice updateImpl(
        com.covisint.papi.sample.portlet.model.Invoice invoice, boolean merge)
        throws SystemException {
        invoice = toUnwrappedModel(invoice);

        boolean isNew = invoice.isNew();

        InvoiceModelImpl invoiceModelImpl = (InvoiceModelImpl) invoice;

        Session session = null;

        try {
            session = openSession();

            BatchSessionUtil.update(session, invoice, merge);

            invoice.setNew(false);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }

        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

        if (isNew || !InvoiceModelImpl.COLUMN_BITMASK_ENABLED) {
            FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
        }
        else {
            if ((invoiceModelImpl.getColumnBitmask() &
                    FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_INVOICEFORUSER.getColumnBitmask()) != 0) {
                Object[] args = new Object[] {
                        Long.valueOf(invoiceModelImpl.getOriginalUserId())
                    };

                FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_INVOICEFORUSER,
                    args);
                FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_INVOICEFORUSER,
                    args);

                args = new Object[] { Long.valueOf(invoiceModelImpl.getUserId()) };

                FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_INVOICEFORUSER,
                    args);
                FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_INVOICEFORUSER,
                    args);
            }
        }

        EntityCacheUtil.putResult(InvoiceModelImpl.ENTITY_CACHE_ENABLED,
            InvoiceImpl.class, invoice.getPrimaryKey(), invoice);

        return invoice;
    }

    protected Invoice toUnwrappedModel(Invoice invoice) {
        if (invoice instanceof InvoiceImpl) {
            return invoice;
        }

        InvoiceImpl invoiceImpl = new InvoiceImpl();

        invoiceImpl.setNew(invoice.isNew());
        invoiceImpl.setPrimaryKey(invoice.getPrimaryKey());

        invoiceImpl.setInvoiceId(invoice.getInvoiceId());
        invoiceImpl.setPath(invoice.getPath());
        invoiceImpl.setUserId(invoice.getUserId());

        return invoiceImpl;
    }

    /**
     * Returns the invoice with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
     *
     * @param primaryKey the primary key of the invoice
     * @return the invoice
     * @throws com.liferay.portal.NoSuchModelException if a invoice with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    @Override
    public Invoice findByPrimaryKey(Serializable primaryKey)
        throws NoSuchModelException, SystemException {
        return findByPrimaryKey(((Long) primaryKey).longValue());
    }

    /**
     * Returns the invoice with the primary key or throws a {@link com.covisint.papi.sample.portlet.NoSuchInvoiceException} if it could not be found.
     *
     * @param invoiceId the primary key of the invoice
     * @return the invoice
     * @throws com.covisint.papi.sample.portlet.NoSuchInvoiceException if a invoice with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public Invoice findByPrimaryKey(long invoiceId)
        throws NoSuchInvoiceException, SystemException {
        Invoice invoice = fetchByPrimaryKey(invoiceId);

        if (invoice == null) {
            if (_log.isWarnEnabled()) {
                _log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + invoiceId);
            }

            throw new NoSuchInvoiceException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
                invoiceId);
        }

        return invoice;
    }

    /**
     * Returns the invoice with the primary key or returns <code>null</code> if it could not be found.
     *
     * @param primaryKey the primary key of the invoice
     * @return the invoice, or <code>null</code> if a invoice with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    @Override
    public Invoice fetchByPrimaryKey(Serializable primaryKey)
        throws SystemException {
        return fetchByPrimaryKey(((Long) primaryKey).longValue());
    }

    /**
     * Returns the invoice with the primary key or returns <code>null</code> if it could not be found.
     *
     * @param invoiceId the primary key of the invoice
     * @return the invoice, or <code>null</code> if a invoice with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public Invoice fetchByPrimaryKey(long invoiceId) throws SystemException {
        Invoice invoice = (Invoice) EntityCacheUtil.getResult(InvoiceModelImpl.ENTITY_CACHE_ENABLED,
                InvoiceImpl.class, invoiceId);

        if (invoice == _nullInvoice) {
            return null;
        }

        if (invoice == null) {
            Session session = null;

            boolean hasException = false;

            try {
                session = openSession();

                invoice = (Invoice) session.get(InvoiceImpl.class,
                        Long.valueOf(invoiceId));
            } catch (Exception e) {
                hasException = true;

                throw processException(e);
            } finally {
                if (invoice != null) {
                    cacheResult(invoice);
                } else if (!hasException) {
                    EntityCacheUtil.putResult(InvoiceModelImpl.ENTITY_CACHE_ENABLED,
                        InvoiceImpl.class, invoiceId, _nullInvoice);
                }

                closeSession(session);
            }
        }

        return invoice;
    }

    /**
     * Returns all the invoices where userId = &#63;.
     *
     * @param userId the user ID
     * @return the matching invoices
     * @throws SystemException if a system exception occurred
     */
    public List<Invoice> findByInvoiceForUser(long userId)
        throws SystemException {
        return findByInvoiceForUser(userId, QueryUtil.ALL_POS,
            QueryUtil.ALL_POS, null);
    }

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
    public List<Invoice> findByInvoiceForUser(long userId, int start, int end)
        throws SystemException {
        return findByInvoiceForUser(userId, start, end, null);
    }

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
    public List<Invoice> findByInvoiceForUser(long userId, int start, int end,
        OrderByComparator orderByComparator) throws SystemException {
        FinderPath finderPath = null;
        Object[] finderArgs = null;

        if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
                (orderByComparator == null)) {
            finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_INVOICEFORUSER;
            finderArgs = new Object[] { userId };
        } else {
            finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_INVOICEFORUSER;
            finderArgs = new Object[] { userId, start, end, orderByComparator };
        }

        List<Invoice> list = (List<Invoice>) FinderCacheUtil.getResult(finderPath,
                finderArgs, this);

        if ((list != null) && !list.isEmpty()) {
            for (Invoice invoice : list) {
                if ((userId != invoice.getUserId())) {
                    list = null;

                    break;
                }
            }
        }

        if (list == null) {
            StringBundler query = null;

            if (orderByComparator != null) {
                query = new StringBundler(3 +
                        (orderByComparator.getOrderByFields().length * 3));
            } else {
                query = new StringBundler(2);
            }

            query.append(_SQL_SELECT_INVOICE_WHERE);

            query.append(_FINDER_COLUMN_INVOICEFORUSER_USERID_2);

            if (orderByComparator != null) {
                appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
                    orderByComparator);
            }

            String sql = query.toString();

            Session session = null;

            try {
                session = openSession();

                Query q = session.createQuery(sql);

                QueryPos qPos = QueryPos.getInstance(q);

                qPos.add(userId);

                list = (List<Invoice>) QueryUtil.list(q, getDialect(), start,
                        end);
            } catch (Exception e) {
                throw processException(e);
            } finally {
                if (list == null) {
                    FinderCacheUtil.removeResult(finderPath, finderArgs);
                } else {
                    cacheResult(list);

                    FinderCacheUtil.putResult(finderPath, finderArgs, list);
                }

                closeSession(session);
            }
        }

        return list;
    }

    /**
     * Returns the first invoice in the ordered set where userId = &#63;.
     *
     * @param userId the user ID
     * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
     * @return the first matching invoice
     * @throws com.covisint.papi.sample.portlet.NoSuchInvoiceException if a matching invoice could not be found
     * @throws SystemException if a system exception occurred
     */
    public Invoice findByInvoiceForUser_First(long userId,
        OrderByComparator orderByComparator)
        throws NoSuchInvoiceException, SystemException {
        Invoice invoice = fetchByInvoiceForUser_First(userId, orderByComparator);

        if (invoice != null) {
            return invoice;
        }

        StringBundler msg = new StringBundler(4);

        msg.append(_NO_SUCH_ENTITY_WITH_KEY);

        msg.append("userId=");
        msg.append(userId);

        msg.append(StringPool.CLOSE_CURLY_BRACE);

        throw new NoSuchInvoiceException(msg.toString());
    }

    /**
     * Returns the first invoice in the ordered set where userId = &#63;.
     *
     * @param userId the user ID
     * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
     * @return the first matching invoice, or <code>null</code> if a matching invoice could not be found
     * @throws SystemException if a system exception occurred
     */
    public Invoice fetchByInvoiceForUser_First(long userId,
        OrderByComparator orderByComparator) throws SystemException {
        List<Invoice> list = findByInvoiceForUser(userId, 0, 1,
                orderByComparator);

        if (!list.isEmpty()) {
            return list.get(0);
        }

        return null;
    }

    /**
     * Returns the last invoice in the ordered set where userId = &#63;.
     *
     * @param userId the user ID
     * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
     * @return the last matching invoice
     * @throws com.covisint.papi.sample.portlet.NoSuchInvoiceException if a matching invoice could not be found
     * @throws SystemException if a system exception occurred
     */
    public Invoice findByInvoiceForUser_Last(long userId,
        OrderByComparator orderByComparator)
        throws NoSuchInvoiceException, SystemException {
        Invoice invoice = fetchByInvoiceForUser_Last(userId, orderByComparator);

        if (invoice != null) {
            return invoice;
        }

        StringBundler msg = new StringBundler(4);

        msg.append(_NO_SUCH_ENTITY_WITH_KEY);

        msg.append("userId=");
        msg.append(userId);

        msg.append(StringPool.CLOSE_CURLY_BRACE);

        throw new NoSuchInvoiceException(msg.toString());
    }

    /**
     * Returns the last invoice in the ordered set where userId = &#63;.
     *
     * @param userId the user ID
     * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
     * @return the last matching invoice, or <code>null</code> if a matching invoice could not be found
     * @throws SystemException if a system exception occurred
     */
    public Invoice fetchByInvoiceForUser_Last(long userId,
        OrderByComparator orderByComparator) throws SystemException {
        int count = countByInvoiceForUser(userId);

        List<Invoice> list = findByInvoiceForUser(userId, count - 1, count,
                orderByComparator);

        if (!list.isEmpty()) {
            return list.get(0);
        }

        return null;
    }

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
    public Invoice[] findByInvoiceForUser_PrevAndNext(long invoiceId,
        long userId, OrderByComparator orderByComparator)
        throws NoSuchInvoiceException, SystemException {
        Invoice invoice = findByPrimaryKey(invoiceId);

        Session session = null;

        try {
            session = openSession();

            Invoice[] array = new InvoiceImpl[3];

            array[0] = getByInvoiceForUser_PrevAndNext(session, invoice,
                    userId, orderByComparator, true);

            array[1] = invoice;

            array[2] = getByInvoiceForUser_PrevAndNext(session, invoice,
                    userId, orderByComparator, false);

            return array;
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }
    }

    protected Invoice getByInvoiceForUser_PrevAndNext(Session session,
        Invoice invoice, long userId, OrderByComparator orderByComparator,
        boolean previous) {
        StringBundler query = null;

        if (orderByComparator != null) {
            query = new StringBundler(6 +
                    (orderByComparator.getOrderByFields().length * 6));
        } else {
            query = new StringBundler(3);
        }

        query.append(_SQL_SELECT_INVOICE_WHERE);

        query.append(_FINDER_COLUMN_INVOICEFORUSER_USERID_2);

        if (orderByComparator != null) {
            String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

            if (orderByConditionFields.length > 0) {
                query.append(WHERE_AND);
            }

            for (int i = 0; i < orderByConditionFields.length; i++) {
                query.append(_ORDER_BY_ENTITY_ALIAS);
                query.append(orderByConditionFields[i]);

                if ((i + 1) < orderByConditionFields.length) {
                    if (orderByComparator.isAscending() ^ previous) {
                        query.append(WHERE_GREATER_THAN_HAS_NEXT);
                    } else {
                        query.append(WHERE_LESSER_THAN_HAS_NEXT);
                    }
                } else {
                    if (orderByComparator.isAscending() ^ previous) {
                        query.append(WHERE_GREATER_THAN);
                    } else {
                        query.append(WHERE_LESSER_THAN);
                    }
                }
            }

            query.append(ORDER_BY_CLAUSE);

            String[] orderByFields = orderByComparator.getOrderByFields();

            for (int i = 0; i < orderByFields.length; i++) {
                query.append(_ORDER_BY_ENTITY_ALIAS);
                query.append(orderByFields[i]);

                if ((i + 1) < orderByFields.length) {
                    if (orderByComparator.isAscending() ^ previous) {
                        query.append(ORDER_BY_ASC_HAS_NEXT);
                    } else {
                        query.append(ORDER_BY_DESC_HAS_NEXT);
                    }
                } else {
                    if (orderByComparator.isAscending() ^ previous) {
                        query.append(ORDER_BY_ASC);
                    } else {
                        query.append(ORDER_BY_DESC);
                    }
                }
            }
        }

        String sql = query.toString();

        Query q = session.createQuery(sql);

        q.setFirstResult(0);
        q.setMaxResults(2);

        QueryPos qPos = QueryPos.getInstance(q);

        qPos.add(userId);

        if (orderByComparator != null) {
            Object[] values = orderByComparator.getOrderByConditionValues(invoice);

            for (Object value : values) {
                qPos.add(value);
            }
        }

        List<Invoice> list = q.list();

        if (list.size() == 2) {
            return list.get(1);
        } else {
            return null;
        }
    }

    /**
     * Returns all the invoices.
     *
     * @return the invoices
     * @throws SystemException if a system exception occurred
     */
    public List<Invoice> findAll() throws SystemException {
        return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
    public List<Invoice> findAll(int start, int end) throws SystemException {
        return findAll(start, end, null);
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
    public List<Invoice> findAll(int start, int end,
        OrderByComparator orderByComparator) throws SystemException {
        FinderPath finderPath = null;
        Object[] finderArgs = new Object[] { start, end, orderByComparator };

        if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
                (orderByComparator == null)) {
            finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
            finderArgs = FINDER_ARGS_EMPTY;
        } else {
            finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
            finderArgs = new Object[] { start, end, orderByComparator };
        }

        List<Invoice> list = (List<Invoice>) FinderCacheUtil.getResult(finderPath,
                finderArgs, this);

        if (list == null) {
            StringBundler query = null;
            String sql = null;

            if (orderByComparator != null) {
                query = new StringBundler(2 +
                        (orderByComparator.getOrderByFields().length * 3));

                query.append(_SQL_SELECT_INVOICE);

                appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
                    orderByComparator);

                sql = query.toString();
            } else {
                sql = _SQL_SELECT_INVOICE;
            }

            Session session = null;

            try {
                session = openSession();

                Query q = session.createQuery(sql);

                if (orderByComparator == null) {
                    list = (List<Invoice>) QueryUtil.list(q, getDialect(),
                            start, end, false);

                    Collections.sort(list);
                } else {
                    list = (List<Invoice>) QueryUtil.list(q, getDialect(),
                            start, end);
                }
            } catch (Exception e) {
                throw processException(e);
            } finally {
                if (list == null) {
                    FinderCacheUtil.removeResult(finderPath, finderArgs);
                } else {
                    cacheResult(list);

                    FinderCacheUtil.putResult(finderPath, finderArgs, list);
                }

                closeSession(session);
            }
        }

        return list;
    }

    /**
     * Removes all the invoices where userId = &#63; from the database.
     *
     * @param userId the user ID
     * @throws SystemException if a system exception occurred
     */
    public void removeByInvoiceForUser(long userId) throws SystemException {
        for (Invoice invoice : findByInvoiceForUser(userId)) {
            remove(invoice);
        }
    }

    /**
     * Removes all the invoices from the database.
     *
     * @throws SystemException if a system exception occurred
     */
    public void removeAll() throws SystemException {
        for (Invoice invoice : findAll()) {
            remove(invoice);
        }
    }

    /**
     * Returns the number of invoices where userId = &#63;.
     *
     * @param userId the user ID
     * @return the number of matching invoices
     * @throws SystemException if a system exception occurred
     */
    public int countByInvoiceForUser(long userId) throws SystemException {
        Object[] finderArgs = new Object[] { userId };

        Long count = (Long) FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_INVOICEFORUSER,
                finderArgs, this);

        if (count == null) {
            StringBundler query = new StringBundler(2);

            query.append(_SQL_COUNT_INVOICE_WHERE);

            query.append(_FINDER_COLUMN_INVOICEFORUSER_USERID_2);

            String sql = query.toString();

            Session session = null;

            try {
                session = openSession();

                Query q = session.createQuery(sql);

                QueryPos qPos = QueryPos.getInstance(q);

                qPos.add(userId);

                count = (Long) q.uniqueResult();
            } catch (Exception e) {
                throw processException(e);
            } finally {
                if (count == null) {
                    count = Long.valueOf(0);
                }

                FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_INVOICEFORUSER,
                    finderArgs, count);

                closeSession(session);
            }
        }

        return count.intValue();
    }

    /**
     * Returns the number of invoices.
     *
     * @return the number of invoices
     * @throws SystemException if a system exception occurred
     */
    public int countAll() throws SystemException {
        Long count = (Long) FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
                FINDER_ARGS_EMPTY, this);

        if (count == null) {
            Session session = null;

            try {
                session = openSession();

                Query q = session.createQuery(_SQL_COUNT_INVOICE);

                count = (Long) q.uniqueResult();
            } catch (Exception e) {
                throw processException(e);
            } finally {
                if (count == null) {
                    count = Long.valueOf(0);
                }

                FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL,
                    FINDER_ARGS_EMPTY, count);

                closeSession(session);
            }
        }

        return count.intValue();
    }

    /**
     * Initializes the invoice persistence.
     */
    public void afterPropertiesSet() {
        String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
                    com.liferay.util.service.ServiceProps.get(
                        "value.object.listener.com.covisint.papi.sample.portlet.model.Invoice")));

        if (listenerClassNames.length > 0) {
            try {
                List<ModelListener<Invoice>> listenersList = new ArrayList<ModelListener<Invoice>>();

                for (String listenerClassName : listenerClassNames) {
                    Class<?> clazz = getClass();

                    listenersList.add((ModelListener<Invoice>) InstanceFactory.newInstance(
                            clazz.getClassLoader(), listenerClassName));
                }

                listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
            } catch (Exception e) {
                _log.error(e);
            }
        }
    }

    public void destroy() {
        EntityCacheUtil.removeCache(InvoiceImpl.class.getName());
        FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
        FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
    }
}
