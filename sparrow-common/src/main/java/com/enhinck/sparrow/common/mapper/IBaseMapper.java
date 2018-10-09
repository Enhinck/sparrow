package com.enhinck.sparrow.common.mapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 
 * @author hueb
 *
 */
public interface IBaseMapper<T> extends Mapper<T> , MySqlMapper<T> {

}
