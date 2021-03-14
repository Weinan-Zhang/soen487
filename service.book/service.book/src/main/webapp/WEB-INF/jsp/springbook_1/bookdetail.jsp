<%--
  Created by IntelliJ IDEA.
  User: Bruce
  Date: 2021-02-27
  Time: 8:51 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>文章内容区</title>
    <link  href="/resources/static/springbook_1/css/css.css" rel="stylesheet" type="text/css">
    <link href="/resources/static/springbook_1/css/arcticle.css" rel="stylesheet" type="text/css">

    <!--<link rel="stylesheet" href="kindeditor-4.1.10/themes/default/default.css" />
    <script charset="utf-8"  src="kindeditor-4.1.10/kindeditor-min.js"></script>
    <script>
                var editor;
                KindEditor.ready(function(K) {
                    editor = K.create('textarea[name="content"]', {
                        resizeType : 1,
                        allowPreviewEmoticons : false,
                        allowImageUpload : false,
                        items : [
                            'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
                            'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
                            'insertunorderedlist', '|', 'emoticons', 'image', 'link']
                    });

                });
    </script>	-->
    <script src="/resources/static/springbook_1/js/jquery.js"></script>
    <script src="/resources/static/springbook_1/js/js.js"></script>
    <script src="/resources/static/springbook_1/js/list.js"></script>
    <script src="/resources/static/springbook_1/js/comment.js"></script>
</head>

<body>
<jsp:include page="header_nav.jsp"></jsp:include>

<div class="box clear">
    <div class="list">
        <h3>${book.getTitle()}</h3>
        <div class="tab_1">
            <div class="info clear">
                <img src="http://localhost:8000/covers/cover_${book.getCoverImg()}" class="left" style="margin-right:20px;width:150px;">
                <p class="left" style="width:580px; overflow:hidden;">
                    ${book.getDescription()}
                    ${book.getDescription()}
                    ${book.getDescription()}
                    ${book.getDescription()}
                    ${book.getDescription()}
                    ${book.getDescription()}
                    ${book.getDescription()}
                    ${book.getDescription()}
                    ${book.getDescription()}
                    ${book.getDescription()}
                    ${book.getDescription()}
                    ${book.getDescription()}
                    ${book.getDescription()}
                    ${book.getDescription()}
                    ${book.getDescription()}
                    ${book.getDescription()}
                    ${book.getDescription()}
                    ${book.getDescription()}
                    ${book.getDescription()}
                    ${book.getDescription()}
                    ${book.getDescription()}
                    ${book.getDescription()}
                    ${book.getDescription()}
                    ${book.getDescription()}
                </p>
                <p></p>
                <ul class="clear info_1">
                    <p><b>Detail information:</b></p>
                    <li><span>Author：</span>
                        <c:forEach items="${book.getAuthors()}" var="author">
                            &nbsp;<a href="/listbook/author/${author.getAid()}">${author.getFirstname()} ${author.getLastname()}</a>
                        </c:forEach>
                    </li>
                    <li><span>Publisher：</span>${book.getPublisher()}</li>
                    <li><span>Format：</span>${book.getFormat()}</li>
                    <li><span>Language：</span>${book.getLanguage()}</li>
                    <li><span>ISBN：</span>${book.getIsbn13()}</li>
                    <li><span>Price：</span>${book.getOriginalPrice()}</li>
                </ul>
            </div>
        </div><!--简介介绍-->
<%--        <div class="tab_1 ">--%>
<%--            <div class="tit">评论<b>(345)</b></div>--%>
<%--        </div>--%>
        <div class="comment">
            <div class="com_con clear zong">
<%--                <div class="portrait left mar_top">--%>
<%--                    <img src="../../../static/springbook_1/img/avatar.jpg"><br/>--%>
<%--                    <span>1922527784</span>--%>
<%--                </div>--%>
                <table width="40%" align="center">
                    <tr>
                        <td >
                            <form action="/modifybook/${book.getBid()}" method="get" class="kdit_w">
                                <span><input type="submit" value="modify" class="ease" /></span>
                            </form>
                        </td>
                        <td>
                            <form action="/deletebook" method="post" class="kdit_w">
                                <span>
                                    <input type="submit" value="delete" class="ease" >
                                </span>
                                <input type="hidden" name="bid" value="${book.getBid()}"/>
                                <input type="hidden" name="_method" value="delete"/>
                            </form>
                        </td>
                    </tr>
                </table>
            </div>
        </div><!--评论结束-->
    </div><!--右边结束-->
    <jsp:include page="right_side_list.jsp"></jsp:include>
</div><!--中间box 结束-->
<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>