package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final MealService service;

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    public Meal get(int id){
        int userId = SecurityUtil.authUserId();
        log.info("get {}", id);
        return service.get(id, userId);
    }

    public void delete(int id){
        int userId = SecurityUtil.authUserId();
        log.info("delete {}", id);
        service.delete(id, userId);
    }

    public Collection<MealTo> getAll(){
        int userId = SecurityUtil.authUserId();
        log.info("getAll for user {}", userId);
        return MealsUtil.getTos(service.getAll(SecurityUtil.authUserId()), SecurityUtil.authUserCaloriesPerDay());
    }

    public Meal create(Meal meal){
        int userId = SecurityUtil.authUserId();
        checkNew(meal);
        log.info("create {} with id={}", meal, userId);
        return service.create(meal, userId);
    }

    public void update(Meal meal, int id) {
        int userId = SecurityUtil.authUserId();
        assureIdConsistent(meal, id);
        log.info("update {} with id={}", meal, userId);
        service.update(meal, userId);
    }

    public List<MealTo> betweenHalfOpen(@Nullable LocalDate startDate,
                                        @Nullable LocalTime startTime,
                                        @Nullable LocalDate endDate,
                                        @Nullable LocalTime endTime){
        int userId = SecurityUtil.authUserId();
        log.info("get between dates: {} - {} , and times : {} - {}", startDate, endDate, startTime, endTime);
        List<Meal> mealsDateFiltered = service.getBetweenHalfOpen(startDate, endDate, userId);
        return MealsUtil.getFilteredTos(mealsDateFiltered, SecurityUtil.authUserCaloriesPerDay(),
                startTime, endTime);
    }
}