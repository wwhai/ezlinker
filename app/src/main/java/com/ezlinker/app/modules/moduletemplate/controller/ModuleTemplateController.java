package com.ezlinker.app.modules.moduletemplate.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ezlinker.app.common.exception.BadRequestException;
import com.ezlinker.app.common.exception.BizException;
import com.ezlinker.app.common.exception.XException;
import com.ezlinker.app.common.exchange.R;
import com.ezlinker.app.common.web.CurdController;
import com.ezlinker.app.modules.device.pojo.FieldParam;
import com.ezlinker.app.modules.device.pojo.FieldType;
import com.ezlinker.app.modules.moduletemplate.model.ModuleTemplate;
import com.ezlinker.app.modules.moduletemplate.service.IModuleTemplateService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * <p>
 * 产品上面的模块模板
 * </p>
 *
 * @author wangwenhai
 * @since 2019-12-19
 */
@RestController
@RequestMapping("/moduleTemplates")
public class ModuleTemplateController extends CurdController<ModuleTemplate> {

    @Resource
    IModuleTemplateService iModuleTemplateService;

    public ModuleTemplateController(HttpServletRequest httpServletRequest) {
        super(httpServletRequest);
    }

    /**
     * 创建模块
     *
     * @param moduleTemplate
     * @return
     * @throws XException
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    protected R add(@RequestBody @Valid ModuleTemplate moduleTemplate) throws BadRequestException {
        List<FieldParam> dataAreas = new ArrayList<>();
        if (moduleTemplate.getCount() > 10) {
            throw new BadRequestException("数目最多为10个!", "Count must <=10");
        }
        switch (moduleTemplate.getType()) {

            case ModuleTemplate.ModuleType.SWITCH:
                FieldParam dataArea0 = new FieldParam();
                dataArea0.setField("state");
                dataArea0.setFieldType(FieldType.BOOLEAN);
                dataArea0.setDefaultValue("false");
                dataArea0.setDescription("开关状态");
                dataAreas.add(dataArea0);
                moduleTemplate.setFieldParams(dataAreas);
                moduleTemplate.setIcon(ModuleTemplate.Icon.SWITCH);
                iModuleTemplateService.save(moduleTemplate);
                break;
            case ModuleTemplate.ModuleType.SWITCH_GROUP:
                Integer count = moduleTemplate.getCount();
                for (int i = 0; i < count; i++) {
                    FieldParam dataArea1 = new FieldParam();
                    dataArea1.setField("state" + i);
                    dataArea1.setFieldType(FieldType.BOOLEAN);
                    dataArea1.setDefaultValue("false");
                    dataArea1.setDescription("开关状态");
                    dataAreas.add(dataArea1);
                }
                moduleTemplate.setFieldParams(dataAreas);
                moduleTemplate.setIcon(ModuleTemplate.Icon.SWITCH_GROUP);

                iModuleTemplateService.save(moduleTemplate);
                break;
            case ModuleTemplate.ModuleType.PROGRESS_BAR:
                FieldParam l = new FieldParam();
                l.setField("left");
                l.setFieldType(FieldType.NUMBER);
                l.setDefaultValue("0");
                l.setDescription("最小值");

                FieldParam r = new FieldParam();
                r.setField("right");
                r.setFieldType(FieldType.NUMBER);
                r.setDefaultValue("100");
                r.setDescription("最大值");

                FieldParam value = new FieldParam();
                value.setField("value");
                value.setFieldType(FieldType.NUMBER);
                value.setDefaultValue("0");
                value.setDescription("当前值");

                dataAreas.add(l);
                dataAreas.add(value);
                dataAreas.add(r);
                moduleTemplate.setFieldParams(dataAreas);
                moduleTemplate.setIcon(ModuleTemplate.Icon.PROGRESS_BAR);

                iModuleTemplateService.save(moduleTemplate);
                break;
            //按钮只有一个字段
            case ModuleTemplate.ModuleType.BUTTON:

                FieldParam buttonArea1 = new FieldParam();
                buttonArea1.setField("cmd");
                buttonArea1.setFieldType(FieldType.NUMBER);
                buttonArea1.setDefaultValue("0");
                buttonArea1.setDescription("按钮事件");
                dataAreas.add(buttonArea1);
                moduleTemplate.setFieldParams(dataAreas);
                moduleTemplate.setIcon(ModuleTemplate.Icon.BUTTON);

                iModuleTemplateService.save(moduleTemplate);

                break;
            case ModuleTemplate.ModuleType.BUTTON_GROUP:


                for (int i = 0; i < moduleTemplate.getCount(); i++) {
                    FieldParam temp = new FieldParam();
                    temp.setField("cmd");
                    temp.setFieldType(FieldType.NUMBER);
                    temp.setDefaultValue("0");
                    temp.setDescription("按钮事件");
                    dataAreas.add(temp);
                }
                moduleTemplate.setFieldParams(dataAreas);
                moduleTemplate.setIcon(ModuleTemplate.Icon.BUTTON_GROUP);

                iModuleTemplateService.save(moduleTemplate);
                break;
            // 数据体类型
            case ModuleTemplate.ModuleType.DATA_ENTITY:
                if (moduleTemplate.getFieldParams() == null || moduleTemplate.getFieldParams().size() < 1) {
                    throw new BadRequestException("数据体最少包含一个字段", "Chart must have lest one field");
                }
                // 挨个检查字段合法
                for (int i = 0; i < moduleTemplate.getFieldParams().size(); i++) {
                    checkFieldParamFormat(moduleTemplate.getFieldParams().get(i));
                }
                moduleTemplate.setIcon(ModuleTemplate.Icon.DATA_ENTITY);
                iModuleTemplateService.save(moduleTemplate);
                break;
            // 流媒体设备不需要有数据字段,但是需要推流密钥
            // 需要单独建表来保存，一般和三方服务关联
            // 在生产设备的时候需要执行这个步骤，设计的时候不用
            case ModuleTemplate.ModuleType.STREAM:
                moduleTemplate.setFieldParams(null);
                moduleTemplate.setIcon(ModuleTemplate.Icon.STREAM);
                iModuleTemplateService.save(moduleTemplate);
                break;
            default:
                throw new BadRequestException("不支持的模块类型:" + moduleTemplate.getType(), "Module types unsupported:" + moduleTemplate.getType());
        }
        return success();

    }


    /**
     * @param defaultValue
     * @throws BadRequestException
     */
    private void checkIsNumber(String defaultValue) throws BadRequestException {
        if (!Pattern.compile("[0-9]*").matcher(defaultValue).matches()) {
            throw new BadRequestException("默认值必须为数字", "Default value must be a number");
        }
        if ((Long.parseLong(defaultValue) > Long.MAX_VALUE) || (Long.parseLong(defaultValue) < Long.MIN_VALUE)) {
            throw new BadRequestException("默认值在" + Long.MIN_VALUE + "-" + Long.MIN_VALUE + "之间", "Default value between" + Long.MIN_VALUE + "-" + Long.MIN_VALUE);

        }
    }

    /**
     * 检查表结构合法性
     *
     * @throws BadRequestException
     */
    private void checkFieldParamFormat(FieldParam dataArea) throws BadRequestException {

        String defaultValue = dataArea.getDefaultValue();
        FieldType type = dataArea.getFieldType();
        if (type == FieldType.NUMBER) {

            checkIsNumber(defaultValue);

        } else if (type == FieldType.TEXT) {
            if (defaultValue.length() > 256) {
                throw new BadRequestException("默认值长度不可超过256个字符", "Default string value length must less than 256 chars");

            }

        } else if (type == FieldType.BOOLEAN) {
            if ((!defaultValue.equalsIgnoreCase("boolean")) || (!dataArea.getDefaultValue().equalsIgnoreCase("false"))) {
                throw new BadRequestException("默认值必须为true或者false", "Default value must be 'true' or 'false'");

            }

        } else if (type == FieldType.JSON) {
            try {
                JSONArray.parse(dataArea.getDefaultValue());
            } catch (Exception e) {
                throw new BadRequestException("默认值必须为JSON数组格式", "Default value must be json array");

            }
        } else {
            throw new BadRequestException("不支持的字段类型", "Field format unsupported");

        }

    }

    /**
     * 更新
     *
     * @param id
     * @param form
     * @return
     */
    @Override
    @PutMapping("/{id}")
    protected R update(@PathVariable Long id, @RequestBody ModuleTemplate form) throws BadRequestException {
        ModuleTemplate module = iModuleTemplateService.getById(id);
        checkModelNull(module);
        BeanUtil.copyProperties(form, module, CopyOptions.create().ignoreNullValue());
        boolean ok = iModuleTemplateService.updateById(module);
        return ok ? data(module) : fail();
    }

    /**
     * 删除模块
     *
     * @param ids
     * @return
     */
    @Override
    protected R delete(@PathVariable Integer[] ids) {
        boolean ok = iModuleTemplateService.removeByIds(Arrays.asList(ids));
        return ok ? success() : fail();
    }

    /**
     * 获取字典项列表
     *
     * @param current
     * @param size
     * @param productId
     * @return
     */
    @GetMapping
    public R queryForPage(
            @RequestParam Long current,
            @RequestParam Long size,
            @RequestParam Long productId) {
        QueryWrapper<ModuleTemplate> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("product_id", productId);
        queryWrapper.orderByDesc("create_time");
        IPage<ModuleTemplate> iPage = iModuleTemplateService.page(new Page<>(current, size), queryWrapper);
        return data(iPage);
    }

    /**
     * 获取详情
     *
     * @param id
     * @return
     * @throws XException
     */
    @Override
    protected R get(@PathVariable Long id) throws XException {
        ModuleTemplate module = iModuleTemplateService.getById(id);
        if (module == null) {
            throw new BizException("Component not exists", "模块不存在");

        }
        return data(module);
    }
}

