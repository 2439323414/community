
/**
 *向后台发送ajax请求
 * @param targetId
 * @param type
 * @param commentContent
 */
function comment2target(targetId,type,commentContent) {
    if (!commentContent){
        alert("不能回复空内容~~");
        return;
    }
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType:'application/json',
        data: JSON.stringify({
            "parentId":targetId,
            "content":commentContent,
            "type":type
        }),
        success: function (response) {
            if (response.code == 200){
                // $("#comment_section").hide();
                window.location.reload();
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

/**
 * 提交回复
 */
function post() {
    var questionId = $("#question_id").val();
    var commentContent = $("#comment_content").val();
    comment2target(questionId,1,commentContent);
}

/**
 * 提交二级回复
 * @param e
 */
function comment(e) {
    var commentId = e.getAttribute("data-id");
    var content = $("#input-"+commentId).val();
    comment2target(commentId,2,content);
}
/**
 * 展开二级评论
 * @param e
 */
function collapseComments(e) {
    var id = e.getAttribute("data-id");
    var comment = $("#comment-"+id);
    //判断有没有in；
    if (comment.hasClass("in")){
        comment.removeClass("in");
        e.classList.remove("active");
    }else {
        var subCommentContainer = $("#comment-"+id);
        if(subCommentContainer.children().length!=1){
            comment.addClass("in");
            e.classList.add("active");
        }else {
            $.getJSON( "/comment/"+id, function( data ) {
                $.each( data.data.reverse(), function(index,comment) {

                    var mediaLeftElement = $("<div/>",{
                        "class":"media-left"
                    }).append($("<img/>",{
                        "class":"media-object img-rounded img-pro",
                        "src":comment.user.avatarUrl
                    }));
                    var mediaBodyElement = $("<div/>",{
                        "class":"media-body"
                    }).append($("<h5/>",{
                        "class":"media-heading",
                        "html":comment.user.name
                    })).append($("<div/>",{
                        "html":comment.content
                    })).append($("<div/>",{
                        "class":"menu"
                    }).append($("<span/>",{
                        "class":"pull-right",
                        "html":moment(comment.gmtCreate).format('YYYY-MM-DD')
                    })));

                    var mediaElement = $("<div/>",{
                        "class":"media"
                    }).append(mediaLeftElement).append(mediaBodyElement);
                    var commentElement = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments"
                    }).append(mediaElement);
                    subCommentContainer.prepend(commentElement);
                });
                comment.addClass("in");
                e.classList.add("active");
            });
        }

    }
}