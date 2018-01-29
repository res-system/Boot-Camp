/**
 * @file employee_info_input.js
 */
(function () {
    //------------------------------------------------------------------------
    // 初期設定.



    //------------------------------------------------------------------------
    // イベント設定.
    //-- 画面ロード後のイベント処理. --//
    Commons.afterLoad = function() {
        // 初期フォーカス等.
        Commons.focus('#data_employee_no');
    };

    //-- 内容を変更する ボタン押下処理. --//
    $('#btn_update').on('click', function (event) {
        Commons.closeMessage();
        ModalConfirm.show({
            msgData:{button:ModalConfirm.BTN_YES_NO, text:'変更を反映します。よろしいですか？'},
            btnYesFunc: doUpdate
        });
    });



    //------------------------------------------------------------------------
    // 画面処理.
    /**
     * 更新処理.
     */
    function doUpdate() {
        Commons.closeMessage();
        Commons.action({
                 url: '/employee_info/update'
                ,data: $('#myform').serialize()
                ,success: function (result, status, xhr) {
                        if (result.status === 'OK') {
                            Commons.changeScreen('/employee_info/complete');
                        } else {
                            Commons.showMessages('#main_contents', result.messageList);
                            Commons.focus('#data_employee_no');
                        }
                    }
                });
    };



}());
