package com.typhus.common.design.factory;

import com.typhus.common.enums.IKVEnum;
import org.springframework.beans.BeansException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 抽象工厂模版方法
 *
 * @author typhus-xxj
 * @version AbstractStrategyFactory.java, v 0.1 2023年07月13日 11:24 typhus-xxj Exp $
 */
public abstract class AbstractStrategyFactory<T extends IKVEnum<?>, Strategy> implements ApplicationContextAware, ApplicationRunner {

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        AbstractStrategyFactory.applicationContext = applicationContext;
    }

    /**
     * spring 上下文环境
     */
    private static ApplicationContext applicationContext;

    /**
     * 存放策略的容器
     */
    protected HashMap<T, Strategy> strategiesMap = new HashMap<>();

    /**
     * 从策略对象中获取
     *
     * @return 从策略中获取List<T>的实现
     */
    protected abstract Function<Strategy, Set<T>> getFunction();

    /**
     * 获取策略Class类型
     *
     * @return 策略Class
     */
    protected abstract Class<Strategy> getClazz();

    //@PostConstruct
    public void init() {
        Map<String, Strategy> beansOfType = applicationContext.getBeansOfType(getClazz());
        for (Map.Entry<String, Strategy> item : beansOfType.entrySet()) {
            Set<T> keys = getFunction().apply(item.getValue());
            for (T key : keys) {
                strategiesMap.put(key, item.getValue());
            }
        }

    }

    @Override
    public void run(ApplicationArguments args) {
        this.init();
    }

    /**
     * 根据key获取对应的策略
     *
     * @param key key
     * @return 策略列表
     */
    public Strategy getStrategies(T key) {
        return strategiesMap.get(key);
    }

    /**
     * 根据key获取对应的策略,并执行相应的策略逻辑
     *
     * @param key      key
     * @param consumer 拿到策略后执行逻辑的函数式接口
     */
    public void getStrategyAndExecute(T key, Consumer<Strategy> consumer) {
        Strategy strategy = strategiesMap.get(key);
        if (Objects.isNull(strategy)) {
            return;
        }
        consumer.accept(strategy);
    }

}
