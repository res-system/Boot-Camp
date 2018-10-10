/**
 * @file emp_expenses_show.js
 */
(function () {
    //------------------------------------------------------------------------
    //- 設定.
    var tableId = '#main_tbl';

    // 初期設定.
    init();


    //------------------------------------------------------------------------
    //- イベント設定.
    //-- 画面ロード後のイベント処理. --//
    Commons.afterLoad = function() {
    };


    //------------------------------------------------------------------------
    //- 画面処理.
    /**
     * 初期値設定を行います.
     */
    function init() {
        $('#sidenavToggler').click();
        Commons.resetChangeInput();
    }

}());