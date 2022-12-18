package com.example.demo.SaveFromFile;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class LessonServiceSave {

    public List<NativeLesson> getNativeLesson(InputStream file) {
        try {
            OPCPackage pkg = OPCPackage.open(file);
            XSSFWorkbook book = new XSSFWorkbook(pkg);
            XSSFSheet sheet = book.getSheet("Лист1");
            Iterator<Row> ri = sheet.rowIterator();
            long idWeekend = 0L;
            List<String> weekend = List.of("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday");
            List<NativeLesson> nativeLessons = new ArrayList<>();
            List<String> group = new ArrayList<>();
            int number = 0;
            while(ri.hasNext()) {
                XSSFRow row = (XSSFRow) ri.next();

                Iterator<Cell> ci = row.cellIterator();

                while(ci.hasNext()) {
                    XSSFCell cell = (XSSFCell) ci.next();
                    if(cell.getCellType() == CellType.STRING){
                        if(row.getRowNum() == 0){
                            group.add(cell.getStringCellValue());
                        }
                        else {
                            if (cell.getStringCellValue().equals("end")) {
                                idWeekend++;
                            } else if (!cell.getStringCellValue().equals("")) {
                                NativeLesson nativeLesson = new NativeLesson(group.get(cell.getColumnIndex() - 3), cell.getStringCellValue(), (long) number, weekend.get((int) idWeekend));
                                nativeLessons.add(nativeLesson);
                            }
                        }
                    } else if (cell.getCellType() == CellType.NUMERIC) {
                        if(cell.getNumericCellValue() < 10){
                            number = (int) cell.getNumericCellValue();
                        }
                    }
                }
            }
            pkg.close();
            return convert(nativeLessons);
        } catch (InvalidFormatException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<NativeLesson> convert(List<NativeLesson> nativeLessons){
        List<NativeLesson> nativeLessonList = new ArrayList<>();
        for (NativeLesson nativeLesson : nativeLessons) {
            if (nativeLesson.getLesson().length() == 0) {
                for (int j = 1; j <= 18; j++) {
                    NativeLesson nativeLesson1 = new NativeLesson(nativeLesson);
                    nativeLesson1.setWeak((long) j);
                    nativeLessonList.add(nativeLesson1);
                }
            } else if (nativeLesson.getLesson().startsWith("ч/н")) {
                add(nativeLesson, nativeLessonList, Odds.EVEN);
            } else if (nativeLesson.getLesson().startsWith("н/н")) {
                add(nativeLesson, nativeLessonList, Odds.ODD);
            } else {
                add(nativeLesson, nativeLessonList, Odds.NONE);
            }
        }
        return nativeLessonList;
    }

    private void add(NativeLesson nativeLesson, List<NativeLesson> nativeLessonList, Odds odds){
        String[] regex = null;
        switch (odds){
            case NONE -> regex = nativeLesson.getLesson().split("_");
            case ODD,EVEN -> regex = nativeLesson.getLesson().substring(4).split("_");
        }
        String[] weeks = regex[0].split(",");
        for (String week : weeks) {
            if (week.length() == 1 || week.length() == 2 || (week.length() == 3 && week.endsWith("н"))) {
                if(week.endsWith("н")){
                    week = week.replace("н", "");
                }
                NativeLesson nativeLesson1 = new NativeLesson(nativeLesson);
                nativeLesson1.setWeak(Long.parseLong(week));
                nativeLesson1.setLesson(regex[1]);
                nativeLesson1.setType(regex[2]);
                nativeLesson1.setClassroom(regex[3]);
                nativeLesson1.setTeacher(regex[4]);
                nativeLessonList.add(nativeLesson1);
            } else if (week.length() == 3 || week.length() == 4){
                String[] fromTo = week.split("-");
                if(fromTo[1].endsWith("н")){
                    fromTo[1] = fromTo[1].replace("н", "");
                }
                for (int l = Integer.parseInt(fromTo[0]); l <= Integer.parseInt(fromTo[1]); l++) {
                    switch (odds){
                        case ODD -> {
                            if(l%2 == 1){
                                NativeLesson nativeLesson1 = new NativeLesson(nativeLesson);
                                nativeLesson1.setWeak((long) l);
                                nativeLesson1.setLesson(regex[1]);
                                nativeLesson1.setType(regex[2]);
                                nativeLesson1.setClassroom(regex[3]);
                                nativeLesson1.setTeacher(regex[4]);
                                nativeLessonList.add(nativeLesson1);}
                        }
                        case EVEN -> {
                            if(l%2 == 0){
                                NativeLesson nativeLesson1 = new NativeLesson(nativeLesson);
                                nativeLesson1.setWeak((long) l);
                                nativeLesson1.setLesson(regex[1]);
                                nativeLesson1.setType(regex[2]);
                                nativeLesson1.setClassroom(regex[3]);
                                nativeLesson1.setTeacher(regex[4]);
                                nativeLessonList.add(nativeLesson1);}
                        }
                        case NONE -> {
                            NativeLesson nativeLesson1 = new NativeLesson(nativeLesson);
                            nativeLesson1.setWeak((long) l);
                            nativeLesson1.setLesson(regex[1]);
                            nativeLesson1.setType(regex[2]);
                            nativeLesson1.setClassroom(regex[3]);
                            nativeLesson1.setTeacher(regex[4]);
                            nativeLessonList.add(nativeLesson1);
                        }
                    }
                }
            }
        }
    }
}
