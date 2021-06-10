<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <title>Edit meal</title>
</head>
<body>
<script>
    $(function () {
        $('input[name=dateTime]').datepicker();
    });
</script>
<jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.Meal"/>
<form method="post" action="">
    <input type="hidden" name="id" value="${meal.id}">
    <dl>
        <dt>DateTime:</dt>
        <dd><input type="datetime-local" value="${meal.dateTime}" name="dateTime"/></dd>
    </dl>
    <dl>
        <dt>Description</dt>
        <dd><input type="text" value="${meal.description}" name="description"></dd>
    </dl>
    <dl>
        <dt>Calories</dt>
        <dd><input type="number" value="${meal.calories}" name="calories"></dd>
    </dl>
    <button type="submit">Save</button>
    <button onclick="window.history.back()">Cancel</button>
</form>
</body>
</html>