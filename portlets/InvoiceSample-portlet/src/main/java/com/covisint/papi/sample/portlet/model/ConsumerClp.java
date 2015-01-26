package com.covisint.papi.sample.portlet.model;

import com.covisint.papi.sample.portlet.service.ClpSerializer;
import com.covisint.papi.sample.portlet.service.ConsumerLocalServiceUtil;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.impl.BaseModelImpl;

import java.io.Serializable;

import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.Map;


public class ConsumerClp extends BaseModelImpl<Consumer> implements Consumer {
    private long _consumerId;
    private String _consumerName;
    private String _password;
    private BaseModel<?> _consumerRemoteModel;

    public ConsumerClp() {
    }

    public Class<?> getModelClass() {
        return Consumer.class;
    }

    public String getModelClassName() {
        return Consumer.class.getName();
    }

    public long getPrimaryKey() {
        return _consumerId;
    }

    public void setPrimaryKey(long primaryKey) {
        setConsumerId(primaryKey);
    }

    public Serializable getPrimaryKeyObj() {
        return new Long(_consumerId);
    }

    public void setPrimaryKeyObj(Serializable primaryKeyObj) {
        setPrimaryKey(((Long) primaryKeyObj).longValue());
    }

    @Override
    public Map<String, Object> getModelAttributes() {
        Map<String, Object> attributes = new HashMap<String, Object>();

        attributes.put("consumerId", getConsumerId());
        attributes.put("consumerName", getConsumerName());
        attributes.put("password", getPassword());

        return attributes;
    }

    @Override
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

    public long getConsumerId() {
        return _consumerId;
    }

    public void setConsumerId(long consumerId) {
        _consumerId = consumerId;

        if (_consumerRemoteModel != null) {
            try {
                Class<?> clazz = _consumerRemoteModel.getClass();

                Method method = clazz.getMethod("setConsumerId", long.class);

                method.invoke(_consumerRemoteModel, consumerId);
            } catch (Exception e) {
                throw new UnsupportedOperationException(e);
            }
        }
    }

    public String getConsumerName() {
        return _consumerName;
    }

    public void setConsumerName(String consumerName) {
        _consumerName = consumerName;

        if (_consumerRemoteModel != null) {
            try {
                Class<?> clazz = _consumerRemoteModel.getClass();

                Method method = clazz.getMethod("setConsumerName", String.class);

                method.invoke(_consumerRemoteModel, consumerName);
            } catch (Exception e) {
                throw new UnsupportedOperationException(e);
            }
        }
    }

    public String getPassword() {
        return _password;
    }

    public void setPassword(String password) {
        _password = password;

        if (_consumerRemoteModel != null) {
            try {
                Class<?> clazz = _consumerRemoteModel.getClass();

                Method method = clazz.getMethod("setPassword", String.class);

                method.invoke(_consumerRemoteModel, password);
            } catch (Exception e) {
                throw new UnsupportedOperationException(e);
            }
        }
    }

    public BaseModel<?> getConsumerRemoteModel() {
        return _consumerRemoteModel;
    }

    public void setConsumerRemoteModel(BaseModel<?> consumerRemoteModel) {
        _consumerRemoteModel = consumerRemoteModel;
    }

    public Object invokeOnRemoteModel(String methodName,
        Class<?>[] parameterTypes, Object[] parameterValues)
        throws Exception {
        Object[] remoteParameterValues = new Object[parameterValues.length];

        for (int i = 0; i < parameterValues.length; i++) {
            if (parameterValues[i] != null) {
                remoteParameterValues[i] = ClpSerializer.translateInput(parameterValues[i]);
            }
        }

        Class<?> remoteModelClass = _consumerRemoteModel.getClass();

        ClassLoader remoteModelClassLoader = remoteModelClass.getClassLoader();

        Class<?>[] remoteParameterTypes = new Class[parameterTypes.length];

        for (int i = 0; i < parameterTypes.length; i++) {
            if (parameterTypes[i].isPrimitive()) {
                remoteParameterTypes[i] = parameterTypes[i];
            } else {
                String parameterTypeName = parameterTypes[i].getName();

                remoteParameterTypes[i] = remoteModelClassLoader.loadClass(parameterTypeName);
            }
        }

        Method method = remoteModelClass.getMethod(methodName,
                remoteParameterTypes);

        Object returnValue = method.invoke(_consumerRemoteModel,
                remoteParameterValues);

        if (returnValue != null) {
            returnValue = ClpSerializer.translateOutput(returnValue);
        }

        return returnValue;
    }

    public void persist() throws SystemException {
        if (this.isNew()) {
            ConsumerLocalServiceUtil.addConsumer(this);
        } else {
            ConsumerLocalServiceUtil.updateConsumer(this);
        }
    }

    @Override
    public Consumer toEscapedModel() {
        return (Consumer) ProxyUtil.newProxyInstance(Consumer.class.getClassLoader(),
            new Class[] { Consumer.class }, new AutoEscapeBeanHandler(this));
    }

    public Consumer toUnescapedModel() {
        return this;
    }

    @Override
    public Object clone() {
        ConsumerClp clone = new ConsumerClp();

        clone.setConsumerId(getConsumerId());
        clone.setConsumerName(getConsumerName());
        clone.setPassword(getPassword());

        return clone;
    }

    public int compareTo(Consumer consumer) {
        int value = 0;

        if (getConsumerId() < consumer.getConsumerId()) {
            value = -1;
        } else if (getConsumerId() > consumer.getConsumerId()) {
            value = 1;
        } else {
            value = 0;
        }

        if (value != 0) {
            return value;
        }

        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof ConsumerClp)) {
            return false;
        }

        ConsumerClp consumer = (ConsumerClp) obj;

        long primaryKey = consumer.getPrimaryKey();

        if (getPrimaryKey() == primaryKey) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return (int) getPrimaryKey();
    }

    @Override
    public String toString() {
        StringBundler sb = new StringBundler(7);

        sb.append("{consumerId=");
        sb.append(getConsumerId());
        sb.append(", consumerName=");
        sb.append(getConsumerName());
        sb.append(", password=");
        sb.append(getPassword());
        sb.append("}");

        return sb.toString();
    }

    public String toXmlString() {
        StringBundler sb = new StringBundler(13);

        sb.append("<model><model-name>");
        sb.append("com.covisint.papi.sample.portlet.model.Consumer");
        sb.append("</model-name>");

        sb.append(
            "<column><column-name>consumerId</column-name><column-value><![CDATA[");
        sb.append(getConsumerId());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>consumerName</column-name><column-value><![CDATA[");
        sb.append(getConsumerName());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>password</column-name><column-value><![CDATA[");
        sb.append(getPassword());
        sb.append("]]></column-value></column>");

        sb.append("</model>");

        return sb.toString();
    }
}
