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

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Component
public class LessonServiceSave {

    public List<NativeLesson> getNativeLesson(InputStream file) {
        try {
            OPCPackage pkg = OPCPackage.open(file);
            XSSFWorkbook book = new XSSFWorkbook(pkg);
            XSSFSheet sheet = book.getSheet("Sheet1");
            Iterator<Row> ri = sheet.rowIterator();
            List<String> weekend = List.of("ПОНЕДЕЛЬНИК", "ВТОРНИК", "СРЕДА", "ЧЕТВЕРГ", "ПЯТНИЦА", "СУББОТА");
            String week = "";
            List<NativeLesson> lessons2 = new ArrayList<>();
            List<String> group = new ArrayList<>();
            int number = 0;
            while(ri.hasNext()) {
                XSSFRow row = (XSSFRow) ri.next();

                Iterator<Cell> ci = row.cellIterator();
                int countFirst = 0;
                int countNext = 0;
                while(ci.hasNext()) {
                    XSSFCell cell = (XSSFCell) ci.next();
                    if(cell.getCellType() == CellType.STRING){
                        if(row.getRowNum() == 0 && countFirst > 3){
                            group.add(cell.getStringCellValue());
                        }
                        else {
                            if (weekend.contains(cell.getStringCellValue().trim())) {
                                week = cell.getStringCellValue().trim();
                            } else if(!cell.getStringCellValue().trim().equals("") && countNext != 3) {
                                NativeLesson lesson = new NativeLesson(group.get(cell.getColumnIndex() - 3), cell.getStringCellValue().trim(), (long) number, week);
                                lessons2.add(lesson);
                            }
                            countNext++;
                        }
                        countFirst++;
                    } else if (cell.getCellType() == CellType.NUMERIC) {
                        if(cell.getNumericCellValue() < 10){
                            number = (int) cell.getNumericCellValue();
                        }
                    }
                }
            }
            pkg.close();
            return convert(lessons2).stream().distinct().toList();
        } catch (InvalidFormatException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<NativeLesson> convert(List<NativeLesson> nativeLessons){
        Map<KeyMap, ListLesson> keyMapListLessonMap = new HashMap<>();
        List<NativeLesson> lessonList = new ArrayList<>();
        for (NativeLesson lesson : nativeLessons) {
            if (lesson.getLesson().length() == 0) {
                for (int j = 1; j <= 18; j++) {
                    NativeLesson lesson1 = new NativeLesson(lesson);
                    lesson1.setWeak((long) j);
                    addLesson(keyMapListLessonMap, lesson1);
                }
            } else if (lesson.getLesson().startsWith("ч/н")) {
                add(keyMapListLessonMap, lesson, lessonList, Odds.EVEN);
            } else if (lesson.getLesson().startsWith("н/н")) {
                add(keyMapListLessonMap, lesson, lessonList, Odds.ODD);
            } else {
                add(keyMapListLessonMap, lesson, lessonList, Odds.NONE);
            }
        }
        ArrayList<NativeLesson> lessonList1 = new ArrayList<>();
        keyMapListLessonMap.entrySet().stream().forEach(n -> {
            lessonList1.addAll(n.getValue().lessons);
        });
        return lessonList1;
    }

    private void add(Map<KeyMap, ListLesson> keyMapListLessonMap, NativeLesson nativeLesson, List<NativeLesson> nativeLessonList, Odds odds){
        String[] regex = null;
        switch (odds){
            case NONE -> regex = regex(nativeLesson.getLesson());
            case ODD,EVEN -> regex = regex(nativeLesson.getLesson().substring(4));
        }
        String[] weeks = regex[0].split(",");
        for (String week : weeks) {
            if (week.length() == 1 || week.length() == 2 || (week.length() == 3 && week.endsWith("н"))) {
                if(week.endsWith("н")){
                    week = week.replace("н", "");
                }
                NativeLesson lesson1 = new NativeLesson(nativeLesson);
                lesson1.setWeak(Long.parseLong(week));
                lesson1.setLesson(regex[1].trim());
                lesson1.setType(regex[2].trim());
                lesson1.setClassroom(regex[3].trim());
                lesson1.setTeacher(regex[4].trim().replace("-- продолжение --", ""));
                addLesson(keyMapListLessonMap, lesson1);
            } else if (week.length() == 3 || week.length() == 4 || week.length() == 5){
                String[] fromTo = week.split("-");
                if(fromTo[1].endsWith("н")){
                    fromTo[1] = fromTo[1].replace("н", "");
                }
                for (int l = Integer.parseInt(fromTo[0]); l <= Integer.parseInt(fromTo[1]); l++) {
                    switch (odds){
                        case ODD -> {
                            if(l%2 == 1){
                                NativeLesson lesson1 = new NativeLesson(nativeLesson);
                                lesson1.setWeak((long) l);
                                lesson1.setLesson(regex[1].trim());
                                lesson1.setType(regex[2].trim());
                                lesson1.setClassroom(regex[3].trim());
                                lesson1.setTeacher(regex[4].trim().replace("-- продолжение --", ""));
                                addLesson(keyMapListLessonMap, lesson1);
                            }
                        }
                        case EVEN -> {
                            if(l%2 == 0){
                                NativeLesson lesson1 = new NativeLesson(nativeLesson);
                                lesson1.setWeak((long) l);
                                lesson1.setLesson(regex[1].trim());
                                lesson1.setType(regex[2].trim());
                                lesson1.setClassroom(regex[3].trim());
                                lesson1.setTeacher(regex[4].trim().replace("-- продолжение --", ""));
                                addLesson(keyMapListLessonMap, lesson1);
                            }
                        }
                        case NONE -> {
                            NativeLesson lesson1 = new NativeLesson(nativeLesson);
                            lesson1.setWeak((long) l);
                            lesson1.setLesson(regex[1].trim());
                            lesson1.setType(regex[2].trim());
                            lesson1.setClassroom(regex[3].trim());
                            lesson1.setTeacher(regex[4].trim().replace("-- продолжение --", ""));
                            addLesson(keyMapListLessonMap, lesson1);
                        }
                    }
                }
            }
        }
    }
    public String[] regex(String str){
        List<String> list = new ArrayList<>();
        String[] strings = str.split(" ");
        list.add(strings[0]);
        String lesson = "";
        int i = 1;
        for (;i< strings.length;i++) {
            if(!strings[i].equals("пр.з") && !strings[i].equals("лаб.") && !strings[i].equals("лек.")){
                lesson += " " + strings[i];
            }
            else {
                break;
            }
        }
        list.add(lesson.trim());
        String typeOfLesson = "";
        for (;i< strings.length;i++){
            if(!strings[i].startsWith("А") && !strings[i].startsWith("Б") && !strings[i].startsWith("В") && !strings[i].startsWith("Г") && !strings[i].startsWith("Д")){
                typeOfLesson += " " + strings[i];
            }
            else {
                break;
            }
        }
        list.add(typeOfLesson.trim());
        list.add(strings[i]);
        i++;
        String teacher = "";
        for(;i < strings.length;i++){
            teacher += " " + strings[i];
        }
        list.add(teacher.trim());
        return list.toArray(String[]::new);
    }

    public static void addLesson(Map<KeyMap, ListLesson> keyMapListLessonMap, NativeLesson lesson1){
        KeyMap keyMap = new KeyMap(lesson1.getNumber(), lesson1.getGroup(), lesson1.getDay(), lesson1.getWeak());
        if(keyMapListLessonMap.containsKey(keyMap)){
            ListLesson listLesson = keyMapListLessonMap.get(keyMap);
            List<Integer> list = new ArrayList<>();
            if(listLesson.lessons.size() != 0 && !lesson1.getLesson().equals("")){
                for(int i = 0; i < listLesson.lessons.size();i++){
                    if(listLesson.lessons.get(i).getLesson().equals("")){
                        list.add(i);
                    }
                }
                for(Integer integer: list){
                    listLesson.lessons.remove(integer.intValue());
                }
                listLesson.lessons.add(lesson1);
                keyMapListLessonMap.remove(keyMap);
                keyMapListLessonMap.put(keyMap, listLesson);
            }
        }
        else {
            ListLesson listLesson = new ListLesson();
            listLesson.lessons.add(lesson1);
            keyMapListLessonMap.put(keyMap, listLesson);
        }
    }
}
