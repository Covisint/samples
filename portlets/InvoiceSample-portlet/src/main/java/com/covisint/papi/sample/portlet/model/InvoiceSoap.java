package com.covisint.papi.sample.portlet.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.covisint.papi.sample.portlet.service.http.InvoiceServiceSoap}.
 *
 * @author    Nitin R. Khobragade
 * @see       com.covisint.papi.sample.portlet.service.http.InvoiceServiceSoap
 * @generated
 */
public class InvoiceSoap implements Serializable {
    private long _invoiceId;
    private String _path;
    private long _consumerId;

    public InvoiceSoap() {
    }

    public static InvoiceSoap toSoapModel(Invoice model) {
        InvoiceSoap soapModel = new InvoiceSoap();

        soapModel.setInvoiceId(model.getInvoiceId());
        soapModel.setPath(model.getPath());
        soapModel.setConsumerId(model.getConsumerId());

        return soapModel;
    }

    public static InvoiceSoap[] toSoapModels(Invoice[] models) {
        InvoiceSoap[] soapModels = new InvoiceSoap[models.length];

        for (int i = 0; i < models.length; i++) {
            soapModels[i] = toSoapModel(models[i]);
        }

        return soapModels;
    }

    public static InvoiceSoap[][] toSoapModels(Invoice[][] models) {
        InvoiceSoap[][] soapModels = null;

        if (models.length > 0) {
            soapModels = new InvoiceSoap[models.length][models[0].length];
        } else {
            soapModels = new InvoiceSoap[0][0];
        }

        for (int i = 0; i < models.length; i++) {
            soapModels[i] = toSoapModels(models[i]);
        }

        return soapModels;
    }

    public static InvoiceSoap[] toSoapModels(List<Invoice> models) {
        List<InvoiceSoap> soapModels = new ArrayList<InvoiceSoap>(models.size());

        for (Invoice model : models) {
            soapModels.add(toSoapModel(model));
        }

        return soapModels.toArray(new InvoiceSoap[soapModels.size()]);
    }

    public long getPrimaryKey() {
        return _invoiceId;
    }

    public void setPrimaryKey(long pk) {
        setInvoiceId(pk);
    }

    public long getInvoiceId() {
        return _invoiceId;
    }

    public void setInvoiceId(long invoiceId) {
        _invoiceId = invoiceId;
    }

    public String getPath() {
        return _path;
    }

    public void setPath(String path) {
        _path = path;
    }

    public long getConsumerId() {
        return _consumerId;
    }

    public void setConsumerId(long consumerId) {
        _consumerId = consumerId;
    }
}
