<!doctype html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title>登录</title>
    <link href="/resources/static/springbook_1/css/css.css" rel="stylesheet" type="text/css">
    <link href="/resources/static/springbook_1/css/sign.css" rel="stylesheet" type="text/css">
    <script src="/resources/static/springbook_1/js/jquery.js"></script>
    <script src="/resources/static/springbook_1/js/js.js"></script>
    <script src="https://cdn.bootcss.com/axios/0.17.1/axios.min.js"></script>
    <style>
        .submit{ margin-left:73px;}
    </style>
    <script>

        function login() {
            let username = document.querySelector('#username').value;
            let password = document.querySelector('#password').value;

            var params = new URLSearchParams();
            params.append('username', username);
            params.append('password', password);

            let jwt =  localStorage.getItem('jwt');

            axios.post(
                '/login',
                params
            )
                .then((response) => {
                    // console.log(response.headers['authorization']);
                    if (response.headers['authorization']!=undefined) {
                        // 本地存储token
                        localStorage.setItem('jwt', response.headers['authorization']);
                        // 把token加入header里
                        axios.defaults.headers['authorization'] = response.headers['authorization'];
                        axios.get('/')
                            .then((response) => {
                                // console.log(response.data);
                                window.location = "/";
                            })
                            .catch(function (error){
                                console.log(error);
                            });

                    } else {
                        window.location = "/login";
                    }
                })
                .catch(function (error) {
                    console.log(error);
                });
        }
    </script>
</head>

<body>
<jsp:include page="header_nav.jsp"></jsp:include>
<!--头部结束-->
<div class="box">
<%--    <h3>User Login</h3>--%>
    <div class="menu_3">
        <form  id="user_login" action="/login" method="post">
            <p><span>Username：</span> <input type="text" name="username" id="username" value="" class="text_1" placeholder="username">
                <br/><span  class="none"> <em>*</em><i>please enter correct username</i></span>
            </p>
            <p><span>Password：&nbsp;</span> <input type="password" name="password" id="password" class="text_3" placeholder="password">
                <br/><span  class="none">  <em>*</em><i>please enter correct password</i></span>
            </p>
            <p style=" margin-left:70px;"><input  type="checkbox" value="1" name="cookie">&nbsp;remember me | <a href="pass.html">forget password?</a></p>
            <p><input type="button" value="login" class="submit" onclick="login()"></p>
<%--            <p><span>使用其他帐号直接登录</span>  <a href="javascript:;">腾讯QQ</a> <a href="javascript:;">新浪微博</a></p>--%>
        </form>
    </div>
</div>
<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>
