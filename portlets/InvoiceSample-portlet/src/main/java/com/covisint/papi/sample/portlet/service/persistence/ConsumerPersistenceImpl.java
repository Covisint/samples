package com.covisint.papi.sample.portlet.service.persistence;

import com.covisint.papi.sample.portlet.NoSuchConsumerException;
import com.covisint.papi.sample.portlet.model.Consumer;
import com.covisint.papi.sample.portlet.model.impl.ConsumerImpl;
import com.covisint.papi.sample.portlet.model.impl.ConsumerModelImpl;
import com.covisint.papi.sample.portlet.service.persistence.ConsumerPersistence;
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
import com.liferay.portal.kernel.util.Validator;
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
 * The persistence implementation for the consumer service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Nitin R. Khobragade
 * @see ConsumerPersistence
 * @see ConsumerUtil
 * @generated
 */
public class ConsumerPersistenceImpl extends BasePersistenceImpl<Consumer>
    implements ConsumerPersistence {
    /*
     * NOTE FOR DEVELOPERS:
     *
     * Never modify or reference this class directly. Always use {@link ConsumerUtil} to access the consumer persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
     */
    public static final String FINDER_CLASS_NAME_ENTITY = ConsumerImpl.class.getName();
    public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
        ".List1";
    public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
        ".List2";
    public static final FinderPath FINDER_PATH_FETCH_BY_CONSUMERNAME = new FinderPath(ConsumerModelImpl.ENTITY_CACHE_ENABLED,
            ConsumerModelImpl.FINDER_CACHE_ENABLED, ConsumerImpl.class,
            FINDER_CLASS_NAME_ENTITY, "fetchByConsumerName",
            new String[] { String.class.getName() },
            ConsumerModelImpl.CONSUMERNAME_COLUMN_BITMASK);
    public static final FinderPath FINDER_PATH_COUNT_BY_CONSUMERNAME = new FinderPath(ConsumerModelImpl.ENTITY_CACHE_ENABLED,
            ConsumerModelImpl.FINDER_CACHE_ENABLED, Long.class,
            FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByConsumerName",
            new String[] { String.class.getName() });
    public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(ConsumerModelImpl.ENTITY_CACHE_ENABLED,
            ConsumerModelImpl.FINDER_CACHE_ENABLED, ConsumerImpl.class,
            FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
    public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(ConsumerModelImpl.ENTITY_CACHE_ENABLED,
            ConsumerModelImpl.FINDER_CACHE_ENABLED, ConsumerImpl.class,
            FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
    public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(ConsumerModelImpl.ENTITY_CACHE_ENABLED,
            ConsumerModelImpl.FINDER_CACHE_ENABLED, Long.class,
            FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
    private static final String _SQL_SELECT_CONSUMER = "SELECT consumer FROM Consumer consumer";
    private static final String _SQL_SELECT_CONSUMER_WHERE = "SELECT consumer FROM Consumer consumer WHERE ";
    private static final String _SQL_COUNT_CONSUMER = "SELECT COUNT(consumer) FROM Consumer consumer";
    private static final String _SQL_COUNT_CONSUMER_WHERE = "SELECT COUNT(consumer) FROM Consumer consumer WHERE ";
    private static final String _FINDER_COLUMN_CONSUMERNAME_CONSUMERNAME_1 = "consumer.consumerName IS NULL";
    private static final String _FINDER_COLUMN_CONSUMERNAME_CONSUMERNAME_2 = "consumer.consumerName = ?";
    private static final String _FINDER_COLUMN_CONSUMERNAME_CONSUMERNAME_3 = "(consumer.consumerName IS NULL OR consumer.consumerName = ?)";
    private static final String _ORDER_BY_ENTITY_ALIAS = "consumer.";
    private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No Consumer exists with the primary key ";
    private static final String _NO_SUCH_ENTITY_WITH_KEY = "No Consumer exists with the key {";
    private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = GetterUtil.getBoolean(PropsUtil.get(
                PropsKeys.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE));
    private static Log _log = LogFactoryUtil.getLog(ConsumerPersistenceImpl.class);
    private static Consumer _nullConsumer = new ConsumerImpl() {
            @Override
            public Object clone() {
                return this;
            }

            @Override
            public CacheModel<Consumer> toCacheModel() {
                return _nullConsumerCacheModel;
            }
        };

    private static CacheModel<Consumer> _nullConsumerCacheModel = new CacheModel<Consumer>() {
            public Consumer toEntityModel() {
                return _nullConsumer;
            }
        };

    @BeanReference(type = ConsumerPersistence.class)
    protected ConsumerPersistence consumerPersistence;
    @BeanReference(type = InvoicePersistence.class)
    protected InvoicePersistence invoicePersistence;
    @BeanReference(type = ResourcePersistence.class)
    protected ResourcePersistence resourcePersistence;
    @BeanReference(type = UserPersistence.class)
    protected UserPersistence userPersistence;

    /**
     * Caches the consumer in the entity cache if it is enabled.
     *
     * @param consumer the consumer
     */
    public void cacheResult(Consumer consumer) {
        EntityCacheUtil.putResult(ConsumerModelImpl.ENTITY_CACHE_ENABLED,
            ConsumerImpl.class, consumer.getPrimaryKey(), consumer);

        FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CONSUMERNAME,
            new Object[] { consumer.getConsumerName() }, consumer);

        consumer.resetOriginalValues();
    }

    /**
     * Caches the consumers in the entity cache if it is enabled.
     *
     * @param consumers the consumers
     */
    public void cacheResult(List<Consumer> consumers) {
        for (Consumer consumer : consumers) {
            if (EntityCacheUtil.getResult(
                        ConsumerModelImpl.ENTITY_CACHE_ENABLED,
                        ConsumerImpl.class, consumer.getPrimaryKey()) == null) {
                cacheResult(consumer);
            } else {
                consumer.resetOriginalValues();
            }
        }
    }

    /**
     * Clears the cache for all consumers.
     *
     * <p>
     * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
     * </p>
     */
    @Override
    public void clearCache() {
        if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
            CacheRegistryUtil.clear(ConsumerImpl.class.getName());
        }

        EntityCacheUtil.clearCache(ConsumerImpl.class.getName());

        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
    }

    /**
     * Clears the cache for the consumer.
     *
     * <p>
     * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
     * </p>
     */
    @Override
    public void clearCache(Consumer consumer) {
        EntityCacheUtil.removeResult(ConsumerModelImpl.ENTITY_CACHE_ENABLED,
            ConsumerImpl.class, consumer.getPrimaryKey());

        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

        clearUniqueFindersCache(consumer);
    }

    @Override
    public void clearCache(List<Consumer> consumers) {
        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

        for (Consumer consumer : consumers) {
            EntityCacheUtil.removeResult(ConsumerModelImpl.ENTITY_CACHE_ENABLED,
                ConsumerImpl.class, consumer.getPrimaryKey());

            clearUniqueFindersCache(consumer);
        }
    }

    protected void cacheUniqueFindersCache(Consumer consumer) {
        if (consumer.isNew()) {
            Object[] args = new Object[] { consumer.getConsumerName() };

            FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_CONSUMERNAME, args,
                Long.valueOf(1));
            FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CONSUMERNAME, args,
                consumer);
        } else {
            ConsumerModelImpl consumerModelImpl = (ConsumerModelImpl) consumer;

            if ((consumerModelImpl.getColumnBitmask() &
                    FINDER_PATH_FETCH_BY_CONSUMERNAME.getColumnBitmask()) != 0) {
                Object[] args = new Object[] { consumer.getConsumerName() };

                FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_CONSUMERNAME,
                    args, Long.valueOf(1));
                FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CONSUMERNAME,
                    args, consumer);
            }
        }
    }

    protected void clearUniqueFindersCache(Consumer consumer) {
        ConsumerModelImpl consumerModelImpl = (ConsumerModelImpl) consumer;

        Object[] args = new Object[] { consumer.getConsumerName() };

        FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_CONSUMERNAME, args);
        FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_CONSUMERNAME, args);

        if ((consumerModelImpl.getColumnBitmask() &
                FINDER_PATH_FETCH_BY_CONSUMERNAME.getColumnBitmask()) != 0) {
            args = new Object[] { consumerModelImpl.getOriginalConsumerName() };

            FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_CONSUMERNAME, args);
            FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_CONSUMERNAME, args);
        }
    }

    /**
     * Creates a new consumer with the primary key. Does not add the consumer to the database.
     *
     * @param consumerId the primary key for the new consumer
     * @return the new consumer
     */
    public Consumer create(long consumerId) {
        Consumer consumer = new ConsumerImpl();

        consumer.setNew(true);
        consumer.setPrimaryKey(consumerId);

        return consumer;
    }

    /**
     * Removes the consumer with the primary key from the database. Also notifies the appropriate model listeners.
     *
     * @param consumerId the primary key of the consumer
     * @return the consumer that was removed
     * @throws com.covisint.papi.sample.portlet.NoSuchConsumerException if a consumer with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public Consumer remove(long consumerId)
        throws NoSuchConsumerException, SystemException {
        return remove(Long.valueOf(consumerId));
    }

    /**
     * Removes the consumer with the primary key from the database. Also notifies the appropriate model listeners.
     *
     * @param primaryKey the primary key of the consumer
     * @return the consumer that was removed
     * @throws com.covisint.papi.sample.portlet.NoSuchConsumerException if a consumer with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    @Override
    public Consumer remove(Serializable primaryKey)
        throws NoSuchConsumerException, SystemException {
        Session session = null;

        try {
            session = openSession();

            Consumer consumer = (Consumer) session.get(ConsumerImpl.class,
                    primaryKey);

            if (consumer == null) {
                if (_log.isWarnEnabled()) {
                    _log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
                }

                throw new NoSuchConsumerException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
                    primaryKey);
            }

            return remove(consumer);
        } catch (NoSuchConsumerException nsee) {
            throw nsee;
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }
    }

    @Override
    protected Consumer removeImpl(Consumer consumer) throws SystemException {
        consumer = toUnwrappedModel(consumer);

        Session session = null;

        try {
            session = openSession();

            BatchSessionUtil.delete(session, consumer);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }

        clearCache(consumer);

        return consumer;
    }

    @Override
    public Consumer updateImpl(
        com.covisint.papi.sample.portlet.model.Consumer consumer, boolean merge)
        throws SystemException {
        consumer = toUnwrappedModel(consumer);

        boolean isNew = consumer.isNew();

        Session session = null;

        try {
            session = openSession();

            BatchSessionUtil.update(session, consumer, merge);

            consumer.setNew(false);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }

        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

        if (isNew || !ConsumerModelImpl.COLUMN_BITMASK_ENABLED) {
            FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
        }

        EntityCacheUtil.putResult(ConsumerModelImpl.ENTITY_CACHE_ENABLED,
            ConsumerImpl.class, consumer.getPrimaryKey(), consumer);

        clearUniqueFindersCache(consumer);
        cacheUniqueFindersCache(consumer);

        return consumer;
    }

    protected Consumer toUnwrappedModel(Consumer consumer) {
        if (consumer instanceof ConsumerImpl) {
            return consumer;
        }

        ConsumerImpl consumerImpl = new ConsumerImpl();

        consumerImpl.setNew(consumer.isNew());
        consumerImpl.setPrimaryKey(consumer.getPrimaryKey());

        consumerImpl.setConsumerId(consumer.getConsumerId());
        consumerImpl.setConsumerName(consumer.getConsumerName());
        consumerImpl.setPassword(consumer.getPassword());

        return consumerImpl;
    }

    /**
     * Returns the consumer with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
     *
     * @param primaryKey the primary key of the consumer
     * @return the consumer
     * @throws com.liferay.portal.NoSuchModelException if a consumer with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    @Override
    public Consumer findByPrimaryKey(Serializable primaryKey)
        throws NoSuchModelException, SystemException {
        return findByPrimaryKey(((Long) primaryKey).longValue());
    }

    /**
     * Returns the consumer with the primary key or throws a {@link com.covisint.papi.sample.portlet.NoSuchConsumerException} if it could not be found.
     *
     * @param consumerId the primary key of the consumer
     * @return the consumer
     * @throws com.covisint.papi.sample.portlet.NoSuchConsumerException if a consumer with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public Consumer findByPrimaryKey(long consumerId)
        throws NoSuchConsumerException, SystemException {
        Consumer consumer = fetchByPrimaryKey(consumerId);

        if (consumer == null) {
            if (_log.isWarnEnabled()) {
                _log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + consumerId);
            }

            throw new NoSuchConsumerException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
                consumerId);
        }

        return consumer;
    }

    /**
     * Returns the consumer with the primary key or returns <code>null</code> if it could not be found.
     *
     * @param primaryKey the primary key of the consumer
     * @return the consumer, or <code>null</code> if a consumer with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    @Override
    public Consumer fetchByPrimaryKey(Serializable primaryKey)
        throws SystemException {
        return fetchByPrimaryKey(((Long) primaryKey).longValue());
    }

    /**
     * Returns the consumer with the primary key or returns <code>null</code> if it could not be found.
     *
     * @param consumerId the primary key of the consumer
     * @return the consumer, or <code>null</code> if a consumer with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public Consumer fetchByPrimaryKey(long consumerId)
        throws SystemException {
        Consumer consumer = (Consumer) EntityCacheUtil.getResult(ConsumerModelImpl.ENTITY_CACHE_ENABLED,
                ConsumerImpl.class, consumerId);

        if (consumer == _nullConsumer) {
            return null;
        }

        if (consumer == null) {
            Session session = null;

            boolean hasException = false;

            try {
                session = openSession();

                consumer = (Consumer) session.get(ConsumerImpl.class,
                        Long.valueOf(consumerId));
            } catch (Exception e) {
                hasException = true;

                throw processException(e);
            } finally {
                if (consumer != null) {
                    cacheResult(consumer);
                } else if (!hasException) {
                    EntityCacheUtil.putResult(ConsumerModelImpl.ENTITY_CACHE_ENABLED,
                        ConsumerImpl.class, consumerId, _nullConsumer);
                }

                closeSession(session);
            }
        }

        return consumer;
    }

    /**
     * Returns the consumer where consumerName = &#63; or throws a {@link com.covisint.papi.sample.portlet.NoSuchConsumerException} if it could not be found.
     *
     * @param consumerName the consumer name
     * @return the matching consumer
     * @throws com.covisint.papi.sample.portlet.NoSuchConsumerException if a matching consumer could not be found
     * @throws SystemException if a system exception occurred
     */
    public Consumer findByConsumerName(String consumerName)
        throws NoSuchConsumerException, SystemException {
        Consumer consumer = fetchByConsumerName(consumerName);

        if (consumer == null) {
            StringBundler msg = new StringBundler(4);

            msg.append(_NO_SUCH_ENTITY_WITH_KEY);

            msg.append("consumerName=");
            msg.append(consumerName);

            msg.append(StringPool.CLOSE_CURLY_BRACE);

            if (_log.isWarnEnabled()) {
                _log.warn(msg.toString());
            }

            throw new NoSuchConsumerException(msg.toString());
        }

        return consumer;
    }

    /**
     * Returns the consumer where consumerName = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
     *
     * @param consumerName the consumer name
     * @return the matching consumer, or <code>null</code> if a matching consumer could not be found
     * @throws SystemException if a system exception occurred
     */
    public Consumer fetchByConsumerName(String consumerName)
        throws SystemException {
        return fetchByConsumerName(consumerName, true);
    }

    /**
     * Returns the consumer where consumerName = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
     *
     * @param consumerName the consumer name
     * @param retrieveFromCache whether to use the finder cache
     * @return the matching consumer, or <code>null</code> if a matching consumer could not be found
     * @throws SystemException if a system exception occurred
     */
    public Consumer fetchByConsumerName(String consumerName,
        boolean retrieveFromCache) throws SystemException {
        Object[] finderArgs = new Object[] { consumerName };

        Object result = null;

        if (retrieveFromCache) {
            result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_CONSUMERNAME,
                    finderArgs, this);
        }

        if (result instanceof Consumer) {
            Consumer consumer = (Consumer) result;

            if (!Validator.equals(consumerName, consumer.getConsumerName())) {
                result = null;
            }
        }

        if (result == null) {
            StringBundler query = new StringBundler(3);

            query.append(_SQL_SELECT_CONSUMER_WHERE);

            if (consumerName == null) {
                query.append(_FINDER_COLUMN_CONSUMERNAME_CONSUMERNAME_1);
            } else {
                if (consumerName.equals(StringPool.BLANK)) {
                    query.append(_FINDER_COLUMN_CONSUMERNAME_CONSUMERNAME_3);
                } else {
                    query.append(_FINDER_COLUMN_CONSUMERNAME_CONSUMERNAME_2);
                }
            }

            query.append(ConsumerModelImpl.ORDER_BY_JPQL);

            String sql = query.toString();

            Session session = null;

            try {
                session = openSession();

                Query q = session.createQuery(sql);

                QueryPos qPos = QueryPos.getInstance(q);

                if (consumerName != null) {
                    qPos.add(consumerName);
                }

                List<Consumer> list = q.list();

                result = list;

                Consumer consumer = null;

                if (list.isEmpty()) {
                    FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CONSUMERNAME,
                        finderArgs, list);
                } else {
                    consumer = list.get(0);

                    cacheResult(consumer);

                    if ((consumer.getConsumerName() == null) ||
                            !consumer.getConsumerName().equals(consumerName)) {
                        FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CONSUMERNAME,
                            finderArgs, consumer);
                    }
                }

                return consumer;
            } catch (Exception e) {
                throw processException(e);
            } finally {
                if (result == null) {
                    FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_CONSUMERNAME,
                        finderArgs);
                }

                closeSession(session);
            }
        } else {
            if (result instanceof List<?>) {
                return null;
            } else {
                return (Consumer) result;
            }
        }
    }

    /**
     * Returns all the consumers.
     *
     * @return the consumers
     * @throws SystemException if a system exception occurred
     */
    public List<Consumer> findAll() throws SystemException {
        return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
    }

    /**
     * Returns a range of all the consumers.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param start the lower bound of the range of consumers
     * @param end the upper bound of the range of consumers (not inclusive)
     * @return the range of consumers
     * @throws SystemException if a system exception occurred
     */
    public List<Consumer> findAll(int start, int end) throws SystemException {
        return findAll(start, end, null);
    }

    /**
     * Returns an ordered range of all the consumers.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param start the lower bound of the range of consumers
     * @param end the upper bound of the range of consumers (not inclusive)
     * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
     * @return the ordered range of consumers
     * @throws SystemException if a system exception occurred
     */
    public List<Consumer> findAll(int start, int end,
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

        List<Consumer> list = (List<Consumer>) FinderCacheUtil.getResult(finderPath,
                finderArgs, this);

        if (list == null) {
            StringBundler query = null;
            String sql = null;

            if (orderByComparator != null) {
                query = new StringBundler(2 +
                        (orderByComparator.getOrderByFields().length * 3));

                query.append(_SQL_SELECT_CONSUMER);

                appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
                    orderByComparator);

                sql = query.toString();
            } else {
                sql = _SQL_SELECT_CONSUMER.concat(ConsumerModelImpl.ORDER_BY_JPQL);
            }

            Session session = null;

            try {
                session = openSession();

                Query q = session.createQuery(sql);

                if (orderByComparator == null) {
                    list = (List<Consumer>) QueryUtil.list(q, getDialect(),
                            start, end, false);

                    Collections.sort(list);
                } else {
                    list = (List<Consumer>) QueryUtil.list(q, getDialect(),
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
     * Removes the consumer where consumerName = &#63; from the database.
     *
     * @param consumerName the consumer name
     * @return the consumer that was removed
     * @throws SystemException if a system exception occurred
     */
    public Consumer removeByConsumerName(String consumerName)
        throws NoSuchConsumerException, SystemException {
        Consumer consumer = findByConsumerName(consumerName);

        return remove(consumer);
    }

    /**
     * Removes all the consumers from the database.
     *
     * @throws SystemException if a system exception occurred
     */
    public void removeAll() throws SystemException {
        for (Consumer consumer : findAll()) {
            remove(consumer);
        }
    }

    /**
     * Returns the number of consumers where consumerName = &#63;.
     *
     * @param consumerName the consumer name
     * @return the number of matching consumers
     * @throws SystemException if a system exception occurred
     */
    public int countByConsumerName(String consumerName)
        throws SystemException {
        Object[] finderArgs = new Object[] { consumerName };

        Long count = (Long) FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_CONSUMERNAME,
                finderArgs, this);

        if (count == null) {
            StringBundler query = new StringBundler(2);

            query.append(_SQL_COUNT_CONSUMER_WHERE);

            if (consumerName == null) {
                query.append(_FINDER_COLUMN_CONSUMERNAME_CONSUMERNAME_1);
            } else {
                if (consumerName.equals(StringPool.BLANK)) {
                    query.append(_FINDER_COLUMN_CONSUMERNAME_CONSUMERNAME_3);
                } else {
                    query.append(_FINDER_COLUMN_CONSUMERNAME_CONSUMERNAME_2);
                }
            }

            String sql = query.toString();

            Session session = null;

            try {
                session = openSession();

                Query q = session.createQuery(sql);

                QueryPos qPos = QueryPos.getInstance(q);

                if (consumerName != null) {
                    qPos.add(consumerName);
                }

                count = (Long) q.uniqueResult();
            } catch (Exception e) {
                throw processException(e);
            } finally {
                if (count == null) {
                    count = Long.valueOf(0);
                }

                FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_CONSUMERNAME,
                    finderArgs, count);

                closeSession(session);
            }
        }

        return count.intValue();
    }

    /**
     * Returns the number of consumers.
     *
     * @return the number of consumers
     * @throws SystemException if a system exception occurred
     */
    public int countAll() throws SystemException {
        Long count = (Long) FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
                FINDER_ARGS_EMPTY, this);

        if (count == null) {
            Session session = null;

            try {
                session = openSession();

                Query q = session.createQuery(_SQL_COUNT_CONSUMER);

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
     * Initializes the consumer persistence.
     */
    public void afterPropertiesSet() {
        String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
                    com.liferay.util.service.ServiceProps.get(
                        "value.object.listener.com.covisint.papi.sample.portlet.model.Consumer")));

        if (listenerClassNames.length > 0) {
            try {
                List<ModelListener<Consumer>> listenersList = new ArrayList<ModelListener<Consumer>>();

                for (String listenerClassName : listenerClassNames) {
                    Class<?> clazz = getClass();

                    listenersList.add((ModelListener<Consumer>) InstanceFactory.newInstance(
                            clazz.getClassLoader(), listenerClassName));
                }

                listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
            } catch (Exception e) {
                _log.error(e);
            }
        }
    }

    public void destroy() {
        EntityCacheUtil.removeCache(ConsumerImpl.class.getName());
        FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
        FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
    }
}
