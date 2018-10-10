/**
 * @file modal_select_group.js
 */
var ModalSelectGroup = {};

//-------------------- 定数.


//-------------------- 変数.
/** 選択時処理. */
ModalSelectGroup.selectedFunc = null;
ModalSelectGroup.isAdd = false;


//-------------------- 処理.
/**
 * ダイアログを表示します.
 * @param {object} args - パラメタ.
 * <pre>
 *  {function} [selectedFunc(data)]  - 選択時処理(選択データ).
 *  {boolean} [isAdd]  - 追加機能有無.
 * </pre>
 */
ModalSelectGroup.show = function (args) {
    ModalSelectGroup.selectedFunc = null;
    if (args && args.selectedFunc) { ModalSelectGroup.selectedFunc = args.selectedFunc; }
    if (args && args.isAdd) { ModalSelectGroup.isAdd = args.isAdd; }
    $('#modal_select_group').modal('show');
};
(function () {

    //------------------------------------------------------------------------
    //- 設定.
    var modalSelector = '#modal_select_group';
    var tableId = modalSelector + '_tbl';
    var sortId = modalSelector + '_searchCond_sort';
    var dataList = null;


    //------------------------------------------------------------------------
    //- イベント.
    //-- ダイアログ 表示前処理. --//
    $(modalSelector).on('show.bs.modal', function(event) {
        if (event.namespace === 'bs.modal') { setModal(); }
    });
    //-- ダイアログ 表示時処理. --//
    $(modalSelector).on('shown.bs.modal', function(event) {
        Commons.focus(modalSelector + '_keyword');
    });
    //-- ダイアログ 閉じる前処理. --//
    $(modalSelector).on('hide.bs.modal', function(event) {
    });
    //-- ダイアログ 閉じた後処理. --//
    $(modalSelector).on('hidden.bs.modal', function(event) {
        $('body').addClass('modal-open');
        clearModal();
    });
    //-- 閉じる ボタン押下処理. --//
    $(modalSelector + '_btn_close').on('click', function (event) {
        $(modalSelector).modal('hide');
    });

    //-- 検索キーワード ENTERキー押下処理. --//
    $(modalSelector + '_keyword').on('keypress', function(event) {
        if (event.which !== 13) { return true; }
        $(modalSelector + '_btn_search').click();
        return false;
    });

    //-- 検索 ボタン押下処理. --//
    $(modalSelector + '_btn_search').on('click', function(event) {
        Commons.closeMessage();
        doSearch(function() {
                    $(modalSelector + '_searchCond_page').val(1);
                    BizCommons.sortReset(tableId, sortId);
                    $(modalSelector + '_searchCond_keyword').val($(modalSelector + '_keyword').val());
                    $(modalSelector + '_searchCond_is_all').val(($(modalSelector + '_is_all').prop('checked')) ? Commons.ON : Commons.OFF);
                });
    });

    //-- ソート項目押下処理. --//
    $(modalSelector + ' .sort-link').on('click', function(event) { 
        Commons.closeMessage();
        var sortLink = $(this);
        doSearch(function() {
                    $(modalSelector + '_searchCond_page').val(1);
                    BizCommons.sort(tableId, sortId, sortLink);
                });
        return false;
    });

    //-- 追加する ボタン押下処理. --//
    $(modalSelector + '_btn_add').on('click', function(event) {
        Commons.closeMessage(modalSelector);
        doCheck(function() {
                    ModalConfirm.show({ title: '追加'
                            , msgData: {button:ModalConfirm.BTN_YES_NO, text:Commons.getCommonMessage('W00002','グループの追加') }
                            , btnYesFunc: function() { 
                                          doInsert(); 
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
        clearModal();
        $(modalSelector + ' .new_input').hide();
        if (ModalSelectGroup.isAdd) {
            $(modalSelector + ' .new_input').show();
        }
        doSearch();
    }

    /**
     * モーダルダイアログのクリア設定を行います.
     */
    function clearModal() {
        Commons.closeMessage(modalSelector);
        init();
    }



    //------------------------------------------------------------------------
    //- 画面処理.
    /**
     * 初期化.
     */
    function init() {
        $(modalSelector + '_name').val('');
        $(modalSelector + '_keyword').val('');
        $(modalSelector + '_is_all').prop('checked', false);
        $(modalSelector + '_searchCond_keyword').val('');
        $(modalSelector + '_searchCond_is_all').val('');
        $(modalSelector + '_searchCond_page').val(1);
        $(modalSelector + '_searchCond_selected_id').val('');
        $(modalSelector + '_searchCond_selected_sub_id').val('');
        $(modalSelector + '_searchCond_sort').val('');
        clearRow();
    }

    /**
     * リストデータの検索を行います.
     * @param {function} setSearchCond() - 検索条件設定処理.
     */
    function doSearch(setSearchCond) {
        if (setSearchCond) { setSearchCond(); }
        Commons.action({ url: '/select_group/find_list'
                , data: $(modalSelector + '_form').serialize()
                , success: function(result, status, xhr) {
                              makeRow(result.form.list, result.form.searchCond);
                              Commons.showMessages(modalSelector, result.messageList);
                              Commons.focus(modalSelector + '_keyword');
                          }
                });
    };

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
            var name = Commons.escapeHTML(list[i].name);
            var member_count = Commons.escapeHTML(list[i].member_count);
            var grp_status_name = Commons.escapeHTML(list[i].grp_status_name);
            var memo = Commons.escapeHTML(list[i].memo);

            var htmlText = '';
            htmlText += '<tr>\n';
            htmlText +=   '<td class="container">\n';
            if (list[i].is_root === Commons.ON) {
                htmlText += '<div class="row">\n';
                htmlText +=   '<div class="col-6 omit sub"><span class="strong sub">[ルート]</span></div>\n';
                htmlText += '</div>\n';
            }
            htmlText +=     '<div class="row">\n';
            htmlText +=       '<div class="col-5 omit "><span title="' + name + '">' + name + '</span></div>\n';
            htmlText +=       '<div class="col-2 omit sub"><span title="' + member_count + '">' + member_count + '</span></div>\n';
            htmlText +=       '<div class="col-2 omit sub"><span title="' + grp_status_name + '">' + grp_status_name + '</span></div>\n';
            htmlText +=       '<div class="col-3 omit sub"><span title="' + memo + '">' + memo + '</span></div>\n';
            htmlText +=     '</div>\n';
            htmlText +=     '<input type="hidden" name="selected_index" value="' + i + '" />';
            htmlText +=   '</td>\n';
            htmlText += '</tr>\n';
            tbody.append(htmlText);
        }
        dataList = list;

        // 明細設定.
        $(modalSelector + '_searchCond_page').val(searchCond.page);
        $(modalSelector + '_searchCond_total_page').val(searchCond.total_page);
        $(modalSelector + '_searchCond_total_size').val(searchCond.total_size);
        $(modalSelector + '_searchCond_sort').val(searchCond.sort);
        setMainTbl();

        // 検索条件設定.
        $(modalSelector + '_keyword').val($(modalSelector + '_searchCond_keyword').val());
        $(modalSelector + '_is_all').prop('checked', ($(modalSelector + '_searchCond_is_all').val() === Commons.ON));
        if ($(modalSelector + '_searchCond_keyword').val() || $(modalSelector + '_searchCond_is_all').val() === Commons.ON) {
            $(modalSelector + '_tbl_cond').text(
                      (($(modalSelector + '_searchCond_keyword').val()) 
                          ? '「' + $(modalSelector + '_searchCond_keyword').val() + '」' : '')
                    + (($(modalSelector + '_searchCond_is_all').val() === Commons.ON) 
                          ? ' (無効も含める)' : ''));
            $(modalSelector + '_tbl_cond_div').show();
        } else {
            $(modalSelector + '_tbl_cond').text('');
            $(modalSelector + '_tbl_cond_div').hide();
        }
    }

    /**
     * 明細行をクリアします.
     */
    function clearRow() {
        dataList = null;
        Commons.clearTableRow(tableId);
    }

    /**
     * 明細の設定を行います.
     */
    function setMainTbl() {
        //-- 明細選択処理. --//
        Commons.setSelectTable({ tbl:tableId, selectFunc:doSelect });
        //-- ページング. --//
        Commons.setPaging({
                  list:modalSelector + '_pagination'
                , page:modalSelector + '_searchCond_page',totalPage:modalSelector + '_searchCond_total_page',totalSize:modalSelector + '_searchCond_total_size'
                , event:function() {
                              Commons.closeMessage(modalSelector);
                              doSearch();
                          }
                });
    }

    /**
     * 明細行の選択処理を行います.
     * @param {number} row - 行(1～).
     * @param {number} col - 列(1～).
     * @param {object} e   - イベントオブジェクト.
     * @return {boolean} 選択結果.
     */
    function doSelect(row,col,e) {
        Commons.closeMessage(modalSelector);
        if ($(e.target).prop('tagName') !== 'BUTTON') {
            var selected_index = $(e.target).parents('tr').find('input[type="hidden"][name="selected_index"]').val();
            if (ModalSelectGroup.selectedFunc != null) { 
                if (ModalSelectGroup.selectedFunc(dataList[selected_index])) {
                    $(modalSelector).modal('hide');
                }
            } else {
                $(modalSelector).modal('hide');
            }
        }
        return false;
    };

    /**
     * チェック処理を行います.
     * @param {function} success() - 成功時処理.
     */
    function doCheck(success) {
        Commons.action({ url: '/select_group/check'
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
     * 追加処理を行います.
     */
    function doInsert() {
        Commons.action({ url: '/select_group/insert'
                , data: $(modalSelector + '_form').serialize()
                , success: function(result, status, xhr) {
                              Commons.showMessages(modalSelector, result.messageList);
                              if (result.status === 'OK') {
                                  $(modalSelector + '_name').val('');
                                  if (ModalSelectGroup.selectedFunc != null) { 
                                      if (ModalSelectGroup.selectedFunc(result.form.data)) {
                                          $(modalSelector).modal('hide');
                                      }
                                  } else {
                                      $(modalSelector).modal('hide');
                                  }
                              }
                          }
                });
    }

}());