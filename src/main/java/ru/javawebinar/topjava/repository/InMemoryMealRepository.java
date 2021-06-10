package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryMealRepository implements MealRepository {
    private static final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private static final AtomicInteger counter = new AtomicInteger(0);

    {
        save(new Meal(LocalDateTime.of(2021, Month.JUNE, 10, 10, 10), "Colazione", 500));
        save(new Meal(LocalDateTime.of(2021, Month.JUNE, 10, 22, 20), "Pranzo", 500));
        save(new Meal(LocalDateTime.of(2021, Month.JUNE, 10, 21, 30), "Cena", 1000));
        save(new Meal(LocalDateTime.of(2021, Month.JUNE, 11, 10, 24), "Colazione", 500));
        save(new Meal(LocalDateTime.of(2021, Month.JUNE, 11, 10, 10), "Pranzo", 1000));
        save(new Meal(LocalDateTime.of(2021, Month.JUNE, 11, 10, 20), "Cena", 1000));
        save(new Meal(LocalDateTime.of(2021, Month.JUNE, 12, 10, 30), "Merenda", 500));

    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        return repository.put(meal.getId(), meal);
    }

    @Override
    public void delete(int id) {
        repository.remove(id);
    }

    @Override
    public Meal get(int id) {
        return repository.get(id);
    }

    @Override
    public Collection<Meal> getAll() {
        return repository.values();
    }
}
