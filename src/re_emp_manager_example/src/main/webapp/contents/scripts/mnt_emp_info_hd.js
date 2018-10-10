/**
 * @file mnt_emp_info_hd.js
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
        $('#keyword').val($('#searchCond_keyword').val());
        $('#is_all').prop('checked', ($('#searchCond_is_all').val() === Commons.ON));
        BizCommons.setSort(tableId, sortId);
        setMainTbl();
        Commons.resetChangeInput();
    }

    /**
     * リストサイズのチェックを行います.
     * @param {function} success(result) - 成功時処理(処理結果).
     */
    function checkSize(success) {
        Commons.action({ url: '/mnt_emp_info_hd/check_size'
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
        Commons.action({ url: '/mnt_emp_info_hd/find_list'
                , data: $('#myform').serialize()
                , success: function(result,status,xhr) {
                              Commons.showMessages('#main_contents', result.messageList);
                              makeRow(result.form.list, result.form.searchCond);
                              Commons.focus('#keyword');
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
            var seq = Commons.escapeHTML(list[i].seq);
            var label = Commons.escapeHTML(list[i].label);
            var type_name = Commons.escapeHTML(list[i].type_name);
            var required_name = Commons.escapeHTML(list[i].required_name);
            var maxlength = Commons.escapeHTML(list[i].maxlength);
            var status_name = Commons.escapeHTML(list[i].status_name);
            var memo = Commons.escapeHTML(list[i].memo);

            var htmlText = '';
            htmlText += '<tr>\n';
            htmlText +=   '<td class="container">\n';
            htmlText +=     '<div class="row">\n';
            htmlText +=       '<div class="col-1  omit sub"><span title="' + seq + '">' + seq + '</span></div>\n';
            htmlText +=       '<div class="col-7  omit sub"><span title="' + label + '">' + label + '</span></div>\n';
            htmlText +=       '<div class="col-1  omit sub"><span title="' + type_name + '">' + type_name + '</span></div>\n';
            htmlText +=       '<div class="col-1  omit sub"><span title="' + required_name + '">' + required_name + '</span></div>\n';
            htmlText +=       '<div class="col-1  omit sub"><span title="' + maxlength + '">' + maxlength + '</span></div>\n';
            htmlText +=       '<div class="col-1  omit sub"><span title="' + status_name + '">' + status_name + '</span></div>\n';
            htmlText +=     '</div>\n';
            htmlText +=     '<div class="row">\n';
            htmlText +=       '<div class="col-1  omit sub"></div>\n';
            htmlText +=       '<div class="col-11 omit sub"><span title="' + memo + '">' + memo + '</span></div>\n';
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

        // 検索条件設定.
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
        Commons.focus('#data_seq');
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
                            , msgData: {button:ModalConfirm.BTN_YES_NO, text:Commons.getCommonMessage('W00002','社員情報ヘッダーの追加') }
                            , btnYesFunc: function() { 
                                          doUpdateAction('/mnt_emp_info_hd/insert', ModalInput.successFunc); 
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
                            , msgData: {button:ModalConfirm.BTN_YES_NO, text:Commons.getCommonMessage('W00002','社員情報ヘッダーの更新') }
                            , btnYesFunc: function() { 
                                          doUpdateAction('/mnt_emp_info_hd/update', ModalInput.successFunc); 
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
                            , msgData: {button:ModalConfirm.BTN_YES_NO, text:Commons.getCommonMessage('W00002','社員情報ヘッダーの削除') }
                            , btnYesFunc: function() { 
                                          doUpdateAction('/mnt_emp_info_hd/delete', ModalInput.successFunc);  
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
        initData();
        if (ModalInput.mode === BizCommons.MODE_ADD) {
            $(modalSelector + '_btn_add').show();
            $(modalSelector + ' .new_input').show();
            doGetMaxSeq();
        } else {
            $(modalSelector + '_btn_update').show();
            $(modalSelector + '_btn_copy').show();
            $(modalSelector + '_btn_delete').show();
            $(modalSelector + ' .update_input').show();
            doFind(function(result) { Commons.focus('#data_seq'); });
        }
        Commons.resetChangeInput();
    }

    /**
     * 初期値設定を行います.
     */
    function initData() {
        $('#data_id').val(ModalInput.selectedId);
        $('#data_seq').val('');
        $('#data_label').val('');
        $('#data_type option:first').prop('selected', true);
        $('#data_maxlength').val('0');
        $('#data_required option:first').prop('selected', true);
        $('#data_status option:first').prop('selected', true);
        $('#data_memo').val('');
    }

    /**
     * 値の設定を行います.
     * @param {object} data - データオブジェクト.
     */
    function setData(data) {
        $('#data_id').val(data.id);
        $('#data_seq').val(data.seq);
        $('#data_label').val(data.label);
        $('#data_type').val(data.type);
        $('#data_maxlength').val(data.maxlength);
        $('#data_required').val(data.required);
        $('#data_status').val(data.status);
        $('#data_memo').val(data.memo);
        if (data.status === Commons.OFF) {
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
                    Commons.focus('#data_relationship'); 
                    $('#data_id').val('');
                    Commons.showMessage(modalSelector, {kind:Commons.MSG_WORN, text:Commons.getCommonMessage('W00013')});
                    doGetMaxSeq();
                });
    }


    /**
     * 表示するデータを検索します.
     * @param {function} success(result) - 成功時処理(処理結果).
     */
    function doFind(success) {
        Commons.action({ url: '/mnt_emp_info_hd/find_data'
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
        Commons.action({ url: '/mnt_emp_info_hd/check'
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
     * 最大表示順取を得します.
     */
    function doGetMaxSeq() {
        Commons.action({ url: '/mnt_emp_info_hd/get_max_seq'
                , data: $(modalSelector + '_form').serialize()
                , success: function(result, status, xhr) {
                              if (result.status === 'OK') {
                                  $('#data_seq').val(result.form.maxSeq);
                              } else {
                                  $(modalSelector).modal('hide');
                                  Commons.showMessages('#main_contents', result.messageList);
                              }
                          }
                });
    }

}());