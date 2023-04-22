package ctgu.yao.usersbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import ctgu.yao.usersbackend.model.User;
import ctgu.yao.usersbackend.utils.Result;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author danhen
* @description 针对表【user】的数据库操作Service
* @createDate 2023-03-26 16:56:10
*/
public interface UserService extends IService<User> {
    /**
     * 用户注册
     * @param userAccount 账号
     * @param userPassword  密码
     * @param checkPassword 校验密码
     * @return -1/user.getId()
     */
    Result<Long> userRegister(String userAccount, String userPassword, String checkPassword, String planetCode);

    /**
     * 用户登录
     * @param userAccount 账号
     * @param userPassword 密码
     * @param request 用户信息（脱敏后）
     * @return 用户信息（脱敏后）
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 通过username查询用户信息
     * @param username 用户名
     * @return 用户集合
     */
    Result<List<User>> searchUsers(String username,HttpServletRequest request);

    /**
     * 删除用户（注意这里是逻辑删除）
     * @param id
     * @return
     */
    Result<Boolean> deleteUser(long id);
    /**
     * 用户脱敏
     * @param user
     * @return
     */
    User safeUser(User user);

    /**
     * 用户注销
     * @param request
     */
    Result<Integer> userLogout(HttpServletRequest request);

}
