package com.yun.demo.tools;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.github.javafaker.Faker;
import com.yun.demo.vo.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Locale;

@Slf4j
public class EasyexcelTest {

    public static Faker faker = new Faker(Locale.CHINA);

    @Test
    public void pathTest(){
        String path = getClass().getResource("/").getPath();
        System.out.println(path);
    }

    public static List<UserInfo> data(int size){
        List<UserInfo> datas = ListUtils.newArrayList();
        for (int i = 0; i < size; i++) {
            UserInfo data = new UserInfo();
            data.setUsername(faker.name().fullName());
            data.setMobile(faker.phoneNumber().cellPhone());
            data.setEmail(faker.internet().emailAddress());
            data.setCreteTime(faker.date().birthday());
            data.setSalary(faker.number().randomDouble(2,10,100));
            datas.add(data);
        }
        return datas;
    }

    @Test
    // 数据量不大的情况下使用(5000以内)，数据量大参考 重复多次写入
    public void simpleExportTest(){
        String fileName = getClass().getResource("/").getPath()+System.currentTimeMillis()+"_userInfo.xlsx";
        log.info("fileName:{}",fileName);
        EasyExcel.write(fileName, UserInfo.class).sheet("用户信息").doWrite(data(10));
    }

    @Test
    public void repeatWriteExportTest(){
        String fileName = getClass().getResource("/").getPath()+System.currentTimeMillis()+"_userInfo.xlsx";
        log.info("fileName:{}",fileName);
        try (ExcelWriter excelWritr = EasyExcel.write(fileName, UserInfo.class).build()){
            WriteSheet writerSheet = EasyExcel.writerSheet("用户信息").build();
            // 重复调用写入，根据实际情况确定循环次数
            for (int i = 0; i < 5; i++) {
                excelWritr.write(data(10),writerSheet);
            }
        }
        // 写入不同 sheet ，不同对象
//        try (ExcelWriter excelWritr = EasyExcel.write(fileName).build()){
//            for (int i = 0; i < 5; i++) {
//                WriteSheet writerSheet = EasyExcel.writerSheet(i,"用户信息"+i).head(UserInfo.class).build();
//                excelWritr.write(data(10),writerSheet);
//            }
//        }
    }

    @Test
    public void simpleImportTest(){
        String fileName = getClass().getResource("/").getPath()+"1696514299993_userInfo.xlsx";
        // 读取第一个sheet ，文件流自动关闭。每次读取 100 条数据，然后返回对数据处理
//        EasyExcel.read(fileName, UserInfo.class,new PageReadListener<UserInfo>(dataList -> {
//            dataList.forEach(System.out::println);
//        })).sheet().doRead();

        EasyExcel.read(fileName, UserInfo.class, new ReadListener<UserInfo>() {

            //  单次缓存的数据量
            public static final int BATCH_COUNT = 3;

            // 临时存储
            private List<UserInfo> cachedData = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

            // 每读一行数据都会调用该方法
            @Override
            public void invoke(UserInfo data, AnalysisContext context) {
                cachedData.add(data);
                if (cachedData.size() >= BATCH_COUNT) {
                    saveData();
                    cachedData = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
                }
            }

            private void saveData() {
                log.info("{}条数据开始保存",cachedData.size());
                log.info("保存数据成功");
            }

            // 读完整个excel之后会调用这个方法
            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
                saveData();
            }
        }).sheet().doRead();
    }
}
