package com.xigua;

import com.alibaba.fastjson2.JSONObject;
import com.xigua.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName XiGuaTest
 * @Description TODO
 * @Author wangjinfei
 * @Date 2023/11/30 12:03
 */
@SpringBootTest
public class XiGuaTest {
    @Test
    public void test1() throws Exception {
//        String userJson = "{\"id\":1,\"name\":\"John\"}";
        User user1 = new User();
        user1.setAge(20);
        user1.setName("张三");
        Map<String, Object> map = new HashMap<>();
        map.put("classType", User.class);

        Class<?> classType = (Class<?>) map.get("classType");
        Object o = JSONObject.parseObject(JSONObject.toJSONString(user1), classType);
        System.out.println("o = " + o);

        Field nameField = classType.getDeclaredField("name");
        Field ageField = classType.getDeclaredField("age");

        nameField.setAccessible(true);
        ageField.setAccessible(true);

        nameField.set(o, "李四");
        ageField.set(o, 30);

        Method getNameMethod = classType.getDeclaredMethod("getName");
        Method getAgeMethod = classType.getDeclaredMethod("getAge");

        Object nameValue = getNameMethod.invoke(o);
        Object ageValue = getAgeMethod.invoke(o);
        System.out.println("o = " + o);



    }
}
