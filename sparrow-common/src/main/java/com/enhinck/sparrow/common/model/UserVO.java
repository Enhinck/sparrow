package com.enhinck.sparrow.common.model;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 
 * @author hueb
 *
 */
@Data
@EqualsAndHashCode(callSuper=true)
@ApiModel("UserVO")
public class UserVO extends BaseVO {

	private static final long serialVersionUID = 1364758034808187361L;
	@ApiModelProperty(value = "登录用户名", required = true)
	private String username;
	@ApiModelProperty(value = "随机验证码", required = false)
	private String code;
	@ApiModelProperty(value = "密码", required = true)
	private String password;
	@ApiModelProperty(value = "手机号", required = false)
	private String mobile;
	@ApiModelProperty(hidden = true)
	private String xLongToken;
	@ApiModelProperty(value = "状态", required = false)
	private Integer status;
	@ApiModelProperty(value = "分页 当前页", required = false)
	private Integer pageNum;
	@ApiModelProperty(value = "分页 每页几条", required = false)
	private Integer numPerPage;
	@ApiModelProperty(value = "角色", required = false)
	private List<RoleVO> roleVOS;
	@ApiModelProperty(value = "项目", required = false)
	private List<ProjectVO> projectVOs;




}
