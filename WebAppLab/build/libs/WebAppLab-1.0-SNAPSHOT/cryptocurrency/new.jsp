<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>New</title>
</head>
<body leftmargin="50px">
<h2>Cryptocurrency</h2>
<form name="save" action="cryptocurrency" method="post">
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
    </table>
    <button type="submit" name="action" value="create">Create</button>
</form>
</body>
</html>
