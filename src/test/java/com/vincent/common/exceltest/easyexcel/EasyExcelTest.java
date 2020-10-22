package com.vincent.common.exceltest.easyexcel;

import com.alibaba.excel.EasyExcel;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

/**
 * @author vincent
 */
public class EasyExcelTest {
    private static final String WRITEPATH = "/Users/vincent/IDEA_Project/my_project/common/src/test/java/com/vincent/common/exceltest/template/write";

    private static final String READPATH = "/Users/vincent/IDEA_Project/my_project/common/src/test/java/com/vincent/common/exceltest/template/read";

    private List<DemoWriteDto> data() {
        List<DemoWriteDto> list = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            DemoWriteDto data = new DemoWriteDto();
            data.setString("字符串" + i);
            data.setDate(new Date());
            data.setDoubleData(0.56);
            list.add(data);
        }
        return list;
    }

    /**
     * 最简单的写
     * 1. 创建 excel 对应的实体对象 参照{@link DemoWriteDto}
     * 2. 直接写即可
     */
    @Test
    public void simpleWrite() {
        // 写法1
        // 这里 需要指定写用哪个 class 去写，然后写到第一个 sheet，名字为模板，然后文件流会自动关闭
        // 官方文档中说 03.xls 版本的 excel 需要设置 excelType()，但是自测下来并不需要。
        String fileName_03 = Paths.get(WRITEPATH).resolve("EasyExcelWrite_03.xls").toString();
        EasyExcel.write(fileName_03, DemoWriteDto.class).sheet("模板").doWrite(data());
        System.out.println();

        String fileName_07 = Paths.get(WRITEPATH).resolve("EasyExcelWrite_07.xlsx").toString();
        EasyExcel.write(fileName_07, DemoWriteDto.class).sheet("模板").doWrite(data());

//        // 写法2
//        fileName = PATH + "simpleWrite" + System.currentTimeMillis() + ".xlsx";
//        // 这里需要指定写用哪个 class 去写
//        ExcelWriter excelWriter = null;
//        try {
//            excelWriter = EasyExcel.write(fileName, DemoWriteDto.class).build();
//            WriteSheet writeSheet = EasyExcel.writerSheet("模板").build();
//            excelWriter.write(data(), writeSheet);
//        } finally {
//            // 千万别忘记 finish 会帮忙关闭流
//            if (excelWriter != null) {
//                excelWriter.finish();
//            }
//        }
    }

    /**
     * 数据量小的情况下，最简单的读
     */
    @Test
    public void simpleDoReadSync() throws IOException {
        // 数据量不是很大的话，直接用下面的方法即可
        File xlsFile = Paths.get(READPATH).resolve("EasyExcelRead_03.xls").toFile();
        List<ConverterDto> list_03 = EasyExcel.read(xlsFile).head(ConverterDto.class).sheet().doReadSync();
        list_03.forEach(System.out::println);
        System.out.println();

        File xlsxFile = Paths.get(READPATH).resolve("EasyExcelRead_07.xlsx").toFile();
        List<ConverterDto> list_07 = EasyExcel.read(xlsxFile).head(ConverterDto.class).sheet().doReadSync();
        list_07.forEach(System.out::println);
    }

    /**
     * 数据量大的情况下，最简单的读
     * 1. 创建 excel 对应的实体对象 参照{@link DemoReadDto}
     * 2. 由于默认一行行的读取 excel，所以需要创建 excel 一行一行的回调监听器，参照{@link DemoDataListener}
     * 3. 直接读即可
     */
    @Test
    public void simpleRead() {
        // 有个很重要的点 DemoDataListener 不能被 spring 管理，要每次读取 excel 都要 new，然后里面用到 spring 可以构造方法传进去
        // 写法1：
        String fileName = Paths.get(READPATH).resolve("EasyExcelRead_03.xls").toString();
        // 这里 需要指定读用哪个 class 去读，然后读取第一个 sheet 文件流会自动关闭
        EasyExcel.read(fileName, DemoReadDto.class, new DemoDataListener()).sheet().doRead();

//        // 写法2：
//        fileName = READPATH + "EasyExcel.xlsx";
//        ExcelReader excelReader = null;
//        try {
//            excelReader = EasyExcel.read(fileName, DemoReadDto.class, new DemoDataListener()).build();
//            ReadSheet readSheet = EasyExcel.readSheet(0).build();
//            excelReader.read(readSheet);
//        } finally {
//            if (excelReader != null) {
//                // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
//                excelReader.finish();
//            }
//        }
    }
}
