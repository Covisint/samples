package com.covisint.papi.sample.portlet.service.persistence;

import com.covisint.papi.sample.portlet.model.Consumer;

import com.liferay.portal.service.persistence.BasePersistence;

/**
 * The persistence interface for the consumer service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Nitin R. Khobragade
 * @see ConsumerPersistenceImpl
 * @see ConsumerUtil
 * @generated
 */
public interface ConsumerPersistence extends BasePersistence<Consumer> {
    /*
     * NOTE FOR DEVELOPERS:
     *
     * Never modify or reference this interface directly. Always use {@link ConsumerUtil} to access the consumer persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
     */

    /**
    * Caches the consumer in the entity cache if it is enabled.
    *
    * @param consumer the consumer
    */
    public void cacheResult(
        com.covisint.papi.sample.portlet.model.Consumer consumer);

    /**
    * Caches the consumers in the entity cache if it is enabled.
    *
    * @param consumers the consumers
    */
    public void cacheResult(
        java.util.List<com.covisint.papi.sample.portlet.model.Consumer> consumers);

    /**
    * Creates a new consumer with the primary key. Does not add the consumer to the database.
    *
    * @param consumerId the primary key for the new consumer
    * @return the new consumer
    */
    public com.covisint.papi.sample.portlet.model.Consumer create(
        long consumerId);

    /**
    * Removes the consumer with the primary key from the database. Also notifies the appropriate model listeners.
    *
    * @param consumerId the primary key of the consumer
    * @return the consumer that was removed
    * @throws com.covisint.papi.sample.portlet.NoSuchConsumerException if a consumer with the primary key could not be found
    * @throws SystemException if a system exception occurred
    */
    public com.covisint.papi.sample.portlet.model.Consumer remove(
        long consumerId)
        throws com.covisint.papi.sample.portlet.NoSuchConsumerException,
            com.liferay.portal.kernel.exception.SystemException;

    public com.covisint.papi.sample.portlet.model.Consumer updateImpl(
        com.covisint.papi.sample.portlet.model.Consumer consumer, boolean merge)
        throws com.liferay.portal.kernel.exception.SystemException;

    /**
    * Returns the consumer with the primary key or throws a {@link com.covisint.papi.sample.portlet.NoSuchConsumerException} if it could not be found.
    *
    * @param consumerId the primary key of the consumer
    * @return the consumer
    * @throws com.covisint.papi.sample.portlet.NoSuchConsumerException if a consumer with the primary key could not be found
    * @throws SystemException if a system exception occurred
    */
    public com.covisint.papi.sample.portlet.model.Consumer findByPrimaryKey(
        long consumerId)
        throws com.covisint.papi.sample.portlet.NoSuchConsumerException,
            com.liferay.portal.kernel.exception.SystemException;

    /**
    * Returns the consumer with the primary key or returns <code>null</code> if it could not be found.
    *
    * @param consumerId the primary key of the consumer
    * @return the consumer, or <code>null</code> if a consumer with the primary key could not be found
    * @throws SystemException if a system exception occurred
    */
    public com.covisint.papi.sample.portlet.model.Consumer fetchByPrimaryKey(
        long consumerId)
        throws com.liferay.portal.kernel.exception.SystemException;

    /**
    * Returns the consumer where consumerName = &#63; or throws a {@link com.covisint.papi.sample.portlet.NoSuchConsumerException} if it could not be found.
    *
    * @param consumerName the consumer name
    * @return the matching consumer
    * @throws com.covisint.papi.sample.portlet.NoSuchConsumerException if a matching consumer could not be found
    * @throws SystemException if a system exception occurred
    */
    public com.covisint.papi.sample.portlet.model.Consumer findByConsumerName(
        java.lang.String consumerName)
        throws com.covisint.papi.sample.portlet.NoSuchConsumerException,
            com.liferay.portal.kernel.exception.SystemException;

    /**
    * Returns the consumer where consumerName = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
    *
    * @param consumerName the consumer name
    * @return the matching consumer, or <code>null</code> if a matching consumer could not be found
    * @throws SystemException if a system exception occurred
    */
    public com.covisint.papi.sample.portlet.model.Consumer fetchByConsumerName(
        java.lang.String consumerName)
        throws com.liferay.portal.kernel.exception.SystemException;

    /**
    * Returns the consumer where consumerName = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
    *
    * @param consumerName the consumer name
    * @param retrieveFromCache whether to use the finder cache
    * @return the matching consumer, or <code>null</code> if a matching consumer could not be found
    * @throws SystemException if a system exception occurred
    */
    public com.covisint.papi.sample.portlet.model.Consumer fetchByConsumerName(
        java.lang.String consumerName, boolean retrieveFromCache)
        throws com.liferay.portal.kernel.exception.SystemException;

    /**
    * Returns all the consumers.
    *
    * @return the consumers
    * @throws SystemException if a system exception occurred
    */
    public java.util.List<com.covisint.papi.sample.portlet.model.Consumer> findAll()
        throws com.liferay.portal.kernel.exception.SystemException;

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
    public java.util.List<com.covisint.papi.sample.portlet.model.Consumer> findAll(
        int start, int end)
        throws com.liferay.portal.kernel.exception.SystemException;

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
    public java.util.List<com.covisint.papi.sample.portlet.model.Consumer> findAll(
        int start, int end,
        com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
        throws com.liferay.portal.kernel.exception.SystemException;

    /**
    * Removes the consumer where consumerName = &#63; from the database.
    *
    * @param consumerName the consumer name
    * @return the consumer that was removed
    * @throws SystemException if a system exception occurred
    */
    public com.covisint.papi.sample.portlet.model.Consumer removeByConsumerName(
        java.lang.String consumerName)
        throws com.covisint.papi.sample.portlet.NoSuchConsumerException,
            com.liferay.portal.kernel.exception.SystemException;

    /**
    * Removes all the consumers from the database.
    *
    * @throws SystemException if a system exception occurred
    */
    public void removeAll()
        throws com.liferay.portal.kernel.exception.SystemException;

    /**
    * Returns the number of consumers where consumerName = &#63;.
    *
    * @param consumerName the consumer name
    * @return the number of matching consumers
    * @throws SystemException if a system exception occurred
    */
    public int countByConsumerName(java.lang.String consumerName)
        throws com.liferay.portal.kernel.exception.SystemException;

    /**
    * Returns the number of consumers.
    *
    * @return the number of consumers
    * @throws SystemException if a system exception occurred
    */
    public int countAll()
        throws com.liferay.portal.kernel.exception.SystemException;
}
