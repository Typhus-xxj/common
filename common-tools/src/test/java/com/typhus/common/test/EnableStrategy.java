package com.typhus.common.test;

import com.typhus.common.enums.EnableEnum;

import java.util.Set;

/**
 * @author typhus-xxj
 * @version EnableStrategy.java, v 0.1 2023年07月13日 14:41 typhus-xxj Exp $
 */
public interface EnableStrategy {


    Set<EnableEnum> getImpl();

}
