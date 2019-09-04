
function post() {
    var questionId = $("#question_id").val();
    var commentContent = $("#comment_content").val();
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType:'application/json',
        data: JSON.stringify({
        "parentId":questionId,
        "content":commentContent,
        "type":1
        }),
        success: function (response) {
            if (response.code == 200){
                $("#comment_content").hidden;
            }else {
                if (response.code == 2003){
                   var isAccepted = confirm(response.message);
                   if (isAccepted){
                        window.open("https://github.com/login/oauth/authorize?client_id=277275c1da3f356a21d4&redirect_uri=http://localhost:8080/callback&scope=user&state=1");
                        window.localStorage.setItem("closable","true");
                   }
                }else {
                    alert(response.message);
                }
            }
        },
        dataType: "json"
    });
}