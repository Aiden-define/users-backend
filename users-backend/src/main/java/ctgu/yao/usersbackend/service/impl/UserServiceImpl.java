package ctgu.yao.usersbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ctgu.yao.usersbackend.contant.UserContant;
import ctgu.yao.usersbackend.exception.BusinessException;
import ctgu.yao.usersbackend.mapper.UserMapper;
import ctgu.yao.usersbackend.model.User;
import ctgu.yao.usersbackend.service.UserService;
import ctgu.yao.usersbackend.utils.ErrorCode;
import ctgu.yao.usersbackend.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
* @author danhen
* @description 针对表【user】的数据库操作Service实现
* @createDate 2023-03-26 16:56:10
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {


    @Resource
    private UserMapper userMapper;
    /**
     * 用户注册
     * @param userAccount 用户账户
     * @param userPassword 用户密码
     * @param checkPassword 密码校验
     * @return 用户id
     */
    @Override
    public Result<Long> userRegister(String userAccount, String userPassword, String checkPassword, String planetCode) {
        //1.校验
        //apache.commons包内的方法，该方法用于判断多个参数是否为空
        if(StringUtils.isAnyBlank(userAccount,userPassword,checkPassword,planetCode)){
            //return Result.fail(ErrorCode.PARAMS_ERROR);
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数为空");
        }
        if(userAccount.length() < 6 || userPassword.length()<11 || planetCode.length() > 5){
            //return Result.fail("账号长度不支持");
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号长度过短或星球编号过长");
        }
        //账号创建条件(字母开头，允许6-16字节，允许字母数字下划线)
        String vaildPattern = "^[a-zA-Z][a-zA-Z0-9_]{5,15}$";
        Matcher matcher = Pattern.compile(vaildPattern).matcher(userPassword);
        if(!matcher.matches()){
            //return Result.fail("密码支持字母数字");
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码支持字母数字");
        }
        //密码和校验密码相同
        if(!userPassword.equals(checkPassword)){
            //return Result.fail("密码和校验密码不符");
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码和校验密码不符");
        }
        //账户不能重复,这里查询了数据库，我们放最后，前面条件不满足就不走到这一步，避免性能浪费
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount",userAccount);
        long count = userMapper.selectCount(queryWrapper);
        if(count>0){
            //return Result.fail("账户名重复");
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账户名重复");
        }
        QueryWrapper<User> queryWrapperA = new QueryWrapper<>();
        queryWrapperA.eq("planetCode",planetCode);
        Long count1 = userMapper.selectCount(queryWrapperA);
        if(count1 > 0){
            //return Result.fail("星球编号重复");
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"星球编号重复");
        }
        //2.加密
        String miPassword = DigestUtils.md5DigestAsHex((UserContant.MOHU + userPassword).getBytes());
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(miPassword);
        user.setPlanetCode(planetCode);
        int save = userMapper.insert(user);
        if(save==0){
            //return Result.fail("插入失败");
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"插入失败");
        }
        //return user.getId();
        Long id = user.getId();
        return Result.success(id);
    }

    /**
     * 用户登录
     * @param userAccount 账号
     * @param userPassword 密码
     * @return
     */
    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        //是否为空
        if(StringUtils.isAnyBlank(userAccount,userPassword)){
            //return null;
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"不能为空");
        }
        //用户账号是否存在
        String encryptPassword = DigestUtils.md5DigestAsHex((UserContant.MOHU + userPassword).getBytes());
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount",userAccount);
        queryWrapper.eq("userPassword",encryptPassword);
        /*if(userMapper.selectCount(queryWrapper)==0){
            //return null;
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账户不存在");
        }
        //密码是否正确*/
        User user = userMapper.selectOne(queryWrapper);
        /*if(user==null||!user.getUserPassword().equals(DigestUtils.md5DigestAsHex((UserContant.MOHU+userPassword).getBytes()))){
            log.info("user login failed, userAccount cannot match userPassword");
            return null;
            //throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码错误");
        }*/
        if(user==null){
            log.info("user login failed, userAccount cannot match userPassword");
            return null;
            //throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码错误");
        }

        //用户脱敏
        User handleUser = safeUser(user);
        //用户登录成功，存入session
        request.getSession().setAttribute(UserContant.SUER_LOGIN_STATE,handleUser);

        log.info("session:{}",request.getSession().getAttribute(UserContant.SUER_LOGIN_STATE));

        return handleUser;
    }

    /**
     * 查询用户
     * @param username 用户名
     * @param request
     * @return
     */

    @Override
    public Result<List<User>> searchUsers(String username,HttpServletRequest request) {
        List<User> users = null ;
        if(username == null){
            users = userMapper.selectList(null);
        }else {
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.like("username", username);
            users = userMapper.selectList(queryWrapper);
        }
        ArrayList<User> handleUser = new ArrayList<>();
        for (User user : users) {
            handleUser.add(safeUser(user));
        }
        return Result.success(handleUser);
    }

    @Override
    public Result<Boolean> deleteUser(long id) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",id);
        int re = userMapper.deleteById(id);
        return Result.success(re != 0);
    }

    /**
     * 用户脱敏
     * @param user
     * @return
     */
    @Override
    public User safeUser(User user){
        if(user == null){
            return null;
        }
        User handleUser = new User();
        handleUser.setId(user.getId());
        handleUser.setUsername(user.getUsername());
        handleUser.setAvatarUrl(user.getAvatarUrl());
        handleUser.setGender(user.getGender());
        handleUser.setUserAccount(user.getUserAccount());
        handleUser.setPhone(user.getPhone());
        handleUser.setUserRole(user.getUserRole());
        handleUser.setEmail(user.getEmail());
        handleUser.setUserStatus(user.getUserStatus());
        handleUser.setCreateTime(user.getCreateTime());
        handleUser.setPlanetCode(user.getPlanetCode());
        return handleUser;
    }

    /**
     * 用户注销
     * @param request
     * @return
     */
    @Override
    public Result<Integer> userLogout(HttpServletRequest request) {
        //登出，移除session中存储的内容
        request.getSession().removeAttribute(UserContant.SUER_LOGIN_STATE);
        return Result.success(1);
    }
}




