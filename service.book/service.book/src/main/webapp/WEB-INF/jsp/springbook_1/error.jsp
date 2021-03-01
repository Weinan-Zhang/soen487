<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>Springbook</title>
    <link  href="/resources/static/springbook_1/css/css.css" rel="stylesheet" type="text/css">
    <link  href="/resources/static/springbook_1/css/index.css" rel="stylesheet" type="text/css">
    <script src="/resources/static/springbook_1/js/jquery.js"></script>
    <script src="/resources/static/springbook_1/js/js.js"></script>
<%--    <script src="/resources/static/springbook_1/js"></script>--%>

    <!--[if lt IE 9]>
    <script>for(var i=0,t="abbr, article, aside, audio, canvas, datalist, details, dialog, eventsource, figure, footer, header, hgroup, mark, menu, meter, nav, output, progress, section, time, video, figcaption, main".split(", ");i<t.length;i++){document.createElement(t[i]);}</script>
    <![endif]-->
    <style>
        .err {
            width: 100%;
            line-height: 200px;
            color: red;
            margin: 0 auto;
            text-align: center;
        }
    </style>
</head>

<!--[if lte IE 9]>
<body class="ie9-lt ">
<![endif]-->
<body>
<jsp:include page="header_nav.jsp"></jsp:include>
<div class="err">
    <h1>${info}</h1>
</div>
<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>

