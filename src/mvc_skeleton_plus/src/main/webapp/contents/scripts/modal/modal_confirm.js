/**
 * @file modal_confirm.js
 */
var ModalConfirm = {};

//-------------------- 定数.
/** ボタン種別[制御なし]. */
ModalConfirm.BTN_NONE = "0";
/** ボタン種別[警告(OK)]. */
ModalConfirm.BTN_ERROR = "1";
/** ボタン種別[情報(OK)]. */
ModalConfirm.BTN_INFO = "2";
/** ボタン種別[確認(YES/NO)]. */
ModalConfirm.BTN_YES_NO = "3";
/** ボタン種別[確認(YES/NO/CANCEL)]. */
ModalConfirm.BTN_YES_NO_CANCEL = "4";

/** 「ＯＫ」ボタン押下. */
ModalConfirm.BTN_OK    = 'modal-btn-ok';
/** 「はい」ボタン押下. */
ModalConfirm.BTN_YES   = 'modal-btn-yes';
/** 「いいえ」ボタン押下. */
ModalConfirm.BTN_NO    = 'modal-btn-no';
/** 「キャンセル」ボタン押下. */
ModalConfirm.BTN_CANCE = 'modal-btn-cancel';



//-------------------- 変数.
/** 押下ボタン判別用. */
ModalConfirm.button = '';
/** タイトル. */
ModalConfirm.title = null;
/** メッセージデータ. */
ModalConfirm.msgData = null;
/** 「ＯＫ」ボタン押下時処理. */
ModalConfirm.btnOkFunc = null;
/** 「はい」ボタン押下時処理. */
ModalConfirm.btnYesFunc = null;
/** 「いいえ」ボタン押下時処理. */
ModalConfirm.btnNoFunc = null;
/** 「キャンセル」ボタン押下時処理. */
ModalConfirm.btnCancelFunc = null;
/** 閉じた後の処理. */
ModalConfirm.closedFunc = null;
/** 初期フォーカスボタン. */
ModalConfirm.focusButton = null;



//-------------------- 処理.
/**
 * ダイアログを表示します.
 * @param {object} args - パラメタ.
 * <pre>
 *  {string} [title]              - タイトル.
 *  {string} [msgData]            - メッセージデータ.
 *  {function} [btnOkFunc()]      - 「ＯＫ」ボタン押下時処理.
 *  {function} [btnYesFunc()]     - 「はい」ボタン押下時処理.
 *  {function} [btnNoFunc()]      - 「いいえ」ボタン押下時処理.
 *  {function} [btnCancelFunc()]  - 「キャンセル」ボタン押下時処理.
 *  {function} [closedFunc()]     - 閉じた後の処理.
 *  {string} [focusButton]        - 初期フォーカスボタン.
 *
 * 使用例）
 *  ModalConfirm.show({
 *            title: '確認'
 *          , msgData: {button:ModalConfirm.BTN_YES_NO_CANCEL, text:'確認ダイアログのテストです'}
 *          , btnOkFunc: function() {
 *                      ...
 *                  }
 *          , btnYesFunc: function() {
 *                      ...
 *                  }
 *          , btnNoFunc: function() {
 *                      ...
 *                  }
 *          , btnCancelFunc: function() {
 *                      ...
 *                  }
 *          , closedFunc: function() {
 *                      ...
 *                  }
 *          , focusButton: ModalConfirm.BTN_CANCE
 *          });
 * </pre>
 */
ModalConfirm.show = function (args) {
  ModalConfirm.button = '';
  ModalConfirm.title = '';
  ModalConfirm.msgData = null;
  ModalConfirm.btnOkFunc = null;
  ModalConfirm.btnYesFunc = null;
  ModalConfirm.btnNoFunc = null;
  ModalConfirm.btnCancelFunc = null;
  ModalConfirm.closedFunc = null;
  ModalConfirm.focusButton = null;
  if (args) {
    if (args.msgData != null) {
      ModalConfirm.title = args.title;
      ModalConfirm.msgData = args.msgData;
      if (args.btnOkFunc)     { ModalConfirm.btnOkFunc = args.btnOkFunc; }
      if (args.btnYesFunc)    { ModalConfirm.btnYesFunc = args.btnYesFunc; }
      if (args.btnNoFunc)     { ModalConfirm.btnNoFunc = args.btnNoFunc; }
      if (args.btnCancelFunc) { ModalConfirm.btnCancelFunc = args.btnCancelFunc; }
      if (args.closedFunc)    { ModalConfirm.closedFunc = args.closedFunc; }
      if (args.focusButton)   { ModalConfirm.focusButton = args.focusButton; }
      $('#modal_confirm').modal('show');
    }
  }
};
(function () {

  //------------------------------------------------------------------------
  //- 設定.
  var targetMain = '#modal_confirm';
  var btnFunc = null;



  //------------------------------------------------------------------------
  //- イベント.
  //-- ダイアログ 表示前処理. --//
  $(targetMain).on('show.bs.modal', function(event) {
        if (event.namespace === 'bs.modal') { setModal(); }
      });
  //-- ダイアログ 表示時処理. --//
  $(targetMain).on('shown.bs.modal', function(event) {
        if (ModalConfirm.focusButton != null) { $(targetMain + ' .' + ModalConfirm.focusButton).focus(); }
      });
  //-- ダイアログ 閉じる前処理. --//
  $(targetMain).on('hide.bs.modal', function(event) {
      });
  //-- ダイアログ 閉じた後処理. --//
  $(targetMain).on('hidden.bs.modal', function(event) {
        $('body').addClass('modal-open');
        //-- ボタン押下処理. --//
        if (btnFunc != null) { btnFunc(); }
        //-- 閉じた後の処理. --//
        if (ModalConfirm.closedFunc != null) { ModalConfirm.closedFunc(); }
      });


  //-- 「ＯＫ」押下処理. --//
  $(targetMain + ' .modal-btn-ok').on("click", function (event) {
        ModalConfirm.button = event.target.id;
        btnFunc = ModalConfirm.btnOkFunc;
        $(targetMain).modal('hide');
      });
  //-- 「はい」押下処理. --//
  $(targetMain + ' .modal-btn-yes').on("click", function (event) {
        ModalConfirm.button = event.target.id;
        btnFunc = ModalConfirm.btnYesFunc;
        $(targetMain).modal('hide');
      });
  //-- 「いいえ」押下処理. --//
  $(targetMain + ' .modal-btn-no').on("click", function (event) {
        ModalConfirm.button = event.target.id;
        btnFunc = ModalConfirm.btnNoFunc;
        $(targetMain).modal('hide');
      });
  //-- 「キャンセル」押下処理. --//
  $(targetMain + ' .modal-btn-cancel').on("click", function (event) {
        ModalConfirm.button = event.target.id;
        btnFunc = ModalConfirm.btnCancelFunc;
        $(targetMain).modal('hide');
      });



  //------------------------------------------------------------------------
  // 処理.
  /**
   * モーダルダイアログの表示設定を行います.
   */
  function setModal() {
    clearModal();

    if (ModalConfirm.msgData !== null) {
      if (String(ModalConfirm.msgData.button) === ModalConfirm.BTN_INFO) {
        // 情報(OK).
        $(targetMain + ' .modal-icon-info').show();
        $(targetMain + ' .modal-btn-ok').show();
        if (ModalConfirm.focusButton == null) {
          ModalConfirm.focusButton = ModalConfirm.BTN_OK;
        }
      } else if (String(ModalConfirm.msgData.button) === ModalConfirm.BTN_YES_NO) {
        // 確認(YES/NO).
        $(targetMain + ' .modal-icon-caution').show();
        $(targetMain + ' .modal-btn-yes').show();
        $(targetMain + ' .modal-btn-no').show();
        if (ModalConfirm.focusButton == null) {
          ModalConfirm.focusButton = ModalConfirm.BTN_YES;
        }
      } else if (String(ModalConfirm.msgData.button) === ModalConfirm.BTN_YES_NO_CANCEL) {
        // 確認(YES/NO/CANCEL).
        $(targetMain + ' .modal-icon-caution').show();
        $(targetMain + ' .modal-btn-yes').show();
        $(targetMain + ' .modal-btn-no').show();
        $(targetMain + ' .modal-btn-cancel').show();
        if (ModalConfirm.focusButton == null) {
          ModalConfirm.focusButton = ModalConfirm.BTN_YES;
        }
      } else {
        // 警告(OK).
        $(targetMain + ' .modal-icon-error').show();
        $(targetMain + ' .modal-btn-ok').show();
        if (ModalConfirm.focusButton == null) {
          ModalConfirm.focusButton = ModalConfirm.BTN_OK;
        }
      }

      // メッセージ.
      $(targetMain + ' .modal-title-text').text(ModalConfirm.title);
      $(targetMain + ' .modal-msg').html($('<span />').text(ModalConfirm.msgData.text).html().replace(/\r?\n/g,'<br />'));
      $(targetMain + ' .modal-msg').show();
    }
  }

  /**
   * モーダルダイアログの表示設定をクリアします.
   */
  function clearModal() {
    btnFunc = null; 

    $(targetMain + ' .modal-icon-info'   ).hide();
    $(targetMain + ' .modal-icon-caution').hide();
    $(targetMain + ' .modal-icon-error'  ).hide();

    $(targetMain + ' .modal-btn-ok'    ).hide();
    $(targetMain + ' .modal-btn-yes'   ).hide();
    $(targetMain + ' .modal-btn-no'    ).hide();
    $(targetMain + ' .modal-btn-cancel').hide();

    $(targetMain + ' .modal-title-text').text('');
    $(targetMain + ' .modal-msg').text('');
    $(targetMain + ' .modal-msg').hide();
  }

}());
