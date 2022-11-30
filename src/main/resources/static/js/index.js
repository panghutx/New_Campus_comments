

        //提交数据
        let title = jQuery("#title");
        let score = jQuery("#score");
        let content = jQuery("#content");
        //let status = jQuery("input[name = status]:checked").val();

        checkLogin();

        function submit() {
            //检测提交数据
            if (jQuery.trim(title.val()) == "") {
                alert("请输入商家位置");
                title.focus();
                return;
            }
            if (jQuery.trim(score.val()) == "") {
                alert("请输入评分");
                score.focus();
                return;
            }
            if (jQuery.trim(content.val()) == "") {
                alert("请输入评价");
                content.focus();
                return;
            }
            //检查是否匿名

            jQuery.ajax({
                url: 'submit',
                type: 'post',
                data: {
                    title: title.val(),
                    content: content.val(),
                    score: score.val(),
                    status: jQuery("input[name = status]:checked").val()
                },
                success: function (body) {
                    if (body == -1) {
                        alert("输入错误，请重新输入");
                    } else if (body == 0) {
                        alert("非匿名方式需登录，请登录后重试");
                    } else if (body == 1) {
                        alert("提交成功");
                        location.reload();
                    }
                }
            })
        }

        //首页展示六条数据
        function headList() {
            let coreBody = jQuery("#bodyDiv");
            let bodyDiv = "";
            jQuery.ajax({
                url: 'headList',
                type: 'get',
                success: function (body) {
                    let len = body.discusses.length;
                    for (let i = 0; i < len; i++) {
                        let discuss = body.discusses[i];
                        let username = body.usernames[i];
                        bodyDiv += '<div class="card h-100">';
                        bodyDiv += '<div class="card-body">';
                        bodyDiv += '<h3 class="score" style="display:inline-block">' + discuss.score+'分'+
                        '</h3>'
                        bodyDiv += '<h5 class="card-title">' + discuss.title + '</h5>';
                        bodyDiv += '<p class="card-text">' + discuss.content + '</p>';
                        bodyDiv += '</div>';
                        bodyDiv += '<div class="card-footer">';
                        bodyDiv += '<small class="text-muted">' + username + '</small>';
                        bodyDiv += '<small class="text-muted" style="margin-left: 20px;">' + discuss
                            .createTime + '</small>';
                        bodyDiv += '</div></div>';

                    }
                    coreBody.html(bodyDiv);

                }
            })

            
        }

        headList();