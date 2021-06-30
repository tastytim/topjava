package ru.javawebinar.topjava.repository.jpa;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.Util;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class JpaMealRepositoryTest implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(JpaMealRepositoryTest.class);


    private final Map<Integer, Map<Integer, Meal>> usersMealsMap = new ConcurrentHashMap<>();
    protected  final AtomicInteger counter = new AtomicInteger(START_SEQ);

    {
        Map<Integer, Meal> userMeals = new ConcurrentHashMap<>();
        MealTestData.meals.forEach(meal -> userMeals.put(meal.getId(), meal));
        usersMealsMap.put(UserTestData.USER_ID, userMeals);
    }

    @PostConstruct
    public void postConstruct() {
        log.info("+++ PostConstruct");
    }

    @PreDestroy
    public void preDestroy() {
        log.info("+++ PreDestroy");
    }


    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        Objects.requireNonNull(meal, "meal must not be null");
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            Map<Integer, Meal> map = new ConcurrentHashMap<>();
            map.put(meal.id(), meal);
            usersMealsMap.put(userId, map);
            log.info("SAVE MEAL{} WHERE USER {}",meal,userId);
            return meal;
        }
        Map<Integer, Meal> map = new ConcurrentHashMap<>();
        map.put(meal.id(), meal);
        usersMealsMap.put(userId, map);
        log.info("UPDATE MEAL{} WHERE USER {}",meal,userId);
        return meal;
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        Map<Integer, Meal> meals = usersMealsMap.get(userId);
        log.info("DELETE ID{} FROM USER {}",id,userId);
        return meals!=null && meals.remove(id) !=null;
    }

    @Override
    public Meal get(int id, int userId) {
        Map<Integer, Meal> mealMap = usersMealsMap.get(userId);
        log.info("GET ID{} FROM USER {} WHERE LIST {}",id,userId,mealMap);
        return mealMap == null? null : mealMap.get(id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        Map<Integer, Meal> meals = usersMealsMap.get(userId);
        List<Meal> mealList = meals.values().stream()
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
        log.info("GET_ALL LIST {} FROM USER {}",mealList, userId);
        return mealList;
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        log.info(
                "GET_ALL MEALS{} ORDER BY START_TIME{} AND END_TIME{} WHERE USER {}",
                filterByPredicate(userId, meal -> Util.isBetweenHalfOpen(meal.getDateTime(),
                        startDateTime, endDateTime)),startDateTime,endDateTime,userId);
        return filterByPredicate(userId, meal -> Util.isBetweenHalfOpen(meal.getDateTime(), startDateTime, endDateTime));
    }

    private List<Meal> filterByPredicate(int userId, Predicate<Meal> filter) {
        Map<Integer, Meal> meals = usersMealsMap.get(userId);
        return meals.values().stream()
                .filter(filter)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}