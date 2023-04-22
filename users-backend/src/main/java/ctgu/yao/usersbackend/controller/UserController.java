package ctgu.yao.usersbackend.controller;
import ctgu.yao.usersbackend.contant.UserContant;
import ctgu.yao.usersbackend.exception.BusinessException;
import ctgu.yao.usersbackend.model.User;
import ctgu.yao.usersbackend.model.request.UserLoginRequest;
import ctgu.yao.usersbackend.model.request.UserRegisterRequest;
import ctgu.yao.usersbackend.service.UserService;
import ctgu.yao.usersbackend.utils.ErrorCode;
import ctgu.yao.usersbackend.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static ctgu.yao.usersbackend.contant.UserContant.SUER_LOGIN_STATE;

/**
 * 用户接口
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/register")
    public Result<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String planetCode = userRegisterRequest.getPlanetCode();
        return userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
    }

    @PostMapping("/login")
    public Result<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        User handleUser = userService.userLogin(userAccount, userPassword, request);
        return Result.success(handleUser);
    }

    @GetMapping("/current")
    public Result<User> getCurrent(HttpServletRequest request) {
        Object user = request.getSession().getAttribute(SUER_LOGIN_STATE);
        User currentUser = (User) user;
        log.info("user:{}",user);
        if (currentUser == null) {
            log.info("Now do not have user");
            throw new BusinessException(ErrorCode.NOT_LOGIN);
            //return null;
        }
        User byId = userService.getById(currentUser.getId());
        //return userService.safeUser(byId);
        return Result.success(userService.safeUser(byId));
    }

    @GetMapping("/search")
    public Result<List<User>> searchByUsername(String username, HttpServletRequest request) {
        //只有管理员有查权限
        if (userRole(request)) {
            //return Result.fail("权限不足");
            throw new BusinessException(ErrorCode.NO_AUTH,"权限不足");
        }
        return userService.searchUsers(username, request);
    }

    @PostMapping("/{id}")
    public Result<Boolean> deleteById(@PathVariable long id, HttpServletRequest request) {
        if (userRole(request) || id <= 0) {
            //return Result.fail("权限不足或id不存在");
            throw new BusinessException(ErrorCode.NO_AUTH,"权限不足或id不存在");
        }
        //如果你开启了mybatis-plus的逻辑删除，调用删除，mybatis-plus会自动帮你转化为逻辑删除
        return userService.deleteUser(id);
    }


    /**
     * 权限判断
     *
     * @param request
     * @return
     */
    public boolean userRole(HttpServletRequest request) {
        Object attribute = request.getSession().getAttribute(SUER_LOGIN_STATE);
        User user = (User) attribute;
        if (user == null || user.getUserRole() != UserContant.ADMIN_ROLE) {
            return true;
        }
        return false;
    }

    @PostMapping("/loginout")
    public Result<Integer> userLogin(HttpServletRequest request) {
        if (request.getSession() == null)
            //return null;
            throw new BusinessException(ErrorCode.NOT_LOGIN,"账号未登录");
        return userService.userLogout(request);
    }
}
