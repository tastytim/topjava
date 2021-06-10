<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Meals</title>
    <style>
        table, th, td {
            border: 1px solid black;
        }
    </style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<p></p>
<a href="mealList?action=create">Add Meal</a>
<table border="1" cellpadding="8" cellspacing="0">
    <th>
        <tr>
            <td>Date</td>
            <td>Description</td>
            <td>Calories</td>
            <td></td>
            <td></td>
        </tr>
    </th>
    <tbody>
    <tr>
        <c:forEach var="meal" items="${requestScope.mealList}">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>
    <tr style="${meal.excess == false ? 'color: green':'color: red'}"${meal}>
        <td>
            <%= TimeUtil.toString(meal.getDateTime()) %>
        </td>
        <td>
            <c:out value="${meal.description}"/>
        </td>
        <td>
            <c:out value="${meal.calories}"/>
        </td>
        <td><a href="?action=update&id=${meal.id}"> Update</a></td>
        <td><a href="?action=delete&id=${meal.id}">Delete</a></td>
    </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
