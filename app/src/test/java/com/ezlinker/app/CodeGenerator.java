package com.ezlinker.app;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.ezlinker.app.common.model.XEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author wangwenhai
 */
public class CodeGenerator {
    private static String AUTHOR = "wangwenhai";
    private static String IP = "112.74.44.130";
    private static String PORT = "3306";
    private static String DB_NAME = "ezlinker";
    private static String USER = "easylinker";
    private static String PASSWORD = "123456";
    private static String PROJECT = "app";
    private static String TABLE_PREFIX = "ez_";


    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    private static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        String help = tip + ":";
        System.out.println(help);
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    /**
     * RUN THIS
     */
    public static void main(String[] args) {
        AutoGenerator mpg = new AutoGenerator();
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/" + PROJECT + "/src/main/java");
        gc.setFileOverride(true);
        gc.setEnableCache(false);
        gc.setBaseResultMap(true);
        //gc.setBaseColumnList(true);
        gc.setAuthor(AUTHOR);
        gc.setOpen(false);

        mpg.setGlobalConfig(gc);
        // 数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUrl("jdbc:mysql://" + IP + ":" + PORT + "/" + DB_NAME + "?tinyInt1isBit=FALSE&allowMultiQueries=true&useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=GMT%2B8");
        dataSourceConfig.setDriverName("com.mysql.cj.jdbc.Driver");
        dataSourceConfig.setUsername(USER);
        dataSourceConfig.setPassword(PASSWORD);
        mpg.setDataSource(dataSourceConfig);

        // 包配置
        PackageConfig pc = new PackageConfig();
        String moduleName = scanner("请输入模块[module]名");
        pc.setModuleName(moduleName);
        pc.setEntity("model");
        pc.setParent("com.ezlinker.app.modules");
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出[V city模板]
        String templatePath = "/templates/mapper.xml.vm";
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                String path = PROJECT + "/src/main/resources/mapper/" + pc.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
                if (StringUtils.isEmpty(pc.getModuleName())) {
                    path = PROJECT + "/src/main/resources/mapper/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
                }
                return path;
            }
        });

        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);
        mpg.setTemplate(new TemplateConfig().setXml(null));

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setRestControllerStyle(true);
        strategy.setTablePrefix(TABLE_PREFIX);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setSuperEntityClass(XEntity.class);
        strategy.setSuperEntityColumns("id", "x", "create_time", "record_version");
        //支持View
        strategy.setSkipView(false);
        //支持Lombok
        strategy.setEntityLombokModel(true);
        //支持链式属性
        strategy.setEntityBuilderModel(true);

        strategy.setSuperControllerClass("com.ezlinker.app.common.web.CurdController");
        strategy.setInclude(scanner("请输入表名"));
        strategy.setControllerMappingHyphenStyle(false);
        strategy.setVersionFieldName("record_version");
        mpg.setStrategy(strategy);
        mpg.execute();
    }
}
