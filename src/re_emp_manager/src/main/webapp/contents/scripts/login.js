/**
 * @file login.js
 */
(function () {

    //------------------------------------------------------------------------
    //- 設定.

    // 初期設定.
    init();


    //------------------------------------------------------------------------
    //- イベント設定.
    //-- 画面ロード後のイベント処理. --//
    Commons.afterLoad = function() {
        if ($('#code').val()) {
            Commons.focus('#id');
        } else {
            Commons.focus('#code');
        }
    };

    //-- 識別コード ENTERキー押下処理. --//
    $('#code').on('keypress', function(event) {
        if (event.which !== 13) { return true; }
        if (!$ReC.isStrBlk($('#code').val())) {
            Commons.focus('#id');
            return false;
        }
        return true;
    });

    //-- ログインID ENTERキー押下処理. --//
    $('#id').on('keypress', function(event) {
        if (event.which !== 13) { return true; }
        if (!$ReC.isStrBlk($('#id').val())) {
            Commons.focus('#key');
            return false;
        }
        return true;
    });

    //-- パスワード ENTERキー押下処理. --//
    $('#key').on('keypress', function(event) {
        if (event.which !== 13) { return true; }
        if (!$ReC.isStrBlk($('#id').val()) && !$ReC.isStrBlk($('#key').val())) {
            Commons.closeMessage();
            doLogin();
            return false;
        }
        return true;
    });

    //-- パスワード ENTERキー押下処理. --//
    $('#save_1').on('keypress', function(event) {
        if (event.which !== 13) { return true; }
        if (!$ReC.isStrBlk($('#id').val()) && !$ReC.isStrBlk($('#key').val())) {
            Commons.closeMessage();
            doLogin();
            return false;
        }
        return true;
    });

    //-- ログイン ボタン押下処理. --//
    $('#btn_login').on('click', function(event) {
        Commons.closeMessage();
        doLogin();
    });

    //-- アカウント登録 ボタン押下処理. --//
    $('#make_new_account').on('click', function(event) {
        Commons.closeMessage();
        ModalConfirm.show({
                  msgData: {button:ModalConfirm.BTN_ERROR, text:'工事中です。'}
                , title: '工事中です。'});
    });


    //------------------------------------------------------------------------
    //- 処理.
    /**
     * 初期設定.
     */
    function init() {
        $('#key').val('');
    }

    /**
     * ログイン.
     */
    function doLogin() {
        Commons.action({ url: '/login/login'
                , data: $('#myform').serialize()
                , success: function(result, status, xhr) {
                              Commons.showMessages('#main_contents', result.messageList);
                              if (result.status === 'OK') {
                                  init();
                                  $('#next').val(result.form.next);
                                  Commons.post('#myform', '/login/next');
                              } else {
                                  $('#key').val('');
                                  Commons.focus('#code');
                              }
                          }
                });
    };

}());
