$(document).ready(function () {
    //顶部下拉菜单的显示和隐藏
    $(".msg_content").hide();
    $("#msg_btn").hover(function () {
        $(".msg_content").show();
    }, function () {
        $(".msg_content").hide();
    });
    $(".msg_content").hover(function () {
        $(".msg_content").show();
    }, function () {
        $(".msg_content").hide();
    });
    
    $('#ex1').slider({
    	formatter: function(value) {
    		return '当前价格: ￥' + value;
    	}
    });
});