package com.vincent.common.exceltest.poi;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.vavr.API;
import io.vavr.CheckedFunction2;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import static io.vavr.API.$;
import static io.vavr.API.Case;

/**
 * @author vincent
 */
@Slf4j
public class ExcelPoiUtils {
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public static <T> List<T> readExcel(File file, Class<T> clazz) throws Exception {
        List<T> list = Lists.newArrayList();

        // 1. 创建一个工作簿
        Workbook workbook = WorkbookFactory.create(file);

        // 2. 获取表
        Sheet sheet = workbook.getSheetAt(0);

        // 3. 获取表中的内容
        // 获取表抬头
        List<String> rowFirst = Lists.newArrayList();
        Row rowTitle = sheet.getRow(0);
        if (Objects.nonNull(rowTitle)) {
            // 读取列数
            int cellCount = rowTitle.getPhysicalNumberOfCells();
            for (int cellNum = 0; cellNum < cellCount; cellNum++) {
                Cell cell = rowTitle.getCell(cellNum);
                if (Objects.nonNull(cell)) {
                    String cellValue = cell.getStringCellValue();
                    rowFirst.add(cellValue);
                }
            }
        }
        log.info(String.format("RowFirst：%s...... \n", Arrays.toString(rowFirst.toArray())));

        /*
         * Class<T> clazz：需要转换为具体的实体类。
         * 此方法为获取 clazz（实体类）中的全部字段属性，与读取到的 excel 表头字段进行匹配，返回一个 Map<columnIndex,{columnIndex,name,field}>
         * columnIndex：为 clazz（实体类）字段属性在 excel 表头字段中的对应位置。
         * name：为自定义注解 @ExcelTransfer 中的 value 值（如：@ExcelTransfer(value = "字符串标题")）；如果没有设置自定义注解，则为 clazz（实体类）字段属性名。
         * field：为 clazz（实体类）字段属性。
         */
        List<Field> allFieldsList = FieldUtils.getAllFieldsList(clazz);
        Map<Integer, ClazzFields> mapClazzFields = allFieldsList.stream()
                .map(field -> {
                    if (field.isAnnotationPresent(ExcelTransfer.class)) {
                        ExcelTransfer annotation = field.getAnnotation(ExcelTransfer.class);
                        String annotationVal = annotation.value();
                        return new ClazzFields(rowFirst.indexOf(annotationVal), annotationVal, field);
                    }
                    return new ClazzFields(rowFirst.indexOf(field.getName()), field.getName(), field);
                })
                .collect(Collectors.toMap(ClazzFields::getColumnIndex, Function.identity(), (v1, v2) -> v1));

        // 获取除表抬头外的内容
        // 读取行数
        int rowCount = sheet.getPhysicalNumberOfRows();
        for (int rowNum = 1; rowNum < rowCount; rowNum++) {

            T instance = clazz.newInstance();

            Row rowData = sheet.getRow(rowNum);
            // 读取列数
            int cellCount = rowTitle.getPhysicalNumberOfCells();
            for (int cellNum = 0; cellNum < cellCount; cellNum++) {

                ClazzFields clazzFields = mapClazzFields.get(cellNum);

                log.info(String.format("RowNum - CellNum：[ %s - %s ]......", (rowNum + 1), (cellNum + 1)));
                // 读取每一个单元格中的内容
                Cell cell = rowData.getCell(cellNum);
                if (Objects.nonNull(cell)) {
                    /*
                     * 匹配每个单元格数据类型，把读取到的单元格的内容全部转换为 String 类型
                     */
                    String cellValue = cellTypeConvertToString(cell);
                    log.info(String.format("CellValue Convert To String：%s", cellValue));

                    if (Objects.isNull(clazzFields)) {
                        log.warn("The DtoField does not match the excel table field......\n");
                        continue;
                    }

                    Field dtoField = clazzFields.getField();
                    Field field = instance.getClass().getDeclaredField(dtoField.getName());
                    field.setAccessible(true);
                    Object o = fieldTypeConvertTo(dtoField, cellValue);
                    field.set(instance, o);

                    log.info(String.format("DtoFieldType：%s，DtoFieldValue：%s......\n", dtoField.getType(), o));
                }
            }
            list.add(instance);
        }
        return list;
    }

    private static Map<Class, BiFunction<String, String, Object>> MAP;

    static {
        MAP = Maps.newHashMap();
        MAP.put(String.class, (s1, s2) -> s1);
        MAP.put(Integer.class, (s1, s2) -> Integer.valueOf(s1));
        MAP.put(Byte.class, (s1, s2) -> Byte.valueOf(s1));
        MAP.put(Short.class, (s1, s2) -> Short.valueOf(s1));
        MAP.put(Long.class, (s1, s2) -> Long.valueOf(s1));
        MAP.put(Float.class, (s1, s2) -> Float.valueOf(s1));
        MAP.put(Double.class, (s1, s2) -> Double.valueOf(s1));
        MAP.put(Boolean.class, (s1, s2) -> Boolean.valueOf(s1));
        MAP.put(BigDecimal.class, (s1, s2) -> new BigDecimal(s1));

        MAP.put(Date.class, CheckedFunction2.<String, String, Object>of(DateUtils::parseDate).unchecked());

        MAP.put(int.class, (s1, s2) -> Integer.valueOf(s1));
        MAP.put(byte.class, (s1, s2) -> Byte.valueOf(s1));
        MAP.put(short.class, (s1, s2) -> Short.valueOf(s1));
        MAP.put(long.class, (s1, s2) -> Long.valueOf(s1));
        MAP.put(float.class, (s1, s2) -> Float.valueOf(s1));
        MAP.put(double.class, (s1, s2) -> Double.valueOf(s1));
        MAP.put(boolean.class, (s1, s2) -> Boolean.valueOf(s1));
    }

    /**
     * 方法一：表驱动
     * 把单元格的值转换成相对于的类型值
     *
     * @param field
     * @param value
     * @return
     */
    private static Object fieldTypeConvertTo(Field field, String value) {
        if (!MAP.containsKey(field.getType())) {
            return null;
        }
        String format = Optional.ofNullable(field.getAnnotation(ExcelTransfer.class))
                .map(ExcelTransfer::paseDate)
                .orElse(YYYY_MM_DD_HH_MM_SS);
        if (field.getType() == Date.class) {
            log.info(String.format("If CellFieldType is date, the pattern of CellField is %s ......", format));
        }
        log.info(String.format("CellFieldType（String） Convert To DtoFieldType（%s）......", field.getType()));
        return MAP.get(field.getType()).apply(value, format);
    }

    /**
     * 第二种方法：if else ...
     * 把单元格的值转换成相对于的类型值
     *
     * @param field
     * @param value
     * @return
     */
    private static Object fieldTypeConvertTo2(Field field, String value) {
        if (!MAP.containsKey(field.getType())) {
            return null;
        }
        String format = Optional.ofNullable(field.getAnnotation(ExcelTransfer.class))
                .map(ExcelTransfer::paseDate)
                .orElse(YYYY_MM_DD_HH_MM_SS);

        if (ClassUtils.isPrimitiveOrWrapper(field.getType())) {
            try {
                Method valueOf = field.getType().getDeclaredMethod("valueOf");
                return valueOf.invoke(null, value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (field.getType() == Date.class) {
            try {
                return DateUtils.parseDate(value, format);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if (field.getType() == BigDecimal.class) {
            return new BigDecimal(value);
        }
        return null;
    }

    /**
     * 方法一：switch case ...
     * excel 单元格转换为 String 类型
     *
     * @param cell
     * @return
     */
    public static String cellTypeConvertToString(Cell cell) {
        // 匹配每个单元格数据类型
        String cellValue = "";
        switch (cell.getCellTypeEnum()) {
            // 布尔类型
            case BLANK:
                log.info("CellType：【BLANK】");
                break;
            // 字符串类型
            case STRING:
                log.info("CellType：【STRING】");
                cellValue = cell.getStringCellValue();
                break;
            // 布尔类型
            case BOOLEAN:
                log.info("CellType：【BOOLEAN】");
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            // 数字类型（数字，日期）
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    log.info("CellType：【NUMERIC Convert to DATE】");
//                    DataFormatter dataFormatter = new DataFormatter();
//                    cellValue = dataFormatter.formatCellValue(cell);
                    cellValue = DateFormatUtils.format(cell.getDateCellValue(), YYYY_MM_DD_HH_MM_SS);
                } else {
                    // 不是日期格式，防止数字过长（可能是科学计数法），所以需要转换成字符串输出
                    log.info("CellType：【NUMERIC Convert to STRING】");
                    cell.setCellType(CellType.STRING);
                    cellValue = cell.getStringCellValue();
                }
                break;
            case ERROR:
                log.info("CellType：【ERROR】=> Get the cell error...");
                break;
        }
        return cellValue;
    }

    /**
     * 方法二：模式匹配
     *
     * @param cell
     * @return
     */
    public static String cellTypeConvertToStringPatternMatching(Cell cell) {
        return API.Match(cell.getCellTypeEnum()).of(
                Case($(s -> s == CellType.BLANK), () -> ""),
                Case($(s -> s == CellType.STRING), cell::getStringCellValue),
                Case($(s -> s == CellType.BOOLEAN), () -> String.valueOf(cell.getBooleanCellValue())),
                Case($(s -> s == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)), () -> DateFormatUtils.format(cell.getDateCellValue(), YYYY_MM_DD_HH_MM_SS)),
                Case($(s -> s == CellType.NUMERIC && !DateUtil.isCellDateFormatted(cell)), () -> {
                    cell.setCellType(CellType.STRING);
                    return cell.getStringCellValue();
                }),
                Case($(s -> s == CellType.ERROR), () -> {
                    log.info("【ERROR】：Get the cell error...");
                    return "";
                })
        );
    }
}


@Data
class ClazzFields {
    /**
     * 列的位置
     */
    private Integer columnIndex;

    /**
     * 如果有自定义注解，则为 @ExcelTransfer 中的 value 值（如：@ExcelTransfer(value = "字符串标题")）；
     * 如果没有设置自定义注解，则为 clazz（实体类）字段属性名
     */
    private String name;

    /**
     * clazz 实体类字段属性
     */
    private Field field;

    public ClazzFields() {
    }

    public ClazzFields(Integer columnIndex, String name, Field field) {
        this.columnIndex = columnIndex;
        this.name = name;
        this.field = field;
    }
}
