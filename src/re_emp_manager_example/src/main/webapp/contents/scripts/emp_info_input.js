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

    //-- 住所を新しくする ボタン押下処理. --//
    $('#btn_addr_renew').on('click', function(event) {
        Commons.closeMessage();
        if (!$ReC.isStrBlk($('#empAddrList_0_addr_id').val())) {
            ModalConfirm.show({ title: '確認'
                    , msgData:{button:ModalConfirm.BTN_YES_NO, text:Commons.getCommonMessage('W00002','現住所の旧住所への移動') }
                    , btnYesFunc: function() {
                                  renewAddr();
                              }
                    , focusButton: ModalConfirm.BTN_NO
                    });
        }
    });


    //------------------------------------------------------------------------
    //- 画面処理.
    /**
     * 初期値設定を行います.
     */
    function init() {
        $('#sidenavToggler').click();
        Commons.resetChangeInput();
        var addrDisabled = $ReC.isStrBlk($('#empAddrList_0_addr_id').val());
        Commons.disabled('#btn_addr_renew', addrDisabled);
        Commons.disabled('#empAddrList_1_postal_code', addrDisabled);
        Commons.disabled('#empAddrList_1_addr1', addrDisabled);
        Commons.disabled('#empAddrList_1_addr2', addrDisabled);
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

    /**
     * 住所の切替処理.
     */
    function renewAddr() {
        Commons.action({ url: '/emp_info/get_current_addr'
                , data: $('#myform').serialize()
                , success: function(result, status, xhr) {
                              Commons.showMessages('#main_contents', result.messageList);
                              if (result.status === 'OK') {
                                  var addrData = result.form.empAddrList[0];
                                  // 旧住所設定.
                                  $('#empAddrList_1_postal_code').val(addrData.postal_code); 
                                  $('#empAddrList_1_addr1').val(addrData.addr1); 
                                  $('#empAddrList_1_addr2').val(addrData.addr2);
                                  // 現住所クリア. 
                                  $('#empAddrList_0_postal_code').val('');
                                  $('#empAddrList_0_addr1').val(''); 
                                  $('#empAddrList_0_addr2').val(''); 
                                  $('#empAddrList_0_nearest_station').val(''); 
                                  Commons.ChangeInputOn();
                                  Commons.focus('#empAddrList_0_postal_code');
                              }
                          }
                });
    };

}());
