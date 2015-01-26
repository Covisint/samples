package com.covisint.papi.sample.portlet.service.persistence;

import com.covisint.papi.sample.portlet.model.Invoice;
import com.covisint.papi.sample.portlet.service.InvoiceLocalServiceUtil;

import com.liferay.portal.kernel.dao.orm.BaseActionableDynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;

/**
 * @author Nitin R. Khobragade
 * @generated
 */
public abstract class InvoiceActionableDynamicQuery
    extends BaseActionableDynamicQuery {
    public InvoiceActionableDynamicQuery() throws SystemException {
        setBaseLocalService(InvoiceLocalServiceUtil.getService());
        setClass(Invoice.class);

        setClassLoader(com.covisint.papi.sample.portlet.service.ClpSerializer.class.getClassLoader());

        setPrimaryKeyPropertyName("invoiceId");
    }
}
