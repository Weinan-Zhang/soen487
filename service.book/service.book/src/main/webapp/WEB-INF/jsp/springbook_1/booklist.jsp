<%--
  Created by IntelliJ IDEA.
  User: Bruce
  Date: 2021-02-27
  Time: 12:40 a.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>book - list</title>
    <link  href="/resources/static/springbook_1/css/css.css" rel="stylesheet" type="text/css">
    <script src="/resources/static/springbook_1/js/jquery.js"></script>
    <script src="/resources/static/springbook_1/js/js.js"></script>
    <script src="/resources/static/springbook_1/js/list.js"></script>
    <style>
        .box{width:1100px; margin:50px auto;}
        .box .list{width:750px; float:left;}
        .pos a{color:#16A1E7;}
        .box .list .con{ margin:0px 0px 50px;}
        .box .list .con h3{ font-size:20px; letter-spacing:1px;}
        .box .list .con h3 a{color:#407BB7}
        .box .list .con .c{ line-height:2em; text-indent:2em; margin:10px 0px;}
        .box .list .con .f{ text-align:right;color:#666; font-size:12px;}
        .box .list .con li{border-bottom:1px  dashed #DBDBDB; padding-bottom:20px; margin-top:40px;}
    </style>
</head>

<body>
<jsp:include page="header_nav.jsp"></jsp:include>

<div class="box clear">
    <div class="list">
<%--        <div class="tit">--%>
<%--            <b>书名</b> <b>浏览次数</b> <b>文件大小</b>--%>
<%--        </div>--%>
        <ul class="con">
            <c:forEach items="${books}" var="book">
                <li>
                    <table cellspacing="10px">
                        <tr>
                            <td><span><img src="http://localhost:8000/covers/cover_${book.getCoverImg()}"></span></td>
                            <td>
                                <span>
                                    <p><b><a href="bookdetail/${book.getBid()}" class="single">${book.getTitle()}</a></b></p>
                                    <p>${book.getFormat()}</p>
                                    <p>${book.getPublisher()}</p>
                                    <p>
                                        <c:forEach items="${book.getAuthors()}" var="author">
                                            <span><a href="/listbook/author/${author.getAid()}">${author.getFirstname()}&nbsp;${author.getLastname()}</a></span>
                                        </c:forEach>
                                    </p>
                                    <p>${book.getDescription()}</p>
                                    <%--<p><span class="mar_left">23k</span><span class="red mar_left">35454</span></p>--%>
                                </span>
                            </td>
                        </tr>
                    </table>
                </li>
            </c:forEach>
        </ul><!--内容区-->

        <ul class="page clear">
            <li>首页</li>
            <li>上一页</li>
            <li><a href="javascript:;" class="thispage">1</a></li>
            <li><a href="javascript:;">2</a></li>
            <li><a href="javascript:;">3</a></li>
            <li><a href="javascript:;">4</a></li>
            <li><a href="javascript:;">5</a></li>
            <li><a href="javascript:;">下一页</a></li>
            <li><a href="javascript:;">尾页</a></li>
            <li class="tz"><select>
                <option value="1">1</option>
                <option value="1">1</option>
                <option value="1">1</option>
            </select><a href="javascript:;">跳转</a>
            </li>
        </ul><!--分页-->


    </div>

    <div class="list2">
        <div class="one">
            <p>新书上架</p>
            <div class="notice">
                <ul>
                    <li><a href="javascrip:;">文化苦旅</a></li>
                    <li><a href="javascrip:;">谢谢你曾来过我的世界</a></li>
                    <li><a href="javascrip:;">文化苦旅</a></li>
                    <li><a href="javascrip:;">文化苦旅</a></li>
                    <li><a href="javascrip:;">文化苦旅</a></li>
                    <li><a href="javascrip:;">文化苦旅</a></li>
                    <li><a href="javascrip:;">文化苦旅</a></li>
                    <!-- <li><a href="javascrip:;">文化苦旅</a></li>
                         <li><a href="javascrip:;">文化苦旅</a></li>
                         <li><a href="javascrip:;">文化苦旅</a></li>-->
                </ul>
            </div>
        </div><!--新书上架结束-->

        <div class="two">
            <p><a href="javascript:;" class="active">本周最热排行</a><a href="javascript:;">本月最热排行</a></p>
            <ul class="active">
                <li><a href="javascrip:;">文化苦旅</a></li>
                <li><a href="javascrip:;">谢谢你曾来过我的世界</a></li>
                <li><a href="javascrip:;">文化苦旅</a></li>
                <li><a href="javascrip:;">文化苦旅</a></li>
                <li><a href="javascrip:;">文化苦旅</a></li>
                <li><a href="javascrip:;">文化苦旅</a></li>
                <li><a href="javascrip:;">文化苦旅</a></li>
            </ul>
            <ul>
                <li><a href="javascrip:;">文化苦旅</a></li>
                <li><a href="javascrip:;">谢谢你曾来过我的世界</a></li>
                <li><a href="javascrip:;">文化苦旅</a></li>
                <li><a href="javascrip:;">文化苦旅</a></li>
                <li><a href="javascrip:;">文化苦旅</a></li>
                <li><a href="javascrip:;">文化苦旅</a></li>
                <li><a href="javascrip:;">谢谢你曾来过我的世界</a></li>
            </ul>
        </div><!--tab切换-->

    </div>
</div><!--左边结束-->
<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>