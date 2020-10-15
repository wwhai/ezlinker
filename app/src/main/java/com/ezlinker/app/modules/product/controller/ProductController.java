package com.ezlinker.app.modules.product.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ezlinker.app.common.exception.BadRequestException;
import com.ezlinker.app.common.exception.BizException;
import com.ezlinker.app.common.exception.XException;
import com.ezlinker.app.common.exchange.R;
import com.ezlinker.app.common.web.XController;
import com.ezlinker.app.modules.device.model.Device;
import com.ezlinker.app.modules.device.service.IDeviceService;
import com.ezlinker.app.modules.product.model.Product;
import com.ezlinker.app.modules.product.service.IProductService;
import com.ezlinker.app.modules.relation.model.RelationProductModule;
import com.ezlinker.app.modules.relation.service.IRelationProductModuleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * <p>
 * 产品（设备的抽象模板）
 * </p>
 *
 * @author wangwenhai
 * @since 2019-11-13
 */
@RestController
@RequestMapping("/products")
public class ProductController extends XController {

    @Resource
    IProductService iProductService;

    @Resource
    IDeviceService iDeviceService;

    @Resource
    IRelationProductModuleService iRelationProductModuleService;

    public ProductController(HttpServletRequest httpServletRequest) {
        super(httpServletRequest);
    }


    /**
     * 创建产品
     *
     * @param product 产品:必传
     * @return
     */

    @PostMapping
    protected R add(@RequestBody @Valid Product product) throws BizException {
        if (product.getFieldParams().size() > 20) {
            throw new BizException("The fields must less than 20!", "属性最多不能超过20个");
        }
        boolean ok = iProductService.save(product);
        return ok ? data(product) : fail();

    }

    /**
     * 删除产品
     *
     * @param ids 批量删除的ID数组:必传
     * @return
     */

    @DeleteMapping("/{ids}")
    public R delete(@PathVariable Integer[] ids) throws BizException {
        for (Integer id : ids) {
            // 查询是否有生产了设备
            int count = iDeviceService.count(new QueryWrapper<Device>().eq("product_id", id));
            if (count > 0) {
                throw new BizException("", "该产品下已经生产设备,不可删除");
            } else {
                iProductService.removeById(id);
            }
        }
        return success();
    }


    /**
     * 更新产品信息
     *
     * @param newProduct 产品:必传
     * @return
     * @throws XException
     */
    @PutMapping("/{id}")
    public R update(@PathVariable Long id, @RequestBody @Valid Product newProduct) throws XException {
        Product product = iProductService.getById(id);
        if (product == null) {
            throw new BadRequestException("Product not exists!", "产品不存在");
        }
        // 判断是否该产品下面已经有了设备，如果有设备，就不允许修改表结构
        long count = iDeviceService.count(new QueryWrapper<Device>().eq("product_id", id));

        if (count > 0) {
            if (newProduct.getFieldParams() != null) {
                throw new BadRequestException("Forbidden!", "该产品已经存在设备，不可修改数据定义！");

            }
        }
        newProduct.setId(id);
        boolean ok = iProductService.updateById(newProduct);
        return ok ? data(newProduct) : fail();

    }


    /**
     * 查看产品详情
     *
     * @param productId 产品ID:必传
     * @return
     */


    @GetMapping("/{productId}")
    public R get(@PathVariable Long productId) {
        Product data = iProductService.details(productId);
        return data(data);
    }

    /**
     * 查询
     *
     * @param projectId 所属项目ID
     * @param name      名称
     * @param current   页码
     * @param size      页长
     * @return
     */
    @GetMapping
    public R queryForPage(
            @RequestParam(required = false) Long projectId,
            @RequestParam(required = false, defaultValue = "1") Integer current,
            @RequestParam(required = false, defaultValue = "20") Integer size,
            @RequestParam(required = false) String name) {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        if (projectId != null) {
            queryWrapper.eq("project_id", projectId);
        }
        queryWrapper.like(name != null, "name", name);
        queryWrapper.orderByDesc("create_time");
        IPage<Product> projectPage = iProductService.page(new Page<>(current, size), queryWrapper);
        return data(projectPage);
    }


    @PutMapping("/module")
    public R updateModule(@RequestBody RelationProductModule newRelation) throws BizException {

        RelationProductModule relation = iRelationProductModuleService.getById(newRelation.getId());
        if (relation == null) {
            throw new BizException("Module not exists!", "module不存在");
        }

        boolean ok = iRelationProductModuleService.updateById(newRelation);
        return ok ? data(newRelation) : fail();
    }

    /**
     * 删除模块关系
     *
     * @param id
     * @return
     */
    @DeleteMapping("/module/{id}")
    public R deleteModule(@PathVariable Long id) {
        boolean ok = iRelationProductModuleService.removeById(id);
        return ok ? success() : fail();
    }
}

