<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>List</title>
</head>
<body leftmargin="50px">
<h2>Monetary Unit</h2>

<table>
    <tr>
        <td>
            <form name="task1" action="monetaryunit" method="post">
                <button type="submit" name="action" value="task1">Maximum percentage year difference</button>
            </form>
        </td>
        <td>
            <form name="task2" action="monetaryunit" method="post">
                <button type="submit" name="action" value="task2">Price per unit</button>
            </form>
        </td>
    </tr>
</table>

<table border="1px">
    <c:if test="${elements.size() != 0}">
        <tr>
            <td>Name</td>
            <td>High52week</td>
            <td>Low52week</td>
            <td>Current</td>
            <td>Code</td>
            <td>Country</td>
            <td>Actions</td>
        </tr>
        <c:forEach var="elem" items="${elements}">
            <tr>
                <td><c:out value="${elem.name}"/></td>
                <td><c:out value="${elem.price.high52week}"/></td>
                <td><c:out value="${elem.price.low52week}"/></td>
                <td><c:out value="${elem.price.current}"/></td>
                <td><c:out value="${elem.code}"/></td>
                <td><c:out value="${elem.country}"/></td>
                <td>
                    <form name="edit" action="monetaryunit" method="post">
                        <input type="hidden" name="action" value="edit"/>
                        <button type="submit" name="uuid" value="${elem.uuid}">Edit</button>
                    </form>
                    <form name="delete" action="monetaryunit" method="post">
                        <input type="hidden" name="action" value="delete"/>
                        <button type="submit" name="uuid" value="${elem.uuid}">Delete</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </c:if>
</table>
<c:if test="${elements.size() == 0}">
    <p>Empty</p>
</c:if>
<form name="new" action="monetaryunit" method="post">
    <button type="submit" name="action" value="new">New</button>
</form>
</body>
</html>
