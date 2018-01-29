/**
 * @file employee_search.js
 */
(function () {
    //------------------------------------------------------------------------
    // 初期設定.
    clearRow();
    var groupList = $ReC.parseJSON(EmployeeSearch.groupListText);

    // 検索する.
    searchEvent();



    //------------------------------------------------------------------------
    // イベント設定.
    //-- 画面ロード後のイベント処理. --//
    Commons.afterLoad = function() {
        // 初期フォーカス等.
        Commons.focus('#data_keyword');
    };

    //-- 検索キーワード ENTERキー押下処理. --//
    $('#data_keyword').on('keypress', function (event) {
        if (event.which !== 13) { return true; }
        if (!$ReC.isStrBlk($('#data_keyword').val())) {
            $('#btn_search').click();
            return false;
        }
        return true;
    });

    //-- 検索 ボタン押下処理. --//
    $('#btn_search').on('click', function (event) {
        $('#data_page').val(1);
        searchEvent();
    });

    //-- 新規追加 ボタン押下処理. --//
    $('#btn_add').on('click', function (event) {
        Commons.post('#myform', '/employee_search/add/');
    });

    //-- 更新 ボタン押下処理. --//
    $('#btn_update').on('click', function (event) {
        if (Commons.isChangedInput) {
            ModalConfirm.show({
                 msgData:{button:ModalConfirm.BTN_YES_NO, text:'変更を反映します。よろしいですか？'}
                ,btnYesFunc: function(){ 
                        doUpdate($('#myform').serialize()); 
                    }
            });
        } else {
            ModalConfirm.show({msgData:{button:ModalConfirm.BTN_ERROR, text:'内容が変更されていません。'}});
        }
    });



    //------------------------------------------------------------------------
    // 画面処理.
    /**
     * 明細行クリア.
     */
    function clearRow() {
        Commons.clearTableRow('#employee_list');
    }

    /**
     * 検索イベント.
     */
    function searchEvent() {
        var action = 'search';
        var postData = $('#myform').serialize();
        if (Commons.isChangedInput) {
            ModalConfirm.show({
                 title:'検索'
                ,msgData:{button:ModalConfirm.BTN_YES_NO, text:'社員を検索します。\n入力内容が破棄されますがよろしいですか？'}
                ,btnYesFunc: function () { 
                        doSearch(action, postData); 
                    }
                ,focusButton: ModalConfirm.BTN_NO
            });
        } else {
            doSearch(action, postData);
        }
    };

    /**
     * 検索処理.
     * @param {string} action - アクション.
     * @param {any} postData - 送信データ.
     */
    function doSearch(action, postData) {
        $('#data_selected_employee_id').val('');
        Commons.closeMessage();
        clearRow();
        Commons.action({
                 url: '/employee_search/' + action
                ,data: postData
                ,success: function (result, status, xhr) {
                        Commons.showMessages('#main_contents', result.messageList);
                        if (result.status === 'OK') {
                            makeRow( result.data.list
                                    ,result.data.data.page
                                    ,result.data.data.total_page
                                    ,result.data.data.total_size);
                        } else {
                            makeRow([], 0, 0, 0);
                        }
                        Commons.focus('#data_keyword');
                    }
                });
    };

    /**
     * 明細行作成.
     * @param {Array} list - 行.
     */
    function makeRow(list, page, total_page, total_size) {
        // 初期設定.
        var isUpdate = false;
        $('.form-control').off('change');
        Commons.isChangedInput = false;

        // リスト設定.
        clearRow();
        var tbody = $('#employee_list tbody');
        for (var i = 0, imax = list.length; i < imax; i++) {
            var listId = 'list_' + i + '_';
            var listName = 'list[' + i + '].';
            var rowHtml = '';
            rowHtml += '<tr class="row_' + (i + 1) + '">';
            rowHtml +=    '<td> ' + $ReC.escapeHTML(list[i].employee_no)      + ' </td>';
            rowHtml +=    '<td> ' + $ReC.escapeHTML(list[i].family_name) 
                                  + ' ' + $ReC.escapeHTML(list[i].first_name) + ' </td>';
            rowHtml +=    '<td> ' + $ReC.escapeHTML(list[i].position)         + ' </td>';
            rowHtml +=    '<td> ' + $ReC.escapeHTML(list[i].emp_status_name)  + ' </td>';
            rowHtml +=    '<td> ' + $ReC.escapeHTML(list[i].joining_date)     + ' </td>';
            rowHtml +=    '<td> ' + $ReC.escapeHTML(list[i].joining_date)     + ' </td>';
            rowHtml +=    '<td> ';
            if (!$ReC.isEmpty(list[i].account_id) || groupList.length <= 1) {
                rowHtml +=  $ReC.escapeHTML(list[i].group_name);
                rowHtml +=  makeHidden(listId, listName, 'group_id',   $ReC.escapeHTML(list[i].group_id) );
                rowHtml +=  makeHidden(listId, listName, 'group_name', $ReC.escapeHTML(list[i].group_name) );
            } else {
                rowHtml +=  '<div class="select">';
                rowHtml +=    '<select class="form-control group_id" ' + makeIdAndName(listId, listName, 'group_id') + ' >';
                for (var j = 0, jmax = groupList.length; j < jmax; j++) {
                    var selected = ((groupList[j].value === String(list[i].group_id)) ? 'selected="true"' : '');
                    rowHtml +=    '<option value="' + groupList[j].value + '" ' + selected + '>' + groupList[j].text + '</option>';
                }
                rowHtml +=    '</select>';
                rowHtml +=    '<span class="arrow"></span>';
                rowHtml +=  '</div>';
                isUpdate = true;
            }
            rowHtml +=      makeHidden(listId, listName, 'employee_id',     $ReC.escapeHTML(list[i].employee_id) );
            rowHtml +=      makeHidden(listId, listName, 'employee_no',     $ReC.escapeHTML(list[i].employee_no) );
            rowHtml +=      makeHidden(listId, listName, 'family_name',     $ReC.escapeHTML(list[i].family_name) );
            rowHtml +=      makeHidden(listId, listName, 'first_name',      $ReC.escapeHTML(list[i].first_name) );
            rowHtml +=      makeHidden(listId, listName, 'position',        $ReC.escapeHTML(list[i].position) );
            rowHtml +=      makeHidden(listId, listName, 'emp_status_name', $ReC.escapeHTML(list[i].emp_status_name) );
            rowHtml +=      makeHidden(listId, listName, 'joining_date',    $ReC.escapeHTML(list[i].joining_date) );
            rowHtml +=      makeHidden(listId, listName, 'leaving_date',    $ReC.escapeHTML(list[i].leaving_date) );
            rowHtml +=      makeHidden(listId, listName, 'account_id',      $ReC.escapeHTML(list[i].account_id) );
            rowHtml +=      makeHidden(listId, listName, 'is_change',       $ReC.escapeHTML(list[i].is_change) );
            rowHtml +=    '</td>';
            if (!$ReC.isEmpty(list[i].account_id) || groupList.length <= 1) {
                rowHtml +='<td></td>';
            } else {
                rowHtml +='<td> ';
                rowHtml +=    '<label class="checkbox">';
                rowHtml +=      '<input type="checkbox" class="form-control is_delete" value="1" ';
                rowHtml +=          makeIdAndName(listId, listName, 'is_delete') + ' ' + ((list[i].is_delete==='1')?'checked':'') + ' />';
                rowHtml +=      '<span class="indicator"><br />削除</span>';
                rowHtml +=    '</label>';
                rowHtml += '</td>';
            }
            rowHtml += '</tr>';
            tbody.append(rowHtml);
        }
        Commons.setSelectTable({ tbl:'#employee_list', selectFunc:doSelect });
        $('#list_size').val(list.length);

        // ページング設定.
        $('#data_page').val(page);
        $('#data_total_page').val(total_page);
        $('#data_total_size').val(total_size);
        Commons.setPaging({list:'#employee_search_pagination', page:'#data_page', totalPage:'#data_total_page', totalSize:'#data_total_size', event:searchEvent});

        // 更新ボタン表示制御.
        if (isUpdate) { 
          $('#btn_update').fadeIn('fast');
        } else { 
          $('#btn_update').fadeOut('fast'); 
        }

        // 入力内容変更イベント設定.
        Commons.isChangedInput = false;
        $('#employee_list tbody').find('.form-control').on('change', function (event) {
            $(this).parents('tr').find('input[type="hidden"].is_change').val('1');
            Commons.isChangedInput = true;
        });
    }

    /**
     * idとnameの作成.
     * @param {string} listId - リストID.
     * @param {string} listName - リスト名称.
     * @param {string} name - 名称.
     * @return {string} HTML文字列.
     */
    function makeIdAndName(listId, listName, name) {
        return 'id="' + listId + name + '" name="' + listName + name + '"';
    }

    /**
     * hiddenの作成.
     * @param {string} listId - リストID.
     * @param {string} listName - リスト名称.
     * @param {string} name - 名称.
     * @param {string} value - 値.
     * @return {string} HTML文字列.
     */
    function makeHidden(listId, listName, name, value) {
        return '<input type="hidden" class="' + name + '" ' + makeIdAndName(listId, listName, name) + ' value="' + value + '" />';
    }

    /**
     * 明細行選択.
     * @param {number} row - 行(1～).
     * @param {number} col - 列(1～).
     * @return {boolean} 選択結果.
     */
    function doSelect(row,col) {
        if (col < 7) {
            if (Commons.isChangedInput) {
                ModalConfirm.show({
                     title:'社員選択'
                    ,msgData:{button:ModalConfirm.BTN_YES_NO, text:'社員個人情報管理[詳細]に遷移します。\n入力内容が破棄されますがよろしいですか？'}
                    ,btnYesFunc: function () { 
                            selectedFunc(row,col); 
                        }
                    ,focusButton: ModalConfirm.BTN_NO
                });
            } else {
                selectedFunc(row,col);
            }
        }
        return false;
    };

    /**
     * 明細行選択後処理.
     * @param {number} row - 行(1～).
     * @param {number} col - 列(1～).
     */
    function selectedFunc(row,col) {
        // 行選択色設定.
        var tr = $('#employee_list tbody tr.row_' + row);
        Commons.setSelectedRow(tr);

        // 選択処理.
        var employee_id = $('#employee_list tbody tr.row_' + row).children('td').children('input[type="hidden"].employee_id').val();
        $('#data_selected_employee_id').val(employee_id);
        Commons.post('#myform', '/employee_search/select');
    };

    /**
     * 更新処理.
     * @param {any} postData - 送信データ.
     */
    function doUpdate(postData) {
        Commons.closeMessage();
        Commons.action({
                 url: '/employee_search/update'
                ,data: postData
                ,success: function (result, status, xhr) {
                        Commons.showMessages('#main_contents', result.messageList);
                        if (result.status === 'OK') {
                            $('#data_page').val(1);
                            doSearch('complete', postData);
                        } else {
                            makeRow( result.data.list
                                    ,result.data.data.page
                                    ,result.data.data.total_page
                                    ,result.data.data.total_size);
                            // 初期フォーカス等.
                            Commons.focus('#data_keyword');
                        }
                    }
                });
    };



}());