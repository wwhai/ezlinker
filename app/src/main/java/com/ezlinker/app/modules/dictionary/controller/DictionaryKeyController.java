package com.ezlinker.app.modules.dictionary.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ezlinker.app.common.web.CurdController;
import com.ezlinker.app.modules.dictionary.model.DictionaryKey;
import com.ezlinker.app.modules.dictionary.model.DictionaryValue;
import com.ezlinker.app.modules.dictionary.service.IDictionaryKeyService;
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
 * 字典的项
 * </p>
 *
 * @author wangwenhai
 * @since 2019-11-14
 */
@RestController
@RequestMapping("/dictionaries/keys")
public class DictionaryKeyController extends CurdController<DictionaryKey> {
    @Autowired
    IDictionaryKeyService iDictionaryKeyService;
    @Autowired
    IDictionaryValueService iDictionaryValueService;

    public DictionaryKeyController(HttpServletRequest httpServletRequest) {
        super(httpServletRequest);
    }


    /**
     * 新增一个字典项
     *
     * @param dictionaryKey
     * @return
     * @throws XException
     */
    @Override
    protected R add(@RequestBody @Valid DictionaryKey dictionaryKey) throws XException {
        boolean ok = iDictionaryKeyService.save(dictionaryKey);
        return ok ? data(dictionaryKey) : fail();
    }

    /**
     * 查看字典项详情
     *
     * @param id
     * @return
     * @throws XException
     */
    @Override
    protected R get(@PathVariable Long id) throws XException {
        return data(iDictionaryKeyService.getById(id));
    }

    /**
     * 删除字典项
     *
     * @param ids
     * @return
     * @throws XException
     */
    @Override
    protected R delete(@PathVariable Integer[] ids) throws XException {
        boolean okKey = iDictionaryKeyService.removeByIds(Arrays.asList(ids));
        boolean okValue = iDictionaryValueService.remove(new QueryWrapper<DictionaryValue>().in("key_id", (Object[]) ids));
        return okKey && okValue ? success() : fail();
    }

    /**
     * 获取字典项列表
     *
     * @param current
     * @param size
     * @param table
     * @param name
     * @param label
     * @return
     */
    @GetMapping
    public R queryForPage(
            @RequestParam Long current,
            @RequestParam Long size,
            @RequestParam(required = false) String table,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer label) {
        QueryWrapper<DictionaryKey> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(table != null, "table_name", table);
        queryWrapper.eq(name != null, "name", name);
        queryWrapper.eq(label != null, "label", label);
        queryWrapper.orderByDesc("create_time");
        IPage<DictionaryKey> page = iDictionaryKeyService.page(new Page<>(current, size), queryWrapper);
        return data(page);
    }


}

