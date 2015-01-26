package com.covisint.papi.sample.portlet.service.http;

import com.covisint.papi.sample.portlet.service.InvoiceServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * <p>
 * This class provides a SOAP utility for the
 * {@link com.covisint.papi.sample.portlet.service.InvoiceServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 * </p>
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.covisint.papi.sample.portlet.model.InvoiceSoap}.
 * If the method in the service utility returns a
 * {@link com.covisint.papi.sample.portlet.model.Invoice}, that is translated to a
 * {@link com.covisint.papi.sample.portlet.model.InvoiceSoap}. Methods that SOAP cannot
 * safely wire are skipped.
 * </p>
 *
 * <p>
 * The benefits of using the SOAP utility is that it is cross platform
 * compatible. SOAP allows different languages like Java, .NET, C++, PHP, and
 * even Perl, to call the generated services. One drawback of SOAP is that it is
 * slow because it needs to serialize all calls into a text format (XML).
 * </p>
 *
 * <p>
 * You can see a list of services at
 * http://localhost:8080/api/secure/axis. Set the property
 * <b>axis.servlet.hosts.allowed</b> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author    Nitin R. Khobragade
 * @see       InvoiceServiceHttp
 * @see       com.covisint.papi.sample.portlet.model.InvoiceSoap
 * @see       com.covisint.papi.sample.portlet.service.InvoiceServiceUtil
 * @generated
 */
public class InvoiceServiceSoap {
    private static Log _log = LogFactoryUtil.getLog(InvoiceServiceSoap.class);

    public static com.covisint.papi.sample.portlet.model.InvoiceSoap[] getInvoicesForConsumer(
        long consumerId) throws RemoteException {
        try {
            java.util.List<com.covisint.papi.sample.portlet.model.Invoice> returnValue =
                InvoiceServiceUtil.getInvoicesForConsumer(consumerId);

            return com.covisint.papi.sample.portlet.model.InvoiceSoap.toSoapModels(returnValue);
        } catch (Exception e) {
            _log.error(e, e);

            throw new RemoteException(e.getMessage());
        }
    }
}
