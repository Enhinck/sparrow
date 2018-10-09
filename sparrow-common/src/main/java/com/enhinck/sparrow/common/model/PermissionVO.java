package com.enhinck.sparrow.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
@ApiModel("PermissionVO")
public class PermissionVO extends BaseVO {
	private static final long serialVersionUID = 3429620002969037955L;

	@ApiModelProperty(value = "菜单编码", required = true)
	private String menuCode;
	@ApiModelProperty(value = "菜单名称", required = true)
	private String menuName;
	@ApiModelProperty(value = "权限编码", required = false)
	private String permissionCode;
	@ApiModelProperty(value = "权限名称", required = false)
	private String permissionName;
	@ApiModelProperty(value = "分页 当前页", required = false)
	private Integer pageNum;
	@ApiModelProperty(value = "分页 每页几条", required = false)
	private Integer numPerPage;

}
