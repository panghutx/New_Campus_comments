let loginname = jQuery("#loginname");
let username = jQuery("#username");
let password = jQuery("#password");

function mybtn() {

    if (jQuery.trim(loginname.val()) == "") {
        alert("请输入登录名");
        loginname.focus();
        return;
    }

    if (jQuery.trim(username.val()) == "") {
        alert("请输入用户名");
        username.focus();
        return;
    }

    if (jQuery.trim(password.val()) == "") {
        alert("请输入密码");
        password.focus();
        return;
    }
    jQuery.ajax({
        url: 'sign',
        type: 'post',
        data: {
            loginName: loginname.val(),
            username: username.val(),
            password: password.val()
        },
        success: function (body) {
            if (body == 1) {
                alert("注册成功，已为您自动登录");
                location.href="index.html";
            } else {
                alert("注册失败");
            }
        }
    })
}