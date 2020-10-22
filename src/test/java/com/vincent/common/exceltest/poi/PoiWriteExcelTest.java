package com.vincent.common.exceltest.poi;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author vincent
 */
public class PoiWriteExcelTest {
    private static final String PATH = "/Users/vincent/IDEA_Project/my_project/common/src/test/java/com/vincent/common/exceltest/template/write";

    private static final Path FILEPATH = Paths.get(PATH);

    /**
     * 03 版本 Excel 后缀名为 xls，最多只有 A65536 行，
     * 创建 03 版本 Excel 的对象为：HSSFWorkbook，
     * 超出 A65536 行时会报异常。
     *
     * @throws Exception
     */
    @Test
    public void writeExcel03Test() throws Exception {
        long begin = System.currentTimeMillis();

        // 1. 创建一个工作簿
        Workbook workbook = new HSSFWorkbook();

        // 2. 创建一个工作表
        Sheet sheet = workbook.createSheet("Excel_03_xls");

        // 3. 创建第一行
        Row rowFirst = sheet.createRow(0);
        for (int rowFirstCellNum = 0; rowFirstCellNum < 10; rowFirstCellNum++) {
            Cell cell = rowFirst.createCell(rowFirstCellNum);
            cell.setCellValue("表头" + rowFirstCellNum);
        }

        for (int rowNum = 1; rowNum < 65536; rowNum++) {
            Row row = sheet.createRow(rowNum);
            for (int cellNum = 0; cellNum < 10; cellNum++) {
                Cell cell = row.createCell(cellNum);
                if (cellNum == 9) {
                    LocalDateTime now = LocalDateTime.now();
                    String format = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    cell.setCellValue(format);
                } else {
                    cell.setCellValue(cellNum);
                }
            }
        }
        System.out.println("Generate Excel Over......");

        // 5. 生成一张表（IO 流），03 版本的后缀名 xls
        OutputStream outputStream = Files.newOutputStream(FILEPATH.resolve("Excel_03.xls"));

        // 6. 输出
        workbook.write(outputStream);

        // 7. 关闭流
        outputStream.close();
        System.out.println("Excel_03 Generated Successfully......");

        long end = System.currentTimeMillis();
        System.out.println(String.format("Excel_03 Generated Time：%s ms......", (end - begin)));
    }

    /**
     * 07 版本 Excel 后缀名为 xlsx，行数不限，
     * 创建 07 版本 Excel 的对象为：XSSFWorkbook。
     *
     * @throws Exception
     */
    @Test
    public void writeExcel07Test() throws Exception {
        long begin = System.currentTimeMillis();

        // 1. 创建一个工作簿
        Workbook workbook = new XSSFWorkbook();

        // 2. 创建一个工作表
        Sheet sheet = workbook.createSheet("Excel_07_xlsx");

        // 3. 创建一个行
        Row rowFirst = sheet.createRow(0);
        for (int rowFirstCellNum = 0; rowFirstCellNum < 10; rowFirstCellNum++) {
            Cell cell = rowFirst.createCell(rowFirstCellNum);
            cell.setCellValue("表头" + rowFirstCellNum);
        }

        for (int rowNum = 1; rowNum < 65536; rowNum++) {
            Row row = sheet.createRow(rowNum);
            for (int cellNum = 0; cellNum < 10; cellNum++) {
                Cell cell = row.createCell(cellNum);
                if (cellNum == 9) {
                    LocalDateTime now = LocalDateTime.now();
                    String format = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    cell.setCellValue(format);
                } else {
                    cell.setCellValue(cellNum);
                }
            }
        }
        System.out.println("Generate Excel Over......");

        // 5. 生成一张表（IO 流），07 版本的后缀名 xlsx
        OutputStream outputStream = Files.newOutputStream(FILEPATH.resolve("Excel_07.xlsx"));

        // 6. 输出
        workbook.write(outputStream);

        // 7. 关闭流
        outputStream.close();
        System.out.println("Excel_07 Generated Successfully......");

        long end = System.currentTimeMillis();
        System.out.println(String.format("Excel_07 Generated Time：%s ms......", (end - begin)));
    }

    /**
     * 07 版本 Excel 后缀名为 xlsx，行数不限，
     * 创建 07 版本 Excel 大数据量时的对象为：SXSSFWorkbook，
     * 在写入数据的过程中会产生临时文件，需要清除临时文件，默认写入临时文件的条数为100条，
     * 如果想要自定义内存中数据的数量，可以使用 new SXSSFWorkbook(数量)。
     *
     * @throws Exception
     */
    @Test
    public void writeBigDataExcel07Test() throws Exception {
        long begin = System.currentTimeMillis();

        // 1. 创建一个工作簿
        SXSSFWorkbook workbook = new SXSSFWorkbook();

        // 2. 创建一个工作表
        Sheet sheet = workbook.createSheet("ExcelBigData_07_xlsx");

        // 3. 创建一个行
        Row rowFirst = sheet.createRow(0);
        for (int rowFirstCellNum = 0; rowFirstCellNum < 10; rowFirstCellNum++) {
            Cell cell = rowFirst.createCell(rowFirstCellNum);
            cell.setCellValue("表头" + rowFirstCellNum);
        }

        for (int rowNum = 1; rowNum < 65536; rowNum++) {
            Row row = sheet.createRow(rowNum);
            for (int cellNum = 0; cellNum < 10; cellNum++) {
                Cell cell = row.createCell(cellNum);
                if (cellNum == 9) {
                    LocalDateTime now = LocalDateTime.now();
                    String format = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    cell.setCellValue(format);
                } else {
                    cell.setCellValue(cellNum);
                }
            }
        }
        System.out.println("Generate Excel Over......");

        // 5. 生成一张表（IO 流），07 版本的后缀名 xlsx
        OutputStream outputStream = Files.newOutputStream(FILEPATH.resolve("ExcelBigData_07.xlsx"));

        // 6. 输出
        workbook.write(outputStream);

        // 7. 关闭流
        outputStream.close();

        // 8. 清除临时文件
        workbook.dispose();

        System.out.println("ExcelBigData_07 Generated Successfully......");

        long end = System.currentTimeMillis();
        System.out.println(String.format("ExcelBigData_07 Generated Time：%s ms......", (end - begin)));
    }
}
