package ru.javawebinar.topjava.model;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@NamedQueries({
        @NamedQuery(name = Meal.DELETE, query = "DELETE FROM Meal u WHERE u.id=:id AND u.user.id=:user"),
        @NamedQuery(name = Meal.ALL_SORTED, query = "SELECT u FROM Meal u WHERE u.user.id=:user ORDER BY u.dateTime desc"),
        @NamedQuery(name = Meal.GET, query = "SELECT u FROM Meal u  WHERE u.id=:id AND u.user.id=:user"),
        @NamedQuery(name = Meal.GET_BETWEEN, query = "SELECT u FROM Meal u WHERE u.dateTime>?1 AND u.dateTime<?2 AND u.user.id=?3 ORDER BY u.dateTime DESC")
})

@Entity
@Table(name = "meals", uniqueConstraints = {@UniqueConstraint(columnNames = {"date_time","user_id"}, name = "meals_unique_user_datetime_idx")})
public class Meal extends AbstractBaseEntity  {

    public static final String DELETE = "Meal.delete";
    public static final String ALL_SORTED = "Meal.getAllSorted";
    public static final String GET = "Meal.get";
    public static final String GET_BETWEEN = "Meal.getBetween";

    @Column(name = "date_time", nullable = false, unique = true, columnDefinition = "timestamp default now()")
    @NotNull
    private LocalDateTime dateTime;


    @Column(name = "description", nullable = false)
    @NotNull
    private String description;


    @Column(name = "calories", nullable = false)
    @NotNull
    private int calories;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Meal() {
    }

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }

    public Meal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
