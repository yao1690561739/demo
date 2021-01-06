//标题悬浮变色
$(function () {
    $('#title').mouseenter("swing",function () {
        $(this).css({
            'color': '#1E90FF'
        });
    }).mouseleave("swing",function () {
        $(this).css({
            'color': '#fff'
        });
    });
});
//显示、隐藏密码
$(function () {
    $("#hide").click(function () {
        $(this).css({
            'display': 'none'
        });
        $("#show").css({
            'display': 'block'
        });
        $("#pwd").attr("type","text");
    });
    $("#show").click(function () {
        $("#hide").css({
            'display': 'block'
        });
        $(this).css({
            'display': 'none'
        });
        $("#pwd").attr("type","password");
    });
});