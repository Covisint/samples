package com.covisint.papi.sample.portlet.service;

import com.liferay.portal.service.ServiceWrapper;

/**
 * <p>
 * This class is a wrapper for {@link ConsumerService}.
 * </p>
 *
 * @author    Nitin R. Khobragade
 * @see       ConsumerService
 * @generated
 */
public class ConsumerServiceWrapper implements ConsumerService,
    ServiceWrapper<ConsumerService> {
    private ConsumerService _consumerService;

    public ConsumerServiceWrapper(ConsumerService consumerService) {
        _consumerService = consumerService;
    }

    /**
    * Returns the Spring bean ID for this bean.
    *
    * @return the Spring bean ID for this bean
    */
    public java.lang.String getBeanIdentifier() {
        return _consumerService.getBeanIdentifier();
    }

    /**
    * Sets the Spring bean ID for this bean.
    *
    * @param beanIdentifier the Spring bean ID for this bean
    */
    public void setBeanIdentifier(java.lang.String beanIdentifier) {
        _consumerService.setBeanIdentifier(beanIdentifier);
    }

    public java.lang.Object invokeMethod(java.lang.String name,
        java.lang.String[] parameterTypes, java.lang.Object[] arguments)
        throws java.lang.Throwable {
        return _consumerService.invokeMethod(name, parameterTypes, arguments);
    }

    public com.covisint.papi.sample.portlet.model.Consumer getConsumer(
        java.lang.String consumerId)
        throws com.covisint.papi.sample.portlet.NoSuchConsumerException,
            com.liferay.portal.kernel.exception.SystemException {
        return _consumerService.getConsumer(consumerId);
    }

    /**
     * @deprecated Renamed to {@link #getWrappedService}
     */
    public ConsumerService getWrappedConsumerService() {
        return _consumerService;
    }

    /**
     * @deprecated Renamed to {@link #setWrappedService}
     */
    public void setWrappedConsumerService(ConsumerService consumerService) {
        _consumerService = consumerService;
    }

    public ConsumerService getWrappedService() {
        return _consumerService;
    }

    public void setWrappedService(ConsumerService consumerService) {
        _consumerService = consumerService;
    }
}
