/**
 * @file main_menu.js
 */
(function () {
    //------------------------------------------------------------------------
    // 初期設定.



    //------------------------------------------------------------------------
    // イベント設定.
    //-- 画面ロード後のイベント処理. --//
    Commons.afterLoad = function() {
        // 初期フォーカス等.
    };

    //-- メニュー ボタン押下処理. --//
    $('a.menu-link').on('click', function (event) {
        var nextUrl = $(this).closest('a').children('div').children('input[name="url"]').val();
        if (!$ReC.isStrBlk(nextUrl)) {
            Commons.changeScreen(nextUrl);
        }
    });

    //-- グループを選択する ボタン押下処理. --//
    $('#btn_select_group').on('click', function (event) {
        ModalGroup.show({selectedFunc:selectedFunc});
    });



    //------------------------------------------------------------------------
    // 画面処理.
    function selectedFunc(id,code,name) {
        Commons.closeMessage();
        Commons.action({
                 url: '/modal_group/change_group'
                ,data: {'modal_group_selected_group_id':id, 
                        'modal_group_selected_group_name':name }
                ,success: function (result, status, xhr) {
                        if (result.status === 'OK') {
                            $('#modal_group').modal('hide');
                            Commons.changeScreen('/main_menu/show_message/success_change_group');
                        } else {
                            Commons.showMessages('#modal_group_content', result.messageList);
                        }
                    }
                });
        return false;
    }


}());
