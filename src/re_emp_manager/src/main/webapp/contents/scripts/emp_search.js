/**
 * @file emp_search.js
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
                  url: '/emp_search/find_list'
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
            var situation_name =  Commons.escapeHTML(list[i].situation_name);
            var hire_date =  Commons.escapeHTML(list[i].hire_date);
            var retirement_date =  Commons.escapeHTML(list[i].retirement_date);
            var group_name =  Commons.escapeHTML(list[i].group_name);
            var memo =  Commons.escapeHTML(list[i].memo);
            var authority_name =  Commons.escapeHTML(list[i].authority_name);
            htmlText += '<tr>\n';
            htmlText +=   '<td class="container">\n';
            htmlText +=     '<div class="row">\n';
            htmlText +=       '<div class="col-2 col-lg-1 omit sub"><span title="' + employee_no + '">' + employee_no + '</span><span>　</span></div>\n';
            htmlText +=       '<div class="col-4 col-lg-5 omit sub"><span title="' + name + '">' + name + '</span><span>　</span></div>\n';
            htmlText +=       '<div class="col-1 omit sub"><span title="' + situation_name + '">' + situation_name + '</span><span>　</span></div>\n';
            htmlText +=       '<div class="col-1 omit sub"><span title="' + hire_date + '">' + hire_date + '</span><span>　</span></div>\n';
            htmlText +=       '<div class="col-1 omit sub"><span title="' + retirement_date + '">' + retirement_date + '</span><span>　</span></div>\n';
            htmlText +=       '<div class="col-3 omit sub"><span title="' + group_name + '">' + group_name + '</span><span>　</span></div>\n';
            htmlText +=     '</div>\n';
            htmlText +=     '<div class="row">\n';
            htmlText +=       '<div class="col-2 col-lg-1 omit sub"><span>　</span></div>\n';
            htmlText +=       '<div class="col-7 col-lg-8 omit sub"><span title="' + memo + '">' + memo + '</span><span>　</span></div>\n';
            htmlText +=       '<div class="col-3 omit sub"><span title="' + authority_name + '">' + authority_name + '</span><span>　</span></div>\n';
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
            Commons.post('#myform', '/emp_info/next');
        }
        return false;
    };

}());