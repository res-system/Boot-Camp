/**
 * @file emp_family.js
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
        Commons.focus('#keyword');
    };

    //-- 新規追加処理. --//
    $('#btn_add').on('click', function (event) {
        Commons.closeMessage();
        Commons.action({ url: '/emp_family/check_size'
                , data: $('#myform').serialize()
                , success: function (result, status, xhr) {
                              if (result.status === 'OK') {
                                  ModalInput.show({mode:BizCommons.MODE_ADD, successFunc:successUpdateFunc});
                              } else {
                                  Commons.showMessages('#main_contents', result.messageList);
                              }
                          }
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
     * リストデータの検索を行います.
     */
    function doSearch() {
        Commons.action({ url: '/emp_family/find_list'
                , data: $('#myform').serialize()
                , success: function(result,status,xhr) {
                              Commons.showMessages('#main_contents', result.messageList);
                              makeRow(result.form.list, result.form.searchCond);
                              Commons.focus('#keyword');
                          }
                });
        Commons.resetChangeInput();
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
            var seq = Commons.escapeHTML(list[i].seq);
            var relationship = Commons.escapeHTML(list[i].relationship);
            var family_name = Commons.escapeHTML(list[i].family_name);
            var first_name = Commons.escapeHTML(list[i].first_name);
            var sex_name = Commons.escapeHTML(list[i].sex_name);
            var mynumber = Commons.escapeHTML(list[i].mynumber);
            var living_name = Commons.escapeHTML(list[i].living_name);
            var family_name_kana = Commons.escapeHTML(list[i].family_name_kana);
            var first_name_kana = Commons.escapeHTML(list[i].first_name_kana);
            var birthday = Commons.escapeHTML(list[i].birthday) + BizCommons.toAge(list[i].birthday);
            var memo = Commons.escapeHTML(list[i].memo);

            var htmlText = '';
            htmlText += '<tr>\n';
            htmlText +=   '<td class="container">\n';
            htmlText +=     '<div class="row">\n';
            htmlText +=       '<div class="col-2 omit sub"><span title="' + relationship + '">' + relationship + '</span></div>\n';
            htmlText +=       '<div class="col-4 omit sub">\n';
            htmlText +=         '<span title="' + family_name + '">' + family_name + '</span>\n';
            htmlText +=         '<span title="' + first_name + '">' + first_name + '</span>\n';
            htmlText +=       '</div>\n';
            htmlText +=       '<div class="col-2 omit sub"><span title="' + sex_name + '">' + sex_name + '</span></div>\n';
            htmlText +=       '<div class="col-4 omit sub"><span title="' + mynumber + '">' + mynumber + '</span></div>\n';
            htmlText +=     '</div>\n';
            htmlText +=     '<div class="row">\n';
            htmlText +=       '<div class="col-2 omit sub"><span title="' + living_name + '">' + living_name + '</span></span></div>\n';
            htmlText +=       '<div class="col-4 omit sub">\n';
            if (!$ReC.isStrBlk(family_name_kana) || !$ReC.isStrBlk(first_name_kana)) {
                htmlText +=     '<span>(</span>\n';
                htmlText +=     '<span title="' + family_name_kana + '">' + family_name_kana + '</span>\n';
                htmlText +=     '<span title="' + first_name_kana + '">' + first_name_kana + '</span>\n';
                htmlText +=     '<span>)</span>\n';
            }
            htmlText +=         '\n';
            htmlText +=       '</div>\n';
            htmlText +=       '<div class="col-2 omit sub"><span title="' + birthday + '">' + birthday + '</span></div>\n';
            htmlText +=       '<div class="col-4 omit sub"><span title="' + memo + '">' + memo + '</span></div>\n';
            htmlText +=     '</div>\n';
            htmlText +=     '<input type="hidden" name="seq" value="' + seq + '" />\n';
            htmlText +=   '</td>\n';
            htmlText += '</tr>\n';
            tbody.append(htmlText);
        }

        // 明細設定.
        $('#list_size').val(list.length);
        setMainTbl();
    }

    /**
     * 明細の設定を行います.
     */
    function setMainTbl() {
        //-- 明細選択処理. --//
        Commons.setSelectTable({ tbl:tableId, selectFunc:doSelect });
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
            var selected_id = $(e.target).parents('tr').find('input[type="hidden"][name="seq"]').val();
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
        Commons.focus('#data_relationship');
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
                            , msgData: {button:ModalConfirm.BTN_YES_NO, text:Commons.getCommonMessage('W00002','家族情報の追加') }
                            , btnYesFunc: function() { 
                                          doUpdateAction('/emp_family/insert', ModalInput.successFunc); 
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
                            , msgData: {button:ModalConfirm.BTN_YES_NO, text:Commons.getCommonMessage('W00002','家族情報の更新') }
                            , btnYesFunc: function() { 
                                          doUpdateAction('/emp_family/update', ModalInput.successFunc); 
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
                            , msgData: {button:ModalConfirm.BTN_YES_NO, text:Commons.getCommonMessage('W00002','家族情報の削除') }
                            , btnYesFunc: function() { 
                                          doUpdateAction('/emp_family/delete', ModalInput.successFunc);  
                                      }
                            , focusButton: ModalConfirm.BTN_YES
                            });
                });
    });

    //-- コピーする ボタン押下処理. --//
    $(modalSelector + '_btn_copy').on('click', function (event) {
        ModalConfirm.show({ title: 'コピー'
                , msgData: {button:ModalConfirm.BTN_YES_NO, text:Commons.getCommonMessage('W00001','コピーして新規追加します。')}
                , btnYesFunc: function() { 
                              doCopy();
                          }
                , focusButton: ModalConfirm.BTN_NO
                });
    });


    //------------------------------------------------------------------------
    // 処理.
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
        } else {
            $(modalSelector + '_btn_update').show();
            $(modalSelector + '_btn_copy').show();
            $(modalSelector + '_btn_delete').show();
            $(modalSelector + ' .update_input').show();
            doFind(function(result) { Commons.focus('#data_relationship'); });
        }
        Commons.resetChangeInput();
    }

    /**
     * 初期値設定を行います.
     */
    function initData() {
        $('#data_seq').val(ModalInput.selectedId);
        $('#data_personal_id').val('');
        $('#data_relationship').val('');
        $('#data_living option:first').prop('selected', true);
        $('#data_family_name').val('');
        $('#data_first_name').val('');
        $('#data_family_name_kana').val('');
        $('#data_first_name_kana').val('');
        $('#data_sex option:first').prop('selected', true);
        $('#data_birthday').val('');
        $('#data_mynumber').val('');
        $('#data_memo').val('');
    }

    /**
     * 値の設定を行います.
     * @param {object} data - データオブジェクト.
     */
    function setData(data) {
        $('#data_seq').val(data.seq);
        $('#data_personal_id').val(data.personal_id);
        $('#data_relationship').val(data.relationship);
        $('#data_living').val(data.living);
        $('#data_family_name').val(data.family_name);
        $('#data_first_name').val(data.first_name);
        $('#data_family_name_kana').val(data.family_name_kana);
        $('#data_first_name_kana').val(data.first_name_kana);
        $('#data_sex').val(data.sex);
        $('#data_birthday').datepicker('setDate', data.birthday);
        $('#data_mynumber').val(data.mynumber);
        $('#data_memo').val(data.memo);
    }

    /**
     * コピー用の設定を行います.
     */
    function doCopy() {
        Commons.closeMessage(modalSelector);
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
                    $('#data_seq').val('');
                    $('#data_personal_id').val('');
                    Commons.showMessage(modalSelector, {kind:Commons.MSG_WORN, text:Commons.getCommonMessage('W00013')});
                });
    }


    /**
     * 表示するデータを検索します.
     * @param {function} [success(result)] - 成功時処理(処理結果).
     */
    function doFind(success) {
        Commons.action({ url: '/emp_family/find_data'
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
        Commons.action({ url: '/emp_family/check'
                , data: $(modalSelector + '_form').serialize()
                , success: function (result, status, xhr) {
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
