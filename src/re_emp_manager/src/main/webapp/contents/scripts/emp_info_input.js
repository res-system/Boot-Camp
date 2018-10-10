/**
 * @file emp_info_input.js
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
        Commons.focus('#data_employee_no');
    };

    //-- 入力内容を保存します ボタン押下処理. --//
    $('#btn_update').on('click', function(event) {
        Commons.closeMessage();
        doCheck(function() {
                ModalConfirm.show({ title: '追加'
                        , msgData:{button:ModalConfirm.BTN_YES_NO, text:Commons.getCommonMessage('W00002','入力内容を保存') }
                        , btnYesFunc: function() { 
                                      doUpdate(); 
                                  }
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
     * @param {function} success() - 成功時処理.
     */
    function doCheck(success) {
        Commons.action({ url: '/emp_info/check'
                , data: $('#myform').serialize()
                , success: function(result, status, xhr) {
                              if (result.status === 'OK') {
                                  success();
                              } else {
                                  Commons.showMessages('#main_contents', result.messageList);
                              }
                          }
                });
    }

    /**
     * 更新処理.
     */
    function doUpdate() {
        Commons.action({ url: '/emp_info/update'
                , data: $('#myform').serialize()
                , success: function(result, status, xhr) {
                              if (result.status === 'OK') {
                                  Commons.changeScreen('/emp_info/complete');
                              } else {
                                  Commons.showMessages('#main_contents', result.messageList);
                                  Commons.focus('#data_employee_no');
                                  Commons.ChangeInputOn();
                              }
                          }
                });
    };

}());