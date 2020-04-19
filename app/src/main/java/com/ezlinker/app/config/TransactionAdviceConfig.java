package com.ezlinker.app.config;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import javax.annotation.Resource;


/**
 * @author wangwenhai
 */
@Aspect
@Configuration
public class TransactionAdviceConfig {
    private static final String AOP_POINTCUT_EXPRESSION = "execution (* com.ezlinker.app.modules.*.service(..))";

    @Resource
    private PlatformTransactionManager transactionManager;

    @Bean
    public Advisor txAdviceAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(AOP_POINTCUT_EXPRESSION);
        DefaultTransactionAttribute txAttrREQUIRED = new DefaultTransactionAttribute();
        txAttrREQUIRED.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        DefaultTransactionAttribute txAttrREQUIREDREADONLY = new DefaultTransactionAttribute();
        txAttrREQUIREDREADONLY.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        txAttrREQUIREDREADONLY.setReadOnly(true);

        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();

        source.addTransactionalMethod("save*", txAttrREQUIRED);
        source.addTransactionalMethod("delete*", txAttrREQUIRED);
        source.addTransactionalMethod("update*", txAttrREQUIRED);
//        source.addTransactionalMethod("exec*", txAttrREQUIRED);
//        source.addTransactionalMethod("set*", txAttrREQUIRED);
//        source.addTransactionalMethod("get*", txAttrREQUIREDREADONLY);
//        source.addTransactionalMethod("query*", txAttrREQUIREDREADONLY);
//        source.addTransactionalMethod("find*", txAttrREQUIREDREADONLY);
//        source.addTransactionalMethod("list*", txAttrREQUIREDREADONLY);
//        source.addTransactionalMethod("count*", txAttrREQUIREDREADONLY);
//        source.addTransactionalMethod("is*", txAttrREQUIREDREADONLY);

        return new DefaultPointcutAdvisor(pointcut, new TransactionInterceptor(transactionManager, source));
    }
}
