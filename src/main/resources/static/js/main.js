//根据session得到uid
//获取登录状态
function checkLogin() {
    jQuery.ajax({
        url: 'person',
        type: 'get',
        success: function (body) {
            //用户登录
            if (body != "") {
                jQuery("#personInfo").attr("href", "userinfo.html?uid=" + body + "");
                jQuery("#loginList").css("display", "none");
                jQuery("#personList").css("display", "inline");
            } else {
                jQuery("#loginList").css("display", "inline");
                jQuery("#personList").css("display", "none");
            }
        }
    })
}

//退出登录
function logout() {
    jQuery.ajax({
        url: 'logout',
        type: 'get',
        success: function (body) {
            alert("退出成功");
            location.href = "index.html";
        }
    })
}