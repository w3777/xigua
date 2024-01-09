package com.xigua;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

/**
 * @ClassName ShuaTiTest
 * @Description TODO
 * @Author wangjinfei
 * @Date 2024/1/8 11:27
 */
public class ShuaTiTest {
    //数组中心位置
    @Test
    void test01(){
//        List<Integer> list = new ArrayList(Arrays.asList(2,5,3,6,5,6));
        List<Integer> list = new ArrayList(Arrays.asList(1,4,0,2,2));


        for (int i = 0; i < list.size(); i++) {
            Integer left = 1;
            Integer right = 1;

            if(i != 0){
                List<Integer> leftList = list.subList(0, i);
                for (Integer integer : leftList) {
                    left = left * integer;
                }
            }


            List<Integer> rightList = list.subList(i + 1, list.size());
            for (Integer integer : rightList) {
                right = right * integer;
            }

            if(left.compareTo(right) == 0){
                System.out.println(i);
            }

        }
    }

    //url拼接
    @Test
    void test02(){
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        String[] array = str.split(",");

        if(array.length == 0){
            System.out.println("/");
        }

        String resultStr = "";
        for (String s : array) {
            char c = s.charAt(s.length() - 1);
            if(c == '/'){
                s = s.substring(0, s.length() - 1);
            }

            char c1 = s.charAt(0);
            if(c1 != '/'){
                s = '/' + s;
            }
            resultStr = resultStr + s;
        }
        System.out.println(resultStr);
    }

    //字母索引
    @Test
    void test03(){
        Scanner scanner = new Scanner(System.in);
        String str = scanner.next();
        Integer n = scanner.nextInt();

        char kthLetter = str.chars()
                .sorted()
                .skip(n - 1)
                .mapToObj(c -> (char) c)
                .findFirst()
                .orElse('\0');
        System.out.println(str.indexOf(kthLetter));
    }

    @Test
    void test04(){
        Scanner scanner = new Scanner(System.in);
        int n = Integer.valueOf(scanner.nextLine());

        Map<Integer,Integer> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            String s = scanner.nextLine();
            String[] array = s.split(" ");
            Integer index = Integer.valueOf(array[0]);
            Integer value = Integer.valueOf(array[1]);

            map.merge(index,value,Integer::sum);
        }

        map.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(entry -> {
            System.out.println(entry.getKey() + " " + entry.getValue());
        });
    }

    @Test
    void test05(){
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        String[] split = s.split("");
        StringBuilder str = new StringBuilder();
        for (int i = split.length - 1; i >= 0; i--) {
            str.append(split[i]);
        }
        System.out.println(str);
    }

    @Test
    void test06(){
        Scanner scanner = new Scanner(System.in);
        Integer n = Integer.valueOf(scanner.nextLine());

        List<String> famaList = new ArrayList<>();
        List<String> shuliangList = new ArrayList<>();

        String s = scanner.nextLine();
        famaList = Arrays.asList(s.split(" "));

        String s2 = scanner.nextLine();
        shuliangList = Arrays.asList(s2.split(" "));

        Set<Integer> num = new HashSet<>();

        for (String fama : famaList) {
            int sum = 0;
            for (String shuliang : shuliangList) {
                sum = Integer.valueOf(fama) * Integer.valueOf(shuliang) + sum;
                num.add(sum);
            }
        }
        System.out.println(num.size());
    }

    @Test
    void test07(){
        Scanner scanner = new Scanner(System.in);
        int num = scanner.nextInt();

        int count = 0;

        for (int i = 1; i <= num; i++) {
            if(String.valueOf(i).contains("7") || (i % 7 == 0)){
                count++;
            }
        }
        System.out.println(count);
    }

    @Test
    void test08(){
        Scanner scanner = new Scanner(System.in);
        StringBuilder stringBuilder = new StringBuilder();
        List<String> list = Arrays.asList(scanner.nextLine().split(" "));
        for (int i = list.size() - 1; i >= 0; i--) {
            stringBuilder.append(list.get(i));

            if (i != 0){
                stringBuilder.append(" ");
            }
        }
        System.out.println(stringBuilder);
    }

    @Test
    void test09(){
        Scanner scanner = new Scanner(System.in);
        Integer n = Integer.valueOf(scanner.nextLine());

        List<Integer> list = new ArrayList<>();
        for (Integer i = 0; i < n; i++) {
            list.add(Integer.valueOf(scanner.nextLine()));
        }
        List<Integer> collect = list.stream().distinct().sorted().collect(Collectors.toList());
        for (Integer i : collect) {
            System.out.println(i);
        }
    }

    @Test
    void test10(){
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();

        Map<String,Integer> map = new LinkedHashMap<>();
        String[] array = s.split("");
        for (String string : array) {
            if(map.containsKey(string)){
                map.merge(string,map.get(string),Integer::sum);
                continue;
            }
            map.put(string,1);
        }
        Integer min = map.values().stream().min(Comparator.comparing(x -> x)).get();

        StringBuilder str = new StringBuilder();
        for (String string : array) {
            if(map.get(string) != min){
                str.append(string);
            }
        }
        System.out.println(str);
    }

    @Test
    void test11(){
        Scanner scanner = new Scanner(System.in);
        List<String> list = Arrays.asList(scanner.nextLine().split(" "));

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = list.size() - 1; i >= 0; i--) {
            String[] array = list.get(i).split("");
            for (int j = array.length - 1; j >= 0; j--) {
                stringBuilder.append(array[j]);
            }

            if (i != 0){
                stringBuilder.append(" ");
            }
        }

        System.out.println(stringBuilder);
    }

    @Test
    void test12(){
        Scanner scanner = new Scanner(System.in);
        String[] array = scanner.nextLine().split(" ");

        int num1 = Integer.valueOf(array[0]);
        int num2 = Integer.valueOf(array[1]);

        int min = Math.min(num1, num2);
        int max = 1;
        for (int i = 2; i <= min; i++) {
            if(num1 % i == 0 && num2 % i == 0){
                max = i;
            }
        }
        System.out.println(num1 * num2 / max);
    }

    @Test
    void test13(){
        Scanner scanner = new Scanner(System.in);
        String n = scanner.nextLine();
        List<String> list = Arrays.asList(scanner.nextLine().split(" "));
        String s = scanner.nextLine();
        List<Integer> collect = new ArrayList<>();
        if("0".equals(s)){
            collect = list.stream().map(m -> Integer.valueOf(m)).sorted().collect(Collectors.toList());
            System.out.println(collect);
        }else{
            collect = list.stream().map(m -> Integer.valueOf(m)).sorted(Comparator.reverseOrder()).collect(Collectors.toList());
            System.out.println(collect);
        }
        for (Integer i : collect) {
            System.out.println(i + " ");
        }

    }
}
