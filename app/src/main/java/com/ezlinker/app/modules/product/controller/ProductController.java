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
import com.ezlinker.app.modules.module.model.Module;
import com.ezlinker.app.modules.module.service.IModuleService;
import com.ezlinker.app.modules.product.model.Product;
import com.ezlinker.app.modules.product.service.IProductService;
import com.ezlinker.app.modules.relation.model.RelationProductModule;
import com.ezlinker.app.modules.relation.model.RelationProductTag;
import com.ezlinker.app.modules.relation.service.IRelationProductModuleService;
import com.ezlinker.app.modules.relation.service.IRelationProductTagService;
import com.ezlinker.app.modules.tag.model.Tag;
import com.ezlinker.app.modules.tag.service.ITagService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

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
    IModuleService iModuleService;

    @Resource
    IRelationProductModuleService iRelationProductModuleService;

    public ProductController(HttpServletRequest httpServletRequest) {
        super(httpServletRequest);
    }

    @Resource
    ITagService iTagService;
    @Resource
    IRelationProductTagService iRelationProductTagService;

    /**
     * 创建产品
     *
     * @param product 产品:必传
     * @return
     */

    @PostMapping
    protected R add(@RequestBody @Valid Product product) throws BadRequestException {
        if (product.getParameters() == null) {
            throw new BadRequestException("Parameters can't null!", "Parameters参数不可空");

        }

        boolean ok = iProductService.save(product);
        if (ok) {
            if (product.getTags() != null) {
                for (String tag : product.getTags()) {
                    Tag t = new Tag();
                    t.setName(tag).setLinkId(product.getId());
                    iTagService.save(t);
                    RelationProductTag productTag = new RelationProductTag();
                    productTag.setProductId(product.getId()).setTagId(t.getId());
                    iRelationProductTagService.save(productTag);
                }
            }

        }
        return ok ? data(product) : fail();

    }

    /**
     * 删除产品
     *
     * @param ids 批量删除的ID数组:必传
     * @return
     */

    @DeleteMapping("/{ids}")
    public R delete(@PathVariable Integer[] ids) {
        boolean ok = iProductService.removeByIds(Arrays.asList(ids));
        return ok ? success() : fail();
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
            if (newProduct.getParameters() != null) {
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
     * @param id 产品ID:必传
     * @return
     */


    @GetMapping("/{id}")
    public R get(@PathVariable Long id) throws XException {
        Product product = iProductService.getById(id);
        if (product == null) {
            throw new BizException("Product not exists!", "产品不存在");
        }
        List<Tag> tagList = iTagService.list(new QueryWrapper<Tag>().eq("link_id", id));
        Set<String> tags = new HashSet<>();

        for (Tag tag : tagList) {
            tags.add(tag.getName());
        }
        product.setTags(tags);
        return data(product);
    }

    /**
     * 查询
     *
     * @param projectId 所属项目ID
     * @param name      名称
     * @param type      类型
     * @param current   页码
     * @param size      页长
     * @return
     */
    @GetMapping
    public R queryForPage(
            @RequestParam Long projectId,
            @RequestParam Integer current,
            @RequestParam Integer size,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer type) {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("project_id", projectId);
        queryWrapper.eq(type != null, "type", type);
        queryWrapper.like(name != null, "name", name);

        queryWrapper.orderByDesc("create_time");
        IPage<Product> projectPage = iProductService.page(new Page<>(current, size), queryWrapper);

        for (Product product : projectPage.getRecords()) {
            addTags(product);

        }

        return data(projectPage);
    }


    private void addTags(Product product) {
        List<Tag> tagList = iTagService.list(new QueryWrapper<Tag>().eq("link_id", product.getId()));
        Set<String> tags = new HashSet<>();
        for (Tag tag : tagList) {
            tags.add(tag.getName());
        }
        product.setTags(tags);
    }

    /**
     * 获取模块信息
     *
     * @param productId
     * @param moduleId
     * @return
     * @throws XException
     */
    @GetMapping("/module/get")
    public R getModule(@RequestParam Long productId, @RequestParam Long moduleId) throws XException {


        RelationProductModule relation = iRelationProductModuleService
                .getOne(new QueryWrapper<RelationProductModule>().eq("product_id", productId).eq("module_id", moduleId));
        if (relation == null) {
            throw new BizException("Product or module not exists!", "产品或模块不存在");
        }

        Module module = iModuleService.getById(moduleId);
        return data(module);
    }

    @GetMapping("/module")
    public R queryModuleForPage(
            @RequestParam Long productId,
            @RequestParam Integer current,
            @RequestParam Integer size) {

        //获取product 关联的 module
        QueryWrapper<RelationProductModule> relationQuery = new QueryWrapper<>();
        relationQuery.eq("product_id", productId);
        List<RelationProductModule> relationList = iRelationProductModuleService
                .page(new Page<>(current, size), relationQuery).getRecords();

        //获取module_id
        List<Long> ids = new ArrayList<>(relationList.size());
        for (RelationProductModule rel : relationList) {
            ids.add(rel.getModuleId());
        }

        //通过module_id_list 获取module_list
        List<Module> moduleList = iModuleService.list(new QueryWrapper<Module>().in("id", ids));

        return data(moduleList);
    }

    /**
     * 为产品添加模块
     *
     * @param relation
     * @return
     */
    @PostMapping("/module")
    public R addModule(@RequestBody RelationProductModule relation) throws BizException {
        Long productId = relation.getProductId();
        Product product = iProductService.getById(productId);
        if (product == null) {
            throw new BizException("Product not exists!", "产品不存在");
        }

        Long moduleId = relation.getModuleId();
        Module module = iModuleService.getById(moduleId);
        if (module == null) {
            throw new BizException("Module not exists!", "模块不存在");
        }


        boolean ok = iRelationProductModuleService.save(relation);

        return ok ? data(relation) : fail();
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

