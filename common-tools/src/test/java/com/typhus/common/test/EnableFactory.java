package com.typhus.common.test;

import com.typhus.common.design.factory.AbstractStrategyFactory;
import com.typhus.common.enums.EnableEnum;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.function.Function;

/**
 * 测试
 *
 * @author typhus-xxj
 * @version EnableFactory.java, v 0.1 2023年07月13日 14:37 typhus-xxj Exp $
 */
@Component
public class EnableFactory extends AbstractStrategyFactory<EnableEnum, EnableStrategy> {


    @Override
    protected Function<EnableStrategy, Set<EnableEnum>> getFunction() {
        return EnableStrategy::getImpl;
    }

    @Override
    protected Class<EnableStrategy> getClazz() {
        return EnableStrategy.class;
    }
}
