package com.ezlinker.app.modules.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ezlinker.app.modules.product.model.Product;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 产品（设备的抽象模板） Mapper 接口
 * </p>
 *
 * @author wangwenhai
 * @since 2019-11-13
 */
public interface ProductMapper extends BaseMapper<Product> {
    Product details(@Param("productId") Long productId);
}
