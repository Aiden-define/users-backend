package ctgu.yao.usersbackend.service.impl;

import ctgu.yao.usersbackend.contant.UserContant;
import org.junit.Test;
import org.springframework.util.DigestUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestMatch {
    public static Dog dog;

    public TestMatch() {
        dog = new Dog(1,"lisi");
    }
    @Test
    public void test() {
//        String vaildPattern = "^[a-zA-Z][a-zA-Z0-9_]{4,15}$";
//        String userAccount = "yaojiahua19";
//        Matcher matcher = Pattern.compile(vaildPattern).matcher(userAccount);
//        System.out.println(matcher.matches());
//        String s = DigestUtils.md5DigestAsHex(("YJH" + "sadad111").getBytes());
//        System.out.println(s);
        String userPassword = "yjh360681...";
        String hex = DigestUtils.md5DigestAsHex((UserContant.MOHU + userPassword).getBytes());
        System.out.println(hex);
        dog.setId(2);
        System.out.println(dog.getId());

    }
}
class Dog{
    private int id;
    private String name;

    public Dog(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
