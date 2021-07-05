package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Meal u WHERE u.id=:id AND u.user.id=:user")
    int delete(@Param("id") int id, @Param("user") int userId);


    @Query("SELECT u FROM Meal u WHERE u.id=:id AND u.user.id=:user")
    Meal getByIdAndUserId (@Param("id") int id, @Param("user") int userId);

    @Query("SELECT u FROM Meal u WHERE u.user.id=:user ORDER BY u.dateTime DESC")
    List<Meal> getAllByAllSortedOrOrderByDateTimeDesc (@Param("user") int userId);

    @Query("select u from Meal u WHERE u.dateTime>:start_time and u.dateTime<:end_time and u.user.id=:user ORDER BY u.dateTime desc")
    List<Meal> getAllBetweenDates(
            @Param("start_time")LocalDateTime start_datetime
            ,@Param("end_time") LocalDateTime end_datetime
            ,@Param("user") int userId);
}
