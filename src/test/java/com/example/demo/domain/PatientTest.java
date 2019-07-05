package com.example.demo.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.annotation.Timed;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(SpringRunner.class)  // 使用JUnit 4
@SpringBootTest
public class PatientTest {

    private Patient patient;

    @Before
    public void setUp() throws Exception {
        patient = new Patient();
    }

    @Test
    public void getName() {
        // 实例变量
        ReflectionTestUtils.setField(this.patient, "name", "李四"); // 测试patient对象是否有name属性
        ReflectionTestUtils.setField(this.patient, "name", "李四", String.class); // 测试patient对象是否有String类型的name属性
        ReflectionTestUtils.setField(this.patient, Patient.class, "name", "李四", String.class);

        ReflectionTestUtils.getField(this.patient, "name");
        ReflectionTestUtils.getField(this.patient, Patient.class, "name");

        // 类变量
        ReflectionTestUtils.setField(Patient.class, "PAT_NO", 0);
        ReflectionTestUtils.setField(Patient.class, "PAT_NO", 0, int.class);

        ReflectionTestUtils.getField(Patient.class, "PAT_NO");

        // 方法调用
        ReflectionTestUtils.invokeSetterMethod(this.patient, "setName", "李四");
        ReflectionTestUtils.invokeSetterMethod(this.patient, "setName", "李四", String.class);
        ReflectionTestUtils.invokeGetterMethod(this.patient, "getName");
        ReflectionTestUtils.invokeMethod(this.patient, "setName", "李四");
    }

    @Repeat(2) //重复执行2次
    @Timed(millis = 1000) //执行超时时间1s
    @Test
    public void setName() {
        System.out.println("aaaaaaaaaa");
    }

    @Test
    public void getAge() {
    }

    @Test
    public void setAge() {
    }
}