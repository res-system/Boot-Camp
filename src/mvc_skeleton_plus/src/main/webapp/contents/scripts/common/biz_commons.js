/**
 * @file biz_commons.js
 * @author res.
 */
var BizCommons = { };

//-------------------- 定数.
/** 処理モード[表示]. */
BizCommons.MODE_SHOW = 'mode_show';
/** 処理モード[新規]. */
BizCommons.MODE_ADD = 'mode_add';
/** 処理モード[更新]. */
BizCommons.MODE_UPD = 'mode_upd';
/** 処理モード[削除]. */
BizCommons.MODE_DEL = 'mode_del';
/** 処理モード[確認]. */
BizCommons.MODE_CONF = 'mode_conf';
/** 処理モード[確認(新規)]. */
BizCommons.MODE_CONFA = 'mode_conf_add';
/** 処理モード[確認(更新)]. */
BizCommons.MODE_CONFU = 'mode_conf_upd';



//-------------------- 設定処理.
/**
 * 画面項目初期設定.
 * @param {string} selector - セレクタ(対象の画面).
 */
BizCommons.refreshScreen = function (selector) {
      $('[data-toggle="tooltip"]').tooltip();
    };

/**
 * 画面項目初期設定.
 * @param {string} selector - セレクタ(対象のフォーム).
 */
BizCommons.initFormControl = function (selector) {
      var target = '';
      if (selector) { target = selector + ' '; }

      Commons.setFile(target);
      Commons.setATagLink(target);
      Commons.setPostLink(target);
      Commons.setAccordionMenu(target);
      Commons.setChangeInput(target);

      BizCommons.refreshScreen(target);
    };


/* end of file */