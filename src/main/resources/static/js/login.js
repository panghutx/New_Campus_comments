let loginname = jQuery("#loginname");
let password = jQuery("#password");


function mybtn() {

    if (jQuery.trim(loginname.val()) == "") {
        alert("请输入登录名");
        loginname.focus();
        return;
    }

    if (jQuery.trim(password.val()) == "") {
        alert("请输入密码");
        password.focus();
        return;
    }

    jQuery.ajax({
        url: 'login',
        type: 'post',
        data: {
            loginName: loginname.val(),
            password: password.val()
        },
        success: function (body) {
            if (body == true) {
                location.href = "index.html";
            } else {
                alert("登录失败，用户名或密码错误");
            }
        }
    })
}