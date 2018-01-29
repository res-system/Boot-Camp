/**
 * @file password_change.js
 */
(function () {
    //------------------------------------------------------------------------
    // 初期設定.



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
        ModalConfirm.show({
            msgData:{button:ModalConfirm.BTN_YES_NO, text:'変更を反映します。よろしいですか？'},
            btnYesFunc: doChange
        });
    });



    //------------------------------------------------------------------------
    // 画面処理.
    /**
     * 変更を反映する処理.
     */
    function doChange() {
        Commons.closeMessage();
        Commons.action({
                 url: '/password_change/change'
                ,data: $('#myform').serialize()
                ,success: function (result, status, xhr) {
                        if (result.status === 'OK') {
                            Commons.changeScreen('/main_menu/show_message/success_password_change');
                        } else {
                            Commons.showMessages('#main_contents', result.messageList);
                            Commons.focus('#password');
                        }
                    }
                });
    };



}());