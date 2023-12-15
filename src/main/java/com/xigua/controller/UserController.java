package com.xigua.controller;

import com.xigua.entity.User;
import com.xigua.mapper.UserMapper;
import com.xigua.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

/**
 * @ClassName UserController
 * @Description TODO
 * @Author wangjinfei
 * @Date 2023/11/9 15:20
 */
@AllArgsConstructor
@RequestMapping("user")
@RestController
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/insert")
    public void insert(){
        User user = new User();
        user.setName("西瓜");
        user.setAge(20);
        userService.save(user);
    }

    @PutMapping("/update")
    public void update(@RequestParam Integer id){
        User user = new User();
        user.setId(id);
        user.setName("西瓜");
        user.setAge(20);
        userService.updateById(user);
    }

    @GetMapping("/test")
    public String test(){
        return "123456";
    }

    @GetMapping("/test2")
    public void test2(){
        List<Map<String,Integer>> list = new ArrayList<>();
        Map<String,Integer> map1 = new HashMap<>();
        map1.put("weight",20);
        map1.put("count",0);
        list.add(map1);
        Map<String,Integer> map2 = new HashMap<>();
        map2.put("weight",30);
        map2.put("count",0);
        list.add(map2);
        Map<String,Integer> map3 = new HashMap<>();
        map3.put("weight",50);
        map3.put("count",0);
        list.add(map3);



        int total = 100;
        for (int i = 1; i <= total; i++) {
            List<Map<String, Object>> weightList = new ArrayList<>();
            for (Map<String, Integer> map : list) {
                Integer weight = map.get("weight");
                Integer count = map.get("count");
                Map<String,Object> weightMap = new HashMap<>();
                weightMap.put("weight",weight);
                if(count == 0){
                    weightMap.put("realWeight",BigDecimal.valueOf(weight));
                    weightList.add(weightMap);
                    continue;
                }
                BigDecimal proportion = BigDecimal.valueOf(((double)count / (i - 1)));
                if(proportion.compareTo(BigDecimal.ONE) == 0){
                    continue;
                }
                BigDecimal rabio = BigDecimal.valueOf(((double)weight / 100));
                if(rabio.compareTo(proportion) == -1){
                    continue;
                }
                BigDecimal realWeight = rabio.subtract(proportion);
                weightMap.put("realWeight",realWeight);
                weightList.add(weightMap);
            }
            Map<String, Object> max = weightList.stream().max(Comparator.comparing(map -> (BigDecimal) map.get("realWeight"))).get();
            weightList.clear();
            Map<String, Integer> map = list.stream().filter(s -> max.get("weight").equals(s.get("weight"))).findAny().get();
            map.put("count", map.get("count") + 1);
            System.out.println("第" + i + "次是" + map.get("weight"));
        }
    }

    @PostMapping("/insertBatch")
    public void insertBatch(){
        List<User> userList = new ArrayList<>();
        int count = 3;
        for (int i = 0; i < count; i++) {
            User user = new User();
            user.setName("张三" + i);
            user.setAge(20);
            userList.add(user);
        }
        userMapper.insertBatch(userList);
    }

}
