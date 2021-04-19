<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>New</title>
</head>
<body leftmargin="50px">
<h2>Monetary Unit</h2>
<form name="save" action="monetaryunit" method="post">
    <table border="1px">
        <tr>
            <td>Name</td>
            <td><input type = "text" name = "name"></td>
        </tr>
        <tr>
            <td>High52week</td>
            <td><input type = "text" name = "high52week"></td>
        </tr>
        <tr>
            <td>Low52week</td>
            <td><input type = "text" name = "low52week"></td>
        </tr>
        <tr>
            <td>Current</td>
            <td><input type = "text" name = "current"></td>
        </tr>
        <tr>
            <td>Code</td>
            <td><input type = "text" name = "code"></td>
        </tr>
        <tr>
            <td>Country</td>
            <td><input type = "text" name = "country" value="${element.country}"></td>
        </tr>
    </table>
    <button type="submit" name="action" value="create">Create</button>
</form>
</body>
</html>
