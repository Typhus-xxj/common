package com.typhus.common.design.factory;

import com.typhus.common.enums.EnableEnum;
import com.typhus.common.test.EnableFactory;
import com.typhus.common.test.EnableStrategy;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 测试抽象工厂
 *
 * @author typhus-xxj
 * @version AbstractStrategyFactoryTest.java, v 0.1 2023年07月13日 14:25 typhus-xxj Exp $
 */
@SpringBootTest
public class AbstractStrategyFactoryTest {

    @Autowired
    private EnableFactory enableFactory;

    @Test
    public void test(){
        EnableStrategy strategies = enableFactory.getStrategies(EnableEnum.YES);


    }


}
