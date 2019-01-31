package com.study.shizhanzhinan.chapters_4;

/**
 * 死信队列
 * 也可以称之为死信交换器
 * 当消息在一个队列中变成死信之后,它能被重新被发送到一个交换器中，
 * 这个交换器就是DLX，死信队列
 * 什么消息时死信：
 *  1、消息被拒绝，并且设置了requeue参数为false
 *  2、消息过期
 *  3、队列达到最大长度
 */
public class RabbitmqProducer4_dead_letter_exchange {
}
