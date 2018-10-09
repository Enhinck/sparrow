package com.enhinck.sparrow.common.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode(callSuper=true)
public class ProjectVO extends BaseVO {

    private static final long serialVersionUID = 4242260762529979484L;
    @ApiModelProperty(value = "行政区划代码",required = false)
    private String cityCode;
    @ApiModelProperty(value = "小区名称（项目名称）",required = false)
    private String projectName;
    @ApiModelProperty(value = "组团（可有可无）",required = false)
    private String group;
    @ApiModelProperty(value = "期数（一期、二期可有可无）",required = false)
    private String period;
    @ApiModelProperty(value = "项目名称",required = true)
    private String name;
    @ApiModelProperty(value = "地理坐标轮廓合集",required = false)
	private String coordinates;
    @ApiModelProperty(value = "地理坐标中心点",required = false)
   	private String coordinateCenter;
    @ApiModelProperty(value = "所在地",required = false)
   	private String address;
    @ApiModelProperty(value = "创建人",required = false)
    private Long createUser;
    @ApiModelProperty(value = "修改人",required = false)
    private Long updateUser;
    @ApiModelProperty(value = "状态",required = false)
    private Integer status;

    @ApiModelProperty(value = "分页 当前页",required = false)
    private Integer pageNum;
    @ApiModelProperty(value = "分页 每页几条",required = false)
    private Integer numPerPage;


    @ApiModelProperty(value = "地图类型 1：地图；2：平面图",required = false)
    private Integer mapType;

    @ApiModelProperty(value = "地图图片地址（平面图）",required = false)
    private String[] mapPictures;

}