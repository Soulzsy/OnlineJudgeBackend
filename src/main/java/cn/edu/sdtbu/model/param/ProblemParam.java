package cn.edu.sdtbu.model.param;

import cn.edu.sdtbu.aop.annotation.NullOrNotBlank;
import cn.edu.sdtbu.model.entity.problem.ProblemDescEntity;
import cn.edu.sdtbu.model.entity.problem.ProblemEntity;
import cn.edu.sdtbu.model.enums.ProblemType;
import cn.edu.sdtbu.util.SpringUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-04-20 09:02
 */
@Data
@ApiModel
public class ProblemParam {
    @NotNull
    String title;
    @NullOrNotBlank
    String source;
    @NotNull
    String description;
    @NotNull
    String input;
    @NotNull
    String output;
    @NotNull
    String sample;
    @NullOrNotBlank
    String hint;

    //TODO max or min value
    @ApiModelProperty(notes = "micro seconds")
    Integer timeLimit = 1000;
    //TODO max or min value
    @ApiModelProperty(notes = "kbytes")
    Integer memoryLimit = 1 << 10;
    Boolean specialJudge = false;
    ProblemType type = ProblemType.NORMAL;
    Boolean hide = false;
    public ProblemEntity transformToEntity() {
        ProblemEntity entity = new ProblemEntity();
        SpringUtil.cloneWithoutNullVal(this, entity);
        return entity;
    }
    public ProblemDescEntity transFormToDescEntity() {
        ProblemDescEntity entity = new ProblemDescEntity();
        SpringUtil.cloneWithoutNullVal(this, entity);
        return entity;
    }
}