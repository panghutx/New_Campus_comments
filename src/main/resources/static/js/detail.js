checkLogin();

let pindex ="";
let psize = 6;
let total = "";
let score = "";
let position = "";


function getParamValue(key) {
    var url = location.search;
    if (url != "") {
        url = url.substr(1);
        var kvs = url.split("&");
        for (var i = 0; i < kvs.length; i++) {
            var kv = kvs[i].split("=");
            if (key == kv[0]) return kv[1];
        }
    }
    return "";
}

//获取最新页面数据
function init() {
    
    score = getParamValue("score");
    position = getParamValue("position");
    pindex = getParamValue("pindex"); //从url中获取最新值
    if (pindex == null || pindex == "") {
        pindex = 1;
    }
    
    queryList();

}

init();


//更改pindex

function head() {
    location.href = "detail.html?pindex=1&score="+score+"&position="+position+"";
}

function prePage() {
    if (pindex <= 1) {
        alert("已经是首页了");
        return;
    }
    pindex = parseInt(pindex) - 1;
    location.href = "detail.html?pindex=" + pindex + "&score="+score+"&position="+position+"";
}

function nextPage() {
    if (pindex >= total) {
        alert("已经是尾页了");
        return;
    }
    pindex = parseInt(pindex) + 1;
    location.href = "detail.html?pindex=" + pindex + "&score="+score+"&position="+position+"";
}

function end() {
    location.href = "detail.html?pindex=" + total + "&score="+score+"&position="+position+"";
}

//条件查询
function query() {

    //获取分数和位置
    score = jQuery("#score").val();
    position = jQuery("#position").val();
    queryList();

}

function queryList(){
    let coreBody = jQuery("#bodyDiv");
    let bodyDiv = "";
    jQuery.ajax({
        url: 'queryList',
        type: 'post',
        data: {
            pindex: pindex,
            psize: psize,
            score: score,
            position: position
        },
        success: function (body) {
            let len = body.discusses.length;
            let count=body.count; //数据量
            total = Math.ceil(count/psize); //总页数
            jQuery("#count").html("为您检索到"+count+"条评价，共"+total+"页") ;
            for (let i = 0; i < len; i++) {
                let discuss = body.discusses[i];
                let username = body.usernames[i];
                bodyDiv += '<div class="card h-100">';
                bodyDiv += '<div class="card-body">';
                bodyDiv += '<h3 class="score" style="display:inline-block">' + discuss.score + '分' +
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