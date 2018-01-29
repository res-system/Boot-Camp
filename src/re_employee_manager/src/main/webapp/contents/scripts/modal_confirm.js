/**
 * @file modal_confirm.js
 */
/** @namespace */
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
ModalConfirm.BTN_OK    = 'modal_confirm_btn_ok';
/** 「はい」ボタン押下. */
ModalConfirm.BTN_YES   = 'modal_confirm_btn_yes';
/** 「いいえ」ボタン押下. */
ModalConfirm.BTN_NO    = 'modal_confirm_btn_no';
/** 「キャンセル」ボタン押下. */
ModalConfirm.BTN_CANCE = 'modal_confirm_btn_cancel';



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



//-------------------- 表示処理.
/**
 * <pre>
 * 確認ダイアログを表示します.
 * @param {any} args
 *                  .title          タイトル.
 *                  .msgData        メッセージデータ.
 *                  .btnOkFunc     「ＯＫ」ボタン押下時処理.
 *                  .btnYesFunc    「はい」ボタン押下時処理.
 *                  .btnNoFunc     「いいえ」ボタン押下時処理.
 *                  .btnCancelFunc 「キャンセル」ボタン押下時処理.
 *                  .closedFunc     閉じた後の処理.
 *                  .focusButton    初期フォーカスボタン.
 * 使用例）
 *  ModalConfirm.show({
 *       title:'確認'
 *      ,msgData:{button:ModalConfirm.BTN_YES_NO_CANCEL, text:'確認ダイアログのテストです'}
 *      ,btnOkFunc: function() {
 *          ...
 *      }
 *      ,btnYesFunc: function() {
 *          ...
 *      }
 *      ,btnNoFunc: function() {
 *          ...
 *      }
 *      ,btnCancelFunc: function() {
 *          ...
 *      }
 *      ,closedFunc: function() {
 *          ...
 *      }
 *      ,focusButton: ModalConfirm.BTN_CANCE
 *  });
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
};



//-------------------- メイン処理.
(function () {

    //------------------------------------------------------------------------
    // 設定.
    var modalSelector = '#modal_confirm';



    //------------------------------------------------------------------------
    // イベント.
    //-- ダイアログ 表示前処理. --//
    $(modalSelector).on('show.bs.modal', function () {
        // 表示設定.
        setModal()
    });
    //-- ダイアログ 表示時処理. --//
    $(modalSelector).on('shown.bs.modal', function () {
        // 初期フォーカス.
        if (ModalConfirm.focusButton != null) {
            $('#' + ModalConfirm.focusButton).focus();
        }
    });
    //-- ダイアログ 閉じる前処理. --//
    $(modalSelector).on('hide.bs.modal', function () {});
    //-- ダイアログ 閉じた後処理. --//
    $(modalSelector).on('hidden.bs.modal', function () {
        // 表示設定クリア.
        clearModal();
        //-- 閉じた後の処理. --//
        if (ModalConfirm.closedFunc != null) {
            ModalConfirm.closedFunc();
        }
    });

    //-- ＯＫ ボタン押下処理. --//
    $('#modal_confirm_btn_ok').on("click", function (event) {
        ModalConfirm.button = event.target.id;
        if (ModalConfirm.btnOkFunc != null) { ModalConfirm.btnOkFunc(); }
        $(modalSelector).modal('hide');
    });
    //-- はい ボタン押下処理. --//
    $('#modal_confirm_btn_yes').on("click", function (event) {
        ModalConfirm.button = event.target.id;
        if (ModalConfirm.btnYesFunc != null) { ModalConfirm.btnYesFunc(); }
        $(modalSelector).modal('hide');
    });
    //-- いいえ ボタン押下処理. --//
    $('#modal_confirm_btn_no').on("click", function (event) {
        ModalConfirm.button = event.target.id;
        if (ModalConfirm.btnNoFunc != null) { ModalConfirm.btnNoFunc(); }
        $(modalSelector).modal('hide');
    });
    //-- キャンセル ボタン押下処理. --//
    $('#modal_confirm_btn_cancel').on("click", function (event) {
        ModalConfirm.button = event.target.id;
        if (ModalConfirm.btnCancelFunc != null) { ModalConfirm.btnCancelFunc(); }
        $(modalSelector).modal('hide');
    });



    //------------------------------------------------------------------------
    // 処理.
    /**
     * モーダルダイアログの表示設定を行います.
     */
    function setModal() {
        if (ModalConfirm.msgData !== null) {
            if (String(ModalConfirm.msgData.button) === ModalConfirm.BTN_INFO) {
                // 情報(OK).
                $('#modal_confirm_icon_info').fadeIn('fast');
                $('#modal_confirm_btn_ok').fadeIn('fast');
                $('#modal_confirm_message').addClass("confirm-msg-info");
                if (ModalConfirm.focusButton == null) {
                    ModalConfirm.focusButton = ModalConfirm.BTN_OK;
                }

            } else if (String(ModalConfirm.msgData.button) === ModalConfirm.BTN_YES_NO) {
                // 確認(YES/NO).
                $('#modal_confirm_icon_caution').fadeIn('fast');
                $('#modal_confirm_btn_yes').fadeIn('fast');
                $('#modal_confirm_btn_no').fadeIn('fast');
                if (ModalConfirm.focusButton == null) {
                    ModalConfirm.focusButton = ModalConfirm.BTN_YES;
                }

            } else if (String(ModalConfirm.msgData.button) === ModalConfirm.BTN_YES_NO_CANCEL) {
                // 確認(YES/NO/CANCEL).
                $('#modal_confirm_icon_caution').fadeIn('fast');
                $('#modal_confirm_btn_yes').fadeIn('fast');
                $('#modal_confirm_btn_no').fadeIn('fast');
                $('#modal_confirm_btn_cancel').fadeIn('fast');
                if (ModalConfirm.focusButton == null) {
                    ModalConfirm.focusButton = ModalConfirm.BTN_YES;
                }

            } else {
                // 警告(OK).
                $('#modal_confirm_icon_error').fadeIn('fast');
                $('#modal_confirm_btn_ok').fadeIn('fast');
                $('#modal_confirm_message').addClass("confirm-msg-info");
                if (ModalConfirm.focusButton == null) {
                    ModalConfirm.focusButton = ModalConfirm.BTN_OK;
                }

            }

            // メッセージ.
            $('#modal_confirm_title').html(ModalConfirm.title);
            $('#modal_confirm_message').html(escapeHTML(ModalConfirm.msgData.text));
            $('#modal_confirm_message').fadeIn('fast');

        }
    }

    /**
     * モーダルダイアログの表示設定をクリアします.
     */
    function clearModal() {
        $('#modal_confirm_icon_info').fadeOut('fast');
        $('#modal_confirm_icon_caution').fadeOut('fast');
        $('#modal_confirm_icon_error').fadeOut('fast');

        $('#modal_confirm_btn_ok').fadeOut('fast');
        $('#modal_confirm_btn_yes').fadeOut('fast');
        $('#modal_confirm_btn_no').fadeOut('fast');
        $('#modal_confirm_btn_cancel').fadeOut('fast');

        $('#modal_confirm_message').removeClass("confirm-msg-error");
        $('#modal_confirm_message').removeClass("confirm-msg-info");
        $('#modal_confirm_message').html('');
        $('#modal_confirm_message').fadeOut('fast');
    }

    /** HTMLエスケープ. */
    escapeHTML = function(tx){if(tx){tx=tx.replace(/[&'`"<>]/g,function(match){return {'&':'&amp;',"'":'&#x27;','`':'&#x60;','"':'&quot;','<':'&lt;','>':'&gt;'}[match]});return tx.replace(/\r?\n/g,'<br />');} return tx;};

}());
