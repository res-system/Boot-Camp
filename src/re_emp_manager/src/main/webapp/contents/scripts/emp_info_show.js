/**
 * @file emp_info_show.js
 */
(function () {
    //------------------------------------------------------------------------
    // 初期設定.
    init();


    //------------------------------------------------------------------------
    // イベント設定.
    //-- 画面ロード後のイベント処理. --//
    Commons.afterLoad = function() {
    };

    //-- 内容を変更する ボタン押下処理. --//
    $('#btn_goto_input').on('click', function (event) {
        Commons.changeScreen('/emp_info/input');
    });


    //------------------------------------------------------------------------
    // 画面処理.
    /**
     * 初期値設定を行います.
     */
    function init() {
        $('#sidenavToggler').click();
        Commons.resetChangeInput();
    }

}());