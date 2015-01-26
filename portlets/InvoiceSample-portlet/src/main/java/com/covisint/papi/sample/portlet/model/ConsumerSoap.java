package com.covisint.papi.sample.portlet.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.covisint.papi.sample.portlet.service.http.ConsumerServiceSoap}.
 *
 * @author    Nitin R. Khobragade
 * @see       com.covisint.papi.sample.portlet.service.http.ConsumerServiceSoap
 * @generated
 */
public class ConsumerSoap implements Serializable {
    private long _consumerId;
    private String _consumerName;
    private String _password;

    public ConsumerSoap() {
    }

    public static ConsumerSoap toSoapModel(Consumer model) {
        ConsumerSoap soapModel = new ConsumerSoap();

        soapModel.setConsumerId(model.getConsumerId());
        soapModel.setConsumerName(model.getConsumerName());
        soapModel.setPassword(model.getPassword());

        return soapModel;
    }

    public static ConsumerSoap[] toSoapModels(Consumer[] models) {
        ConsumerSoap[] soapModels = new ConsumerSoap[models.length];

        for (int i = 0; i < models.length; i++) {
            soapModels[i] = toSoapModel(models[i]);
        }

        return soapModels;
    }

    public static ConsumerSoap[][] toSoapModels(Consumer[][] models) {
        ConsumerSoap[][] soapModels = null;

        if (models.length > 0) {
            soapModels = new ConsumerSoap[models.length][models[0].length];
        } else {
            soapModels = new ConsumerSoap[0][0];
        }

        for (int i = 0; i < models.length; i++) {
            soapModels[i] = toSoapModels(models[i]);
        }

        return soapModels;
    }

    public static ConsumerSoap[] toSoapModels(List<Consumer> models) {
        List<ConsumerSoap> soapModels = new ArrayList<ConsumerSoap>(models.size());

        for (Consumer model : models) {
            soapModels.add(toSoapModel(model));
        }

        return soapModels.toArray(new ConsumerSoap[soapModels.size()]);
    }

    public long getPrimaryKey() {
        return _consumerId;
    }

    public void setPrimaryKey(long pk) {
        setConsumerId(pk);
    }

    public long getConsumerId() {
        return _consumerId;
    }

    public void setConsumerId(long consumerId) {
        _consumerId = consumerId;
    }

    public String getConsumerName() {
        return _consumerName;
    }

    public void setConsumerName(String consumerName) {
        _consumerName = consumerName;
    }

    public String getPassword() {
        return _password;
    }

    public void setPassword(String password) {
        _password = password;
    }
}
