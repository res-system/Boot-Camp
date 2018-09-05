/**
 * @file change_password.js
 */
(function () {
    //------------------------------------------------------------------------
    // 初期設定.
    init();


    //------------------------------------------------------------------------
    // イベント設定.
    //-- 画面ロード後のイベント処理. --//
    Commons.afterLoad = function() {
        // 初期フォーカス等.
        Commons.focus('#password');
    };

    //-- 変更を反映する ボタン押下処理. --//
    $('#btn_update').on('click', function (event) {
        Commons.closeMessage();
        doCheck(function() {
            ModalConfirm.show({
                 title:'更新'
               , msgData:{button:ModalConfirm.BTN_YES_NO, text:Commons.getCommonMessage('W00002','パスワードの変更') }
               , btnYesFunc: doUpdate
               , focusButton: ModalConfirm.BTN_YES
           });
        });
    });


    //------------------------------------------------------------------------
    // 画面処理.
    /**
     * 初期化.
     */
    function init() {
        $('#sidenavToggler').click();
        $('#password').val('');
        $('#new_password').val('');
        $('#confirmation_password').val('');
    }

    /**
     * チェック処理を行います.
     */
    function doCheck(success) {
        Commons.action({
                  url: '/change_password/check'
                , data: $('#myform').serialize()
                , success: function (result, status, xhr) {
                        if (result.status === 'OK') {
                            success();
                        } else {
                            Commons.showMessages('#main_contents', result.messageList);
                        }
                    }
                });
    }

    /**
     * パスワード変更.
     */
    function doUpdate() {
        Commons.action({
                  url: '/change_password/update'
                , data: $('#myform').serialize()
                , success: function (result, status, xhr) {
                        if (result.status === 'OK') {
                            init();
                            Commons.changeScreen('/main_menu/show_message/change_password');
                        } else {
                            Commons.showMessages('#main_contents', result.messageList);
                            Commons.focus('#password');
                        }
                    }
                });
    };

}());