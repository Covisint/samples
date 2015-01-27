package com.covisint.papi.sample.portlet.service;

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.InvokableService;

/**
 * The utility for the invoice remote service. This utility wraps {@link com.covisint.papi.sample.portlet.service.impl.InvoiceServiceImpl} and is the primary access point for service operations in application layer code running on a remote server.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Nitin R. Khobragade
 * @see InvoiceService
 * @see com.covisint.papi.sample.portlet.service.base.InvoiceServiceBaseImpl
 * @see com.covisint.papi.sample.portlet.service.impl.InvoiceServiceImpl
 * @generated
 */
public class InvoiceServiceUtil {
    private static InvoiceService _service;

    /*
     * NOTE FOR DEVELOPERS:
     *
     * Never modify this class directly. Add custom service methods to {@link com.covisint.papi.sample.portlet.service.impl.InvoiceServiceImpl} and rerun ServiceBuilder to regenerate this class.
     */

    /**
    * Returns the Spring bean ID for this bean.
    *
    * @return the Spring bean ID for this bean
    */
    public static java.lang.String getBeanIdentifier() {
        return getService().getBeanIdentifier();
    }

    /**
    * Sets the Spring bean ID for this bean.
    *
    * @param beanIdentifier the Spring bean ID for this bean
    */
    public static void setBeanIdentifier(java.lang.String beanIdentifier) {
        getService().setBeanIdentifier(beanIdentifier);
    }

    public static java.lang.Object invokeMethod(java.lang.String name,
        java.lang.String[] parameterTypes, java.lang.Object[] arguments)
        throws java.lang.Throwable {
        return getService().invokeMethod(name, parameterTypes, arguments);
    }

    public static java.util.List<com.covisint.papi.sample.portlet.model.Invoice> getInvoicesForUser(
        long userId) throws com.liferay.portal.kernel.exception.SystemException {
        return getService().getInvoicesForUser(userId);
    }

    public static void clearService() {
        _service = null;
    }

    public static InvoiceService getService() {
        if (_service == null) {
            InvokableService invokableService = (InvokableService) PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(),
                    InvoiceService.class.getName());

            if (invokableService instanceof InvoiceService) {
                _service = (InvoiceService) invokableService;
            } else {
                _service = new InvoiceServiceClp(invokableService);
            }

            ReferenceRegistry.registerReference(InvoiceServiceUtil.class,
                "_service");
        }

        return _service;
    }

    /**
     * @deprecated
     */
    public void setService(InvoiceService service) {
    }
}
