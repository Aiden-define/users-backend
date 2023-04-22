package ctgu.yao.usersbackend.service.impl;

import ctgu.yao.usersbackend.model.User;
import ctgu.yao.usersbackend.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@SpringBootTest
class UserServiceImplTest {
    /*@Resource
    private UserService userService;
    @Test
    public void testUserRegister(){
        String userAccount = "yaojiahua19";
        String userPassword = "360681yjh";
        String checkPassword = "360681yjh";
        String planetCode = "5003";
        //三者中存在为空
        long result = userService.userRegister(userAccount, userPassword, checkPassword,planetCode);
        Assertions.assertEquals(-1,result);
        //密码和校验密码不同
        checkPassword = "11111111";
        result = userService.userRegister(userAccount, userPassword, checkPassword,planetCode);
        Assertions.assertEquals(-1,result);
        //账号长度不达标
        userAccount = "1234";
        result = userService.userRegister(userAccount, userPassword, checkPassword,planetCode);
        Assertions.assertEquals(-1,result);
        userPassword = "1827012245";
        checkPassword = "18270122455";
        result = userService.userRegister(userAccount, userPassword, checkPassword,planetCode);
        Assertions.assertEquals(-1,result);
        //账号包含了特殊字符
        userAccount = "yao jia hua";
        result = userService.userRegister(userAccount, userPassword, checkPassword,planetCode);
        Assertions.assertEquals(-1,result);
        //数据库存在账户名
        userAccount = "asda2000";
        userPassword = "18270122455";
        checkPassword = "18270122455";
        result = userService.userRegister(userAccount, userPassword, checkPassword,planetCode);
        Assertions.assertEquals(10,result);
        //条件正常
        userAccount = "besuccess";
        result = userService.userRegister(userAccount, userPassword, checkPassword,planetCode);
        Assertions.assertTrue(result>0);
    }*/
  /*  @Test
    public void testUserLogin(){
        int cmp = -1;
        //账号或密码为空
        String userAccount = "";
        String userPassword = "123456";
        User result = userService.userLogin(userAccount, userPassword, null);
        Assertions.assertNull(result);
        userAccount = "acc1209";
        userPassword = "";
        result = userService.userLogin(userAccount, userPassword, null);
        Assertions.assertNull(result);
        //账号不存在
        userAccount = "acc1209";
        userPassword = "asda1111";
        result = userService.userLogin(userAccount, userPassword, null);
        Assertions.assertNull(result);
        //密码错误
        userAccount = "asda2000";
        userPassword = "asda111111";
        result = userService.userLogin(userAccount, userPassword, null);
        Assertions.assertNull(result);
        //登陆成功
        userAccount = "asda2000";
        userPassword = "sadad111";
        result = userService.userLogin(userAccount, userPassword, null);
        if(result!=null){
            cmp = 1;
        }
        Assertions.assertEquals(1,cmp);

    }*/

}
