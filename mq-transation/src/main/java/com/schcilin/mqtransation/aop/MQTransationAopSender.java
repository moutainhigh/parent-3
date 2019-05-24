package com.schcilin.mqtransation.aop;

import com.schcilin.mqtransation.anno.MQTransationMessageAnno;
import com.schcilin.mqtransation.constant.MQConstant;
import com.schcilin.mqtransation.coordinator.DBCoordinator;
import com.schcilin.mqtransation.pojo.RabbitMetaMessage;
import com.schcilin.mqtransation.sender.MQTransationSender;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: schcilin
 * @Date: 2019/5/23 15:24
 * @Content: AOP切面实现分布式事务
 */

@Aspect
@Component
@Slf4j
public class MQTransationAopSender {

    @Autowired
    private MQTransationSender mqTransationSender;
    @Autowired
    ApplicationContext applicationContext;

    /**
     * 定义注解类型的切点，只要方法上有该注解，都会匹配
     */
    @Pointcut("@annotation(com.schcilin.mqtransation.anno.MQTransationMessageAnno)")
    public void sendPointCut() {
    }

    @Around("sendPointCut()&& @annotation(mqt)")
    public void beforeSendMsg(ProceedingJoinPoint joinPoint, MQTransationMessageAnno mqt) throws Throwable {
        log.info("==>custom mq annotation:" + mqt);
        String exchange = mqt.exchange();
        String bindingKey = mqt.bindingKey();
        String bizName = mqt.bizName() + MQConstant.DB_SPLIT + getCurrentDateTime();
        String coordinator = mqt.dbCoordinator();
        DBCoordinator dbCoordinator = null;
        try {
            dbCoordinator = (DBCoordinator) applicationContext.getBean(coordinator);
        } catch (Exception e) {
            log.error("无消息存储类，事务执行终止");
            return;
        }
        /**发送前暂存消息*/
        dbCoordinator.setMsgPrepare(bizName);
        Object returnObj = null;
        /** 执行业务函数 */
        try {
            returnObj = joinPoint.proceed();
        } catch (Exception e) {
            log.error("业务执行失败,业务名称:" + bizName);
            throw e;
        }
        if (returnObj == null) {
            returnObj = "";
        }
        /**生成发送消息体*/
        RabbitMetaMessage rabbitMetaMessage = new RabbitMetaMessage();
        rabbitMetaMessage.setMessageId(bizName);
        /**交换机*/
        rabbitMetaMessage.setExchange(exchange);
        /**指定routing key */
        rabbitMetaMessage.setRoutingKey(bindingKey);
        /** 设置需要传递的消息体,可以是任意对象 */
        rabbitMetaMessage.setPayload(returnObj);
        /** 将消息设置为ready状态*/
        dbCoordinator.setMsgReady(bizName, rabbitMetaMessage);

        /** 发送消息 */
        try {
            /**保存消息载体*/
            this.mqTransationSender.setCorrelationData(coordinator);
            this.mqTransationSender.send(rabbitMetaMessage);
        } catch (Exception e) {
            log.error("第一阶段消息发送异常" + bizName, e);
            throw e;
        }

    }

    private String getCurrentDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
        return dateFormat.format(new Date());
    }
}