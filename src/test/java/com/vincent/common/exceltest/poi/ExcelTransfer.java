package com.vincent.common.exceltest.poi;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.vincent.common.exceltest.poi.ExcelPoiUtils.YYYY_MM_DD_HH_MM_SS;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface ExcelTransfer {
    String value() default "";

    String paseDate() default YYYY_MM_DD_HH_MM_SS;
}