package com.rookie.extensions.constants;

public class Constants {
    //ack的三种模式
    public static final String ACK_EXCHANGE_NAME = "ack_exchange";
    public static final String ACK_QUEUE = "ack_queue";

    //发送方确认
    public static final String CONFIRM_QUEUE = "confirm.queue";
    public static final String CONFIRM_EXCHANGE = "confirm.exchange";

    //重试机制
    public static final String RETRY_QUEUE = "retry_queue";
    public static final String RETRY_EXCHANGE_NAME = "retry_exchange";

    //TTL
    public static final String TTL_QUEUE = "ttl_queue";
    public static final String TTL_EXCHANGE_NAME = "ttl_exchange";

    public static final String TTL_QUEUE2 = "ttl_queue2";

    //死信队列
    public static final String DLX_EXCHANGE_NAME = "dlx_exchange";
    public static final String DLX_QUEUE = "dlx_queue";
    public static final String NORMAL_EXCHANGE_NAME = "normal_exchange";
    public static final String NORMAL_QUEUE = "normal_queue";

    //delay
    public static final String DELAY_QUEUE = "delay_queue";
    public static final String DELAY_EXCHANGE_NAME = "delay_exchange";

    //事务
    public static final String TRANS_QUEUE = "trans_queue";

    //Qos
    public static final String QOS_QUEUE = "qos_queue";
    public static final String QOS_EXCHANGE_NAME = "qos_exchange";
}
