package cn.edu.sdtbu.repository;

import cn.edu.sdtbu.model.entity.solution.SolutionEntity;
import cn.edu.sdtbu.repository.base.BaseRepository;

import java.util.List;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-04-21 15:40
 */
public interface SolutionRepository extends BaseRepository<SolutionEntity, Long> {
    List<SolutionEntity> findAllByOwnerId(Long ownerId);
}
