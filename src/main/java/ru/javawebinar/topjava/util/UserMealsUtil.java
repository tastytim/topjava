package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );


        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(0, 0), LocalTime.of(23, 59), 2000);
        mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(0, 0), LocalTime.of(23, 59), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExcess> list = new ArrayList<>();
        for (UserMeal userMeal : meals){
            if(userMeal.getDateTime().toLocalTime().isBefore(endTime)&&userMeal.getDateTime().toLocalTime().isAfter(startTime)){
                int calAll = allCaloriesPerDay(userMeal.getDateTime().toLocalDate(), mapDates(meals));
                list.add(new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(),
                        calAll>caloriesPerDay));
            }
        }
        return list;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        return meals.stream()
                .filter(userMeal -> userMeal.getDateTime().toLocalTime().isAfter(startTime) && userMeal.getDateTime().toLocalTime().isBefore(endTime))
                .map(userMeal -> new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(),
                        allCaloriesPerDay(userMeal.getDateTime().toLocalDate(), mapDates(meals)) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    public static Map<LocalDate, Integer> mapDates (List<UserMeal> meals) {
        HashMap<LocalDate, Integer> map = new LinkedHashMap<>();
        for (UserMeal userMeal: meals){
            map.merge(userMeal.getDateTime().toLocalDate(), userMeal.getCalories(), Integer::sum);
        }
        return map;
    }

    public static int allCaloriesPerDay (LocalDate localDate, Map<LocalDate, Integer> map){
        int caloriesPerDay = 0;
        for (Map.Entry<LocalDate, Integer> entry : map.entrySet()){
            if(entry.getKey().equals(localDate)){
                 caloriesPerDay = entry.getValue();
                break;
            }
        }
        return caloriesPerDay;
    }
}
