/**
 * @file change_group.js
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


    //------------------------------------------------------------------------
    // 画面処理.
    /**
     * 初期値設定を行います.
     */
    function init() {
        $('#sidenavToggler').click();
        $('#keyword').val($('#searchCond_keyword').val());
        setMmainTbl();
        Commons.resetChangeInput();
    }

    /**
     * リストデータの検索を行います.
     */
    function doSearch() {
        // form.
        $('#searchCond_keyword').val($('#keyword').val());
        // action.
        Commons.action({
                  url: '/change_group/find_list'
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
            var root_group_name =  Commons.escapeHTML(list[i].root_group_name);
            var group_name =  Commons.escapeHTML(list[i].group_name);
            var user_name =  Commons.escapeHTML(list[i].user_name);
            var authority_name =  Commons.escapeHTML(list[i].authority_name);
            var created =  Commons.escapeHTML(list[i].created);
            htmlText += '<tr>\n';
            htmlText +=   '<td class="container">\n';
            htmlText +=     '<div class="row pb-3">\n';
            htmlText +=       '<div class="col-12 omit accent">\n';
            htmlText +=         '<span title="' + root_group_name + '">' + root_group_name + '</span>\n';
            if (root_group_name !== group_name) {
                htmlText +=     '<span>／</span>\n';
                htmlText +=     '<span title="' + group_name + '">' + group_name + '</span>\n';
            }
            htmlText +=         '<span>　</span>\n';
            htmlText +=       '</div>\n';
            htmlText +=     '</div>\n';
            htmlText +=     '<div class="row">\n';
            htmlText +=       '<div class="col-2 omit "><span title="' + authority_name + '">' + authority_name + '</span><span>　</span></div>\n';
            htmlText +=       '<div class="col-6 omit "><span title="' + user_name + '">' + user_name + '</span><span>　</span></div>\n';
            htmlText +=       '<div class="col-3 omit "><span title="' + created + '">' + created + '</span><span>　</span></div>\n';
            htmlText +=       '<div class="col-1 omit sub">\n';
            if ($('#login_group_name').val() === group_name) {
                htmlText +=     '<span class="color-red">[選択中]</span>\n';
            }
            htmlText +=       '</div>\n';
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
            $('#searchCond_selected_id').val(selected_id);
            doUpdate();
        }
        return false;
    };

    /**
     * グループ変更.
     */
    function doUpdate() {
        Commons.action({
                  url: '/change_group/change_group'
                , data: $('#myform').serialize()
                , success: function (result, status, xhr) {
                        if (result.status === 'OK') {
                            init();
                            Commons.changeScreen('/main_menu/show_message/change_group');
                        } else {
                            Commons.showMessages('#main_contents', result.messageList);
                            Commons.focus('#keyword');
                        }
                    }
                });
    };

}());