/**
 * @file mnt_group_acc.js
 */
(function () {
    //------------------------------------------------------------------------
    //- 設定.
    // メインテーブルID.
    var tableId = '#main_tbl';
    // ソート項目ID.
    var sortId = '#searchCond_sort';
    // 最大件数.
    var MAX_SIZE = 99;

    // 初期設定.
    init();


    //------------------------------------------------------------------------
    //- イベント設定.
    //-- 画面ロード後のイベント処理. --//
    Commons.afterLoad = function() {
        Commons.focus('#keyword');
    };

    //-- 検索キーワード ENTERキー押下処理. --//
    $('#keyword').on('keypress', function(event) {
        if (event.which !== 13) { return true; }
        $('#btn_search').click();
        return false;
    });

    //-- 検索 ボタン押下処理. --//
    $('#btn_search').on('click', function(event) {
        Commons.closeMessage();
        doSearch(function() {
                    $('#searchCond_page').val(1);
                    BizCommons.sortReset(tableId, sortId);
                    $('#searchCond_keyword').val($('#keyword').val());
                    $('#searchCond_is_all').val( ($('#is_all').prop('checked')) ? Commons.ON : Commons.OFF);
                });
    });

    //-- ソート項目押下処理. --//
    $('.sort-link').on('click', function(event) { 
        Commons.closeMessage();
        var sortLink = $(this);
        doSearch(function() {
                    $('#searchCond_page').val(1);
                    BizCommons.sort(tableId, sortId, sortLink);
                });
        return false;
    });

    //-- 新規追加処理. --//
    $('#btn_add').on('click', function(event) {
        Commons.closeMessage();
        checkSize(function(result) { 
                    ModalInput.show({mode:BizCommons.MODE_ADD, successFunc:successUpdateFunc});
                });
    });


    //------------------------------------------------------------------------
    //- 画面処理.
    /**
     * 初期値設定を行います.
     */
    function init() {
        $('#sidenavToggler').click();
        setMainTbl();
        Commons.resetChangeInput();
    }

    /**
     * リストサイズのチェックを行います.
     * @param {function} success(result) - 成功時処理(処理結果).
     */
    function checkSize(success) {
        Commons.action({ url: '/mnt_group_acc/check_size'
                , data: $('#myform').serialize()
                , success: function (result, status, xhr) {
                              Commons.showMessages('#main_contents', result.messageList);
                              if (result.status === 'OK') { if (success) { success(result); } }
                          }
                });
    };

    /**
     * リストデータの検索を行います.
     * @param {function} setSearchCond() - 検索条件設定処理.
     */
    function doSearch(setSearchCond) {
        if (setSearchCond) { setSearchCond(); }
        Commons.action({ url: '/mnt_group_acc/find_list'
                , data: $('#myform').serialize()
                , success: function(result,status,xhr) {
                              Commons.showMessages('#main_contents', result.messageList);
                              makeRow(result.form.list, result.form.searchCond);
                              Commons.focus('#keyword');
                              Commons.resetChangeInput();
                          }
                });
    };

    /**
     * 明細行をクリアします.
     */
    function clearRow() {
        Commons.clearTableRow(tableId);
    }

    /**
     * 明細行を作成します.
     * @param {array} list - 行.
     * @param {object} searchCond - 検索条件.
     */
    function makeRow(list, searchCond) {
        // 明細行設定.
        clearRow();
        var tbody = $(tableId + ' tbody');
        for (var i = 0, imax = list.length; i < imax; i++) {
            var id = Commons.escapeHTML(list[i].id);
            var name = Commons.escapeHTML(list[i].name);
            var employee_no = Commons.escapeHTML(list[i].employee_no);
            var situation_name = Commons.escapeHTML(list[i].situation_name);
            var group_name = Commons.escapeHTML(list[i].group_name);
            var authority_name = Commons.escapeHTML(list[i].authority_name);
            var gpac_status_name = Commons.escapeHTML(list[i].gpac_status_name);
            var registration_date = Commons.escapeHTML(list[i].registration_date);
            var memo = Commons.escapeHTML(list[i].memo);
            var is_login_acc = (list[i].is_login_acc) ? '【有】' : '';

            var htmlText = '';
            htmlText += '<tr>\n';
            htmlText +=   '<td class="container">\n';
            htmlText +=     '<div class="row">\n';
            htmlText +=       '<div class="col-3 omit "><span title="' + name + '">' + name + '</span></div>\n';
            htmlText +=       '<div class="col-3 omit sub"><span title="' + employee_no + '">' + employee_no + '</span></div>\n';
            htmlText +=       '<div class="col-3 omit sub"><span title="' + group_name + '">' + group_name + '</span></div>\n';
            htmlText +=       '<div class="col-2 omit sub"><span title="' + gpac_status_name + '">' + gpac_status_name + '</span></div>\n';
            htmlText +=       '<div class="col-1 omit sub"><span>' + is_login_acc + '</span></div>\n';
            htmlText +=     '</div>\n';
            htmlText +=     '<div class="row">\n';
            htmlText +=       '<div class="col-3 omit sub"></div>\n';
            htmlText +=       '<div class="col-3 omit sub"><span title="' + situation_name + '">' + situation_name + '</span></div>\n';
            htmlText +=       '<div class="col-3 omit sub"><span title="' + authority_name + '">' + authority_name + '</span></div>\n';
            htmlText +=       '<div class="col-2 omit sub"><span title="' + registration_date + '">' + registration_date + '</span></div>\n';
            htmlText +=     '</div>\n';
            htmlText +=     '<div class="row">\n';
            htmlText +=       '<div class="col-12 omit sub"><span title="' + memo + '">' + memo + '</span></div>\n';
            htmlText +=     '</div>\n';
            htmlText +=     '<input type="hidden" name="id" value="' + id + '" />\n';
            htmlText +=   '</td>\n';
            htmlText += '</tr>\n';
            tbody.append(htmlText);
        }

        // 明細設定.
        $('#searchCond_page').val(searchCond.page);
        $('#searchCond_total_page').val(searchCond.total_page);
        $('#searchCond_total_size').val(searchCond.total_size);
        $('#searchCond_sort').val(searchCond.sort);
        $('#list_size').val(list.length);
        setMainTbl();
    }

    /**
     * 明細の設定を行います.
     */
    function setMainTbl() {
        //-- 明細選択処理. --//
        Commons.setSelectTable({ tbl:tableId, selectFunc:doSelect });
        //-- ページング. --//
        Commons.setPaging({
                  list: '#pagination'
                , page: '#searchCond_page',totalPage:'#searchCond_total_page',totalSize:'#searchCond_total_size'
                , event: function() {
                              Commons.closeMessage();
                              doSearch();
                          }
                });
        //-- 新規追加ボタン制御処理. --//
        ctrlBtnAdd();
        //-- ソート設定. --//
        BizCommons.setSort(tableId, sortId);
        //-- 検索条件設定. --//
        $('#keyword').val($('#searchCond_keyword').val());
        $('#is_all').prop('checked', ($('#searchCond_is_all').val() === Commons.ON));
        if ($('#searchCond_keyword').val() || $('#searchCond_is_all').val() === Commons.ON) {
            $('#main_tbl_cond').text(
                      (($('#searchCond_keyword').val()) 
                          ? '「' + $('#searchCond_keyword').val() + '」' : '')
                    + (($('#searchCond_is_all').val() === Commons.ON) 
                          ? ' (無効も含める)' : ''));
            $('#main_tbl_cond_div').show();
        } else {
            $('#main_tbl_cond').text('');
            $('#main_tbl_cond_div').hide();
        }
    }

    /**
     * 明細行の選択処理を行います.
     * @param {number} row - 行(1～).
     * @param {number} col - 列(1～).
     * @param {object} e   - イベントオブジェクト.
     * @return {boolean} 選択結果.
     */
    function doSelect(row,col,e) {
        Commons.closeMessage();
        if ($(e.target).prop('tagName') !== 'BUTTON') {
            var selected_id = $(e.target).parents('tr').find('input[type="hidden"][name="id"]').val();
            ModalInput.show({mode:BizCommons.MODE_UPD, selectedId:selected_id, successFunc:successUpdateFunc});
        }
        return false;
    };

    /**
     * 更新成功時の処理を行います.
     * @param {object} result - 結果.
     */
    function successUpdateFunc(result) {
        Commons.showMessages('#main_contents', result.messageList);
        doSearch();
    };

    /**
     * 新規追加ボタンの制御を行います.
     */
    function ctrlBtnAdd() {
        if ($ReC.toInt($('#searchCond_total_size').val(), 0) >= MAX_SIZE) {
            $('#btn_add').hide();
        } else {
            $('#btn_add').show();
        }
    }

}());



// -- 入力ダイアログ -------------------------------------------------------------- //
var ModalInput = {};

//-------------------- 変数.
/** 「ＯＫ」ボタン押下時処理. */
ModalInput.mode = null;
ModalInput.selectedId = null;
ModalInput.successFunc = null;


//-------------------- 処理.
/**
 * ダイアログを表示します.
 * @param {object} args - パラメタ.
 * <pre>
 *  {string} mode                     - ダイアログの処理モード.
 *  {string} [selectedId]             - 選択ID.
 *  {function} [successFunc(result)]  - 更新成功時処理(処理結果).
 * </pre>
 */
ModalInput.show = function (args) {
    ModalInput.mode = null;
    ModalInput.selectedId = null;
    ModalInput.successFunc = null;
    if (args && args.mode) { ModalInput.mode = args.mode; }
    if (args && args.selectedId) { ModalInput.selectedId = args.selectedId; }
    if (args && args.successFunc) { ModalInput.successFunc = args.successFunc; }
    $('#modal_input').modal('show');
};
(function () {

    //------------------------------------------------------------------------
    //- 設定.
    var modalSelector = '#modal_input';


    //------------------------------------------------------------------------
    //- イベント.
    //-- ダイアログ 表示前処理. --//
    $(modalSelector).on('show.bs.modal', function(event) {
        if (event.namespace === 'bs.modal') { setModal(); }
    });
    //-- ダイアログ 表示時処理. --//
    $(modalSelector).on('shown.bs.modal', function(event) {
        if (ModalInput.mode === BizCommons.MODE_ADD) {
            Commons.focus('#data_employee_no');
        } else {
            Commons.focus('#data_gpac_status');
        }
    });
    //-- ダイアログ 閉じる前処理. --//
    $(modalSelector).on('hide.bs.modal', function(event) {
    });
    //-- ダイアログ 閉じた後処理. --//
    $(modalSelector).on('hidden.bs.modal', function(event) {
        $('body').addClass('modal-open');
    });
    //-- 閉じる ボタン押下処理. --//
    $(modalSelector + '_btn_close').on('click', function(event) {
        Commons.closeMessage(modalSelector);
        Commons.checkChangedInput('閉じる', 'ダイアログを閉じます', function() { $(modalSelector).modal('hide'); });
    });

    //-- 追加する ボタン押下処理. --//
    $(modalSelector + '_btn_add').on('click', function (event) {
        Commons.closeMessage(modalSelector);
        $('#mode').val(BizCommons.MODE_ADD);
        doCheck(function() {
                    ModalConfirm.show({ title: '追加'
                            , msgData: {button:ModalConfirm.BTN_YES_NO, text:Commons.getCommonMessage('W00002','アカウント情報の追加') }
                            , btnYesFunc: function() { 
                                          doUpdateAction('/mnt_group_acc/insert', ModalInput.successFunc); 
                                      }
                            , focusButton: ModalConfirm.BTN_YES
                            });
                });
    });

    //-- 変更する ボタン押下処理. --//
    $(modalSelector + '_btn_update').on('click', function (event) {
        Commons.closeMessage(modalSelector);
        $('#mode').val(BizCommons.MODE_UPD);
        doCheck(function() {
                    ModalConfirm.show({ title: '更新'
                            , msgData: {button:ModalConfirm.BTN_YES_NO, text:Commons.getCommonMessage('W00002','アカウント情報の更新') }
                            , btnYesFunc: function() { 
                                          doUpdateAction('/mnt_group_acc/update', ModalInput.successFunc); 
                                      }
                            , focusButton: ModalConfirm.BTN_YES
                            });
                });
    });

    //-- 削除する ボタン押下処理. --//
    $(modalSelector + '_btn_delete').on('click', function (event) {
        Commons.closeMessage(modalSelector);
        $('#mode').val(BizCommons.MODE_DEL);
        doCheck(function() {
                    ModalConfirm.show({ title: '削除'
                            , msgData: {button:ModalConfirm.BTN_YES_NO, text:Commons.getCommonMessage('W00002','アカウント情報の削除') }
                            , btnYesFunc: function() { 
                                          doUpdateAction('/mnt_group_acc/delete', ModalInput.successFunc);  
                                      }
                            , focusButton: ModalConfirm.BTN_YES
                            });
                });
    });

    //-- コピーする ボタン押下処理. --//
    $(modalSelector + '_btn_copy').on('click', function (event) {
        Commons.closeMessage(modalSelector);
        ModalConfirm.show({ title: 'コピー'
                , msgData: {button:ModalConfirm.BTN_YES_NO, text:Commons.getCommonMessage('W00001','コピーして新規追加します。')}
                , btnYesFunc: function() { 
                              doCopy();
                          }
                , focusButton: ModalConfirm.BTN_NO
                });
    });


    //-- ログイン情報を追加する ボタン押下処理. --//
    $(modalSelector + '_add_login_info').on('click', function (event) {
        Commons.closeMessage(modalSelector);
        ModalInputLoginInfo.show({mode:BizCommons.MODE_ADD, selectedId:$('#data_id').val(), successFunc:successLoginInfoUpdateFunc});
    });

    //-- ログイン情報を削除する ボタン押下処理. --//
    $(modalSelector + '_del_login_info').on('click', function (event) {
        Commons.closeMessage(modalSelector);
        ModalInputLoginInfo.show({mode:BizCommons.MODE_DEL, selectedId:$('#data_id').val(), successFunc:successLoginInfoUpdateFunc});
    });


    //------------------------------------------------------------------------
    //- 処理.
    /**
     * モーダルダイアログの表示設定を行います.
     */
    function setModal() {
        Commons.closeMessage(modalSelector);
        $(modalSelector + '_btn_update').hide();
        $(modalSelector + '_btn_copy').hide();
        $(modalSelector + '_btn_delete').hide();
        $(modalSelector + '_btn_add').hide();
        $(modalSelector + ' .new_input').hide();
        $(modalSelector + ' .update_input').hide();
        $(modalSelector + ' .show_input').hide();
        $(modalSelector + ' .login_id_div').hide();
        $(modalSelector + ' .add_login_info_div').hide();
        $(modalSelector + ' .del_login_info_div').hide();
        initData();
        if (ModalInput.mode === BizCommons.MODE_ADD) {
            $(modalSelector + '_btn_add').show();
            $(modalSelector + ' .new_input').show();
        } else {
            $(modalSelector + '_btn_update').show();
            $(modalSelector + '_btn_copy').show();
            $(modalSelector + '_btn_delete').show();
            $(modalSelector + ' .update_input').show();
            doFind(function(result) { Commons.focus('#data_gpac_status'); });
        }
        Commons.resetChangeInput();
    }

    /**
     * 初期値設定を行います.
     */
    function initData() {
        $('#data_id').val(ModalInput.selectedId);
        $('#data_employee_no').val('');
        $('#data_family_name').val('');
        $('#data_first_name').val('');
        $('#data_situation option:first').prop('selected', true);
        $('#data_gpac_status option:first').prop('selected', true);
        $('#data_email_addr').val('');
        $('#data_is_invite').prop('checked', false);
        $('#data_memo').val('');

        $('#data_name_show').text('');
        $('#data_employee_no_show').text('');
        $('#data_family_name_show').text('');
        $('#data_first_name_show').text('');
        $('#data_group_name_show').text('');
        $('#data_authority_name_show').text('');
        $('#data_situation_name_show').text('');
        $('#data_gpac_status_name_show').text('');
        $('#data_memo_show').text('');
        $('#data_registration_date_show').text('');
        $(modalSelector + '_login_id').text('');
    }

    /**
     * 値の設定を行います.
     * @param {object} data - データオブジェクト.
     */
    function setData(data) {
        $('#data_id').val(data.id);
        $('#data_employee_no').val(data.employee_no);
        $('#data_family_name').val(data.family_name);
        $('#data_first_name').val(data.first_name);
        $('#data_situation').val(data.situation);
        $('#data_gpac_status').val(data.gpac_status);
        $('#data_email_addr').val(data.email_addr);
        $('#data_memo').val(data.memo);

        $('#data_name_show').text(data.name);
        $('#data_employee_no_show').text(data.employee_no);
        $('#data_family_name_show').text(data.family_name);
        $('#data_first_name_show').text(data.first_name);
        $('#data_group_name_show').text(data.group_name);
        $('#data_authority_name_show').text(data.authority_name);
        $('#data_situation_name_show').text(data.situation_name);
        $('#data_gpac_status_name_show').text(data.gpac_status_name);
        $('#data_memo_show').text(Commons.escapeHTMLCL(data.memo));
        $('#data_registration_date_show').text(data.registration_date);

        if (data.possess_group_id) {
            $(modalSelector + '_btn_update').hide();
            $(modalSelector + '_btn_copy').hide();
            $(modalSelector + '_btn_delete').hide();
            $(modalSelector + '_btn_add').hide();
            $(modalSelector + ' .new_input').hide();
            $(modalSelector + ' .update_input').hide();
            $(modalSelector + ' .show_input').hide();
            $(modalSelector + '_btn_update').show();
            $(modalSelector + ' .show_input').show();
        }
        if (!data.is_login_acc) {
            $(modalSelector + ' .email_addr_div').show();
            $(modalSelector + ' .add_login_info_div').show();
        } else {
            if (data.is_owner !== Commons.ON) {
                $(modalSelector + ' .del_login_info_div').show();
            }
            $(modalSelector + ' .login_id_div').show();
            $(modalSelector + '_login_id').text(data.login_id);
        }
        if (data.gpac_status === Commons.ON) {
            $(modalSelector + '_btn_delete').hide();
        }
    }

    /**
     * コピー用の設定を行います.
     */
    function doCopy() {
        $('#mode').val(BizCommons.MODE_ADD);
        $(modalSelector + '_btn_update').hide();
        $(modalSelector + '_btn_copy').hide();
        $(modalSelector + '_btn_delete').hide();
        $(modalSelector + '_btn_add').hide();
        $(modalSelector + ' .new_input').hide();
        $(modalSelector + ' .update_input').hide();
        $(modalSelector + ' .show_input').hide();
        $(modalSelector + '_btn_add').show();
        $(modalSelector + ' .new_input').show();
        doFind(function(result) { 
                    Commons.focus('#data_employee_no'); 
                    $('#data_id').val('');
                    $('#data_situation option:first').prop('selected', true);
                    $('#data_gpac_status option:first').prop('selected', true);
                    Commons.showMessage(modalSelector, {kind:Commons.MSG_WORN, text:Commons.getCommonMessage('W00013')});
                });
    }


    /**
     * 表示するデータを検索します.
     * @param {function} success(result) - 成功時処理(処理結果).
     */
    function doFind(success) {
        Commons.action({ url: '/mnt_group_acc/find_data'
                , data: $(modalSelector + '_form').serialize()
                , success: function(result, status, xhr) {
                              if (result.status === 'OK') {
                                  setData(result.form.data);
                                  Commons.showMessages(modalSelector, result.messageList);
                                  if (success) { success(result); }
                              } else {
                                  $(modalSelector).modal('hide');
                                  Commons.showMessages('#main_contents', result.messageList);
                              }
                          }
                });
    }

    /**
     * チェック処理を行います.
     * @param {function} success() - 成功時処理.
     */
    function doCheck(success) {
        Commons.action({ url: '/mnt_group_acc/check'
                , data: $(modalSelector + '_form').serialize()
                , success: function(result, status, xhr) {
                              if (result.status === 'OK') {
                                  success();
                              } else {
                                  Commons.showMessages(modalSelector, result.messageList);
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
                , data: $(modalSelector + '_form').serialize()
                , success: function(result, status, xhr) {
                              if (result.status === 'OK') {
                                  if (success) {
                                      $(modalSelector).modal('hide');
                                      success(result);
                                  }
                              }
                              Commons.showMessages(modalSelector, result.messageList);
                          }
                });
    }

    /**
     * 更新成功時の処理を行います.
     * @param {object} result - 結果.
     */
    function successLoginInfoUpdateFunc(result) {
        $(modalSelector).modal('hide');   
        ModalInput.successFunc(result);
    };

}());



// -- ログイン情報入力ダイアログ -------------------------------------------------------------- //
var ModalInputLoginInfo = {};

//-------------------- 変数.
/** 「ＯＫ」ボタン押下時処理. */
ModalInputLoginInfo.mode = null;
ModalInputLoginInfo.selectedId = null;
ModalInputLoginInfo.successFunc = null;


//-------------------- 処理.
/**
 * ダイアログを表示します.
 * @param {object} args - パラメタ.
 * <pre>
 *  {string} mode                     - ダイアログの処理モード.
 *  {string} [selectedId]             - 選択ID.
 *  {function} [successFunc(result)]  - 更新成功時処理(処理結果).
 * </pre>
 */
ModalInputLoginInfo.show = function (args) {
    ModalInputLoginInfo.mode = null;
    ModalInputLoginInfo.selectedId = null;
    ModalInputLoginInfo.successFunc = null;
    if (args && args.mode) { ModalInputLoginInfo.mode = args.mode; }
    if (args && args.selectedId) { ModalInputLoginInfo.selectedId = args.selectedId; }
    if (args && args.successFunc) { ModalInputLoginInfo.successFunc = args.successFunc; }
    $('#modal_input_login_info').modal('show');
};
(function () {

    //------------------------------------------------------------------------
    //- 設定.
    var modalSelector = '#modal_input_login_info';


    //------------------------------------------------------------------------
    //- イベント.
    //-- ダイアログ 表示前処理. --//
    $(modalSelector).on('show.bs.modal', function(event) {
        if (event.namespace === 'bs.modal') { setModal(); }
    });
    //-- ダイアログ 表示時処理. --//
    $(modalSelector).on('shown.bs.modal', function(event) {
        if (ModalInputLoginInfo.mode === BizCommons.MODE_ADD) {
            Commons.focus('#data_login_id');
        } else {
            Commons.focus('#data_password');
        }
    });
    //-- ダイアログ 閉じる前処理. --//
    $(modalSelector).on('hide.bs.modal', function(event) {
    });
    //-- ダイアログ 閉じた後処理. --//
    $(modalSelector).on('hidden.bs.modal', function(event) {
        $('body').addClass('modal-open');
    });
    //-- 閉じる ボタン押下処理. --//
    $(modalSelector + '_btn_close').on('click', function(event) {
        Commons.closeMessage(modalSelector);
        Commons.checkChangedInput('閉じる', 'ダイアログを閉じます', function() { $(modalSelector).modal('hide'); });
    });

    //-- 追加する ボタン押下処理. --//
    $(modalSelector + '_btn_add').on('click', function (event) {
        Commons.closeMessage(modalSelector);
        $(modalSelector + ' input[type="hidden"][name="mode"]').val(BizCommons.MODE_ADD);
        doCheck(function() {
                    ModalConfirm.show({ title: '追加'
                            , msgData: {button:ModalConfirm.BTN_YES_NO, text:Commons.getCommonMessage('W00002','ログイン情報の追加') }
                            , btnYesFunc: function() { 
                                          doUpdateAction('/mnt_group_acc/add_login_info', ModalInputLoginInfo.successFunc); 
                                      }
                            , focusButton: ModalConfirm.BTN_YES
                            });
                });
    });

    //-- 削除する ボタン押下処理. --//
    $(modalSelector + '_btn_delete').on('click', function (event) {
        Commons.closeMessage(modalSelector);
        $(modalSelector + ' input[type="hidden"][name="mode"]').val(BizCommons.MODE_DEL);
        doCheck(function() {
                    ModalConfirm.show({ title: '削除'
                            , msgData: {button:ModalConfirm.BTN_YES_NO, text:Commons.getCommonMessage('W00002','ログイン情報の削除') }
                            , btnYesFunc: function() { 
                                          doUpdateAction('/mnt_group_acc/del_login_info', ModalInputLoginInfo.successFunc);  
                                      }
                            , focusButton: ModalConfirm.BTN_YES
                            });
                });
    });


    //------------------------------------------------------------------------
    //- 処理.
    /**
     * モーダルダイアログの表示設定を行います.
     */
    function setModal() {
        Commons.closeMessage(modalSelector);
        $(modalSelector + '_btn_delete').hide();
        $(modalSelector + '_btn_add').hide();
        $(modalSelector + ' .id_div').hide();
        initData();
        if (ModalInputLoginInfo.mode === BizCommons.MODE_ADD) {
            $(modalSelector + '_btn_add').show();
            $(modalSelector + ' .id_div').show();
        } else {
            $(modalSelector + '_btn_delete').show();
        }
        Commons.resetChangeInput();
    }

    /**
     * 初期値設定を行います.
     */
    function initData() {
        $(modalSelector + ' input[type="hidden"][name="data.id"]').val(ModalInputLoginInfo.selectedId);
        $('#data_login_id').val('');
        $('#data_password').val('');
    }

    /**
     * チェック処理を行います.
     * @param {function} success() - 成功時処理.
     */
    function doCheck(success) {
        Commons.action({ url: '/mnt_group_acc/login_infocheck'
                , data: $(modalSelector + '_form').serialize()
                , success: function(result, status, xhr) {
                              if (result.status === 'OK') {
                                  success();
                              } else {
                                  Commons.showMessages(modalSelector, result.messageList);
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
                , data: $(modalSelector + '_form').serialize()
                , success: function(result, status, xhr) {
                              if (result.status === 'OK') {
                                  if (success) {
                                      $(modalSelector).modal('hide');
                                      success(result);
                                  }
                              }
                              Commons.showMessages(modalSelector, result.messageList);
                          }
                });
    }

}());
