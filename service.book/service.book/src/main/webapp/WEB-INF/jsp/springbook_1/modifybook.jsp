<%--
  Created by IntelliJ IDEA.
  User: Bruce
  Date: 2021-02-26
  Time: 9:30 a.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>注册</title>
    <link href="/resources/static/springbook_1/css/css.css" rel="stylesheet" type="text/css">
    <link href="/resources/static/springbook_1/css/sign.css" rel="stylesheet" type="text/css">
    <script src="/resources/static/springbook_1/js/jquery.js"></script>
    <script src="/resources/static/springbook_1/js/js.js"></script>
<%--    <script src="/resources/static/springbook_1/js/sign.js"></script>--%>
    <style>
        .submit{ margin-left:82px;}
    </style>
</head>

<body>
<jsp:include page="header_nav.jsp"></jsp:include>
<div class="box">
    <h3>Modify book</h3>
    <div class="menu_3_t">
        <form id="add_book_form" enctype="multipart/form-data" action="/book/modify" method="post">
            <p><span>Title:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
                <input type="text" name="title" value="${book.getTitle()}" class="text_1" autocomplete="on"/>
            </p>
            <p><span>Authors:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
                <input type="text" name="authors" value="${authors}" class="text_1" autocomplete="on"/>
            </p>
            <p><span>Publisher:&nbsp;&nbsp;&nbsp;&nbsp;</span>
                <input type="text" name="publisher" value="${book.getPublisher()}" class="text_1" autocomplete="on"/>
            </p>
            <p><span>Format:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
                <input type="text" name="format" value="${book.getFormat()}" class="text_1" autocomplete="on"/>
            </p>
            <p><span>Language:&nbsp;&nbsp;&nbsp;</span>
                <input type="text" name="language" value="${book.getLanguage()}" class="text_1" autocomplete="on"/>
            </p>
            <p><span>Isbn:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
                <input type="text" name="isbn" value="${book.getIsbn13()}" class="text_1"   autocomplete="on"/>
            </p>
<%--            <p><span>Description: </span>--%>
<%--                <textarea name="description"  class="text_1" value="${book.getDescription()}" rows="4" cols="50">--%>
<%--                    ${book.getDescription()}--%>
<%--                </textarea>--%>
<%--            </p>--%>
            <p><span>Description: </span>
                <textarea name="description" class="text_1" value="${book.getDescription()}">${book.getDescription()}</textarea>
            </p>
            <p>
                <span><img src="http://localhost:8000/covers/cover_${book.getCoverImg()}"></span>
            </p>
<%--            <div class="clear">--%>
<%--                <label class="left">Cover image: </label>--%>
<%--            </div><!--设置封面-->--%>
            <div class="up_img">
                <span  class="icon up_img1">Upload Cover Image</span>
                <input type="file" name="up_img" class="up_img2" onchange="getFilename()">
                <br/><i style="margin-left:0px;">file ≤ 3M </i><!--当改变时，上传图片，ajax提交返回图片地址-->
            </div>
            <p>
                <input type="submit"  value="submit"  class="submit">
                <input type="hidden" name="bid" value="${book.getBid()}"/>
                <input type="hidden" name="_method" value="put"/>
            </p>
        </form>
    </div>
</div>
<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>
