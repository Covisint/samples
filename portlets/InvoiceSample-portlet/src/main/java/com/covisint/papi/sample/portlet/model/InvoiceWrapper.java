package com.covisint.papi.sample.portlet.model;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link Invoice}.
 * </p>
 *
 * @author    Nitin R. Khobragade
 * @see       Invoice
 * @generated
 */
public class InvoiceWrapper implements Invoice, ModelWrapper<Invoice> {
    private Invoice _invoice;

    public InvoiceWrapper(Invoice invoice) {
        _invoice = invoice;
    }

    public Class<?> getModelClass() {
        return Invoice.class;
    }

    public String getModelClassName() {
        return Invoice.class.getName();
    }

    public Map<String, Object> getModelAttributes() {
        Map<String, Object> attributes = new HashMap<String, Object>();

        attributes.put("invoiceId", getInvoiceId());
        attributes.put("path", getPath());
        attributes.put("userId", getUserId());

        return attributes;
    }

    public void setModelAttributes(Map<String, Object> attributes) {
        Long invoiceId = (Long) attributes.get("invoiceId");

        if (invoiceId != null) {
            setInvoiceId(invoiceId);
        }

        String path = (String) attributes.get("path");

        if (path != null) {
            setPath(path);
        }

        Long userId = (Long) attributes.get("userId");

        if (userId != null) {
            setUserId(userId);
        }
    }

    /**
    * Returns the primary key of this invoice.
    *
    * @return the primary key of this invoice
    */
    public long getPrimaryKey() {
        return _invoice.getPrimaryKey();
    }

    /**
    * Sets the primary key of this invoice.
    *
    * @param primaryKey the primary key of this invoice
    */
    public void setPrimaryKey(long primaryKey) {
        _invoice.setPrimaryKey(primaryKey);
    }

    /**
    * Returns the invoice ID of this invoice.
    *
    * @return the invoice ID of this invoice
    */
    public long getInvoiceId() {
        return _invoice.getInvoiceId();
    }

    /**
    * Sets the invoice ID of this invoice.
    *
    * @param invoiceId the invoice ID of this invoice
    */
    public void setInvoiceId(long invoiceId) {
        _invoice.setInvoiceId(invoiceId);
    }

    /**
    * Returns the path of this invoice.
    *
    * @return the path of this invoice
    */
    public java.lang.String getPath() {
        return _invoice.getPath();
    }

    /**
    * Sets the path of this invoice.
    *
    * @param path the path of this invoice
    */
    public void setPath(java.lang.String path) {
        _invoice.setPath(path);
    }

    /**
    * Returns the user ID of this invoice.
    *
    * @return the user ID of this invoice
    */
    public long getUserId() {
        return _invoice.getUserId();
    }

    /**
    * Sets the user ID of this invoice.
    *
    * @param userId the user ID of this invoice
    */
    public void setUserId(long userId) {
        _invoice.setUserId(userId);
    }

    /**
    * Returns the user uuid of this invoice.
    *
    * @return the user uuid of this invoice
    * @throws SystemException if a system exception occurred
    */
    public java.lang.String getUserUuid()
        throws com.liferay.portal.kernel.exception.SystemException {
        return _invoice.getUserUuid();
    }

    /**
    * Sets the user uuid of this invoice.
    *
    * @param userUuid the user uuid of this invoice
    */
    public void setUserUuid(java.lang.String userUuid) {
        _invoice.setUserUuid(userUuid);
    }

    public boolean isNew() {
        return _invoice.isNew();
    }

    public void setNew(boolean n) {
        _invoice.setNew(n);
    }

    public boolean isCachedModel() {
        return _invoice.isCachedModel();
    }

    public void setCachedModel(boolean cachedModel) {
        _invoice.setCachedModel(cachedModel);
    }

    public boolean isEscapedModel() {
        return _invoice.isEscapedModel();
    }

    public java.io.Serializable getPrimaryKeyObj() {
        return _invoice.getPrimaryKeyObj();
    }

    public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
        _invoice.setPrimaryKeyObj(primaryKeyObj);
    }

    public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
        return _invoice.getExpandoBridge();
    }

    public void setExpandoBridgeAttributes(
        com.liferay.portal.service.ServiceContext serviceContext) {
        _invoice.setExpandoBridgeAttributes(serviceContext);
    }

    @Override
    public java.lang.Object clone() {
        return new InvoiceWrapper((Invoice) _invoice.clone());
    }

    public int compareTo(Invoice invoice) {
        return _invoice.compareTo(invoice);
    }

    @Override
    public int hashCode() {
        return _invoice.hashCode();
    }

    public com.liferay.portal.model.CacheModel<Invoice> toCacheModel() {
        return _invoice.toCacheModel();
    }

    public Invoice toEscapedModel() {
        return new InvoiceWrapper(_invoice.toEscapedModel());
    }

    public Invoice toUnescapedModel() {
        return new InvoiceWrapper(_invoice.toUnescapedModel());
    }

    @Override
    public java.lang.String toString() {
        return _invoice.toString();
    }

    public java.lang.String toXmlString() {
        return _invoice.toXmlString();
    }

    public void persist()
        throws com.liferay.portal.kernel.exception.SystemException {
        _invoice.persist();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof InvoiceWrapper)) {
            return false;
        }

        InvoiceWrapper invoiceWrapper = (InvoiceWrapper) obj;

        if (Validator.equals(_invoice, invoiceWrapper._invoice)) {
            return true;
        }

        return false;
    }

    /**
     * @deprecated Renamed to {@link #getWrappedModel}
     */
    public Invoice getWrappedInvoice() {
        return _invoice;
    }

    public Invoice getWrappedModel() {
        return _invoice;
    }

    public void resetOriginalValues() {
        _invoice.resetOriginalValues();
    }
}
