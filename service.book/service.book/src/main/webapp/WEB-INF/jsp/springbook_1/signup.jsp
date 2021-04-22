<%--
  Created by IntelliJ IDEA.
  User: Bruce
  Date: 2021-03-29
  Time: 12:08 a.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>sign up</title>
    <link href="/resources/static/springbook_1/css/css.css" rel="stylesheet" type="text/css">
    <link href="/resources/static/springbook_1/css/sign.css" rel="stylesheet" type="text/css">
    <script src="/resources/static/springbook_1/js/jquery.js"></script>
    <script src="/resources/static/springbook_1/js/js.js"></script>
<%--    <script type="text/javascript" src="/resources/static/springbook_1/js/sign.js"></script>--%>
    <script src="https://cdn.bootcss.com/axios/0.17.1/axios.min.js"></script>

    <script>
        function sign_up() {
            let username = document.querySelector('#username').value;
            let email = document.querySelector('#email').value;
            let password = document.querySelector('#password').value;

            var params = new URLSearchParams();
            params.append('username', username);
            params.append('email', email);
            params.append('password', password);

            axios.post(
                '/signup',
                params
            )
                .then((response) => {
                    console.log(response.headers['authorization']);
                    if (response.headers['authorization']!=undefined) {
                        // 本地存储token
                        localStorage.setItem('jwt', response.headers['authorization']);
                        // 把token加入header里
                        axios.defaults.headers['authorization'] = response.headers['authorization'];
                        axios.get('/')
                            .then((response) => {
                                console.log(response.data);
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
    <style>
        .submit{ margin-left:82px;}
    </style>
</head>

<body>
<jsp:include page="header_nav.jsp"></jsp:include>
<div class="box">
<%--    <h3>注册账号   <span class="right">还没有账号 点击<a href="javascript:;">注册</a></span></h3>--%>

    <div class="menu_3">
        <form  id="user_register" name="signup" action="/signup" method="post">
            <p><span>Username:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span> <input type="text" name="username" value="" class="text_1" placeholder="username"  id="username" autocomplete="off" required>
                <br/><span  class="none">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<em>*</em><i>please enter correct username</i></span>
            </p>
            <p><span>Email:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span> <input type="email" name="email"  class="text_2"  id="email" autocomplete="off" required>
                <br/><span  class="none">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<em>*</em><i>please enter correct email</i></span>
            </p>
            <p><span>Password:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span> <input type="password" name="password"  class="text_3"  id="password" autocomplete="off" required>
                <br/><span  class="none">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<em>*</em><i>please enter correct password - 6-9 characters combined with  underscore and number</i></span>
            </p>
            <p><span>Confirm password：</span> <input type="password" name="password2"  class="text_3"  id="repassword" autocomplete="off" required>
                <br/><span  class="none">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<em>*</em><i>The passwords entered are not same</i></span>
            </p>
            <p><input type="button" id="sub-btn" value="submit" class="submit" onclick="sign_up()"></p>
        </form>
    </div>
</div>
<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>
