package com.schcilin.payserver.service.Impl;

import com.schcilin.payserver.entity.PayChannel;
import com.schcilin.payserver.mapper.PayChannelMapper;
import com.schcilin.payserver.service.PayChannelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.reflections.Reflections;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.lang.reflect.Field;
import java.util.Set;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author schcilin
 * @since 2019-06-23
 */
@Service
public class PayChannelServiceImpl extends ServiceImpl<PayChannelMapper, PayChannel> implements PayChannelService {
    private int anInt;

    @Override
    public void test() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Reflections reflections = new Reflections("com.schcilin.payserver.service.Impl");
        Set<Class<?>> annotatedWith = reflections.getTypesAnnotatedWith(Service.class);
        for (Class clzz : annotatedWith) {
            String canonicalName = clzz.getCanonicalName();
            Class<?> aClass = Class.forName(canonicalName);
            PayChannelServiceImpl o = (PayChannelServiceImpl) aClass.newInstance();
            Field[] fields = this.getClass().getFields();
            for (Field field : fields) {
                field.setAccessible(true);
                String simpleName = field.getType().getSimpleName();
            }

            System.out.println(o.anInt);
        }

    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.NESTED, readOnly = false)
    public void testException() {
        try {
            PayChannel channel = new PayChannel();
            channel.setId("11111111");
            channel.setName("444");
            this.baseMapper.insert(channel);


        } finally {
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

    }
}
