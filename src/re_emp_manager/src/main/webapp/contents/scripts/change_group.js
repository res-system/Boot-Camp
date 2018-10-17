/**
 * @file change_group.js
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
        Commons.action({ url: '/change_group/find_list'
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
            var root_group_name = Commons.escapeHTML(list[i].root_group_name);
            var group_name = Commons.escapeHTML(list[i].group_name);
            var authority_name = Commons.escapeHTML(list[i].authority_name);
            var created = Commons.escapeHTML(list[i].created);

            var htmlText = '';
            htmlText += '<tr>\n';
            htmlText +=   '<td class="container">\n';
            if (list[i].is_root === Commons.ON) {
                htmlText += '<div class="row">\n';
                htmlText +=   '<div class="col-1 omit sub"></div>\n';
                htmlText +=   '<div class="col-6 omit sub"><span class="strong sub">[ルート]</span></div>\n';
                htmlText += '</div>\n';
            }
            htmlText +=     '<div class="row">\n';
            htmlText +=       '<div class="col-1 omit accent">\n';
            if ($('#login_group_name').val() === group_name) {
                htmlText +=     '<i class="fa fa-check-circle color-red"></i>\n';
            }
            htmlText +=       '</div>\n';
            htmlText +=       '<div class="col-6 omit accent"><span title="' + group_name + '">' + group_name + '</span></div>\n';
            htmlText +=       '<div class="col-2 omit sub"><span title="' + authority_name + '">' + authority_name + '</span></div>\n';
            htmlText +=       '<div class="col-3 omit sub"><span title="' + created + '">' + created + '</span></div>\n';
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
            changeGroup();
        }
        return false;
    };

    /**
     * グループ変更.
     */
    function changeGroup() {
        Commons.action({ url: '/change_group/change_group'
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
