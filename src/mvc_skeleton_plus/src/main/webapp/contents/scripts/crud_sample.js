/**
 * @file crud_sample.js
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

  //-- 「新規追加」押下処理. --//
  $(targetMain + ' .btn_add').on('click', function (event) {
        Commons.closeMessage(targetMain);
        ModalInput.show({ mode: BizCommons.MODE_ADD, successFunc: successUpdateFunc });
      });



  //------------------------------------------------------------------------
  //- アクション.
  /**
   * リストデータの検索を行います.
   */
  function doSearch() {
    Commons.action({
        url: '/crud_sample/find_list', 
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


  //------------------------------------------------------------------------
  //- 処理.
  /**
   * 値の初期化を行います.
   */
  function initData() {
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
    //-- 明細選択処理. --//
    Commons.setSelectTable({ tbl: targetTbl, selectFunc: doSelect });
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
        var id   = Commons.escapeHTML(list[i].id);
        var code = Commons.escapeHTML(list[i].code);
        var name = Commons.escapeHTML(list[i].name);
        var memo = Commons.escapeHTMLCL(list[i].memo);

        var htmlText = '';
        htmlText += '<tr>\n';
        htmlText +=   '<td class="container-fluid">\n';
        htmlText +=     '<div class="row">\n';
        htmlText +=       '<div class="td col-4  col-sm-2 omit"><span class="" data-toggle="tooltip" title="' + code + '">' + code + '</span></div>\n';
        htmlText +=       '<div class="td col-8  col-sm-4 omit"><span class="" data-toggle="tooltip" title="' + name + '">' + name + '</span></div>\n';
        htmlText +=       '<div class="td col-12 col-sm-6 omit"><span class="" data-toggle="tooltip" title="' + memo + '">' + memo + '</span></div>\n';
        htmlText +=     '</div>\n';
        htmlText +=     '<input type="hidden" name="id" value="' + id + '" />\n';
        htmlText +=   '</td>\n';
        htmlText += '</tr>\n';
        tbody.append(htmlText);
      }
      $(targetInput + '.list_size').val(list.length);
    }

    // テーブル再設定処理.
    setTable();
  }

  /**
   * 明細行の選択処理を行います.
   * @param {number} row - 行(1～).
   * @param {number} col - 列(1～).
   * @param {object} e   - イベントオブジェクト.
   * @return {boolean} 選択結果.
   */
  function doSelect(row, col, e) {
    Commons.closeMessage(targetMain);
    if ($(e.target).prop('tagName') !== 'BUTTON') {
      var selected_id = $(e.target).parents('tr').find('input[type=hidden][name=id]').val();
      ModalInput.show({ mode: BizCommons.MODE_UPD, selectedId: selected_id, successFunc: successUpdateFunc });
    }
    return false;
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




// -- 入力ダイアログ -------------------------------------------------------------- //
var ModalInput = {};

//-------------------- 定数.



//-------------------- 変数.
ModalInput.mode = null;
ModalInput.selectedId = null;
ModalInput.successFunc = null;



//-------------------- 処理.
/**
 * ダイアログを表示します.
 * @param {object} args - パラメタ.
 * <pre>
 *  {string} mode                     - ダイアログの処理モード.
 *  {string} [selectedId]             - 選択ID.
 *  {function} [successFunc(result)]  - 更新成功時処理(処理モード,処理結果).
 * </pre>
 */
ModalInput.show = function (args) {
      ModalInput.mode = null;
      ModalInput.selectedId = null;
      ModalInput.successFunc = null;
      if (args) {
        if (args.mode) { ModalInput.mode = args.mode; }
        if (args.selectedId) { ModalInput.selectedId = args.selectedId; }
        if (args.successFunc) { ModalInput.successFunc = args.successFunc; }
      }
      $('#modal_input').modal('show');
    };
(function () {

  //------------------------------------------------------------------------
  //- 設定.
  var targetMain = '#modal_input';
  var targetForm = targetMain + ' .modal-form';
  var targetInput= targetForm + ' .form-control';
  var targetLabel= targetForm + ' .form-control-static';



  //------------------------------------------------------------------------
  //- イベント.
  //-- ダイアログ基本イベント設定. --//
  Commons.setModalEvent(targetMain, initModal, null, clearData);

  //-- 「追加する」押下処理. --//
  $(targetMain + ' .btn_add').on('click', function (event) {
        Commons.closeMessage(targetMain);
        $(targetInput + '.mode').val(BizCommons.MODE);
        doCheck( function (result) { doUpdate('/crud_sample/insert', ModalInput.successFunc); });
      });

  //-- 「変更する」押下処理. --//
  $(targetMain + ' .btn_update').on('click', function (event) {
        Commons.closeMessage(targetMain);
        $(targetInput + '.mode').val(BizCommons.MODE_UPD);
        doCheck( function (result) { doUpdate('/crud_sample/update', ModalInput.successFunc); });
      });

  //-- 「削除」押下処理. --//
  $(targetMain + ' .btn_delete').on('click', function (event) {
        Commons.closeMessage(targetMain);
        $(targetInput + '.mode').val(BizCommons.MODE_DEL);
        doCheck( function (result) {
              ModalConfirm.show({ title: '削除', 
                  msgData: {button: ModalConfirm.BTN_YES_NO, text: Commons.getCommonMessage('E_COMMON_0001') }, 
                  btnYesFunc: function () { doUpdate('/crud_sample/delete', ModalInput.successFunc); }, 
                  closedFunc: function () {  }, 
                  focusButton: ModalConfirm.BTN_NO
                  });
            });
      });

  //-- 「コピー」押下処理. --//
  $(targetMain + ' .btn_copy').on('click', function (event) {
        Commons.closeMessage(targetMain);
        ModalConfirm.show({ title: 'コピー', 
              msgData: { button: ModalConfirm.BTN_YES_NO, text: Commons.getCommonMessage('E_COMMON_0002') }, 
              btnYesFunc: function () { doCopy(); }, 
              closedFunc: function () {  }, 
              focusButton: ModalConfirm.BTN_NO
            });
      });



  //------------------------------------------------------------------------
  //- アクション.
  /**
   * 表示するデータを検索します.
   * @param {function} [success(result)] - 成功時処理(処理結果).
   */
  function doFind(success) {
    Commons.action({
        url: '/crud_sample/find_data', 
        data: $(targetForm).serialize(), 
        success: function (result, status, xhr) {
              /* -- 成功時. -- */
              if (result.status === 'OK') { 
                setData(result.form.data);
                Commons.showMessages(targetMain, result.messageList);
                if (success) { success(result); }
              } 
              /* -- 失敗時. -- */
              else {
                Commons.hideModal(targetMain);
                Commons.showMessages('#main_contents', result.messageList);
              }
            }
        });
  }

  /**
   * チェック処理を行います.
   * @param {function} success(result) - 成功時処理(処理結果).
   */
  function doCheck(success) {
    Commons.action({
        url: '/crud_sample/check', 
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
                Commons.hideModal(targetMain);
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
   * モーダルダイアログの表示設定を行います.
   */
  function initModal() {
    Commons.closeMessage(targetMain);
    BizCommons.initFormControl(targetMain);
    setScreen(ModalInput.mode);
    initData();
  }

  /**
   * モードに応じた画面の設定を行います.
   * @param {string} mode - モード.
   */
  function setScreen(mode) {
    $(targetMain + ' .btn_copy'  ).hide();
    $(targetMain + ' .btn_delete').hide();
    $(targetMain + ' .btn_add'   ).hide();
    $(targetMain + ' .btn_update').hide();
    if (mode === BizCommons.MODE_ADD) {
      $(targetMain + ' .btn_add'   ).show();
    } else if (mode === BizCommons.MODE_UPD) {
      $(targetMain + ' .btn_copy'  ).show();
      $(targetMain + ' .btn_delete').show();
      $(targetMain + ' .btn_update').show();
    }
  }


  /**
   * 値の初期化を行います.
   */
  function initData() {
    setData();
    $(targetInput + '.mode').val(ModalInput.mode);
    if (ModalInput.mode !== BizCommons.MODE_ADD) {
      // キーで検索.
      $(targetInput + '.id').val(ModalInput.selectedId);
      doFind();
    } else {
      // 初期値設定.
      setData({
          });
    }
    Commons.resetChangeInput();
  }

  /**
   * 値の設定を行います.
   * @param {object} data - データオブジェクト.
   */
  function setData(data) {
    if (data) {
      $(targetInput + '.id').val(data.id);
      $(targetInput + '.code').val(data.code);
      $(targetInput + '.name').val(data.name);
      $(targetInput + '.memo').val(data.memo);
      $(targetInput + '.check[value='+data.check+']').prop('checked', true);
      $(targetInput + '.radio[value='+data.radio+']').prop('checked', true);
      $(targetInput + '.select').val(data.select);
      $(targetLabel + '.updated').text(data.updated);
    } else {
      $(targetInput + '.id').val('');
      $(targetInput + '.code').val('');
      $(targetInput + '.name').val('');
      $(targetInput + '.memo').val('');
      $(targetInput + '.check').prop('checked', false);
      $(targetInput + '.radio').prop('checked', false);
      $(targetInput + '.select').val('');
      $(targetLabel + '.updated').text('');
    }
  }

  /**
   * 値をクリアします.
   */
  function clearData() {
    return true;
  }


  /**
   * コピー用の設定を行います.
   */
  function doCopy() {
    $(targetInput + '.mode').val(BizCommons.MODE_ADD);
    setScreen(BizCommons.MODE_ADD);
    doFind( function (result) {
          $(targetInput + '.id').val('');
          ModalInput.mode = BizCommons.MODE_ADD;
          Commons.showMessage(targetMain, { kind: Commons.MSG_WORN, text: Commons.getCommonMessage('E_COMMON_0003') });
        });
  }



}());