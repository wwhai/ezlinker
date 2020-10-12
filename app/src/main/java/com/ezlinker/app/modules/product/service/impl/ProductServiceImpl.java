package com.ezlinker.app.modules.product.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ezlinker.app.modules.product.mapper.ProductMapper;
import com.ezlinker.app.modules.product.model.Product;
import com.ezlinker.app.modules.product.service.IProductService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 产品（设备的抽象模板） 服务实现类
 * </p>
 *
 * @author wangwenhai
 * @since 2019-11-13
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

    @Resource
    ProductMapper productMapper;


    @Override
    public Product details(Long productId) {
        return productMapper.details(productId);
    }
}
