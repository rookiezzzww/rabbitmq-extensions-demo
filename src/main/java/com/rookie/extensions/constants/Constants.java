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
}
