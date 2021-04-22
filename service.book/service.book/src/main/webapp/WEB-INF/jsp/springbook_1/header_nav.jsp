<%--
  Created by IntelliJ IDEA.
  User: Bruce
  Date: 2021-02-28
  Time: 7:13 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>header and navigation bar part</title>
    <script src="https://cdn.bootcss.com/axios/0.17.1/axios.min.js"></script>
    <script>
        window.onload = function(){
            let jwt =  localStorage.getItem('jwt');
            if (jwt=== null) {
                document.querySelector('#logout').style.display = 'none';
                document.querySelector('#nologin').style.display = 'block';
            } else {
                document.querySelector('#logout').style.display = 'block';
                document.querySelector('#nologin').style.display = 'none';
            }
        }

        function logout(){
            localStorage.removeItem('jwt');
            // document.querySelector('#logout').style.display = 'none';
            // document.querySelector('#nologin').style.display = 'block';
            // window.location = "/login"
        }
    </script>
</head>
<body>
<header class="clear">
    <img src="/resources/static/springbook_1/img/logo.jpg" >
    <div class="logo">
        <%--        <h1>XXX</h1>--%>
        <%--        <div class="clear"></div>--%>
        <%--        <p>想看既看，想听既听得阅读</p>--%>
    </div>
    <div class="search left">
        <form  action="/book/search" method="post">
            <input type="text" value="" placeholder="find a book by title or author" name="keyword">
            <input type="submit" value="search">
        </form>
    </div>
    <div class="reg">
        <div class="delu" id="nologin">
            <a  href="/signup">Sign up</a>
            <span>|</span>
            <a  href="/login">Log in</a>
        </div>
        <div class="delu" id="logout">
            <a  href="<c:url value="/logout" />" onclick="logout()">log out</a>
        </div>
    </div>
</header><!--头部结束-->

<nav class="clear">
    <ul class="nav_1 clear">
        <div class="active"></div>
        <li><a href="/">Home</a></li>
        <c:forEach items="${categories}" var="category">
            <li><a href="/list/category/${category.getCid()}">${category.getName()}</a>
                <ul class="clear">
                    <c:forEach items="${category.getCategories()}" var="subcategory">
                        <li><a href="/list/category/${subcategory.getCid()}">${subcategory.getName()}</a></li>
                    </c:forEach>
                </ul>
            </li>
        </c:forEach>
        <div class="submission">
            <a href="/addbook"><span class="icon_span"></span>Publish new book</a>
        </div>
    </ul>
</nav><!--导航结束-->
</body>
</html>
