package com.vincent.common.exceltest.easyexcel;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.format.NumberFormat;
import lombok.Data;

import java.util.Date;

@Data
public class DemoReadDto {
    private String string;

    /*
     * 这里的时间转换，当为读取 excel 时，时间类为 String 时才会生效，其他类型时 @DateTimeFormat 不会转换。
     *              当为写入 excel 时，@DateTimeFormat 时间转换会生效。
     */
    @DateTimeFormat("yyyy年MM月dd日HH时mm分ss秒")
    private Date date;

    /*
     * 我想接收百分比的数字
     */
    @NumberFormat("#.##%")
    private String doubleData;

    /**
     * 忽略这个字段
     */
    @ExcelIgnore
    private String ignore;
}