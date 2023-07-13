package com.typhus.common.tools;

import cn.hutool.core.collection.ListUtil;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

/**
 * 测试 ConvertUtils
 *
 * @author typhus-xxj
 * @version ConvertUtilsTest.java, v 0.1 2023年07月13日 14:49 typhus-xxj Exp $
 */
public class ConvertUtilsTest {

    private EmployeeDO employeeDO;

    private List<EmployeeDO> employeeDOList;

    @Before
    public void before() {
        employeeDO = new EmployeeDO();
        employeeDO.setName("Jack");
        employeeDO.setAge(18);
        employeeDO.setSalary(3000D);
        employeeDO.setBir(new Date());
        employeeDO.setFriendNameList(ListUtil.of("a", "b", "c"));
        filedDO filedDO = new filedDO();
        filedDO.setFiled("DO");
        employeeDO.setFiledDOS(ListUtil.of(filedDO));

        EmployeeDO employeeDO1 = new EmployeeDO();
        employeeDO1.setName("Sari");
        employeeDO1.setAge(21);
        employeeDO1.setSalary(4000D);
        employeeDO1.setBir(new Date());
        employeeDO1.setFriendNameList(ListUtil.of("e", "f", "g"));
        filedDO filedDO1 = new filedDO();
        filedDO1.setFiled("DO1");
        employeeDO1.setFiledDOS(ListUtil.of(filedDO1));

        employeeDOList = ListUtil.of(employeeDO, employeeDO1);

    }

    @Test
    public void test() {
        EmployeeVO convert = ConvertUtils.convert(employeeDO, EmployeeVO::new);

        System.out.println("convert = " + JacksonUtil.bean2String(convert));

    }

    @Test
    public void testlist() {
        List<EmployeeVO> convert = ConvertUtils.convert(employeeDOList, EmployeeVO.class);
        System.out.println("convert = " + JacksonUtil.bean2String(convert));

    }

    @Test
    public void testlist1() {
        List<EmployeeVO> convert = ConvertUtils.convert(employeeDOList, EmployeeVO.class, (f, t) -> {
            t.setFriendNameList(f.getFriendNameList());
            List<filedVO> filedVOS = ConvertUtils.convert(f.getFiledDOS(), filedVO.class);
            t.setFiledVOS(filedVOS);
        });

        System.out.println("convert = " + JacksonUtil.bean2String(convert));
    }


}


class EmployeeDO {
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 姓名
     */
    private String name;
    /**
     * 工资
     */
    private Double salary;
    /**
     *
     */
    private Date bir;
    /**
     *
     */
    private List<String> friendNameList;

    private List<filedDO> filedDOS;


    public List<filedDO> getFiledDOS() {
        return filedDOS;
    }

    public void setFiledDOS(List<filedDO> filedDOS) {
        this.filedDOS = filedDOS;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Date getBir() {
        return bir;
    }

    public void setBir(Date bir) {
        this.bir = bir;
    }

    public List<String> getFriendNameList() {
        return friendNameList;
    }

    public void setFriendNameList(List<String> friendNameList) {
        this.friendNameList = friendNameList;
    }
}

class EmployeeVO {
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 姓名
     */
    private String name;
    /**
     * 工资
     */
    private Double salary;
    /**
     *
     */
    private Date bir;
    /**
     *
     */
    private List<String> friendNameList;

    private List<filedVO> filedVOS;

    public List<filedVO> getFiledVOS() {
        return filedVOS;
    }

    public void setFiledVOS(List<filedVO> filedVOS) {
        this.filedVOS = filedVOS;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Date getBir() {
        return bir;
    }

    public void setBir(Date bir) {
        this.bir = bir;
    }

    public List<String> getFriendNameList() {
        return friendNameList;
    }

    public void setFriendNameList(List<String> friendNameList) {
        this.friendNameList = friendNameList;
    }
}

class filedDO {
    private String filed;

    public String getFiled() {
        return filed;
    }

    public void setFiled(String filed) {
        this.filed = filed;
    }
}

class filedVO {
    private String filed;

    public String getFiled() {
        return filed;
    }

    public void setFiled(String filed) {
        this.filed = filed;
    }
}