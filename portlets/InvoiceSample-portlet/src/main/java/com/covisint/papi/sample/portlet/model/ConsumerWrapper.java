package com.covisint.papi.sample.portlet.model;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link Consumer}.
 * </p>
 *
 * @author    Nitin R. Khobragade
 * @see       Consumer
 * @generated
 */
public class ConsumerWrapper implements Consumer, ModelWrapper<Consumer> {
    private Consumer _consumer;

    public ConsumerWrapper(Consumer consumer) {
        _consumer = consumer;
    }

    public Class<?> getModelClass() {
        return Consumer.class;
    }

    public String getModelClassName() {
        return Consumer.class.getName();
    }

    public Map<String, Object> getModelAttributes() {
        Map<String, Object> attributes = new HashMap<String, Object>();

        attributes.put("consumerId", getConsumerId());
        attributes.put("consumerName", getConsumerName());
        attributes.put("password", getPassword());

        return attributes;
    }

    public void setModelAttributes(Map<String, Object> attributes) {
        Long consumerId = (Long) attributes.get("consumerId");

        if (consumerId != null) {
            setConsumerId(consumerId);
        }

        String consumerName = (String) attributes.get("consumerName");

        if (consumerName != null) {
            setConsumerName(consumerName);
        }

        String password = (String) attributes.get("password");

        if (password != null) {
            setPassword(password);
        }
    }

    /**
    * Returns the primary key of this consumer.
    *
    * @return the primary key of this consumer
    */
    public long getPrimaryKey() {
        return _consumer.getPrimaryKey();
    }

    /**
    * Sets the primary key of this consumer.
    *
    * @param primaryKey the primary key of this consumer
    */
    public void setPrimaryKey(long primaryKey) {
        _consumer.setPrimaryKey(primaryKey);
    }

    /**
    * Returns the consumer ID of this consumer.
    *
    * @return the consumer ID of this consumer
    */
    public long getConsumerId() {
        return _consumer.getConsumerId();
    }

    /**
    * Sets the consumer ID of this consumer.
    *
    * @param consumerId the consumer ID of this consumer
    */
    public void setConsumerId(long consumerId) {
        _consumer.setConsumerId(consumerId);
    }

    /**
    * Returns the consumer name of this consumer.
    *
    * @return the consumer name of this consumer
    */
    public java.lang.String getConsumerName() {
        return _consumer.getConsumerName();
    }

    /**
    * Sets the consumer name of this consumer.
    *
    * @param consumerName the consumer name of this consumer
    */
    public void setConsumerName(java.lang.String consumerName) {
        _consumer.setConsumerName(consumerName);
    }

    /**
    * Returns the password of this consumer.
    *
    * @return the password of this consumer
    */
    public java.lang.String getPassword() {
        return _consumer.getPassword();
    }

    /**
    * Sets the password of this consumer.
    *
    * @param password the password of this consumer
    */
    public void setPassword(java.lang.String password) {
        _consumer.setPassword(password);
    }

    public boolean isNew() {
        return _consumer.isNew();
    }

    public void setNew(boolean n) {
        _consumer.setNew(n);
    }

    public boolean isCachedModel() {
        return _consumer.isCachedModel();
    }

    public void setCachedModel(boolean cachedModel) {
        _consumer.setCachedModel(cachedModel);
    }

    public boolean isEscapedModel() {
        return _consumer.isEscapedModel();
    }

    public java.io.Serializable getPrimaryKeyObj() {
        return _consumer.getPrimaryKeyObj();
    }

    public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
        _consumer.setPrimaryKeyObj(primaryKeyObj);
    }

    public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
        return _consumer.getExpandoBridge();
    }

    public void setExpandoBridgeAttributes(
        com.liferay.portal.service.ServiceContext serviceContext) {
        _consumer.setExpandoBridgeAttributes(serviceContext);
    }

    @Override
    public java.lang.Object clone() {
        return new ConsumerWrapper((Consumer) _consumer.clone());
    }

    public int compareTo(
        com.covisint.papi.sample.portlet.model.Consumer consumer) {
        return _consumer.compareTo(consumer);
    }

    @Override
    public int hashCode() {
        return _consumer.hashCode();
    }

    public com.liferay.portal.model.CacheModel<com.covisint.papi.sample.portlet.model.Consumer> toCacheModel() {
        return _consumer.toCacheModel();
    }

    public com.covisint.papi.sample.portlet.model.Consumer toEscapedModel() {
        return new ConsumerWrapper(_consumer.toEscapedModel());
    }

    public com.covisint.papi.sample.portlet.model.Consumer toUnescapedModel() {
        return new ConsumerWrapper(_consumer.toUnescapedModel());
    }

    @Override
    public java.lang.String toString() {
        return _consumer.toString();
    }

    public java.lang.String toXmlString() {
        return _consumer.toXmlString();
    }

    public void persist()
        throws com.liferay.portal.kernel.exception.SystemException {
        _consumer.persist();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof ConsumerWrapper)) {
            return false;
        }

        ConsumerWrapper consumerWrapper = (ConsumerWrapper) obj;

        if (Validator.equals(_consumer, consumerWrapper._consumer)) {
            return true;
        }

        return false;
    }

    /**
     * @deprecated Renamed to {@link #getWrappedModel}
     */
    public Consumer getWrappedConsumer() {
        return _consumer;
    }

    public Consumer getWrappedModel() {
        return _consumer;
    }

    public void resetOriginalValues() {
        _consumer.resetOriginalValues();
    }
}
