/**
 * @file change_account_name.js
 */
(function () {
    //------------------------------------------------------------------------
    //- 設定.

    // 初期設定.
    init();


    //------------------------------------------------------------------------
    //- イベント設定.
    //-- 画面ロード後のイベント処理. --//
    Commons.afterLoad = function() {
        Commons.focus('#data_name');
    };

    //-- 変更を反映する ボタン押下処理. --//
    $('#btn_update').on('click', function (event) {
        Commons.closeMessage();
        doCheck(function() {
                    ModalConfirm.show({ title:'更新'
                            , msgData: {button:ModalConfirm.BTN_YES_NO, text:Commons.getCommonMessage('W00002','ユーザー名変更') }
                            , btnYesFunc: doUpdate
                            , focusButton: ModalConfirm.BTN_YES
                            });
                });
    });


    //------------------------------------------------------------------------
    //- 画面処理.
    /**
     * 初期値設定を行います.
     */
    function init() {
        $('#sidenavToggler').click();
        Commons.resetChangeInput();
    }

    /**
     * チェック処理を行います.
     */
    function doCheck(success) {
        Commons.action({ url: '/change_account_name/check'
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
     * アカウント情報変更.
     */
    function doUpdate() {
        Commons.action({ url: '/change_account_name/update'
                , data: $('#myform').serialize()
                , success: function (result, status, xhr) {
                              if (result.status === 'OK') {
                                  init();
                                  Commons.changeScreen('/main_menu/show_message/change_account_name');
                              } else {
                                  Commons.showMessages('#main_contents', result.messageList);
                                  Commons.focus('#data_name');
                              }
                          }
                });
    };

}());