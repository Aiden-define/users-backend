package ctgu.yao.usersbackend.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 定义前端统一返回请求
 */
@Data
public class UserRegisterRequest  implements Serializable {
    private static final long serialVersionUID = 3191241716373120793L;

    private  String userAccount;

    private String userPassword;

    private String checkPassword;

    private String planetCode;

}
