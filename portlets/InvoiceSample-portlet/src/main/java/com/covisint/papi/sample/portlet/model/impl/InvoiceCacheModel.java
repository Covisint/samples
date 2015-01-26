package com.covisint.papi.sample.portlet.model.impl;

import com.covisint.papi.sample.portlet.model.Invoice;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import java.io.Serializable;

/**
 * The cache model class for representing Invoice in entity cache.
 *
 * @author Nitin R. Khobragade
 * @see Invoice
 * @generated
 */
public class InvoiceCacheModel implements CacheModel<Invoice>, Serializable {
    public long invoiceId;
    public String path;
    public long consumerId;

    @Override
    public String toString() {
        StringBundler sb = new StringBundler(7);

        sb.append("{invoiceId=");
        sb.append(invoiceId);
        sb.append(", path=");
        sb.append(path);
        sb.append(", consumerId=");
        sb.append(consumerId);
        sb.append("}");

        return sb.toString();
    }

    public Invoice toEntityModel() {
        InvoiceImpl invoiceImpl = new InvoiceImpl();

        invoiceImpl.setInvoiceId(invoiceId);

        if (path == null) {
            invoiceImpl.setPath(StringPool.BLANK);
        } else {
            invoiceImpl.setPath(path);
        }

        invoiceImpl.setConsumerId(consumerId);

        invoiceImpl.resetOriginalValues();

        return invoiceImpl;
    }
}
