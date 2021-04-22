// JavaScript Document
// window.onload=function()
// {
// 	var box=getClass(document,'div','box')[0];
// 	var body_h=window.innerHeight; //浏览器窗口大小
// 	var box_h=wh(box).h;
// 	var box_t=wh(box).t;
// 	var foot=document.getElementsByTagName('footer')[0];
// 	var foot_h=wh(foot).h;
// 	var other=box_t+foot_h+box_h;
//
// 	var zj_h=body_h-box_t-foot_h;//需要增加的高度
// 	if(other<body_h)
// 	{
// 		css(box,'height',zj_h);
// 	}
// }
// function reg(obj,re)
// {
// 	var val='';
// 	var kg=/^\s*$/;
// 	obj.blur(function()
// 	{
// 		val=$(this).val();
//
// 		if(!kg.test(val))
// 		{
// 			if(!re.test(val))
// 			{
// 			   $(this).siblings('.none').slideDown('slow');
// 			   $(this).siblings('.none').find('i').css('color','red');
// 			}
//
// 		}else
// 		{
// 			$(this).siblings('.none').slideDown('slow');
// 		}
// 	})
//
//     obj_focus(obj);
// }//-----------------------------------必填项
//
// function equal(obj,obj2)
// {
// 	obj.blur(function()
// 	{
// 		if(obj.val()!==obj2.val())
// 		{
// 			$(this).siblings('.none').slideDown('slow');
// 			$(this).siblings('.none').find('i').css('color','red');
// 		}
// 	});
//    obj_focus(obj);
//  }//----------------------判断密码是否一致
//
// function obj_focus(obj)
// {
// 	obj.focus(function()
// 	{
// 		$(this).siblings('.none').removeAttr('style');
// 		$(this).siblings('.none').find('i').removeAttr('style');
// 	});
// }//------------------------------------再次获取光标


// function signup() {
//     document.querySelector('#sub-btn').onclick = function() {
//         const username_id = '#username';
//         const email_id = '#email';
//         const password_id = 'password';
//         let username = document.querySelector(username_id).value;
//         let email = document.querySelector(email_id).value;
//         let password = document.querySelector(password_id).value;
//         const JWT_HEADER = 'authorization';
//         const JWT_LOCAL_STORAGE_NAME = 'jwt';
//         const JWT_SUCCESS_REDIRECT_URL = "/";
//         const JWT_FAIL_REDIRECT_URL = "/login";
//
//         var params = new URLSearchParams();
//         params.append('username', username);
//         params.append('email', email);
//         params.append('password', password);
//
//         axios.post(
//             '/signup',
//             params
//         )
//             .then((response) => {
//                 console.log(response.headers[JWT_HEADER]===undefined);
//                 if (response.headers[JWT_HEADER]!==undefined) {
//                     // 本地存储token
//                     localStorage.setItem(JWT_LOCAL_STORAGE_NAME, response.headers[JWT_HEADER]);
//                     // 把token加入header里
//                     axios.defaults.headers[JWT_HEADER] = response.headers[JWT_HEADER];
//                     window.location = JWT_SUCCESS_REDIRECT_URL;
//                 } else {
//                     window.location = JWT_FAIL_REDIRECT_URL;
//                 }
//             })
//             .catch(function (error) {
//                 console.log(error);
//             });
//     }
// }




