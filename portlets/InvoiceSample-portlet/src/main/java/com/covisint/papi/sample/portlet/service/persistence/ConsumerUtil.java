package com.covisint.papi.sample.portlet.service.persistence;

import com.covisint.papi.sample.portlet.model.Consumer;

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

/**
 * The persistence utility for the consumer service. This utility wraps {@link ConsumerPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Nitin R. Khobragade
 * @see ConsumerPersistence
 * @see ConsumerPersistenceImpl
 * @generated
 */
public class ConsumerUtil {
    private static ConsumerPersistence _persistence;

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
    public static void clearCache(Consumer consumer) {
        getPersistence().clearCache(consumer);
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
    public static List<Consumer> findWithDynamicQuery(DynamicQuery dynamicQuery)
        throws SystemException {
        return getPersistence().findWithDynamicQuery(dynamicQuery);
    }

    /**
     * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
     */
    public static List<Consumer> findWithDynamicQuery(
        DynamicQuery dynamicQuery, int start, int end)
        throws SystemException {
        return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
    }

    /**
     * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
     */
    public static List<Consumer> findWithDynamicQuery(
        DynamicQuery dynamicQuery, int start, int end,
        OrderByComparator orderByComparator) throws SystemException {
        return getPersistence()
                   .findWithDynamicQuery(dynamicQuery, start, end,
            orderByComparator);
    }

    /**
     * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
     */
    public static Consumer update(Consumer consumer, boolean merge)
        throws SystemException {
        return getPersistence().update(consumer, merge);
    }

    /**
     * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
     */
    public static Consumer update(Consumer consumer, boolean merge,
        ServiceContext serviceContext) throws SystemException {
        return getPersistence().update(consumer, merge, serviceContext);
    }

    /**
    * Caches the consumer in the entity cache if it is enabled.
    *
    * @param consumer the consumer
    */
    public static void cacheResult(
        com.covisint.papi.sample.portlet.model.Consumer consumer) {
        getPersistence().cacheResult(consumer);
    }

    /**
    * Caches the consumers in the entity cache if it is enabled.
    *
    * @param consumers the consumers
    */
    public static void cacheResult(
        java.util.List<com.covisint.papi.sample.portlet.model.Consumer> consumers) {
        getPersistence().cacheResult(consumers);
    }

    /**
    * Creates a new consumer with the primary key. Does not add the consumer to the database.
    *
    * @param consumerId the primary key for the new consumer
    * @return the new consumer
    */
    public static com.covisint.papi.sample.portlet.model.Consumer create(
        long consumerId) {
        return getPersistence().create(consumerId);
    }

    /**
    * Removes the consumer with the primary key from the database. Also notifies the appropriate model listeners.
    *
    * @param consumerId the primary key of the consumer
    * @return the consumer that was removed
    * @throws com.covisint.papi.sample.portlet.NoSuchConsumerException if a consumer with the primary key could not be found
    * @throws SystemException if a system exception occurred
    */
    public static com.covisint.papi.sample.portlet.model.Consumer remove(
        long consumerId)
        throws com.covisint.papi.sample.portlet.NoSuchConsumerException,
            com.liferay.portal.kernel.exception.SystemException {
        return getPersistence().remove(consumerId);
    }

    public static com.covisint.papi.sample.portlet.model.Consumer updateImpl(
        com.covisint.papi.sample.portlet.model.Consumer consumer, boolean merge)
        throws com.liferay.portal.kernel.exception.SystemException {
        return getPersistence().updateImpl(consumer, merge);
    }

    /**
    * Returns the consumer with the primary key or throws a {@link com.covisint.papi.sample.portlet.NoSuchConsumerException} if it could not be found.
    *
    * @param consumerId the primary key of the consumer
    * @return the consumer
    * @throws com.covisint.papi.sample.portlet.NoSuchConsumerException if a consumer with the primary key could not be found
    * @throws SystemException if a system exception occurred
    */
    public static com.covisint.papi.sample.portlet.model.Consumer findByPrimaryKey(
        long consumerId)
        throws com.covisint.papi.sample.portlet.NoSuchConsumerException,
            com.liferay.portal.kernel.exception.SystemException {
        return getPersistence().findByPrimaryKey(consumerId);
    }

    /**
    * Returns the consumer with the primary key or returns <code>null</code> if it could not be found.
    *
    * @param consumerId the primary key of the consumer
    * @return the consumer, or <code>null</code> if a consumer with the primary key could not be found
    * @throws SystemException if a system exception occurred
    */
    public static com.covisint.papi.sample.portlet.model.Consumer fetchByPrimaryKey(
        long consumerId)
        throws com.liferay.portal.kernel.exception.SystemException {
        return getPersistence().fetchByPrimaryKey(consumerId);
    }

    /**
    * Returns the consumer where consumerName = &#63; or throws a {@link com.covisint.papi.sample.portlet.NoSuchConsumerException} if it could not be found.
    *
    * @param consumerName the consumer name
    * @return the matching consumer
    * @throws com.covisint.papi.sample.portlet.NoSuchConsumerException if a matching consumer could not be found
    * @throws SystemException if a system exception occurred
    */
    public static com.covisint.papi.sample.portlet.model.Consumer findByConsumerName(
        java.lang.String consumerName)
        throws com.covisint.papi.sample.portlet.NoSuchConsumerException,
            com.liferay.portal.kernel.exception.SystemException {
        return getPersistence().findByConsumerName(consumerName);
    }

    /**
    * Returns the consumer where consumerName = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
    *
    * @param consumerName the consumer name
    * @return the matching consumer, or <code>null</code> if a matching consumer could not be found
    * @throws SystemException if a system exception occurred
    */
    public static com.covisint.papi.sample.portlet.model.Consumer fetchByConsumerName(
        java.lang.String consumerName)
        throws com.liferay.portal.kernel.exception.SystemException {
        return getPersistence().fetchByConsumerName(consumerName);
    }

    /**
    * Returns the consumer where consumerName = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
    *
    * @param consumerName the consumer name
    * @param retrieveFromCache whether to use the finder cache
    * @return the matching consumer, or <code>null</code> if a matching consumer could not be found
    * @throws SystemException if a system exception occurred
    */
    public static com.covisint.papi.sample.portlet.model.Consumer fetchByConsumerName(
        java.lang.String consumerName, boolean retrieveFromCache)
        throws com.liferay.portal.kernel.exception.SystemException {
        return getPersistence()
                   .fetchByConsumerName(consumerName, retrieveFromCache);
    }

    /**
    * Returns all the consumers.
    *
    * @return the consumers
    * @throws SystemException if a system exception occurred
    */
    public static java.util.List<com.covisint.papi.sample.portlet.model.Consumer> findAll()
        throws com.liferay.portal.kernel.exception.SystemException {
        return getPersistence().findAll();
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
    public static java.util.List<com.covisint.papi.sample.portlet.model.Consumer> findAll(
        int start, int end)
        throws com.liferay.portal.kernel.exception.SystemException {
        return getPersistence().findAll(start, end);
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
    public static java.util.List<com.covisint.papi.sample.portlet.model.Consumer> findAll(
        int start, int end,
        com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
        throws com.liferay.portal.kernel.exception.SystemException {
        return getPersistence().findAll(start, end, orderByComparator);
    }

    /**
    * Removes the consumer where consumerName = &#63; from the database.
    *
    * @param consumerName the consumer name
    * @return the consumer that was removed
    * @throws SystemException if a system exception occurred
    */
    public static com.covisint.papi.sample.portlet.model.Consumer removeByConsumerName(
        java.lang.String consumerName)
        throws com.covisint.papi.sample.portlet.NoSuchConsumerException,
            com.liferay.portal.kernel.exception.SystemException {
        return getPersistence().removeByConsumerName(consumerName);
    }

    /**
    * Removes all the consumers from the database.
    *
    * @throws SystemException if a system exception occurred
    */
    public static void removeAll()
        throws com.liferay.portal.kernel.exception.SystemException {
        getPersistence().removeAll();
    }

    /**
    * Returns the number of consumers where consumerName = &#63;.
    *
    * @param consumerName the consumer name
    * @return the number of matching consumers
    * @throws SystemException if a system exception occurred
    */
    public static int countByConsumerName(java.lang.String consumerName)
        throws com.liferay.portal.kernel.exception.SystemException {
        return getPersistence().countByConsumerName(consumerName);
    }

    /**
    * Returns the number of consumers.
    *
    * @return the number of consumers
    * @throws SystemException if a system exception occurred
    */
    public static int countAll()
        throws com.liferay.portal.kernel.exception.SystemException {
        return getPersistence().countAll();
    }

    public static ConsumerPersistence getPersistence() {
        if (_persistence == null) {
            _persistence = (ConsumerPersistence) PortletBeanLocatorUtil.locate(com.covisint.papi.sample.portlet.service.ClpSerializer.getServletContextName(),
                    ConsumerPersistence.class.getName());

            ReferenceRegistry.registerReference(ConsumerUtil.class,
                "_persistence");
        }

        return _persistence;
    }

    /**
     * @deprecated
     */
    public void setPersistence(ConsumerPersistence persistence) {
    }
}
