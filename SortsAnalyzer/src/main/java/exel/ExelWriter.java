package exel;

import fillers.Filler;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.charts.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import sorters.algorithms.Sorting;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;


/**
 *<b>Class for print statistics about all sorting method with different generated arrays to exels file</b>
 *
 * @author Pavlo Danyliuk
 * @version 1.0
 * @see Filler
 */

public class ExelWriter {
    private final Map<Method, Map<Class<? extends Sorting>, long[]>> MAP;
    private final XSSFWorkbook WORKBOOK;
    private final int[] LENS_OF_SORTING_ARRAYS;


    /**
     * To set a map Object. Which contains of method of generating arrays,
     * {@link Class Class} object of sorting method and time needed to sort the array
     * @param map Map object
     *
     */
    public ExelWriter(Map<Method, Map<Class<? extends Sorting>, long[]>> map, int[] lenOfSortingArrays){
        this.MAP = map;
        WORKBOOK = new XSSFWorkbook();
        LENS_OF_SORTING_ARRAYS = lenOfSortingArrays;
    }


    /**
     * Writing received information to exel file
     * @param file .xlsx file, where will be writing a received information
     *
     */
    public void writeStats(File file){
        if(!file.getName().endsWith(".xlsx")){
            System.out.println("Incorrect file format. Need to be \".xlsx\"");
            return;
        }
        createSheets();

        file.getParentFile().mkdirs();

        try(FileOutputStream fout = new FileOutputStream(file)) {
            WORKBOOK.write(fout);
            System.out.println("Created file " + file.getAbsolutePath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void createSheets(){
        for(Map.Entry<Method, Map<Class<? extends Sorting>, long[]>> entry : MAP.entrySet()){

            XSSFSheet sheet = WORKBOOK.createSheet(entry.getKey().getName());

            createTable(entry.getValue(), sheet);
        }
    }

    private void createTable(Map<Class<? extends Sorting>, long[]> sortingMap, XSSFSheet sheet){
        int rowNum = 0;
        Cell cell;
        Row row;

        row = sheet.createRow(rowNum);
        row.createCell(0, CellType.STRING).setCellValue("Methods of sorting");

        for(int i = 0; i < LENS_OF_SORTING_ARRAYS.length; i++){
            cell = row.createCell(i+1, CellType.NUMERIC);
            cell.setCellValue(LENS_OF_SORTING_ARRAYS[i]);
        }
        rowNum++;

        for(Map.Entry<Class<? extends  Sorting>, long[]> entry : sortingMap.entrySet()){
            row = sheet.createRow(rowNum);
            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue(entry.getKey().getSimpleName());
            createCells(1, row, entry.getValue());
            rowNum++;
        }

        buildGraph(sheet, rowNum, LENS_OF_SORTING_ARRAYS.length + 1);

    }

    private int createCells(int cellNum, Row row, long[] arr){
        Cell cell;
        for(long l : arr){
            cell = row.createCell(cellNum, CellType.NUMERIC);
            cell.setCellValue(l);
            cellNum++;
        }
        return cellNum;
    }

    private void buildGraph(Sheet sheet, final int rowCount, final int cellsCount){
        Drawing<?> drawing = sheet.createDrawingPatriarch();
        ClientAnchor anchor = drawing.createAnchor(0,0,0,0, 0, rowCount + 1, cellsCount * 2, rowCount + 20);

        Chart chart = drawing.createChart(anchor);
        ChartLegend legend = chart.getOrCreateLegend();
        legend.setPosition(LegendPosition.TOP_RIGHT);

        LineChartData data = chart.getChartDataFactory().createLineChartData();

        ChartAxis bottomAxis = chart.getChartAxisFactory().createCategoryAxis(AxisPosition.BOTTOM);
        ChartAxis leftAxis = chart.getChartAxisFactory().createValueAxis(AxisPosition.LEFT);
        leftAxis.setCrosses(AxisCrosses.MIN);


        ChartDataSource<Number> xs = DataSources.fromNumericCellRange(sheet, new CellRangeAddress(0, 0, 1, cellsCount - 1));


        for(int i = 1; i < rowCount; i++) {
            data.addSeries(xs, DataSources.fromNumericCellRange(sheet, new CellRangeAddress(i, i, 1, cellsCount - 1))).setTitle(i + " method of sorting");
        }

        chart.plot(data, bottomAxis, leftAxis);

    }

}
