/**
 * @file employee_info_show.js
 */
(function () {
    //------------------------------------------------------------------------
    // 初期設定.



    //------------------------------------------------------------------------
    // イベント設定.
    //-- 画面ロード後のイベント処理. --//
    Commons.afterLoad = function() {
        // 初期フォーカス等.
    };

    //-- 内容を変更する ボタン押下処理. --//
    $('#btn_goto_input').on('click', function (event) {
        Commons.changeScreen('/employee_info/input/');
    });



    //------------------------------------------------------------------------
    // 画面処理.



}());