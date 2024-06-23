package com.yun.demo.gen;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;

import java.util.Collections;

/**
 * @author zxy
 */
public class CodeGenerator {
    //当前项目路径
    static String projectPath = System.getProperty("user.dir");
    //连接数据库的配置
    static String url = "jdbc:mysql://10.16.56.12:3306/hrdb?useSSL=false&useUnicode=true&characterEncoding=utf8&serverTimezone=UTC";
    static String username = "appuser";
    static String password = "123456";

    public static void main(String[] args) {

        String[] tables = {"hr_biz_rule"};

        FastAutoGenerator.create(url, username, password)
                //全局配置
                .globalConfig(builder -> {
                    builder.fileOverride() // 覆盖已生成文件
                            .outputDir(projectPath + "/src/test/java") // 指定输出目录
                            .dateType(DateType.ONLY_DATE)
                            .disableOpenDir(); //不打开文件夹
                })
                //包名配置
                .packageConfig(builder -> {
                    builder.parent("com.yun") // 设置父包名
                            .service("service")
                            .serviceImpl("service.impl")
                            .controller("controller")
                            .entity("entity")
                            .mapper("mapper")
                            .xml("mapper")
                            .pathInfo(Collections.singletonMap(OutputFile.xml, projectPath + "/src/test/resources/mapper")); // 设置mapperXml生成路径
                })
                //策略配置
                .strategyConfig(builder -> {
                    builder.addInclude(tables) // 设置需要生成的表名
                            .entityBuilder() //实体类配置
                            .enableLombok() //开启lombok
                            .disableSerialVersionUID() //禁止生成serialVersionUID
                            .logicDeleteColumnName("delete") //说明逻辑删除字段
                            .controllerBuilder() //controller配置
                            .enableRestStyle() //开启restful api
                            .serviceBuilder() //service配置
                            .formatServiceFileName("%sService") //去掉Service前面的I前缀
                            .mapperBuilder() //mapper配置
                            .enableMapperAnnotation(); //开启@Mapper注解


                })
                //生成时不使用表前缀
//                .strategyConfig((scanner, builder) -> builder.addTablePrefix("t_").build())
                //模版配置
//                .templateConfig(
//                        (scanner, builder) ->
//                                builder.disable(TemplateType.ENTITY)
//                                        .entity("/templates/vm/entity.java")
//                                        .service("/templates/vm/service.java")
//                                        .serviceImpl("/templates/vm/serviceImpl.java")
//                                        .mapper("/templates/vm/mapper.java")
//                                        .mapperXml("/templates/vm/mapper.xml")
//                                        .controller("/templates/vm/controller.java")
//                                        .build()
//                )
                /*
                    模板引擎配置，默认 Velocity 可选模板引擎 Beetl 或 Freemarker
                    .templateEngine(new BeetlTemplateEngine())
                   .templateEngine(new FreemarkerTemplateEngine())
                 */
//                .templateEngine(new FreemarkerTemplateEngine())
//                .templateEngine(new BeetlTemplateEngine())
                .execute();

    }
}