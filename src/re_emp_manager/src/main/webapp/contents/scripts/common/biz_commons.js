/**
 * @file biz_commons.js
 * @author res.
 * @version 1.0.0 (2018.08.08)
 */
/** @namespace */
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


//-------------------- 変数.


//-------------------- ソート処理.
/**
 * ソート項目の制御を行います.
 * @param {object} target - 対象のJQueryオブジェクト.
 * @param {string} sort - 対象のJQueryオブジェクト.
 * @return {string} マークダウンテキスト.
 */
BizCommons.sort = function (target, sort) {
    var result = "";
    if (target != null && target.length > 0) {
        BizCommons.sortReset();
        if (sort === target.attr('data-sort-asc')) {
            result = target.attr('data-sort-desc')
            target.addClass("sort-link-desc");
        } else {
            result = target.attr('data-sort-asc')
            target.addClass("sort-link-asc");
        }        
    }
    return result;
};

/**
 * ソート項目をリセットします.
 */
BizCommons.sortReset = function () {
    $('.sort-link').removeClass("sort-link-desc");
    $('.sort-link').removeClass("sort-link-asc");
};

/**
 * 年齢を算出し表示します.
 * @param {string} birthday - 生年月日.
 * @return {string} 年齢.
 */
BizCommons.toAge = function (birthday) {
    if (!$ReC.isStrBlk(birthday)) {
        var birthdays = birthday.split('/');
        if (birthdays.length === 3) {
            var today = new Date();
              todayNum = today.getFullYear() * 10000 + (today.getMonth() + 1) * 100 + today.getDate();
            var birthDate = new Date(birthdays[0], birthdays[1] - 1, birthdays[2]);
              birthdayNum = birthDate.getFullYear() * 10000 + (birthDate.getMonth() + 1) * 100 + birthDate.getDate();
              var age = (Math.floor((todayNum - birthdayNum) / 10000));
            return '( ' + age + ' )';
        }
    }
    return '';
};

/* end of file */