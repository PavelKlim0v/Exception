<anyxmlelement xmlns:c="http://java.sun.com/jsp/jstl/core" />
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>--%>
<html>
    <head>
        <title>Title</title>
    </head>

    <body>
        <h1>Hello!</h1>
        <h2>All Users</h2>
        <c: forEach var="user" item="${requestScope.listUsers}">
            <ul>
                <li><c:out value="${user.id}"/></li>
                <li><c:out value="${user.name}"/></li>
                <li><c:out value="${user.email}"/></li>
                <li>
                    <a href="update?id=<c:out value='${user.id}' />">Edit</a>
<%--                    &nbsp;&nbsp;&nbsp;--%>
                    <a href="delete?id=<c:out value='${user.id}' />">Delete</a>
                </li>

                <form method="post" action="delete">
                    <input type="number" hidden name="id" value="${user.id}" />

                    <input type="submit" name="delete" value="Delete"/>
                </form>

                <form method="get" action="update">
                    <input type="number" hidden name="id" value="${user.id}" />

                    <input type="submit" value="Edit"/>
                </form>
            </ul>
            <hr />
        </c:>

        <h3>Add user:</h3>
        <form method="post" action="add">
            <label>Enter name:<input type="text" name="name"></label><br />

            <label>Enter email:<input type="text" name="email"></label><br />

            <label><input type="submit" value="Ok" name="Ok"></label>
        </form>
    </body>
</html>
