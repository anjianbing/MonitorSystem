package com.jd.jr.monitor.spout;

import java.util.concurrent.CountDownLatch;

import com.taobao.metamorphosis.Message;


/**
 * Meta消息的包装类，关联一个CountDownLatch
 *
 * @author boyan(boyan@taobao.com)
 * @date 2011-11-8
 *
 */
public final class MetaMessageWrapper {

    public final Message message;
    public final CountDownLatch latch;
    public volatile boolean success = false;


    public MetaMessageWrapper(final Message message) {
        super();
        this.message = message;
        this.latch = new CountDownLatch(1);
    }

}
