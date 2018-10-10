/**
 * @file mnt_group.js
 */
(function () {

    //------------------------------------------------------------------------
    //- 設定.
    // メインテーブルID.
    var tableId = '#main_tbl';
    // 最大件数.
    var MAX_SIZE = 99;

    // 初期設定.
    init();


    //------------------------------------------------------------------------
    //- イベント設定.
    //-- 画面ロード後のイベント処理. --//
    Commons.afterLoad = function() {
        Commons.focus('#btn_selecte_group');
    };

    //-- グループ選択 ボタン押下処理. --//
    $('#btn_selecte_group').on('click', function (event) {
        Commons.closeMessage();
        Commons.checkChangedInput('変更'
                , 'グループを変更します'
                , function() {
                    ModalSelectGroup.show({
                              selectedFunc: function(data) {
                                          $('#data_id').val(data.id);
                                          doSearch();
                                          return true;
                                      }
                            , isAdd: true
                            });
                });
    });

    //-- メンバー追加 ボタン押下処理. --//
    $('#btn_add_member').on('click', function (event) {
        Commons.closeMessage();
        Commons.action({ url: '/mnt_group/check_size'
                , data: $('#myform').serialize()
                , success: function (result, status, xhr) {
                              if (result.status === 'OK') {
                                  ModalSelectMember.show({ selectedFunc: addMember });
                              } else {
                                  Commons.showMessages('#main_contents', result.messageList);
                              }
                          }
                });
    });


    //-- 変更する ボタン押下処理. --//
    $('#btn_update').on('click', function (event) {
        Commons.closeMessage();
        $('#mode').val(BizCommons.MODE_UPD);
        doCheck(function() {
                    ModalConfirm.show({ title: '更新'
                            , msgData: {button:ModalConfirm.BTN_YES_NO, text:Commons.getCommonMessage('W00002','グループ情報の更新') }
                            , btnYesFunc: function() { 
                                          doUpdateAction('/mnt_group/update', function() { 
                                                      doSearch(); 
                                                      Commons.resetChangeInput();
                                                  } ); 
                                      }
                            , focusButton: ModalConfirm.BTN_YES
                            });
                });
    });

    //-- 削除する ボタン押下処理. --//
    $('#btn_delete').on('click', function (event) {
        Commons.closeMessage();
        $('#mode').val(BizCommons.MODE_DEL);
        doCheck(function() {
                    ModalConfirm.show({ title: '削除'
                            , msgData: {button:ModalConfirm.BTN_YES_NO, text:Commons.getCommonMessage('W00002','グループ情報の削除') }
                            , btnYesFunc: function() { 
                                          doUpdateAction('/mnt_group/delete', function() { 
                                                      initData(); 
                                                  } );  
                                      }
                            , focusButton: ModalConfirm.BTN_YES
                            });
                });
    });


    //------------------------------------------------------------------------
    // 画面処理.
    /**
     * 初期値設定を行います.
     */
    function init() {
        $('#sidenavToggler').click();
        initData();
    }

    /**
     * 初期値設定を行います.
     */
    function initData() {
        $('#data_id').val('');
        $('#group_name').val('');
        $('#data_name').val('');
        $('#data_grp_status option:first').prop('selected', true);
        $('#data_memo').val('');

        $('.update_input').hide();
        $('#btn_delete').hide();
        $('.non-root-input').hide();
        $('.root-input').hide();
        clearRow();
        Commons.resetChangeInput();
    }

    /**
     * 値の設定を行います.
     * @param {object} data - データオブジェクト.
     */
    function setData(data) {
        $('#data_id').val(data.id);
        $('#group_name').val(data.name);
        $('#data_name').val(data.name);
        $('#data_grp_status').val(data.grp_status);
        $('#data_memo').val(data.memo);

        $('.update_input').show();
        if (data.grp_status !== Commons.ON) {
            $('#btn_delete').show();
        } else {
            $('#btn_delete').hide();
        }
        $('.non-root-input').hide();
        $('.root-input').hide();
        if (data.is_root === Commons.ON) {
            $('.root-input').show();
        } else {
            $('.non-root-input').show();
        }
    }


    /**
     * 明細行をクリアします.
     */
    function clearRow() {
        Commons.clearTableRow(tableId);
    }

    /**
     * 明細を作成します.
     * @param {array} list - 行.
     */
    function makeRow(list) {
        // 明細行設定.
        clearRow();
        var tbody = $(tableId + ' tbody');
        for (var i = 0, imax = list.length; i < imax; i++) {
            makeRowData(list[i], i);
        }
        // 明細設定.
        $('#list_size').val(list.length);
    }


    /**
     * 明細行を作成します.
     * @param {object} data - 行データ.
     * @param {number} i - 行.
     */
    function makeRowData(data, i) {
        var id_name = 'list[' + i + '].id';
        var authority_id_name = 'list[' + i + '].authority_id';
        var is_default_name = 'list[' + i + '].is_default';
        var is_delete_name = 'list[' + i + '].is_delete';
        var is_existing_name = 'list[' + i + '].is_existing';
        var id_id = 'list_' + i + '_id';
        var authority_id_id = 'list_' + i + '_authority_id';
        var is_default_id = 'list_' + i + '_is_default';
        var is_delete_id = 'list_' + i + '_is_delete';
        var is_existing_id = 'list_' + i + '_is_existing';
        var employee_no = Commons.escapeHTML(data.employee_no);
        var name = Commons.escapeHTML(data.name);

        var rowText = $($('#main_tbl_row').html());
        rowText.find('input.id').prop('name', id_name);
        rowText.find('select.authority_id').prop('name', authority_id_name);
        rowText.find('input.is_default').prop('name', is_default_name);
        rowText.find('input.is_delete').prop('name', is_delete_name);
        rowText.find('input.is_existing').prop('name', is_existing_name);
        rowText.find('input.id').prop('id', id_id);
        rowText.find('select.authority_id').prop('id', authority_id_id);
        rowText.find('input.is_default').prop('id', is_default_id);
        rowText.find('input.is_delete').prop('id', is_delete_id);
        rowText.find('input.is_existing').prop('id', is_existing_id);
        rowText.find('span.employee_no').html(employee_no);
        rowText.find('span.name').html(name);

        // 行作成.
        var tbody = $(tableId + ' tbody');
        var htmlText = '';
        htmlText += '<tr>\n';
        htmlText +=   '<td class="container"> \n';
        htmlText +=     rowText.prop("outerHTML");
        htmlText +=   '</td>\n';
        htmlText += '</tr>\n';
        tbody.append(htmlText);

        // 値設定.
        tbody.find('input[name="' + id_name + '"]').val(data.id);
        tbody.find('select[name="' + authority_id_name + '"]').val(data.authority_id);
        tbody.find('input[name="' + is_default_name + '"]').prop('checked', (data.is_default === Commons.ON));
        tbody.find('input[name="' + is_delete_name + '"]').prop('checked', (data.is_delete === Commons.ON));
        tbody.find('input[name="' + is_existing_name + '"]').val(data.is_existing);

        // 活性・非活性制御.
        Commons.disabled('input[name="' + is_default_name + '"]'
                , (data.is_existing === Commons.ON && data.is_default === Commons.ON));
        Commons.disabled('input[name="' + is_delete_name + '"]'
                , (data.is_default === Commons.ON));

        //-- 削除する ボタン押下処理. --//
        tbody.find('input.is_delete').on('click', function (event) {
            var check = $(event.target);
            var tr = check.parents('tr');
            if (check.prop('checked')) {
                tr.find('i.del-icon').show();
            } else {
                tr.find('i.del-icon').hide();
            }
        });

        // 入力内容変更設定.
        Commons.setChangeInput();
    }

    /**
     * 新規に行を追加します.
     * @param {object} data - 行データ.
     * @return {boolean} - 処理結果.
     */
    function addMember(data) {
        if (isNotDuplicate(data)) {
            var size = $('#list_size').val();
            makeRowData(data, size);
            var tr = $(tableId + ' tbody tr:eq(' + size + ')');
            tr.find('i.add-icon').show();
            size++;
            $('#list_size').val(size);
            var selector = '#modal_select_member';
            Commons.showMessage(selector, {kind:Commons.MSG_INFO, text:Commons.getCommonMessage('I00003',data.name + 'さん')});
            Commons.scrollReset(selector);
            Commons.ChangeInputOn();
        }
        return false;
    }

    /**
     * 重複チェックを行います.
     * @param {object} data - 対象データ.
     * @return {boolean} - チェック結果.
     */
    function isNotDuplicate(data) {
        var result = true;
        var keys = $(tableId + ' tbody input.id');
        for (var i = 0, imax = keys.length; i < imax; i++) {
            if (keys[i].value === data.id) {
                var selector = '#modal_select_member';
                Commons.showMessage(selector, {text:Commons.getCommonMessage('E00014','メンバー')});
                Commons.scrollReset(selector);
                result = false;
                break;
            }
        }
        return result;
    }


    /**
     * リストデータの検索を行います.
     */
    function doSearch() {
        Commons.action({ url: '/mnt_group/find_data'
                , data: $('#myform').serialize()
                , success: function(result,status,xhr) {
                              Commons.showMessages('#main_contents', result.messageList);
                              setData(result.form.data);
                              makeRow(result.form.list);
                              Commons.focus('#btn_selecte_group');
                          }
                });
    };

    /**
     * チェック処理を行います.
     * @param {function} success() - 成功時処理.
     */
    function doCheck(success) {
        Commons.action({ url: '/mnt_group/check'
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
     * 更新系アクション処理を行います.
     * @param {string} action - アクションURL.
     * @param {function} [success(result)] - 成功時処理(処理結果).
     */
    function doUpdateAction(action, success) {
        Commons.action({ url: action
                , data: $('#myform').serialize()
                , success: function(result, status, xhr) {
                              Commons.showMessages('#main_contents', result.messageList);
                              if (result.status === 'OK') {
                                  if (success) { success(result); }
                              }
                          }
                });
    }

}());