package com.covisint.papi.sample.portlet.model.impl;

import com.covisint.papi.sample.portlet.model.Consumer;
import com.covisint.papi.sample.portlet.model.ConsumerModel;
import com.covisint.papi.sample.portlet.model.ConsumerSoap;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import java.io.Serializable;

import java.sql.Types;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The base model implementation for the Consumer service. Represents a row in the &quot;Invoice_Consumer&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link com.covisint.papi.sample.portlet.model.ConsumerModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link ConsumerImpl}.
 * </p>
 *
 * @author Nitin R. Khobragade
 * @see ConsumerImpl
 * @see com.covisint.papi.sample.portlet.model.Consumer
 * @see com.covisint.papi.sample.portlet.model.ConsumerModel
 * @generated
 */
@JSON(strict = true)
public class ConsumerModelImpl extends BaseModelImpl<Consumer>
    implements ConsumerModel {
    /*
     * NOTE FOR DEVELOPERS:
     *
     * Never modify or reference this class directly. All methods that expect a consumer model instance should use the {@link com.covisint.papi.sample.portlet.model.Consumer} interface instead.
     */
    public static final String TABLE_NAME = "Invoice_Consumer";
    public static final Object[][] TABLE_COLUMNS = {
            { "consumerId", Types.BIGINT },
            { "consumerName", Types.VARCHAR },
            { "password_", Types.VARCHAR }
        };
    public static final String TABLE_SQL_CREATE = "create table Invoice_Consumer (consumerId LONG not null primary key,consumerName VARCHAR(75) null,password_ VARCHAR(75) null)";
    public static final String TABLE_SQL_DROP = "drop table Invoice_Consumer";
    public static final String ORDER_BY_JPQL = " ORDER BY consumer.consumerId ASC";
    public static final String ORDER_BY_SQL = " ORDER BY Invoice_Consumer.consumerId ASC";
    public static final String DATA_SOURCE = "liferayDataSource";
    public static final String SESSION_FACTORY = "liferaySessionFactory";
    public static final String TX_MANAGER = "liferayTransactionManager";
    public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.util.service.ServiceProps.get(
                "value.object.entity.cache.enabled.com.covisint.papi.sample.portlet.model.Consumer"),
            true);
    public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.util.service.ServiceProps.get(
                "value.object.finder.cache.enabled.com.covisint.papi.sample.portlet.model.Consumer"),
            true);
    public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(com.liferay.util.service.ServiceProps.get(
                "value.object.column.bitmask.enabled.com.covisint.papi.sample.portlet.model.Consumer"),
            true);
    public static long CONSUMERNAME_COLUMN_BITMASK = 1L;
    public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.util.service.ServiceProps.get(
                "lock.expiration.time.com.covisint.papi.sample.portlet.model.Consumer"));
    private static ClassLoader _classLoader = Consumer.class.getClassLoader();
    private static Class<?>[] _escapedModelInterfaces = new Class[] {
            Consumer.class
        };
    private long _consumerId;
    private String _consumerName;
    private String _originalConsumerName;
    private String _password;
    private long _columnBitmask;
    private Consumer _escapedModel;

    public ConsumerModelImpl() {
    }

    /**
     * Converts the soap model instance into a normal model instance.
     *
     * @param soapModel the soap model instance to convert
     * @return the normal model instance
     */
    public static Consumer toModel(ConsumerSoap soapModel) {
        if (soapModel == null) {
            return null;
        }

        Consumer model = new ConsumerImpl();

        model.setConsumerId(soapModel.getConsumerId());
        model.setConsumerName(soapModel.getConsumerName());
        model.setPassword(soapModel.getPassword());

        return model;
    }

    /**
     * Converts the soap model instances into normal model instances.
     *
     * @param soapModels the soap model instances to convert
     * @return the normal model instances
     */
    public static List<Consumer> toModels(ConsumerSoap[] soapModels) {
        if (soapModels == null) {
            return null;
        }

        List<Consumer> models = new ArrayList<Consumer>(soapModels.length);

        for (ConsumerSoap soapModel : soapModels) {
            models.add(toModel(soapModel));
        }

        return models;
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

    public Class<?> getModelClass() {
        return Consumer.class;
    }

    public String getModelClassName() {
        return Consumer.class.getName();
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

    @JSON
    public long getConsumerId() {
        return _consumerId;
    }

    public void setConsumerId(long consumerId) {
        _columnBitmask = -1L;

        _consumerId = consumerId;
    }

    @JSON
    public String getConsumerName() {
        if (_consumerName == null) {
            return StringPool.BLANK;
        } else {
            return _consumerName;
        }
    }

    public void setConsumerName(String consumerName) {
        _columnBitmask |= CONSUMERNAME_COLUMN_BITMASK;

        if (_originalConsumerName == null) {
            _originalConsumerName = _consumerName;
        }

        _consumerName = consumerName;
    }

    public String getOriginalConsumerName() {
        return GetterUtil.getString(_originalConsumerName);
    }

    @JSON
    public String getPassword() {
        if (_password == null) {
            return StringPool.BLANK;
        } else {
            return _password;
        }
    }

    public void setPassword(String password) {
        _password = password;
    }

    public long getColumnBitmask() {
        return _columnBitmask;
    }

    @Override
    public ExpandoBridge getExpandoBridge() {
        return ExpandoBridgeFactoryUtil.getExpandoBridge(0,
            Consumer.class.getName(), getPrimaryKey());
    }

    @Override
    public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
        ExpandoBridge expandoBridge = getExpandoBridge();

        expandoBridge.setAttributes(serviceContext);
    }

    @Override
    public Consumer toEscapedModel() {
        if (_escapedModel == null) {
            _escapedModel = (Consumer) ProxyUtil.newProxyInstance(_classLoader,
                    _escapedModelInterfaces, new AutoEscapeBeanHandler(this));
        }

        return _escapedModel;
    }

    public Consumer toUnescapedModel() {
        return (Consumer) this;
    }

    @Override
    public Object clone() {
        ConsumerImpl consumerImpl = new ConsumerImpl();

        consumerImpl.setConsumerId(getConsumerId());
        consumerImpl.setConsumerName(getConsumerName());
        consumerImpl.setPassword(getPassword());

        consumerImpl.resetOriginalValues();

        return consumerImpl;
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

        if (!(obj instanceof Consumer)) {
            return false;
        }

        Consumer consumer = (Consumer) obj;

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
    public void resetOriginalValues() {
        ConsumerModelImpl consumerModelImpl = this;

        consumerModelImpl._originalConsumerName = consumerModelImpl._consumerName;

        consumerModelImpl._columnBitmask = 0;
    }

    @Override
    public CacheModel<Consumer> toCacheModel() {
        ConsumerCacheModel consumerCacheModel = new ConsumerCacheModel();

        consumerCacheModel.consumerId = getConsumerId();

        consumerCacheModel.consumerName = getConsumerName();

        String consumerName = consumerCacheModel.consumerName;

        if ((consumerName != null) && (consumerName.length() == 0)) {
            consumerCacheModel.consumerName = null;
        }

        consumerCacheModel.password = getPassword();

        String password = consumerCacheModel.password;

        if ((password != null) && (password.length() == 0)) {
            consumerCacheModel.password = null;
        }

        return consumerCacheModel;
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