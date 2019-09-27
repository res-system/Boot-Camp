/**
 * @file crud_sample_list.js
 */
(function () {
  //------------------------------------------------------------------------
  //- 設定.
  var targetMain = '#main_contents';
  var targetForm = '#myform';
  var targetInput= targetForm + ' .form-control';
  var targetLabel= targetForm + ' .form-control-static';
  var targetTbl  = targetMain + ' .tbl_main';

  // 初期設定.
  init();



  //------------------------------------------------------------------------
  //- イベント設定.
  //-- 初期設定. --//
  function init() {
    Commons.closeMessage(targetMain);
    initTable();
    initData();
  }
  //-- 画面ロード後のイベント処理. --//
  Commons.afterLoad = function () {
        Commons.resetChangeInput();
      };


  //-- 「リロード」押下処理. --//
  $(targetMain + ' .btn_reload').on('click', function (event) {
        Commons.closeMessage(targetMain);
        doSearch();
      });

  //-- 「変更する」押下処理. --//
  $(targetMain + ' .btn_update').on('click', function (event) {
        Commons.closeMessage(targetMain);
        $(targetInput + '.mode').val(BizCommons.MODE_UPD);
        doCheck( function (result) { doUpdate('/crud_sample_list/update', successUpdateFunc); });
      });



  //------------------------------------------------------------------------
  //- アクション.
  /**
   * リストデータの検索を行います.
   */
  function doSearch() {
    Commons.action({
        url: '/crud_sample_list/find_list', 
        data: $(targetForm).serialize(), 
        success: function (result, status, xhr) {
              Commons.showMessages(targetMain, result.messageList);
              setTableRow(result.form.list);
              /* -- 成功時. -- */
              if (result.status === 'OK') { 
                Commons.resetChangeInput();
              } 
              /* -- 失敗時. -- */
              else {}
            }
        });
  }

  /**
   * チェック処理を行います.
   * @param {function} success(result) - 成功時処理(処理結果).
   */
  function doCheck(success) {
    Commons.action({
        url: '/crud_sample_list/check', 
        data: $(targetForm).serialize(), 
        success: function (result, status, xhr) {
              Commons.showMessages(targetMain, result.messageList);
              /* -- 成功時. -- */
              if (result.status === 'OK') { 
                if (success) { success(result); }
              } 
              /* -- 失敗時. -- */
              else {}
            }
        });
  }

  /**
   * 更新処理を行います.
   * @param {string} url - アクションURL.
   * @param {function} [success(result)] - 成功時処理(処理結果).
   */
  function doUpdate(url, success) {
    Commons.action({
        url: url, 
        data: $(targetForm).serialize(), 
        success: function (result, status, xhr) {
              /* -- 成功時. -- */
              if (result.status === 'OK') { 
                if (success) { success(result); }
              } 
              /* -- 失敗時. -- */
              else {
                Commons.showMessages(targetMain, result.messageList);
              }
            }
        });
  }


  //------------------------------------------------------------------------
  //- 処理.
  /**
   * 値の初期化を行います.
   */
  function initData() {
    BizCommons.initFormControl(targetMain);
    doSearch();
  }


  /**
   * テーブルの初期化を行います.
   */
  function initTable() {
    Commons.clearTableRow(targetTbl);
    setTable();
  }

  /**
   * テーブル設定処理を行います.
   */
  function setTable() {
    BizCommons.refreshScreen(targetTbl);
  }

  /**
   * 明細行を設定します.
   * @param {array} list - 行データ.
   */
  function setTableRow(list) {
    Commons.clearTableRow(targetTbl);

    // 明細行設定.
    if (list) {
      var tbody = $(targetTbl + ' tbody');
      for (var i = 0, imax = list.length; i < imax; i++) {
        var row = 'row' + i;
        var updated = Commons.escapeHTML(list[i].updated);

        var htmlText = '';
        htmlText += '<tr class="' + row + '">\n';
        htmlText +=   '<td class="container-fluid">\n';
        htmlText +=     '<div class="row">\n';
        htmlText +=       '<div class="td col-12 col-md-2 omit"><span class="" data-toggle="tooltip" title="' + updated + '">' + updated + '</span></div>\n';
        htmlText +=       '<div class="td col-4  col-md-2 omit"><input type="text" class="form-control code ' + row + '" name="list[' + i + '].code" value="" maxlength="10" /></div>\n';
        htmlText +=       '<div class="td col-8  col-md-3 omit"><input type="text" class="form-control name ' + row + '" name="list[' + i + '].name" value="" maxlength="40" /></div>\n';
        htmlText +=       '<div class="td col-12 col-md-2 omit">\n';
        htmlText +=         '<label class="checkbox"><input type="checkbox" class="form-control check ' + row + '" name="list[' + i + '].check" value="1" />';
        htmlText +=             '<span class="indicator">チェックボックス</span></label><br />\n';
        htmlText +=         '<label class="radio"><input type="radio" class="form-control radio ' + row + '" name="list[' + i + '].radio" value="1" />';
        htmlText +=             '<span class="indicator color-green">可</span></label>';
        htmlText +=         '<label class="radio"><input type="radio" class="form-control radio ' + row + '" name="list[' + i + '].radio" value="2" />';
        htmlText +=             '<span class="indicator color-gray" >不可</span></label><br />\n';
        htmlText +=         '<select class="form-control select ' + row + '" name="list[' + i + '].select" >';
        htmlText +=           '<option value="1" >選択1</option>';
        htmlText +=           '<option value="2" >選択2</option>';
        htmlText +=           '<option value="3" >選択3</option>';
        htmlText +=           '<option value="4" >選択4</option>';
        htmlText +=         '</select>\n';
        htmlText +=       '</div>\n';
        htmlText +=       '<div class="td col-12 col-md-3 omit"><textarea class="form-control memo ' + row + '"  name="list[' + i + '].memo" rows="3" ></textarea></div>\n';
        htmlText +=     '</div>\n';
        htmlText +=     '<input type="hidden" class="form-control id" name="list[' + i + '].id" value="" />\n';
        htmlText +=   '</td>\n';
        htmlText += '</tr>\n';
        tbody.append(htmlText);

        // データ設定.
        setData((targetTbl + ' tr.' + row), list[i]);
      }
      $(targetInput + '.list_size').val(list.length);
    }

    // テーブル再設定処理.
    setTable();
  }

  /**
   * 値の設定を行います.
   * @param {object} target - セレクタ(対象の画面).
   * @param {object} data - データオブジェクト.
   */
  function setData(target, data) {
    if (data) {
      $(target).find('.form-control.id').val(data.id);
      $(target).find('.form-control.code').val(data.code);
      $(target).find('.form-control.name').val(data.name);
      $(target).find('.form-control.memo').val(data.memo);
      $(target).find('.form-control.check[value='+data.check+']').prop('checked', true);
      $(target).find('.form-control.radio[value='+data.radio+']').prop('checked', true);
      $(target).find('.form-control.select').val(data.select);
    }
  }

  /**
   * 更新成功時の処理を行います.
   * @param {object} result - 結果.
   */
  function successUpdateFunc(result) {
    if (result) {
      Commons.showMessages(targetMain, result.messageList);
    }
    doSearch();
  }



}());