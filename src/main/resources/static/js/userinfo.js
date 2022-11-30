let url = location.search;
let uid = url.split('=')[1];

//根据session得到uid
//更新modify页面的url
jQuery.ajax({
    url: 'person',
    type: 'get',
    success: function (body) {
        //用户登录
        if (body != "") {
            jQuery("#modify").attr("href", "modify.html?uid=" + body + ""); 
        }
    }
})



jQuery.ajax({
    url: 'user',
    type: 'post',
    data: {
        uid: uid
    },
    success: function (body) {
        if (body != "") {
            jQuery("#loginname").val(body.loginName);
            jQuery("#username").val(body.username);
            jQuery("#head").attr("src",body.head);
        }
        //根据uid获得评论信息
        getList(uid);
    }
})

function getList(uid) {
    let info = "";
    jQuery.ajax({

        url: 'getListByUid',
        type: 'get',
        data: {
            uid: uid
        },
        success: function (body) {
            let len = body.length;
            for (let i = 0; i < len; i++) {
                let discuss = body[i];
                info += '<tr>';
                info += '<th scope="row">' + discuss.score + '</th>';
                info += '<th scope="row">' + discuss.title + '</th>';
                info += '<th scope="row">' + discuss.content + '</th>';
                info += '<th scope="row">' + discuss.createTime + '</th>';
                info += '</tr>';
            }
            jQuery("#core").html(info);
        }
    })
}