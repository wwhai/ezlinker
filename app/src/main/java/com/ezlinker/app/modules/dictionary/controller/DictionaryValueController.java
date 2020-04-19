package com.ezlinker.app.modules.dictionary.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ezlinker.app.common.web.CurdController;
import com.ezlinker.app.modules.dictionary.model.DictionaryValue;
import com.ezlinker.app.modules.dictionary.service.IDictionaryValueService;
import com.ezlinker.app.common.exception.XException;
import com.ezlinker.app.common.exchange.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;

/**
 * <p>
 * 字典的值
 * </p>
 *
 * @author wangwenhai
 * @since 2019-11-14
 */
@RestController
@RequestMapping("/dictionaries/values")
public class DictionaryValueController extends CurdController<DictionaryValue> {

    @Autowired
    IDictionaryValueService iDictionaryValueService;

    public DictionaryValueController(HttpServletRequest httpServletRequest) {
        super(httpServletRequest);
    }


    /**
     * 新增一个字典值
     *
     * @param dictionaryValue
     * @return
     * @throws XException
     */
    @Override
    protected R add(@RequestBody @Valid DictionaryValue dictionaryValue) throws XException {
        boolean ok = iDictionaryValueService.save(dictionaryValue);
        return ok ? data(dictionaryValue) : fail();
    }

    /**
     * 查看字典值详情
     *
     * @param id
     * @return
     * @throws XException
     */
    @Override
    protected R get(@PathVariable Long id) throws XException {
        return data(iDictionaryValueService.getById(id));
    }

    /**
     * 删除字典值
     *
     * @param ids
     * @return
     * @throws XException
     */
    @Override
    protected R delete(@PathVariable Integer[] ids) throws XException {
        boolean ok = iDictionaryValueService.removeByIds(Arrays.asList(ids));
        return ok ? success() : fail();
    }

    /**
     * 获取字典值列表
     *
     * @param current
     * @param size
     * @param keyId
     * @param label
     * @param label
     * @return
     */
    @GetMapping
    public R queryForPage(@RequestParam int current,
                          @RequestParam Integer size,
                          @RequestParam Long keyId,
                          @RequestParam(required = false) Integer label) {
        QueryWrapper<DictionaryValue> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("key_id", keyId);
        queryWrapper.eq(label != null, "label", label);
        queryWrapper.orderByDesc("create_time");
        IPage<DictionaryValue> iPage = iDictionaryValueService.page(new Page<>(current, size), queryWrapper);
        return data(iPage);
    }
}

