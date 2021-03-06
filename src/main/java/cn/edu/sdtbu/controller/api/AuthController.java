package cn.edu.sdtbu.controller.api;

import cn.edu.sdtbu.exception.TeapotException;
import cn.edu.sdtbu.model.entity.UserEntity;
import cn.edu.sdtbu.model.param.LoginParam;
import cn.edu.sdtbu.model.properties.Const;
import cn.edu.sdtbu.model.vo.UserLoginInfo;
import cn.edu.sdtbu.service.UserService;
import cn.edu.sdtbu.util.RequestIpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * login | logout | reset password
 * @author bestsort
 * @version 1.0
 * @date 2020-04-12 07:18
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/auth")
public class AuthController {
    @Resource
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity<UserLoginInfo> login(@RequestBody LoginParam loginParam,
                                               @ApiIgnore HttpServletRequest request,
                                               @ApiIgnore HttpServletResponse response) {
        UserEntity userEntity = userService.login(loginParam.getIdentify(), loginParam.getPassword(), RequestIpUtil.getClientIp(request));
        log.debug("{} is login", userEntity);
        request.getSession().setAttribute(Const.USER_SESSION_INFO, userEntity);
        if (loginParam.getRemember()) {
            Cookie rememberTokenCookie = new Cookie(Const.REMEMBER_TOKEN,
                userService.generateRememberToken(userEntity, RequestIpUtil.getClientIp(request)));
            rememberTokenCookie.setPath("/");
            response.addCookie(rememberTokenCookie);
        }
        return ResponseEntity.ok().body(UserLoginInfo.fetchByUserEntity(userEntity));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@ApiIgnore HttpSession session,
                                             @ApiIgnore HttpServletResponse response) {
        UserEntity user = (UserEntity) session.getAttribute(Const.USER_SESSION_INFO);
        if (user == null) {
            throw new TeapotException();
        }

        log.debug("{} is logout", user);
        session.removeAttribute(Const.USER_SESSION_INFO);
        response.addCookie(Const.EMPTY_REMEMBER_TOKEN);
        return ResponseEntity.ok().build();
    }
}
