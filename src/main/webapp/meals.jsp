
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Meals</title>
    <style>
        table, th, td {
            border: 1px black;
        }
    </style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<p></p>
<table >
    <th>
        <tr>
            <td>DateAndTime</td>
            <td>Description</td>
            <td>Calories</td>
            <td></td>
            <td></td>
        </tr>
    </th>
    <tbody>
    <c:forEach var="list" items="${list}">
    <tr style="${list.excess == false ? 'color: green':'color: red'}"${list}/></>
        <td>
            <c:out value="${list.dateTime.toString().replace('T',' ')}"/>
        </td>
        <td>
            <c:out value="${list.description}"/>
        </td>
        <td>
            <c:out value="${list.calories}"/>
        </td>
        <td>Update</td>
        <td>Delete</td>
    </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
