<%--
  Created by IntelliJ IDEA.
  User: Bruce
  Date: 2021-02-28
  Time: 7:13 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>header and navigation bar part</title>
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
        <div class="delu">
            <a  href="/signup">Sign up</a>
            <span>|</span>
            <a  href="/login">Log in</a>
        </div>
    </div>
</header><!--头部结束-->

<nav class="clear">
    <ul class="nav_1 clear">
        <div class="active"></div>
        <li><a href="/">Home</a></li>
        <li><a href="/categorylist/1">Literature</a>
            <ul class="clear">
                <li><a href="/categorylist/2">Literature1</a></li>
            </ul>
        </li>
        <li><a href="/categorylist/3">Sociology</a>
            <ul class="clear">
                <li><a href="/categorylist/4">Sociology1</a></li>
            </ul>
        </li>
        <li><a href="/categorylist/5">Psychology</a>
            <ul class="clear">
                <li><a href="/categorylist/6">Psychology1</a></li>
            </ul>
        </li>
        <li><a href="/categorylist/7">Economics</a>
            <ul class="clear">
                <li><a href="/categorylist/8">Economics1</a></li>
            </ul>
        </li>
        <li><a href="/categorylist/9">Sciences</a>
            <ul class="clear">
                <li><a href="/categorylist/10">Sciences1</a></li>
            </ul>
        </li>
        <li><a href="/categorylist/11">History</a>
            <ul class="clear">
                <li><a href="/categorylist/12">History1</a></li>
            </ul>
        </li>
        <li><a href="/categorylist/13">Other</a>
            <ul class="clear">
                <li><a href="/categorylist/14">Other1</a></li>
            </ul>
        </li>
        <div class="submission">
            <a href="/addbook"><span class="icon_span"></span>Publish new book</a>
        </div>
    </ul>
</nav><!--导航结束-->
</body>
</html>
