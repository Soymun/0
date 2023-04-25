package com.example.lessonservice.SaveFromFile;

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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class LessonServiceSave {

    private final List<String> weekend = List.of("ПОНЕДЕЛЬНИК", "ВТОРНИК", "СРЕДА", "ЧЕТВЕРГ", "ПЯТНИЦА", "СУББОТА");

    private List<NativeLesson> list = new ArrayList<>();
    public List<NativeLesson> getNativeLesson(InputStream file, int countGroups) {
        list = new ArrayList<>();
        try (file) {
            OPCPackage pkg = OPCPackage.open(file);
            XSSFWorkbook book = new XSSFWorkbook(pkg);
            XSSFSheet sheet = book.getSheet("Лист1");
            Iterator<Row> rowIterator = sheet.rowIterator();
            getLesson(rowIterator, countGroups);
            pkg.close();
        } catch (InvalidFormatException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return list;
    }

    private void getLesson(Iterator<Row> iterator, int countGroups) throws InterruptedException {
        String day = "";
        long numberOfLesson = 0;
        long contRow = 0;
        List<String> group = getGroup(iterator, countGroups);
        while (iterator.hasNext()) {
            if (contRow > 80) {
                break;
            }
            XSSFRow row = (XSSFRow) iterator.next();
            Iterator<Cell> ci = row.cellIterator();
            int countCell = 1;
            while (ci.hasNext()) {
                if (countCell > countGroups + 3) {
                    break;
                }
                XSSFCell cell = (XSSFCell) ci.next();
                int columnIndex = cell.getColumnIndex();
                if (columnIndex == 0) {
                    String groupString = cell.getStringCellValue();
                    if (weekend.contains(groupString)) {
                        day = groupString;
                    }
                } else if (columnIndex == 1) {
                    if(cell.getCellType() == CellType.NUMERIC){
                        numberOfLesson = (long) cell.getNumericCellValue();
                    }
                } else if (columnIndex >= 3) {
                    String lesson = cell.getStringCellValue().trim();
                    if (lesson.length() != 0) {
                        distribution(new NativeLesson(group.get(columnIndex - 3),
                                cell.getStringCellValue(),
                                numberOfLesson, day));
                    }
                }
                countCell++;
            }
            contRow++;
        }
    }

    private List<String> getGroup(Iterator<Row> iterator, int countGroups) {
        List<String> list = new ArrayList<>();
        if (iterator.hasNext()) {
            Iterator<Cell> cellIterator = iterator.next().cellIterator();
            int numberOfCell = 0;
            while (cellIterator.hasNext()) {
                if (numberOfCell > countGroups + 3) {
                    break;
                }
                Cell cell = cellIterator.next();
                String cellValue = cell.getStringCellValue().trim();
                if (cellValue.length() != 0) {
                    list.add(cellValue);
                }
                numberOfCell++;
            }
            return list;
        }
        return List.of();
    }

    private void distribution(NativeLesson nativeLessons) throws InterruptedException {
        if (nativeLessons.getLesson().startsWith("ч/н")) {
            convert(nativeLessons, Odds.EVEN);
        } else if (nativeLessons.getLesson().startsWith("н/н")) {
            convert(nativeLessons, Odds.ODD);
        } else {
            convert(nativeLessons, Odds.NONE);
        }
    }

    private void convert(NativeLesson nativeLesson, Odds odds) throws InterruptedException {
        String[] regex = null;
        switch (odds) {
            case NONE -> regex = regex(nativeLesson.getLesson());
            case ODD, EVEN -> regex = regex(nativeLesson.getLesson().substring(4));
        }
        convertWeek(nativeLesson, regex, odds);
    }

    private void convertWeek(NativeLesson nativeLesson, String[] regex, Odds odds) throws InterruptedException {
        String[] weeks = regex[0].split(",");
        for (String week : weeks) {
            if (!week.contains("-")) {
                if (week.endsWith("н")) {
                    week = week.replace("н", "");
                }
                createAndAddLesson(Long.parseLong(week),
                        regex[1].trim(),
                        regex[2].trim(),
                        regex[3].trim(),
                        regex[4].trim().replace("-- продолжение --", ""),
                        nativeLesson);
            } else {
                String[] fromTo = week.split("-");
                if (fromTo[1].endsWith("н")) {
                    fromTo[1] = fromTo[1].replace("н", "");
                }
                for (int l = Integer.parseInt(fromTo[0]); l <= Integer.parseInt(fromTo[1]); l++) {
                    addLessonByWeek(nativeLesson, regex, odds, l);
                }
            }
        }
    }

    private void addLessonByWeek(NativeLesson nativeLesson, String[] regex, Odds odds, int week) throws InterruptedException {
        switch (odds) {
            case ODD -> {
                if (week % 2 == 1) {
                    createAndAddLesson(week,
                            regex[1].trim(),
                            regex[2].trim(),
                            regex[3].trim(),
                            regex[4].trim().replace("-- продолжение --", ""),
                            nativeLesson);
                }
            }
            case EVEN -> {
                if (week % 2 == 0) {
                    createAndAddLesson(week,
                            regex[1].trim(),
                            regex[2].trim(),
                            regex[3].trim(),
                            regex[4].trim().replace("-- продолжение --", ""),
                            nativeLesson);
                }
            }
            case NONE -> createAndAddLesson(week,
                    regex[1].trim(),
                    regex[2].trim(),
                    regex[3].trim(),
                    regex[4].trim().replace("-- продолжение --", ""),
                    nativeLesson);
        }
    }

    private void createAndAddLesson(long week, String lesson, String type, String classRoom, String teacher, NativeLesson nativeLesson){
        NativeLesson lesson1 = new NativeLesson(nativeLesson);
        lesson1.setWeak(week);
        lesson1.setLesson(lesson);
        lesson1.setType(type);
        lesson1.setClassroom(classRoom);
        lesson1.setTeacher(teacher);
        list.add(lesson1);
    }

    private String[] regex(String str) {
        List<String> list = new ArrayList<>();
        String[] strings = str.split(" ");
        list.add(strings[0]);
        String lesson = "";
        int i = 1;
        for (; i < strings.length; i++) {
            if (!strings[i].contains("пр.з") && !strings[i].contains("лаб.") && !strings[i].contains("лек.")) {
                lesson = lesson.concat(" " + strings[i]);
            } else {
                break;
            }
        }
        list.add(lesson.trim());
        String typeOfLesson = "";
        for (; i < strings.length; i++) {
            if (!strings[i].startsWith("Сп") && !strings[i].startsWith("А") && !strings[i].startsWith("А") && !strings[i].startsWith("Б") && !strings[i].startsWith("В") && !strings[i].startsWith("Г") && !strings[i].startsWith("Д")) {
                typeOfLesson = typeOfLesson.concat(" " + strings[i]);
            } else {
                break;
            }
        }
        list.add(typeOfLesson.trim());
        list.add(strings[i]);
        i++;
        String teacher = "";
        for (; i < strings.length; i++) {
            teacher = teacher.concat(" " + strings[i]);
        }
        list.add(teacher.trim());
        return list.toArray(String[]::new);
    }
}
