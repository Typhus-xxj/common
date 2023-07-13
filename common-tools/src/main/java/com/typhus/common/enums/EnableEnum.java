package com.typhus.common.enums;

/**
 * 是、否
 *
 * @author typhus-xxj
 * @version EnableEnum.java, v 0.1 2023年07月13日 14:38 typhus-xxj Exp $
 */
public enum EnableEnum implements IKVEnum<Integer> {

    YES(1, "是"),

    NO(0, "否"),

    ;

    private Integer code;

    private String desc;

    EnableEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return desc;
    }
}
