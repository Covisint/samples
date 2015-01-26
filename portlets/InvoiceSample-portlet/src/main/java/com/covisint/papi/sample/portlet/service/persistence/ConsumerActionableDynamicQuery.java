package com.covisint.papi.sample.portlet.service.persistence;

import com.covisint.papi.sample.portlet.model.Consumer;
import com.covisint.papi.sample.portlet.service.ConsumerLocalServiceUtil;

import com.liferay.portal.kernel.dao.orm.BaseActionableDynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;

/**
 * @author Nitin R. Khobragade
 * @generated
 */
public abstract class ConsumerActionableDynamicQuery
    extends BaseActionableDynamicQuery {
    public ConsumerActionableDynamicQuery() throws SystemException {
        setBaseLocalService(ConsumerLocalServiceUtil.getService());
        setClass(Consumer.class);

        setClassLoader(com.covisint.papi.sample.portlet.service.ClpSerializer.class.getClassLoader());

        setPrimaryKeyPropertyName("consumerId");
    }
}
