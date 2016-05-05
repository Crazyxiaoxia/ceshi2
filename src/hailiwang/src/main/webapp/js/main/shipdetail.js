$(document).ready(function () {
    //顶部下拉菜单的显示和隐藏
    $(".msg_content").hide();
    $("#msg_btn").hover(function () {
        $(".msg_content").show();
    }, function () {
        $(".msg_content").hide();
    })
    $(".msg_content").hover(function () {
        $(".msg_content").show();
    }, function () {
        $(".msg_content").hide();
    })

    $(".help_content").hide();
    $("#help_btn").hover(function () {
        $(".help_content").show();
    }, function () {
        $(".help_content").hide();
    })
    $(".help_content").hover(function () {
        $(this).show();
    }, function () {
        $(this).hide();
    })

    $(".user_content").hide();
    $("#user_btn").hover(function () {
        $(".user_content").show();
    }, function () {
        $(".user_content").hide();
    })
    $(".user_content").hover(function () {
        $(this).show();
    }, function () {
        $(this).hide();
    })


    //您的房源控制按钮

    $(".house_list1").click(function () {
        $(".main_rg").empty();
        $(".main_rg").append('<div class="house_content1"> <h2>您没有任何船源!</h2> <p class="space-bottom-4">海狸上出租您的空余空间来赚钱。 您还可以结识来自世界各地有意思的旅行者！</p> <a href="botaer.html" class="scan_houses">发布新的船源</a> </div>');
    })
    $(".house_list2").click(function () {
        $(".main_rg").empty();
        $(".main_rg").append('<div class="house_content2"> <p>您没有将至预定</p> <a class="main_a" href="#">查看过去的预订记录</a> </div>');
    })
    $(".house_list3").click(function () {
        $(".main_rg").empty();
        $(".main_rg").append('<div class="house_content3 main_pannel"> <div class="main_pannel_header pa_font">已验证身份 </div> <ul class="main_pannel_list book list-unstyled"> <li>您的房客在向您预订前将需要验证他们的身份。<a class="main_a" href="#">了解更多</a></li> <li>您必须先验证自己的身份，才能向要求房客验证他们的身份!</li> <li>请<a class="main_a" href="#">验证您的身份</a>，让您符合这项要求。</li> <li class="book_check"> <input type="checkbox">要求房客进行验证 </li> <li> <hr class="book_fgf"> </li> <li> <button class="scan_houses book_btn">保存预定条件</button> </li> </ul> </div>');
    })


    //个人资料控制按钮
    $(".msg_content1").show();
    $(".msg_content2").hide();
    $(".msg_content3").hide();
    $(".msg_content4").hide();
    $(".msg_content5").hide();
    $(".msg_list1").click(function () {
        $(".msg_content1").show();
        $(".msg_content2").hide();
        $(".msg_content3").hide();
        $(".msg_content4").hide();
        $(".msg_content5").hide();
    })
    $(".msg_list2").click(function () {
        $(".msg_content2").show();
        $(".msg_content1").hide();
        $(".msg_content3").hide();
        $(".msg_content4").hide();
        $(".msg_content5").hide();
    })
    $(".msg_list3").click(function () {
        $(".msg_content3").show();
        $(".msg_content2").hide();
        $(".msg_content1").hide();
        $(".msg_content4").hide();
        $(".msg_content5").hide();
    })
    $(".msg_list4").click(function () {
        $(".msg_content4").show();
        $(".msg_content2").hide();
        $(".msg_content3").hide();
        $(".msg_content1").hide();
        $(".msg_content5").hide();
    })
    $(".msg_list5").click(function () {
        $(".msg_content5").show();
        $(".msg_content2").hide();
        $(".msg_content3").hide();
        $(".msg_content4").hide();
        $(".msg_content1").hide();
    })


    $("#main_later").click(function () {
        $("#main_header").hide();
    })
    $("#del").click(function () {
        $("#main_header").hide();
    })

    $(".left_mune").hide();
    $("#btn_sh").on("click", function (e) {
        $(".left_mune").show();
        $(document).one("click", function () {
            $(".left_mune").hide();
        });
        e.stopPropagation();
    });


    //首页搜索地址弹出框

})