package cn.edu.sdtbu.service;

import cn.edu.sdtbu.model.entity.LoginLogEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-04-11 10:43
 */
public interface LoginLogService {
    /**
     * record login ip
     * @param userId user
     * @param ip login ip
     */
    void login(Long userId, String ip);

    /**
     * select login info by pageable
     * @param userId    user
     * @param pageable  page limit
     * @return result
     */
    Page<LoginLogEntity> select(Long userId, Pageable pageable);
}