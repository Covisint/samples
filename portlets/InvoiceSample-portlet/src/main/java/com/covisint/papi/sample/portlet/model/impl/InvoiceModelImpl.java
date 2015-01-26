package com.covisint.papi.sample.portlet.model.impl;

import com.covisint.papi.sample.portlet.model.Invoice;
import com.covisint.papi.sample.portlet.model.InvoiceModel;
import com.covisint.papi.sample.portlet.model.InvoiceSoap;

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
 * The base model implementation for the Invoice service. Represents a row in the &quot;Invoice_Invoice&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link com.covisint.papi.sample.portlet.model.InvoiceModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link InvoiceImpl}.
 * </p>
 *
 * @author Nitin R. Khobragade
 * @see InvoiceImpl
 * @see com.covisint.papi.sample.portlet.model.Invoice
 * @see com.covisint.papi.sample.portlet.model.InvoiceModel
 * @generated
 */
@JSON(strict = true)
public class InvoiceModelImpl extends BaseModelImpl<Invoice>
    implements InvoiceModel {
    /*
     * NOTE FOR DEVELOPERS:
     *
     * Never modify or reference this class directly. All methods that expect a invoice model instance should use the {@link com.covisint.papi.sample.portlet.model.Invoice} interface instead.
     */
    public static final String TABLE_NAME = "Invoice_Invoice";
    public static final Object[][] TABLE_COLUMNS = {
            { "invoiceId", Types.BIGINT },
            { "path_", Types.VARCHAR },
            { "consumerId", Types.BIGINT }
        };
    public static final String TABLE_SQL_CREATE = "create table Invoice_Invoice (invoiceId LONG not null primary key,path_ VARCHAR(75) null,consumerId LONG)";
    public static final String TABLE_SQL_DROP = "drop table Invoice_Invoice";
    public static final String DATA_SOURCE = "liferayDataSource";
    public static final String SESSION_FACTORY = "liferaySessionFactory";
    public static final String TX_MANAGER = "liferayTransactionManager";
    public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.util.service.ServiceProps.get(
                "value.object.entity.cache.enabled.com.covisint.papi.sample.portlet.model.Invoice"),
            true);
    public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.util.service.ServiceProps.get(
                "value.object.finder.cache.enabled.com.covisint.papi.sample.portlet.model.Invoice"),
            true);
    public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(com.liferay.util.service.ServiceProps.get(
                "value.object.column.bitmask.enabled.com.covisint.papi.sample.portlet.model.Invoice"),
            true);
    public static long CONSUMERID_COLUMN_BITMASK = 1L;
    public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.util.service.ServiceProps.get(
                "lock.expiration.time.com.covisint.papi.sample.portlet.model.Invoice"));
    private static ClassLoader _classLoader = Invoice.class.getClassLoader();
    private static Class<?>[] _escapedModelInterfaces = new Class[] {
            Invoice.class
        };
    private long _invoiceId;
    private String _path;
    private long _consumerId;
    private long _originalConsumerId;
    private boolean _setOriginalConsumerId;
    private long _columnBitmask;
    private Invoice _escapedModel;

    public InvoiceModelImpl() {
    }

    /**
     * Converts the soap model instance into a normal model instance.
     *
     * @param soapModel the soap model instance to convert
     * @return the normal model instance
     */
    public static Invoice toModel(InvoiceSoap soapModel) {
        if (soapModel == null) {
            return null;
        }

        Invoice model = new InvoiceImpl();

        model.setInvoiceId(soapModel.getInvoiceId());
        model.setPath(soapModel.getPath());
        model.setConsumerId(soapModel.getConsumerId());

        return model;
    }

    /**
     * Converts the soap model instances into normal model instances.
     *
     * @param soapModels the soap model instances to convert
     * @return the normal model instances
     */
    public static List<Invoice> toModels(InvoiceSoap[] soapModels) {
        if (soapModels == null) {
            return null;
        }

        List<Invoice> models = new ArrayList<Invoice>(soapModels.length);

        for (InvoiceSoap soapModel : soapModels) {
            models.add(toModel(soapModel));
        }

        return models;
    }

    public long getPrimaryKey() {
        return _invoiceId;
    }

    public void setPrimaryKey(long primaryKey) {
        setInvoiceId(primaryKey);
    }

    public Serializable getPrimaryKeyObj() {
        return new Long(_invoiceId);
    }

    public void setPrimaryKeyObj(Serializable primaryKeyObj) {
        setPrimaryKey(((Long) primaryKeyObj).longValue());
    }

    public Class<?> getModelClass() {
        return Invoice.class;
    }

    public String getModelClassName() {
        return Invoice.class.getName();
    }

    @Override
    public Map<String, Object> getModelAttributes() {
        Map<String, Object> attributes = new HashMap<String, Object>();

        attributes.put("invoiceId", getInvoiceId());
        attributes.put("path", getPath());
        attributes.put("consumerId", getConsumerId());

        return attributes;
    }

    @Override
    public void setModelAttributes(Map<String, Object> attributes) {
        Long invoiceId = (Long) attributes.get("invoiceId");

        if (invoiceId != null) {
            setInvoiceId(invoiceId);
        }

        String path = (String) attributes.get("path");

        if (path != null) {
            setPath(path);
        }

        Long consumerId = (Long) attributes.get("consumerId");

        if (consumerId != null) {
            setConsumerId(consumerId);
        }
    }

    @JSON
    public long getInvoiceId() {
        return _invoiceId;
    }

    public void setInvoiceId(long invoiceId) {
        _invoiceId = invoiceId;
    }

    @JSON
    public String getPath() {
        if (_path == null) {
            return StringPool.BLANK;
        } else {
            return _path;
        }
    }

    public void setPath(String path) {
        _path = path;
    }

    @JSON
    public long getConsumerId() {
        return _consumerId;
    }

    public void setConsumerId(long consumerId) {
        _columnBitmask |= CONSUMERID_COLUMN_BITMASK;

        if (!_setOriginalConsumerId) {
            _setOriginalConsumerId = true;

            _originalConsumerId = _consumerId;
        }

        _consumerId = consumerId;
    }

    public long getOriginalConsumerId() {
        return _originalConsumerId;
    }

    public long getColumnBitmask() {
        return _columnBitmask;
    }

    @Override
    public ExpandoBridge getExpandoBridge() {
        return ExpandoBridgeFactoryUtil.getExpandoBridge(0,
            Invoice.class.getName(), getPrimaryKey());
    }

    @Override
    public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
        ExpandoBridge expandoBridge = getExpandoBridge();

        expandoBridge.setAttributes(serviceContext);
    }

    @Override
    public Invoice toEscapedModel() {
        if (_escapedModel == null) {
            _escapedModel = (Invoice) ProxyUtil.newProxyInstance(_classLoader,
                    _escapedModelInterfaces, new AutoEscapeBeanHandler(this));
        }

        return _escapedModel;
    }

    public Invoice toUnescapedModel() {
        return (Invoice) this;
    }

    @Override
    public Object clone() {
        InvoiceImpl invoiceImpl = new InvoiceImpl();

        invoiceImpl.setInvoiceId(getInvoiceId());
        invoiceImpl.setPath(getPath());
        invoiceImpl.setConsumerId(getConsumerId());

        invoiceImpl.resetOriginalValues();

        return invoiceImpl;
    }

    public int compareTo(Invoice invoice) {
        long primaryKey = invoice.getPrimaryKey();

        if (getPrimaryKey() < primaryKey) {
            return -1;
        } else if (getPrimaryKey() > primaryKey) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Invoice)) {
            return false;
        }

        Invoice invoice = (Invoice) obj;

        long primaryKey = invoice.getPrimaryKey();

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
        InvoiceModelImpl invoiceModelImpl = this;

        invoiceModelImpl._originalConsumerId = invoiceModelImpl._consumerId;

        invoiceModelImpl._setOriginalConsumerId = false;

        invoiceModelImpl._columnBitmask = 0;
    }

    @Override
    public CacheModel<Invoice> toCacheModel() {
        InvoiceCacheModel invoiceCacheModel = new InvoiceCacheModel();

        invoiceCacheModel.invoiceId = getInvoiceId();

        invoiceCacheModel.path = getPath();

        String path = invoiceCacheModel.path;

        if ((path != null) && (path.length() == 0)) {
            invoiceCacheModel.path = null;
        }

        invoiceCacheModel.consumerId = getConsumerId();

        return invoiceCacheModel;
    }

    @Override
    public String toString() {
        StringBundler sb = new StringBundler(7);

        sb.append("{invoiceId=");
        sb.append(getInvoiceId());
        sb.append(", path=");
        sb.append(getPath());
        sb.append(", consumerId=");
        sb.append(getConsumerId());
        sb.append("}");

        return sb.toString();
    }

    public String toXmlString() {
        StringBundler sb = new StringBundler(13);

        sb.append("<model><model-name>");
        sb.append("com.covisint.papi.sample.portlet.model.Invoice");
        sb.append("</model-name>");

        sb.append(
            "<column><column-name>invoiceId</column-name><column-value><![CDATA[");
        sb.append(getInvoiceId());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>path</column-name><column-value><![CDATA[");
        sb.append(getPath());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>consumerId</column-name><column-value><![CDATA[");
        sb.append(getConsumerId());
        sb.append("]]></column-value></column>");

        sb.append("</model>");

        return sb.toString();
    }
}