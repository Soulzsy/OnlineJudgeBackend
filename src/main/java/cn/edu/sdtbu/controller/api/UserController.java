package cn.edu.sdtbu.controller.api;

import cn.edu.sdtbu.model.param.UserParam;
import cn.edu.sdtbu.model.vo.UserCenterVO;
import cn.edu.sdtbu.service.ProblemService;
import cn.edu.sdtbu.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * register | update | list login log
 * @author bestsort
 * @version 1.0
 * @date 2020-04-07 16:06
 */

@Slf4j
@RestController
@RequestMapping(value = "/api/user", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UserController {
    @Resource
    UserService userService;
    @Resource
    ProblemService problemService;
    @PutMapping
    public ResponseEntity<Void> register(@RequestBody @Validated(UserParam.Resister.class) UserParam registerAo) {
        log.debug("registered: {}", registerAo.toString());
        userService.addUser(registerAo);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/center")
    public ResponseEntity<UserCenterVO> userCenter(Long userId) {
        return ResponseEntity.ok(
            userService.generatorUserCenterVO(
                problemService.fetchUserCenterProblem(userId),
                userId));
    }
}
