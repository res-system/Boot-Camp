/**
 * @file main_menu.js
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
        Commons.action({ url: '/main_menu/check_size'
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
        Commons.action({ url: '/main_menu/find_list'
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
            var root_group_id = Commons.escapeHTML(list[i].root_group_id);
            var user_id = Commons.escapeHTML(list[i].user_id);
            var root_group_name = Commons.escapeHTML(list[i].root_group_name);
            var group_name = Commons.escapeHTML(list[i].group_name);
            var user_name = Commons.escapeHTML(list[i].user_name);
            var save_flg = Commons.escapeHTML(list[i].save_flg);
            var save_flg_name = Commons.escapeHTML(list[i].save_flg_name);
            var authority_name = Commons.escapeHTML(list[i].authority_name);
            var updated = Commons.escapeHTML(list[i].updated);
            var is_current = Commons.escapeHTML(list[i].is_current);

            var htmlText = '';
            htmlText += '<tr>\n';
            htmlText +=   '<td class="container">\n';
            htmlText +=     '<div class="row">\n';
            htmlText +=       '<div class="col-6 omit sub"></div>\n';
            htmlText +=       '<div class="col-4 omit sub"><span title="' + authority_name + '">' + authority_name + '</span></div>\n';
            htmlText +=       '<div class="col-2 omit sub"><span title="' + updated + '">' + updated + '</span></div>\n';
            htmlText +=     '</div>\n';
            htmlText +=     '<div class="row">\n';
            htmlText +=       '<div class="col-1 omit accent">\n';
            if (is_current === Commons.ON) {
                htmlText +=     '<i class="fa fa-check-circle color-red"></i>\n';
            }
            htmlText +=       '</div>\n';
            htmlText +=       '<div class="col-5 omit accent">\n';
            htmlText +=         '<span title="' + root_group_name + '">' + root_group_name + '</span>\n';
            if (root_group_name !== group_name) {
                htmlText +=     '<span>/</span>\n';
                htmlText +=     '<span title="' + group_name + '">' + group_name + '</span>\n';
            }
            htmlText +=       '</div>\n';
            htmlText +=       '<div class="col-4 omit accent"><span title="' + user_name + '">' + user_name + '</span></div>\n';
            htmlText +=       '<div class="col-2 omit "><span title="' + save_flg_name + '">' + save_flg_name + '</span></div>\n';
            htmlText +=     '</div>\n';
            htmlText +=     '<input type="hidden" name="id" value="' + root_group_id + '" />\n';
            htmlText +=     '<input type="hidden" name="sub_id" value="' + user_id + '" />\n';
            htmlText +=     '<input type="hidden" name="is_save" value="' + save_flg + '" />\n';
            htmlText +=     '<input type="hidden" name="is_current" value="' + is_current + '" />\n';
            htmlText +=     '<input type="hidden" name="root_group_name" value="' + root_group_name + '" />\n';
            htmlText +=     '<input type="hidden" name="group_name" value="' + group_name + '" />\n';
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
        if ($('#searchCond_keyword').val()) {
            $('#main_tbl_cond').text(
                      (($('#searchCond_keyword').val()) 
                          ? '「' + $('#searchCond_keyword').val() + '」' : ''));
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
            var selected_sub_id = $(e.target).parents('tr').find('input[type="hidden"][name="sub_id"]').val();
            var is_save = $(e.target).parents('tr').find('input[type="hidden"][name="is_save"]').val();
            var is_current = $(e.target).parents('tr').find('input[type="hidden"][name="is_current"]').val();
            var root_group_name = $(e.target).parents('tr').find('input[type="hidden"][name="root_group_name"]').val();
            var group_name = $(e.target).parents('tr').find('input[type="hidden"][name="group_name"]').val();
            var user_name = $(e.target).parents('tr').find('input[type="hidden"][name="user_name"]').val();
            if (is_current !== Commons.ON) {
                ModalInput.show({mode:BizCommons.MODE_UPD, successFunc:successUpdateFunc
                        , selectedId:selected_id
                        , selectedSubId:selected_sub_id
                        , isSave:is_save
                        , rootGroupName:root_group_name
                        , groupName:group_name
                        , userName:user_name});
            }
        }
        return false;
    };

    /**
     * 更新成功時の処理を行います.
     * @param {object} result - 結果.
     */
    function successUpdateFunc(result) {
        Commons.showMessages('#main_contents', result.messageList);
        Commons.changeScreen('/main_menu/show_message/change_account');
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



// -- グループアカウント変更ダイアログ -------------------------------------------------------------- //
var ModalInput = {};

//-------------------- 変数.
/** 「ＯＫ」ボタン押下時処理. */
ModalInput.mode = null;
ModalInput.selectedId = null;
ModalInput.selectedSubId = null;
ModalInput.isSave = null;
ModalInput.rootGroupName = null;
ModalInput.groupName = null;
ModalInput.userName = null;
ModalInput.successFunc = null;


//-------------------- 処理.
/**
 * ダイアログを表示します.
 * @param {object} args - パラメタ.
 * <pre>
 *  {string} mode                     - ダイアログの処理モード.
 *  {string} [selectedId]             - 選択ID.
 *  {string} [selectedSubId]          - 選択サブID.
 *  {string} [isSave]                 - アカウント情報保存フラグ.
 *  {string} [rootGroupName]          - ルートグループ名.
 *  {string} [groupName]              - グループ名.
 *  {string} [userName]               - ユーザー名.
 *  {function} [successFunc(result)]  - 更新成功時処理(処理結果).
 * </pre>
 */
ModalInput.show = function (args) {
    ModalInput.mode = null;
    ModalInput.selectedId = null;
    ModalInput.selectedSubId = null;
    ModalInput.isSave = null;
    ModalInput.rootGroupName = null;
    ModalInput.groupName = null;
    ModalInput.userName = null;
    ModalInput.successFunc = null;
    if (args && args.mode) { ModalInput.mode = args.mode; }
    if (args && args.selectedId) { ModalInput.selectedId = args.selectedId; }
    if (args && args.selectedSubId) { ModalInput.selectedSubId = args.selectedSubId; }
    if (args && args.isSave) { ModalInput.isSave = args.isSave; }
    if (args && args.rootGroupName) { ModalInput.rootGroupName = args.rootGroupName; }
    if (args && args.groupName) { ModalInput.groupName = args.groupName; }
    if (args && args.userName) { ModalInput.userName = args.userName; }
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
        $(modalSelector + ' input[type="hidden"][name="mode"]').val(BizCommons.MODE_ADD);
        doCheck(function() {
                    ModalConfirm.show({ title: '追加'
                            , msgData: {button:ModalConfirm.BTN_YES_NO, text:Commons.getCommonMessage('W00002','グループアカウントの連携') }
                            , btnYesFunc: function() { 
                                          doUpdateAction('/main_menu/insert', ModalInput.successFunc); 
                                      }
                            , focusButton: ModalConfirm.BTN_YES
                            });
                });
    });

    //-- 変更する ボタン押下処理. --//
    $(modalSelector + '_btn_update').on('click', function (event) {
        Commons.closeMessage(modalSelector);
        $(modalSelector + ' input[type="hidden"][name="mode"]').val(BizCommons.MODE_UPD);
        doCheck(function() {
                    ModalConfirm.show({ title: '更新'
                            , msgData: {button:ModalConfirm.BTN_YES_NO, text:Commons.getCommonMessage('W00002','グループアカウントの変更') }
                            , btnYesFunc: function() { 
                                          doUpdateAction('/main_menu/update', ModalInput.successFunc); 
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
                            , msgData: {button:ModalConfirm.BTN_YES_NO, text:Commons.getCommonMessage('W00002','グループアカウントの連携を解除') }
                            , btnYesFunc: function() { 
                                          doUpdateAction('/main_menu/delete', successDeletedFunc);  
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
        $(modalSelector + '_btn_update').hide();
        $(modalSelector + '_btn_delete').hide();
        $(modalSelector + '_btn_add').hide();
        $(modalSelector + ' .code_div').hide();
        $(modalSelector + ' .id_div').hide();
        $(modalSelector + ' .key_div').hide();
        initData();
        if (ModalInput.mode === BizCommons.MODE_ADD) {
            $(modalSelector + '_btn_add').show();
            $(modalSelector + '_code_div').show();
            $(modalSelector + ' .code_div').show();
            $(modalSelector + ' .id_div').show();
            $(modalSelector + ' .key_div').show();
        } else {
            $(modalSelector + '_btn_update').show();
            $(modalSelector + '_btn_delete').show();
            if (ModalInput.isSave !== Commons.ON) {
                $(modalSelector + ' .id_div').show();
                $(modalSelector + ' .key_div').show();
            }
        }
        Commons.resetChangeInput();
    }

    /**
     * 初期値設定を行います.
     */
    function initData() {
        $(modalSelector + ' input[type="hidden"][name="searchCond.selected_id"]').val(ModalInput.selectedId);
        $(modalSelector + ' input[type="hidden"][name="searchCond.selected_sub_id"]').val(ModalInput.selectedSubId);
        $(modalSelector + ' input[type="text"][name="code"]').val('');
        $(modalSelector + ' input[type="text"][name="id"]').val('');
        $(modalSelector + ' input[type="password"][name="key"]').val('');
        $(modalSelector + ' input[type="checkbox"][name="save"]').prop('checked', (ModalInput.isSave === Commons.ON));

        var group_name = '';
        if (ModalInput.rootGroupName) {
            group_name = ModalInput.rootGroupName;
            if (ModalInput.rootGroupName !== ModalInput.groupName) {
                group_name += '/' + ModalInput.groupName;
            }
            if (ModalInput.userName) {
                group_name += ' - [' + ModalInput.userName + '] ';
            }
            group_name += 'へアカウントを切り替えます。';
        }
        $(modalSelector + '_group_name').text(group_name);
    }

    /**
     * チェック処理を行います.
     * @param {function} success() - 成功時処理.
     */
    function doCheck(success) {
        Commons.action({ url: '/main_menu/check'
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
     * 削除成功時の処理を行います.
     * @param {object} result - 結果.
     */
    function successDeletedFunc(result) {
        Commons.showMessages('#main_contents', result.messageList);
        Commons.changeScreen('/main_menu/show_message/delete_account');
    };

}());