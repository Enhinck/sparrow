package com.enhinck.sparrow.common.model;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
@ApiModel("RoleVO")
public class RoleVO extends BaseVO {
	private static final long serialVersionUID = 1028950938403161463L;
	@ApiModelProperty(value = "角色编码", required = true)
	private String code;
	@ApiModelProperty(value = "角色名称", required = true)
	private String name;
	@ApiModelProperty(value = "状态", required = false)
	private Integer status;
	@ApiModelProperty(value = "角色备注", required = false)
	private String remark;
	@ApiModelProperty(value = "分页 当前页", required = false)
	private Integer pageNum;
	@ApiModelProperty(value = "分页 每页几条", required = false)
	private Integer numPerPage;
	@ApiModelProperty(value = "权限列表", required = false)
	private List<PermissionVO> permissionVOS;

}
