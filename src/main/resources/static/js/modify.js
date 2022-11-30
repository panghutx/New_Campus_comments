jQuery.ajax({
    url:'show'+location.search,
    type:'get',
    success:function(body){
        //console.log(body.data.username);
        jQuery("#loginname").val(body.loginName);
        jQuery("#username").val(body.username);
        jQuery("#password").val(body.password);
        jQuery("#headImg").attr("src",body.head);
    }
})

//实时预览头像
function imgShow(){
    const ipt = document.querySelector("#formFile")
    const img = document.querySelector('#headImg')
    ipt.addEventListener('change', function(e){
      console.log(e.target.files[0]) // 1. 上传的图片文件对象
      // URL.createObjectURL(e.target.files[0]) // 
      // 2. 使用URL.createObjectURL()创建一个图片临时路径, 路径是以blob开头的
      // 临时路径: 创建这个临时路径的页面关闭后, 这个路径也就失效了
    //  console.log(URL.createObjectURL(e.target.files[0])) // 临时路径地址
     const url = URL.createObjectURL(e.target.files[0])
        // 3. 将img的src属性赋值为临时路径地址
      img.src = url
    })
}
imgShow();




function mysub(){
    
    let uid = location.search.split("=")[1];
    var formData = new FormData();
    formData.append("myfile", $("#formFile")[0].files[0]);
    formData.append("uid", uid);
    formData.append("username", jQuery("#username").val());
    formData.append("password", jQuery("#password").val());

    jQuery.ajax({
        url:'update',
        type:'post',
        processData: false, // 告诉jQuery不要去处理发送的数据
        contentType: false, // 告诉jQuery不要去设置Content-Type请求头
        data: formData,
        success:function(body){
            if(body==1){
                alert("修改成功");
                location.href="userinfo.html?uid="+uid+"";
            }else{
                alert("修改异常")
                location.href="userinfo.html?uid="+uid+"";
            }
        }
    })
}