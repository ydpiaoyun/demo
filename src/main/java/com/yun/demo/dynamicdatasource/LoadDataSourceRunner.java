package com.yun.demo.dynamicdatasource;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.boot.CommandLineRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description: SpringBoot启动时，将数据库表中配置的数据源添加到动态数据源中
 * @date: 2023/7/27 16:56
 **/
//@Component
public class LoadDataSourceRunner implements CommandLineRunner {
    @Resource
    private DynamicDataSource dynamicDataSource;
    @Resource
    private DataSourceMapper dataSourceMapper;
    @Override
    public void run(String... args) throws Exception {
        List<DataSourceEntity> ds = dataSourceMapper.selectList(null);
        if (CollectionUtils.isNotEmpty(ds)) {
            dynamicDataSource.createDataSource(ds);
        }
    }
}