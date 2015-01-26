package com.covisint.papi.sample.portlet.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.service.BaseLocalService;
import com.liferay.portal.service.InvokableLocalService;
import com.liferay.portal.service.PersistedModelLocalService;

/**
 * The interface for the consumer local service.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Nitin R. Khobragade
 * @see ConsumerLocalServiceUtil
 * @see com.covisint.papi.sample.portlet.service.base.ConsumerLocalServiceBaseImpl
 * @see com.covisint.papi.sample.portlet.service.impl.ConsumerLocalServiceImpl
 * @generated
 */
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
    PortalException.class, SystemException.class}
)
public interface ConsumerLocalService extends BaseLocalService,
    InvokableLocalService, PersistedModelLocalService {
    /*
     * NOTE FOR DEVELOPERS:
     *
     * Never modify or reference this interface directly. Always use {@link ConsumerLocalServiceUtil} to access the consumer local service. Add custom service methods to {@link com.covisint.papi.sample.portlet.service.impl.ConsumerLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
     */

    /**
    * Adds the consumer to the database. Also notifies the appropriate model listeners.
    *
    * @param consumer the consumer
    * @return the consumer that was added
    * @throws SystemException if a system exception occurred
    */
    public com.covisint.papi.sample.portlet.model.Consumer addConsumer(
        com.covisint.papi.sample.portlet.model.Consumer consumer)
        throws com.liferay.portal.kernel.exception.SystemException;

    /**
    * Creates a new consumer with the primary key. Does not add the consumer to the database.
    *
    * @param consumerId the primary key for the new consumer
    * @return the new consumer
    */
    public com.covisint.papi.sample.portlet.model.Consumer createConsumer(
        long consumerId);

    /**
    * Deletes the consumer with the primary key from the database. Also notifies the appropriate model listeners.
    *
    * @param consumerId the primary key of the consumer
    * @return the consumer that was removed
    * @throws PortalException if a consumer with the primary key could not be found
    * @throws SystemException if a system exception occurred
    */
    public com.covisint.papi.sample.portlet.model.Consumer deleteConsumer(
        long consumerId)
        throws com.liferay.portal.kernel.exception.PortalException,
            com.liferay.portal.kernel.exception.SystemException;

    /**
    * Deletes the consumer from the database. Also notifies the appropriate model listeners.
    *
    * @param consumer the consumer
    * @return the consumer that was removed
    * @throws SystemException if a system exception occurred
    */
    public com.covisint.papi.sample.portlet.model.Consumer deleteConsumer(
        com.covisint.papi.sample.portlet.model.Consumer consumer)
        throws com.liferay.portal.kernel.exception.SystemException;

    public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery();

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
        throws com.liferay.portal.kernel.exception.SystemException;

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
        int end) throws com.liferay.portal.kernel.exception.SystemException;

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
        throws com.liferay.portal.kernel.exception.SystemException;

    /**
    * Returns the number of rows that match the dynamic query.
    *
    * @param dynamicQuery the dynamic query
    * @return the number of rows that match the dynamic query
    * @throws SystemException if a system exception occurred
    */
    public long dynamicQueryCount(
        com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
        throws com.liferay.portal.kernel.exception.SystemException;

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public com.covisint.papi.sample.portlet.model.Consumer fetchConsumer(
        long consumerId)
        throws com.liferay.portal.kernel.exception.SystemException;

    /**
    * Returns the consumer with the primary key.
    *
    * @param consumerId the primary key of the consumer
    * @return the consumer
    * @throws PortalException if a consumer with the primary key could not be found
    * @throws SystemException if a system exception occurred
    */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public com.covisint.papi.sample.portlet.model.Consumer getConsumer(
        long consumerId)
        throws com.liferay.portal.kernel.exception.PortalException,
            com.liferay.portal.kernel.exception.SystemException;

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public com.liferay.portal.model.PersistedModel getPersistedModel(
        java.io.Serializable primaryKeyObj)
        throws com.liferay.portal.kernel.exception.PortalException,
            com.liferay.portal.kernel.exception.SystemException;

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
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public java.util.List<com.covisint.papi.sample.portlet.model.Consumer> getConsumers(
        int start, int end)
        throws com.liferay.portal.kernel.exception.SystemException;

    /**
    * Returns the number of consumers.
    *
    * @return the number of consumers
    * @throws SystemException if a system exception occurred
    */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public int getConsumersCount()
        throws com.liferay.portal.kernel.exception.SystemException;

    /**
    * Updates the consumer in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
    *
    * @param consumer the consumer
    * @return the consumer that was updated
    * @throws SystemException if a system exception occurred
    */
    public com.covisint.papi.sample.portlet.model.Consumer updateConsumer(
        com.covisint.papi.sample.portlet.model.Consumer consumer)
        throws com.liferay.portal.kernel.exception.SystemException;

    /**
    * Updates the consumer in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
    *
    * @param consumer the consumer
    * @param merge whether to merge the consumer with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
    * @return the consumer that was updated
    * @throws SystemException if a system exception occurred
    */
    public com.covisint.papi.sample.portlet.model.Consumer updateConsumer(
        com.covisint.papi.sample.portlet.model.Consumer consumer, boolean merge)
        throws com.liferay.portal.kernel.exception.SystemException;

    /**
    * Returns the Spring bean ID for this bean.
    *
    * @return the Spring bean ID for this bean
    */
    public java.lang.String getBeanIdentifier();

    /**
    * Sets the Spring bean ID for this bean.
    *
    * @param beanIdentifier the Spring bean ID for this bean
    */
    public void setBeanIdentifier(java.lang.String beanIdentifier);

    public java.lang.Object invokeMethod(java.lang.String name,
        java.lang.String[] parameterTypes, java.lang.Object[] arguments)
        throws java.lang.Throwable;

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public java.lang.String getAuthenticationToken();

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public com.covisint.papi.sample.portlet.model.Consumer getConsumer(
        java.lang.String consumerId)
        throws com.covisint.papi.sample.portlet.NoSuchConsumerException,
            com.liferay.portal.kernel.exception.SystemException;

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public boolean isDefaultDataPopulated();

    public void populateDefaultData();
}
