/**
 * @file mnt_emp_user.js
 */
(function () {
    //------------------------------------------------------------------------
    // メインテーブルID.
    var tableId = '#main_tbl';

    // 初期設定.
    init();


    //------------------------------------------------------------------------
    // イベント設定.
    //-- 画面ロード後のイベント処理. --//
    Commons.afterLoad = function() {
        Commons.focus('#keyword');
    };

    //-- 検索 ボタン押下処理. --//
    $('#btn_search').on("click", function (event) {
        Commons.closeMessage();
        $('#searchCond_page').val(1);
        BizCommons.sortReset();
        doSearch();
    });

    //-- 検索キーワード ENTERキー押下処理. --//
    $('#keyword').on('keypress', function (event) {
        if (event.which !== 13) { return true; }
        if (!$ReC.isStrBlk($('#keyword').val())) {
            Commons.closeMessage();
            $('#searchCond_page').val(1);
            BizCommons.sortReset();
            doSearch();
            return false;
        }
        return true;
    });

    //-- ソート項目押下処理. --//
    $('.sort-link').on('click', function (event) { 
        $('#searchCond_sort').val(BizCommons.sort($(this), $('#searchCond_sort').val())); 
        $('#searchCond_page').val(1);
        doSearch();
        return false;
    });

    //-- 新規追加処理. --//
    $('#btn_add').on('click', function (event) {
        Commons.closeMessage();
        Commons.action({
             url: '/mnt_emp_user/check_size'
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
    // 画面処理.
    /**
     * 初期値設定を行います.
     */
    function init() {
        $('#sidenavToggler').click();
        $('#keyword').val($('#searchCond_keyword').val());
        $('#is_all').prop("checked", ($('#searchCond_is_all').val() === "1"));
        setMmainTbl();
        Commons.resetChangeInput();
    }

    /**
     * リストデータの検索を行います.
     */
    function doSearch() {
        // form.
        $('#searchCond_keyword').val($('#keyword').val());
        $('#searchCond_is_all').val( ($('#is_all').prop("checked")) ? "1" : "0");
        // action.
        Commons.action({
                  url: '/mnt_emp_user/find_list'
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
            var htmlText = '';
            var id =  Commons.escapeHTML(list[i].id);
            var employee_no =  Commons.escapeHTML(list[i].employee_no);
            var name =  Commons.escapeHTML(list[i].name);
            var authority_name =  Commons.escapeHTML(list[i].authority_name);
            var group_name =  Commons.escapeHTML(list[i].group_name);
            var gpac_status_name =  Commons.escapeHTML(list[i].gpac_status_name);
            var situation_name =  Commons.escapeHTML(list[i].situation_name);
            var registration_date =  Commons.escapeHTML(list[i].registration_date);
            var memo =  Commons.escapeHTML(list[i].memo);
            var is_login_acc = (list[i].is_login_acc) ? '【有】' : '';
            htmlText += '<tr>\n';
            htmlText +=   '<td class="container">\n';
            htmlText += '<div class="row">\n';
            htmlText += '<div class="col-2 omit sub"><span title="' + employee_no + '">' + employee_no + '</span><span>　</span></div>\n';
            htmlText += '<div class="col-6 omit sub"><span title="' + name + '">' + name + '</span><span>　</span></div>\n';
            htmlText += '<div class="col-2 omit sub"><span title="' + group_name + '">' + group_name + '</span><span>　</span></div>\n';
            htmlText += '<div class="col-1 omit sub"><span title="' + gpac_status_name + '">' + gpac_status_name + '</span><span>　</span></div>\n';
            htmlText += '<div class="col-1 omit sub"><span>' + is_login_acc + '</span><span>　</span></div>\n';
            htmlText += '</div>\n';
            htmlText += '<div class="row">\n';
            htmlText += '<div class="col-2 omit sub"><span title="' + situation_name + '">' + situation_name + '</span><span>　</span></div>\n';
            htmlText += '<div class="col-6 omit sub"><span></span></div>\n';
            htmlText += '<div class="col-2 omit sub"><span title="' + authority_name + '">' + authority_name + '</span><span>　</span></div>\n';
            htmlText += '<div class="col-1 omit sub"><span title="' + registration_date + '">' + registration_date + '</span><span>　</span></div>\n';
            htmlText += '</div>\n';
            htmlText += '<div class="row">\n';
            htmlText += '<div class="col-7 omit sub"><span title="' + memo + '">' + memo + '</span><span>　</span></div>\n';
            htmlText += '</div>\n';
            htmlText +=     '<input type="hidden" name="id" value="' + id + '" />\n';
            htmlText +=   '</td>\n';
            htmlText += '</tr>\n';
            tbody.append(htmlText);
        }

        // 明細設定.
        $('#searchCond_page').val(searchCond.page);
        $('#searchCond_total_page').val(searchCond.total_page);
        $('#searchCond_total_size').val(searchCond.total_size);
        $('#list_size').val(list.length);
        setMmainTbl();
    }

    /**
     * 明細の設定を行います.
     */
    function setMmainTbl() {
        //-- 明細選択処理. --//
        Commons.setSelectTable({ tbl:tableId, selectFunc:doSelect });
        //-- ページング. --//
        Commons.setPaging({
                  list:'#pagination'
                , page:'#searchCond_page',totalPage:'#searchCond_total_page',totalSize:'#searchCond_total_size'
                , event:function() {
                            Commons.closeMessage();
                            doSearch()
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
        if ($(e.target).prop("tagName") !== 'BUTTON') {
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
        doSearch()
    };

    /**
     * 新規追加ボタンの制御を行います.
     */
    function ctrlBtnAdd() {
        if ($ReC.toInt($('#searchCond_total_size').val(), 0) >= 99) {
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


//-------------------- 表示処理.
/**
 * <pre>
 * ダイアログを表示します.
 * @param {any} args.mode ダイアログの処理モード.
 * @param {any} args.successFunc(result) 更新成功時処理.
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


//-------------------- メイン処理.
(function () {

    //------------------------------------------------------------------------
    // 設定.
    var modalSelector = '#modal_input';


    //------------------------------------------------------------------------
    // イベント.
    //-- ダイアログ 表示前処理. --//
    $(modalSelector).on('show.bs.modal', function (event) {
        if (event.namespace === 'bs.modal') { setModal(); }
    });
    //-- ダイアログ 表示時処理. --//
    $(modalSelector).on('shown.bs.modal', function (event) {
        if (ModalInput.mode === BizCommons.MODE_ADD) {
            Commons.focus('#data_employee_no');
        } else {
            Commons.focus('#data_gpac_status');
        }
    });
    //-- ダイアログ 閉じる前処理. --//
    $(modalSelector).on('hide.bs.modal', function (event) {
    });
    //-- ダイアログ 閉じた後処理. --//
    $(modalSelector).on('hidden.bs.modal', function (event) {
        $('body').addClass('modal-open');
    });

    //-- 閉じる ボタン押下処理. --//
    $(modalSelector + '_btn_close').on("click", function (event) {
        Commons.closeMessage(modalSelector);
        if (Commons.isChangedInput) {
            ModalConfirm.show({
                  title: '閉じる'
                , msgData: {button:ModalConfirm.BTN_YES_NO, text:Commons.getCommonMessage('W00003','ダイアログ')}
                , btnYesFunc: function() { $(modalSelector).modal('hide'); }
                , focusButton: ModalConfirm.BTN_NO
            });
        } else { $(modalSelector).modal('hide'); }
    });

    //-- 追加する ボタン押下処理. --//
    $(modalSelector + '_btn_add').on("click", function (event) {
        Commons.closeMessage(modalSelector);
        $('#mode').val(BizCommons.MODE_ADD);
        doCheck(function() {
                    ModalConfirm.show({ 
                          title: '追加'
                        , msgData:{button:ModalConfirm.BTN_YES_NO, text:Commons.getCommonMessage('W00002','タグの追加') }
                        , btnYesFunc: function() { doUpdateAction('/mnt_emp_user/insert'); }
                        , focusButton: ModalConfirm.BTN_YES
                    });
                });
    });

    //-- 変更する ボタン押下処理. --//
    $(modalSelector + '_btn_update').on("click", function (event) {
        Commons.closeMessage(modalSelector);
        $('#mode').val(BizCommons.MODE_UPD);
        doCheck(function() {
                    ModalConfirm.show({ 
                          title: '更新'
                        , msgData:{button:ModalConfirm.BTN_YES_NO, text:Commons.getCommonMessage('W00002','タグの更新') }
                        , btnYesFunc: function() { doUpdateAction('/mnt_emp_user/update'); }
                        , focusButton: ModalConfirm.BTN_YES
                    });
                });
    });

    //-- コピーする ボタン押下処理. --//
    $(modalSelector + '_btn_copy').on("click", function (event) {
        ModalConfirm.show({
                  title: 'コピー'
                , msgData: {button:ModalConfirm.BTN_YES_NO, text:Commons.getCommonMessage('W00001','コピーして新規追加の画面を開きます。')}
                , btnYesFunc: function() { doCopy() }
                , focusButton: ModalConfirm.BTN_NO
        });
    });

    //-- 削除する ボタン押下処理. --//
    $(modalSelector + '_btn_delete').on("click", function (event) {
        Commons.closeMessage(modalSelector);
        $('#mode').val(BizCommons.MODE_DEL);
        doCheck(function() {
                    ModalConfirm.show({ 
                          title: '削除'
                        , msgData:{button:ModalConfirm.BTN_YES_NO, text:Commons.getCommonMessage('W00002','タグの削除') }
                        , btnYesFunc: function() { doUpdateAction('/mnt_emp_user/delete'); }
                        , focusButton: ModalConfirm.BTN_YES
                    });
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
            doFind(function(){ Commons.focus('#data_gpac_status'); });
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
        }
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
        doFind(function(){ 
            Commons.focus('#data_employee_no'); 
            $('#data_id').val('');
            $('#data_situation option:first').prop('selected', true);
            $('#data_gpac_status option:first').prop('selected', true);
            Commons.showMessage(modalSelector, {kind:Commons.MSG_WORN, text:Commons.getCommonMessage('W00013')});
        });
    }


    /**
     * 表示するデータを検索します.
     * @param {function} success() - 成功時処理.
     */
    function doFind(success) {
        Commons.action({
                  url: '/mnt_emp_user/find_data'
                , data: $(modalSelector + '_form').serialize()
                , success: function(result, status, xhr) {
                        if (result.status === 'OK') {
                            setData(result.form.data);
                            Commons.showMessages(modalSelector, result.messageList);
                            if (success) { success(); }
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
        Commons.action({
                  url: '/mnt_emp_user/check'
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
     */
    function doUpdateAction(action) {
        Commons.closeMessage(modalSelector);
        Commons.action({
                  url: action
                , data: $(modalSelector + '_form').serialize()
                , success: function(result, status, xhr) {
                        if (result.status === 'OK') {
                            $(modalSelector).modal('hide');
                            if (ModalInput.successFunc != null) {
                                ModalInput.successFunc(result);
                            } else {
                                Commons.showMessages('#main_contents', result.messageList);
                                Commons.focus('#keyword');
                            }
                        } else {
                            Commons.showMessages(modalSelector, result.messageList);
                        }
                    }
                });
    }

}());