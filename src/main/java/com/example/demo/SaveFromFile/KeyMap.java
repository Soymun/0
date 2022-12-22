package com.example.demo.SaveFromFile;

import lombok.Data;

import java.util.Objects;

@Data
public class KeyMap {

    private final Long number;

    private final String group;

    private final String day;

    private final Long week;

    public KeyMap(Long number, String group, String day, Long week) {
        this.number = number;
        this.group = group;
        this.day = day;
        this.week = week;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KeyMap keyMap = (KeyMap) o;

        if (!Objects.equals(number, keyMap.number)) return false;
        if (!Objects.equals(group, keyMap.group)) return false;
        if (!Objects.equals(day, keyMap.day)) return false;
        return Objects.equals(week, keyMap.week);
    }

    @Override
    public int hashCode() {
        int result = number != null ? number.hashCode() : 0;
        result = 31 * result + (group != null ? group.hashCode() : 0);
        result = 31 * result + (day != null ? day.hashCode() : 0);
        result = 31 * result + (week != null ? week.hashCode() : 0);
        return result;
    }
}
