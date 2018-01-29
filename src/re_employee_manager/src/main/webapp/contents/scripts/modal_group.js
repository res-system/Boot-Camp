/**
 * @file modal_group.js
 */
/** @namespace */
var ModalGroup = {};

//-------------------- 定数.



//-------------------- 変数.
/** 「ＯＫ」ボタン押下時処理. */
ModalGroup.selectedFunc = null;



//-------------------- 表示処理.
/**
 * <pre>
 * グループ選択ダイアログを表示します.
* @param {any} args.selectedFunc(id,code,name) return boolean  選択時処理.
 */
ModalGroup.show = function (args) {
    ModalGroup.selectedFunc = null;
    if (args && args.selectedFunc) { ModalGroup.selectedFunc = args.selectedFunc; }
    $('#modal_group').modal('show');
};



//-------------------- メイン処理.
(function () {

    //------------------------------------------------------------------------
    // 設定.
    var modalSelector = '#modal_group';



    //------------------------------------------------------------------------
    // イベント.
    //-- ダイアログ 表示前処理. --//
    $(modalSelector).on('show.bs.modal', function () {
        // 表示設定.
        setModal()
    });
    //-- ダイアログ 表示時処理. --//
    $(modalSelector).on('shown.bs.modal', function () {
        Commons.focus('#modal_group_keyword');
    });
    //-- ダイアログ 閉じる前処理. --//
    $(modalSelector).on('hide.bs.modal', function () {});
    //-- ダイアログ 閉じた後処理. --//
    $(modalSelector).on('hidden.bs.modal', function () {
        clearModal();
    });

    //-- 検索 ボタン押下処理. --//
    $('#modal_group_btn_search').on("click", function (event) {
        doSearch();
    });

    //-- 検索キーワード ENTERキー押下処理. --//
    $('#modal_group_keyword').on('keypress', function (event) {
        if (event.which !== 13) { return true; }
        if (!$ReC.isStrBlk($('#modal_group_keyword').val())) {
            $('#modal_group_btn_search').click();
            return false;
        }
        return true;
    });

    //-- 閉じる ボタン押下処理. --//
    $('#modal_group_btn_close').on("click", function (event) {
        $(modalSelector).modal('hide');
    });



    //------------------------------------------------------------------------
    // 処理.
    /**
     * モーダルダイアログの表示設定を行います.
     */
    function setModal() {
        clearModal();
        doSearch();
    }

    /**
     * モーダルダイアログのクリア設定を行います.
     */
    function clearModal() {
        Commons.closeMessage(modalSelector);
        Commons.scrollReset(modalSelector);
        clearInput();
        clearRow();
    }

    /**
     * 入力クリア.
     */
    function clearInput() {
        $('#modal_group_keyword').val('');
        $('#modal_group_pagination').html('');
        $('#modal_group_selected_group').html('');
    }

    /**
     * 明細行クリア.
     */
    function clearRow() {
        Commons.clearTableRow('#modal_group_tbl');
    }

    /**
     * 検索処理.
     */
    function doSearch() {
        Commons.closeMessage(modalSelector);
        clearRow();
        Commons.action({
                 url: '/modal_group/search'
                ,data: $('#modal_group_form').serialize()
                ,success: function (result, status, xhr) {
                        if (result.status === 'OK') {
                            $('#modal_group_selected_group').html(result.data.modal_group_selected_group_name);
                            $('#modal_group_page').val(result.data.modal_group_page);
                            $('#modal_group_total_page').val(result.data.modal_group_total_page);
                            $('#modal_group_total_size').val(result.data.modal_group_total_size);
                            makeRow(result.data.list);
                        } else {
                            Commons.showMessages(modalSelector, result.messageList);
                        }
                        // 初期フォーカス等.
                        Commons.focus('#modal_group_keyword');
                    }
                });
    };

    /**
     * 明細行作成.
     * @param {Array} list - 行.
     */
    function makeRow(list) {
        // リスト設定.
        clearRow();
        var tbody = $('#modal_group_tbl tbody');
        for (var i = 0, imax = list.length; i < imax; i++) {
            var rowHtml = '';
            rowHtml += '<tr class="row_' + (i + 1) + '">';
            rowHtml +=   '<td>';
            rowHtml +=     $ReC.escapeHTML(list[i].name);
            rowHtml +=     '<input type="hidden" name="group_id"   value="' + $ReC.escapeHTML(String(list[i].id)) + '"/>';
            rowHtml +=     '<input type="hidden" name="group_code" value="' + $ReC.escapeHTML(list[i].code) + '"/>';
            rowHtml +=     '<input type="hidden" name="group_name" value="' + $ReC.escapeHTML(list[i].name) + '"/>';
            rowHtml +=   '</td>';
            rowHtml += '</tr>';
            tbody.append(rowHtml);
        }
        Commons.setSelectTable({ tbl:'#modal_group_tbl', selectFunc:doSelect });

        // ページング設定.
        Commons.setPaging({list:'#modal_group_pagination', page:'#modal_group_page', totalPage:'#modal_group_total_page', totalSize:'#modal_group_total_size', event:doSearch});
    }

    /**
     * 明細行選択.
     * @param {number} row - 行(1～).
     * @param {number} col - 列(1～).
     * @return {boolean} 選択結果.
     */
    function doSelect(row,col) {
        var id   = $('#modal_group_tbl tbody tr.row_' + row).children('td').children('input[name="group_id"]').val();
        var code = $('#modal_group_tbl tbody tr.row_' + row).children('td').children('input[name="group_code"]').val();
        var name = $('#modal_group_tbl tbody tr.row_' + row).children('td').children('input[name="group_name"]').val();
        if (ModalGroup.selectedFunc != null) { 
            if (ModalGroup.selectedFunc(id, code, name)) {
                $(modalSelector).modal('hide');
            }
        } else {
            $(modalSelector).modal('hide');
        }
        return true;
    };

}());