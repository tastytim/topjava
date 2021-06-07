package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DbUtils {
    private static final List<MealTo> All_MEALS = new ArrayList<>();
    private static final int CALORIES = 2000;

    public static List<MealTo> getAll_MEALS() {
        return All_MEALS;
    }

    public static int getCALORIES() {
        return CALORIES;
    }

    static {
        initData();
    }

    // Симуляция данных в Database.
    private static void initData() {
        List<Meal> list = Arrays.asList(
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        All_MEALS.addAll(MealsUtil.filteredByStreams(list, LocalTime.of(0,0), LocalTime.of(23,59), getCALORIES()));
    }
}
