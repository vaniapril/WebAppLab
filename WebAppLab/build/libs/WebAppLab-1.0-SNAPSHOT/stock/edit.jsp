<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Edit</title>
</head>
<body leftmargin="50px">
<h2>Stock</h2>
<form name="save" action="stock" method="post">
    <table border="1px">
        <tr>
            <td>Name</td>
            <td><input type = "text" name = "name" value="${element.name}"></td>
        </tr>
        <tr>
            <td>High52week</td>
            <td><input type = "text" name = "high52week" value="${element.price.high52week}"></td>
        </tr>
        <tr>
            <td>Low52week</td>
            <td><input type = "text" name = "low52week" value="${element.price.low52week}"></td>
        </tr>
        <tr>
            <td>Current</td>
            <td><input type = "text" name = "current" value="${element.price.current}"></td>
        </tr>
        <tr>
            <td>Code</td>
            <td><input type = "text" name = "code" value="${element.code}"></td>
        </tr>
    </table>
    <input type="hidden" name="uuid" value="${element.uuid}"/>
    <button type="submit" name="action" value="save" >Save</button>
</form>

</body>
</html>
