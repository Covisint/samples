package com.covisint.papi.sample.portlet.model.impl;

import com.covisint.papi.sample.portlet.model.Consumer;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import java.io.Serializable;

/**
 * The cache model class for representing Consumer in entity cache.
 *
 * @author Nitin R. Khobragade
 * @see Consumer
 * @generated
 */
public class ConsumerCacheModel implements CacheModel<Consumer>, Serializable {
    public long consumerId;
    public String consumerName;
    public String password;

    @Override
    public String toString() {
        StringBundler sb = new StringBundler(7);

        sb.append("{consumerId=");
        sb.append(consumerId);
        sb.append(", consumerName=");
        sb.append(consumerName);
        sb.append(", password=");
        sb.append(password);
        sb.append("}");

        return sb.toString();
    }

    public Consumer toEntityModel() {
        ConsumerImpl consumerImpl = new ConsumerImpl();

        consumerImpl.setConsumerId(consumerId);

        if (consumerName == null) {
            consumerImpl.setConsumerName(StringPool.BLANK);
        } else {
            consumerImpl.setConsumerName(consumerName);
        }

        if (password == null) {
            consumerImpl.setPassword(StringPool.BLANK);
        } else {
            consumerImpl.setPassword(password);
        }

        consumerImpl.resetOriginalValues();

        return consumerImpl;
    }
}
