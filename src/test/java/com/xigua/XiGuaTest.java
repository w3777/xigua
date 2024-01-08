package com.xigua;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.xigua.entity.User;
import com.xigua.mapper.UserMapper;
import com.xigua.rabbitmq.MessageProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.io.File;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName XiGuaTest
 * @Description TODO
 * @Author wangjinfei
 * @Date 2023/11/30 12:03
 */
@SpringBootTest
public class XiGuaTest {
    @Autowired
    UserMapper userMapper;

    @Autowired
    private ApplicationContext context;

    @Autowired
    private MessageProducer messageProducer;

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

    @Test
    void test02(){
        User user = new User();
        user.setName("张三");

        Map<String,Object> map = new HashMap<>();
        map.put("fileName","测试");
        map.put("class",User.class);
        map.put("methodName","getUserByName");
        map.put("methodParameter",user);

        //获取各自的方法名称、class、方法参数
        String methodName = String.valueOf(map.get("methodName"));
        Class<?> classType = (Class<?>) map.get("class");
        Object methodParameter = map.get("methodParameter");

        List<? extends Object> dataList = new ArrayList<>();

        int iWrite = 0; //第几次写入
        int maxWrite = 100; //单次最大写入的数量

        ExcelWriter excelWriter = null;
        WriteSheet writeSheet = EasyExcel.writerSheet(0, "sheet").build();
        try{
//            //设置响应头
//            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//            response.setCharacterEncoding("utf-8");
//            response.setHeader("Content-disposition", "attachment;filename="+ "测试" + ".xlsx");
//            OutputStream outputStream = response.getOutputStream();

            File file = new File("C:\\Users\\Administrator\\Desktop\\测试.xlsx");

            //创建一个对应classType的 excelWriter
            excelWriter = EasyExcel.write(file, classType).build();
            //把methodParameter转成classType类型 （虽然返回的是Object，但实际上已经是classType了）
            methodParameter = JSONObject.parseObject(JSONObject.toJSONString(methodParameter), classType);

            //用反射获取pageStart、pageNum属性  （前提是classType里面要有这俩属性）
            Field pageStartField = classType.getDeclaredField("pageStart");
            Field pageNumField = classType.getDeclaredField("pageSize");
            pageStartField.setAccessible(true);
            pageNumField.setAccessible(true);

            //用反射获取mapper查询数据的方法
            Method method = UserMapper.class.getMethod(methodName, classType);

            //死循环 一直调用查询方法 追加写入excel
            while (true){
                //用反射给pageStart、pageNum赋值
                pageStartField.set(methodParameter, iWrite * maxWrite);
                pageNumField.set(methodParameter, maxWrite);
                //用反射调用mapper查询数据
                Object result = method.invoke(context.getBean(UserMapper.class), methodParameter);
                dataList = JSON.parseArray(JSON.toJSONString(result), classType);

                //追加写
                excelWriter.write(dataList, writeSheet);
                dataList.clear();
                iWrite ++;

                if(iWrite == 10){
                    break;
                }
            }
//            Method method = apiExportMapper.class.getMethod(methodName, aClass);
//            Object obj = JSON.parseObject(JSON.toJSONString(methodParameter), aClass);
//            Object invoke = method.invoke(context.getBean(apiExportMapper.class), obj);
//            objects = JSON.parseArray(JSON.toJSONString(invoke), aClass);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (excelWriter != null){
                excelWriter.finish();
            }
        }
    }

    @Test
    void test03(){
        messageProducer.sendMessage("123");
    }
}
