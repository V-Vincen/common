package com.vincent.common.exceltest.poi;

import lombok.Data;

import java.util.Date;

@Data
public class ExcelDto {
    @ExcelTransfer(value = "字符串标题")
    private String id;

    @ExcelTransfer(value = "字符串日期标题", paseDate = "yyyy>MM>dd HH:mm:ss")
    private Date date;

    @ExcelTransfer(value = "数字标题")
    private Double price;

    @ExcelTransfer(value = "时间日期标题")
    private String data;
}