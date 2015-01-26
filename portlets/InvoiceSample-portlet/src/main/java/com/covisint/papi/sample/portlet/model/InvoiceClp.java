package com.covisint.papi.sample.portlet.model;

import com.covisint.papi.sample.portlet.service.ClpSerializer;
import com.covisint.papi.sample.portlet.service.InvoiceLocalServiceUtil;

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


public class InvoiceClp extends BaseModelImpl<Invoice> implements Invoice {
    private long _invoiceId;
    private String _path;
    private long _consumerId;
    private BaseModel<?> _invoiceRemoteModel;

    public InvoiceClp() {
    }

    public Class<?> getModelClass() {
        return Invoice.class;
    }

    public String getModelClassName() {
        return Invoice.class.getName();
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

    public long getInvoiceId() {
        return _invoiceId;
    }

    public void setInvoiceId(long invoiceId) {
        _invoiceId = invoiceId;

        if (_invoiceRemoteModel != null) {
            try {
                Class<?> clazz = _invoiceRemoteModel.getClass();

                Method method = clazz.getMethod("setInvoiceId", long.class);

                method.invoke(_invoiceRemoteModel, invoiceId);
            } catch (Exception e) {
                throw new UnsupportedOperationException(e);
            }
        }
    }

    public String getPath() {
        return _path;
    }

    public void setPath(String path) {
        _path = path;

        if (_invoiceRemoteModel != null) {
            try {
                Class<?> clazz = _invoiceRemoteModel.getClass();

                Method method = clazz.getMethod("setPath", String.class);

                method.invoke(_invoiceRemoteModel, path);
            } catch (Exception e) {
                throw new UnsupportedOperationException(e);
            }
        }
    }

    public long getConsumerId() {
        return _consumerId;
    }

    public void setConsumerId(long consumerId) {
        _consumerId = consumerId;

        if (_invoiceRemoteModel != null) {
            try {
                Class<?> clazz = _invoiceRemoteModel.getClass();

                Method method = clazz.getMethod("setConsumerId", long.class);

                method.invoke(_invoiceRemoteModel, consumerId);
            } catch (Exception e) {
                throw new UnsupportedOperationException(e);
            }
        }
    }

    public BaseModel<?> getInvoiceRemoteModel() {
        return _invoiceRemoteModel;
    }

    public void setInvoiceRemoteModel(BaseModel<?> invoiceRemoteModel) {
        _invoiceRemoteModel = invoiceRemoteModel;
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

        Class<?> remoteModelClass = _invoiceRemoteModel.getClass();

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

        Object returnValue = method.invoke(_invoiceRemoteModel,
                remoteParameterValues);

        if (returnValue != null) {
            returnValue = ClpSerializer.translateOutput(returnValue);
        }

        return returnValue;
    }

    public void persist() throws SystemException {
        if (this.isNew()) {
            InvoiceLocalServiceUtil.addInvoice(this);
        } else {
            InvoiceLocalServiceUtil.updateInvoice(this);
        }
    }

    @Override
    public Invoice toEscapedModel() {
        return (Invoice) ProxyUtil.newProxyInstance(Invoice.class.getClassLoader(),
            new Class[] { Invoice.class }, new AutoEscapeBeanHandler(this));
    }

    public Invoice toUnescapedModel() {
        return this;
    }

    @Override
    public Object clone() {
        InvoiceClp clone = new InvoiceClp();

        clone.setInvoiceId(getInvoiceId());
        clone.setPath(getPath());
        clone.setConsumerId(getConsumerId());

        return clone;
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

        if (!(obj instanceof InvoiceClp)) {
            return false;
        }

        InvoiceClp invoice = (InvoiceClp) obj;

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
