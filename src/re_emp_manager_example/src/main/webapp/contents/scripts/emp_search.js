/**
 * @file emp_search.js
 */
(function () {
    //------------------------------------------------------------------------
    //- 設定.
    // メインテーブルID.
    var tableId = '#main_tbl';
    // ソート項目ID.
    var sortId = '#searchCond_sort';

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
     * @param {function} setSearchCond() - 検索条件設定処理.
     */
    function doSearch(setSearchCond) {
        if (setSearchCond) { setSearchCond(); }
        Commons.action({ url: '/emp_search/find_list'
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
            var employee_no = Commons.escapeHTML(list[i].employee_no);
            var name = Commons.escapeHTML(list[i].name);
            var sex_name = Commons.escapeHTML(list[i].sex_name);
            var birthday = Commons.escapeHTML(list[i].birthday) + ' ' + BizCommons.toAge(list[i].birthday);
            var situation_name = Commons.escapeHTML(list[i].situation_name);
            var hire_date = Commons.escapeHTML(list[i].hire_date) + ' ' + BizCommons.toLengthOfService(list[i].hire_date, list[i].retirement_date);
            var retirement_date = Commons.escapeHTML(list[i].retirement_date);
            var group_name = Commons.escapeHTML(list[i].group_name);
            var memo = Commons.escapeHTML(list[i].memo);
            var authority_name = Commons.escapeHTML(list[i].authority_name);

            var htmlText = '';
            htmlText += '<tr>\n';
            htmlText +=   '<td class="container">\n';
            htmlText +=     '<div class="row">\n';
            htmlText +=       '<div class="col-3 omit sub">\n';
            if (i == 0 || list[i - 1].group_name != list[i].group_name) {
                htmlText +=     '<span title="' + group_name + '">' + group_name + '</span>\n';
            }
            htmlText +=       '</div>\n';
            htmlText +=       '<div class="col-3 omit sub"><span title="' + employee_no + '">' + employee_no + '</span></div>\n';
            htmlText +=       '<div class="col-3 omit sub"><span title="' + sex_name + '">' + sex_name + '</span></div>\n';
            htmlText +=       '<div class="col-1 omit sub"><span title="' + situation_name + '">' + situation_name + '</span></div>\n';
            htmlText +=       '<div class="col-2 omit sub"><span title="' + hire_date + '">' + hire_date + '</span></div>\n';
            htmlText +=     '</div>\n';
            htmlText +=     '<div class="row">\n';
            htmlText +=       '<div class="col-3 omit sub pl-2"><span title="' + authority_name + '">' + authority_name + '</span></div>\n';
            htmlText +=       '<div class="col-3 omit"><span title="' + name + '">' + name + '</span></div>\n';
            htmlText +=       '<div class="col-3 omit sub"><span title="' + birthday + '">' + birthday + '</span></div>\n';
            htmlText +=       '<div class="col-1 omit sub"></div>\n';
            htmlText +=       '<div class="col-2 omit sub"><span title="' + retirement_date + '">' + retirement_date + '</span></div>\n';
            htmlText +=     '</div>\n';
            htmlText +=     '<div class="row">\n';
            htmlText +=       '<div class="col-3 omit sub"></div>\n';
            htmlText +=       '<div class="col-9 omit sub"><span title="' + memo + '">' + memo + '</span></div>\n';
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
            $('#searchCond_selected_id').val(selected_id);
            Commons.post('#myform', '/emp_info/next');
        }
        return false;
    };

}());
