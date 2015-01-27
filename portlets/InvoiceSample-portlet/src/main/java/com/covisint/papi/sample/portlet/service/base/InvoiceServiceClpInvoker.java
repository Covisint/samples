package com.covisint.papi.sample.portlet.service.base;

import com.covisint.papi.sample.portlet.service.InvoiceServiceUtil;

import java.util.Arrays;


public class InvoiceServiceClpInvoker {
    private String _methodName22;
    private String[] _methodParameterTypes22;
    private String _methodName23;
    private String[] _methodParameterTypes23;
    private String _methodName28;
    private String[] _methodParameterTypes28;

    public InvoiceServiceClpInvoker() {
        _methodName22 = "getBeanIdentifier";

        _methodParameterTypes22 = new String[] {  };

        _methodName23 = "setBeanIdentifier";

        _methodParameterTypes23 = new String[] { "java.lang.String" };

        _methodName28 = "getInvoicesForUser";

        _methodParameterTypes28 = new String[] { "long" };
    }

    public Object invokeMethod(String name, String[] parameterTypes,
        Object[] arguments) throws Throwable {
        if (_methodName22.equals(name) &&
                Arrays.deepEquals(_methodParameterTypes22, parameterTypes)) {
            return InvoiceServiceUtil.getBeanIdentifier();
        }

        if (_methodName23.equals(name) &&
                Arrays.deepEquals(_methodParameterTypes23, parameterTypes)) {
            InvoiceServiceUtil.setBeanIdentifier((java.lang.String) arguments[0]);

            return null;
        }

        if (_methodName28.equals(name) &&
                Arrays.deepEquals(_methodParameterTypes28, parameterTypes)) {
            return InvoiceServiceUtil.getInvoicesForUser(((Long) arguments[0]).longValue());
        }

        throw new UnsupportedOperationException();
    }
}
