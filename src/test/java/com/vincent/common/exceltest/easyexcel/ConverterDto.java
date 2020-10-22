package com.vincent.common.exceltest.easyexcel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.format.NumberFormat;
import lombok.Data;

import java.util.Date;

@Data
public class ConverterDto {
    /**
     * 我自定义 转换器，不管数据库传过来什么 。我给他加上“自定义：”
     */
    @ExcelProperty(converter = CustomStringStringConverter.class)
    private String string;

    /**
     * 需要注意：
     * 1. Excel 中的对应日期字段，必须为日期格式时（如果 Excel 中的对应日期字段为文本格式时，并不会转换为 "yyyy年MM月dd日HH时mm分ss秒" 格式）
     * 2. 同时实体类中 date 为 String 类型时
     * 才会转换为 DateTimeFormat 中的 "yyyy年MM月dd日HH时mm分ss秒" 格式
     */
    @DateTimeFormat("yyyy年MM月dd日HH时mm分ss秒")
    private String date;

    /**
     * 我想接收百分比的数字
     * 需要注意：
     * 如果要转换格式为 NumberFormat 中的 "#.##%"，实体类中 doubleData 字段必须为 String 类型时，才会转换。
     * 如果用 Double 接收，是不会转换格式的。
     */
    @NumberFormat("#.##%")
    private Double doubleData;
}