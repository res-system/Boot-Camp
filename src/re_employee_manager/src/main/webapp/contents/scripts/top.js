/**
 * @file top.js
 */
(function () {
    //------------------------------------------------------------------------
    // 初期設定.
    $('#login_id').val('');
    $('#password').val('');
    $('#save').prop("checked", true);



    //------------------------------------------------------------------------
    // イベント設定.
    //-- 画面ロード後のイベント処理. --//
    Commons.afterLoad = function() {
        // 初期フォーカス等.
        Commons.focus('#login_id');
    };

    //-- ログインID ENTERキー押下処理. --//
    $('#login_id').on('keypress', function (event) {
        if (event.which !== 13) { return true; }
        if (!$ReC.isStrBlk($('#login_id').val())) {
            Commons.focus('#password');
            $('#password').val('');
            return false;
        }
        return true;
    });

    //-- パスワード ENTERキー押下処理. --//
    $('#password').on('keypress', function (event) {
        if (event.which !== 13) { return true; }
        if (!$ReC.isStrBlk($('#login_id').val()) 
                && !$ReC.isStrBlk($('#password').val())) {
            // ログイン！.
            $('#btn_login').click();
            return false;
        }
        return true;
    });

    //-- ログイン ボタン押下処理. --//
    $('#btn_login').on('click', function (event) {
        doLogin();
    });



    //------------------------------------------------------------------------
    // 画面処理.
    /**
     * ログイン処理.
     */
    function doLogin() {
        Commons.closeMessage();
        Commons.action({
                 url: '/login'
                ,data: $('#myform').serialize()
                ,success: function (result, status, xhr) {
                        if (result.status === 'OK') {
                            Commons.post('#myform', '/next');
                        } else {
                            $('#password').val('');
                            Commons.showMessages('#main_contents', result.messageList);
                            Commons.focus('#login_id');
                        }
                    }
                });
    };



}());