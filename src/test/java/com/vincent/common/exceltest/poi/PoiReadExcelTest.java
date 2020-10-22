package com.vincent.common.exceltest.poi;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author vincent
 */
@Slf4j
public class PoiReadExcelTest {
    private static final String PATH = "/Users/vincent/IDEA_Project/my_project/common/src/test/java/com/vincent/common/exceltest/template/read";

    private Path filePath = Paths.get(PATH);

    @Test
    public void readExcelTest() throws Exception {
        // 1. 创建一个工作簿
        Workbook workbook = WorkbookFactory.create(filePath.resolve("PoiExcelRead_03.xls").toFile());

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

        // 获取除表抬头外的内容
        // 读取行数
        int rowCount = sheet.getPhysicalNumberOfRows();
        for (int rowNum = 1; rowNum < rowCount; rowNum++) {
            Row rowData = sheet.getRow(rowNum);
            // 读取列数
            int cellCount = rowTitle.getPhysicalNumberOfCells();
            for (int cellNum = 0; cellNum < cellCount; cellNum++) {
                log.info(String.format("RowNum - CellNum：[ %s - %s ]......", (rowNum + 1), (cellNum + 1)));
                // 读取每一个单元格中的内容
                Cell cell = rowData.getCell(cellNum);
                if (Objects.nonNull(cell)) {
                    // 匹配每个单元格数据类型
                    String cellValue = ExcelPoiUtils.cellTypeConvertToStringPatternMatching(cell);
                    log.info(String.format("CellValue Convert To String：%s", cellValue));
                }
            }
        }
    }

    @Test
    public void readExcelPoiUtilsTest() throws Exception {
        List<ExcelDto> list = ExcelPoiUtils.readExcel(filePath.resolve("PoiExcelRead_07.xlsx").toFile(), ExcelDto.class);
        list.forEach(System.out::println);
    }
}


















