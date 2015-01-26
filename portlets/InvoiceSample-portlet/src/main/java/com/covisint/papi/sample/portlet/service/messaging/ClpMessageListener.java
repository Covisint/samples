package com.covisint.papi.sample.portlet.service.messaging;

import com.covisint.papi.sample.portlet.service.ClpSerializer;
import com.covisint.papi.sample.portlet.service.ConsumerLocalServiceUtil;
import com.covisint.papi.sample.portlet.service.ConsumerServiceUtil;
import com.covisint.papi.sample.portlet.service.InvoiceLocalServiceUtil;
import com.covisint.papi.sample.portlet.service.InvoiceServiceUtil;

import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;


public class ClpMessageListener extends BaseMessageListener {
    public static String getServletContextName() {
        return ClpSerializer.getServletContextName();
    }

    @Override
    protected void doReceive(Message message) throws Exception {
        String command = message.getString("command");
        String servletContextName = message.getString("servletContextName");

        if (command.equals("undeploy") &&
                servletContextName.equals(getServletContextName())) {
            ConsumerLocalServiceUtil.clearService();

            ConsumerServiceUtil.clearService();
            InvoiceLocalServiceUtil.clearService();

            InvoiceServiceUtil.clearService();
        }
    }
}
