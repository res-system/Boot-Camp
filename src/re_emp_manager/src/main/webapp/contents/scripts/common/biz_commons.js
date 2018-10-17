/**
 * @file biz_commons.js
 * @author res.
 * @version 1.0.0 (2018.08.08)
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


//-------------------- 変数.


//-------------------- ソート処理.
/**
 * ソート項目をセットします.
 * @param {string} tableSelector - 対象のテーブルセレクタ−.
 * @param {string} sortSelector - 対象のソート項目セレクタ−.
 */
BizCommons.setSort = function (tableSelector, sortSelector) {
    BizCommons.sortReset(tableSelector, null);
    var sort = $(sortSelector).val();
    if (sort) {
        var sort_link = $(tableSelector + ' .sort-link');
        for (var i = 0, imax = sort_link.length; i < imax; i++) {
            if (sort === $(sort_link[i]).attr('data-sort-desc')) {
                $(sort_link[i]).addClass("sort-link-desc");
            } else if (sort === $(sort_link[i]).attr('data-sort-asc')) {
                $(sort_link[i]).addClass("sort-link-asc");
            }
        }
    }
};

/**
 * ソート項目をリセットします.
 * @param {string} tableSelector - 対象のテーブルセレクタ−.
 * @param {string} sortSelector - 対象のソート項目セレクタ−.
 */
BizCommons.sortReset = function (tableSelector, sortSelector) {
    $(tableSelector + ' .sort-link').removeClass("sort-link-desc");
    $(tableSelector + ' .sort-link').removeClass("sort-link-asc");
    if ($(sortSelector).length > 0) { $(sortSelector).val(''); }
};

/**
 * ソート項目の制御を行います.
 * @param {string} tableSelector - 対象のテーブルセレクタ−.
 * @param {string} sortSelector - 対象のソート項目セレクタ−.
 * @param {object} target - イベント対象のJQueryオブジェクト.
 */
BizCommons.sort = function (tableSelector, sortSelector, target) {
    if (target != null && target.length > 0) {
        BizCommons.sortReset(tableSelector, null);
        var sort = $(sortSelector).val();
        if (sort === target.attr('data-sort-asc')) {
            sort = target.attr('data-sort-desc')
            target.addClass("sort-link-desc");
        } else {
            sort = target.attr('data-sort-asc')
            target.addClass("sort-link-asc");
        }
        $(sortSelector).val(sort);
    }
};


//-------------------- 表示処理.
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
            var todayNum = today.getFullYear() * 10000 + (today.getMonth() + 1) * 100 + today.getDate();
            var birthDate = new Date(birthdays[0], birthdays[1] - 1, birthdays[2]);
            var birthdayNum = birthDate.getFullYear() * 10000 + (birthDate.getMonth() + 1) * 100 + birthDate.getDate();
            var age = (Math.floor((todayNum - birthdayNum) / 10000));
            return '(' + age + '歳)';
        }
    }
    return '';
};
/**
 * 勤続年数を算出し表示します.
 * @param {string} hire_date - 入社日.
 * @param {string} retirement_date - 退職日.
 * @return {string} 勤続年数.
 */
BizCommons.toLengthOfService = function (hire_date, retirement_date) {
    if (!$ReC.isStrBlk(hire_date)) {
        var hire_dates = hire_date.split('/');
        if (hire_dates.length === 3) {
            // 退職日.
            var date1 = new Date();
            if (!$ReC.isStrBlk(retirement_date)) {
                var retirement_dates = retirement_date.split('/');
                if (retirement_dates.length === 3) {
                    var retirementDate = new Date(retirement_dates[0], retirement_dates[1] - 1, retirement_dates[2]);
                    if (retirementDate.getTime() < date1.getTime()) {
                        date1 = retirementDate;
                    }
                }
            }
            var data1Num = date1.getFullYear() * 12 + date1.getMonth();
            // 入社日.
            var date2 = new Date(hire_dates[0], hire_dates[1] - 1, hire_dates[2]);
            var data2Num = date2.getFullYear() * 12 + date2.getMonth();
            // 差分計算.
            var count = data1Num - data2Num;
            var year = parseInt(count / 12);
            var month = count % 12;
            if (month != 0) {
                return '(' + year + '年' + month + 'ヶ月)';
            } else {
                return '(' + year + '年)';
            }
        }
    }
    return '';
};

/**
 * 郵便番号を加工して表示します.
 * @param {string} postalCode - 郵便番号.
 * @return {string} 郵便番号.
 */
BizCommons.showPostalCode = function (postalCode) {
    if (!$ReC.isStrBlk(postalCode) && postalCode.length == 7) {
        return '〒' + postalCode.substr(0,3) + '-' + postalCode.substr(3);
    }
    return "";
};

/**
 * 住所を加工して表示します.
 * @param {string} addr1 - 住所1.
 * @param {string} addr2 - 住所2.
 * @return {string} 住所.
 */
BizCommons.showAddr = function (addr1, addr2) {
    var result = '';
    if (!$ReC.isStrBlk(addr1)) {
        result += addr1;
    }
    if (!$ReC.isStrBlk(addr2)) {
        result += addr2;
    }
    return result;
};

/**
 * 電話番号を加工して表示します.
 * @param {string} tel_no - 電話番号
 * @return {string} 電話番号.
 */
BizCommons.showTelNo = function (tel_no) {
    var result = '';
    if (!$ReC.isStrBlk(tel_no)) {
        result += 'Tel: ' + tel_no;
    }
    return result;
};

/**
 * メールアドレスを加工して表示します.
 * @param {string} email - メールアドレス.
 * @return {string} メールアドレス.
 */
BizCommons.showEmail = function (email) {
    var result = '';
    if (!$ReC.isStrBlk(email)) {
        result += 'E-Mail: ' + email;
    }
    return result;
};

/**
 * フリガナを加工して表示します.
 * @param {string} name_kana - フリガナ.
 * @return {string} フリガナ.
 */
BizCommons.showKana = function (name_kana) {
    var result = '';
    if (!$ReC.isStrBlk(name_kana)) {
        result += '（' + name_kana + '）';
    }
    return result;
};

/**
 * 数値を加工して表示します.
 * @param {string} num - 数値.
 * @return {string} 数値.
 */
BizCommons.showNum = function (num) {
    var result = '';
    if (!$ReC.isStrBlk(num)) {
        result = String(num).replace( /(\d)(?=(\d\d\d)+(?!\d))/g, '$1,');
    } else {
        result = '0';
    }
    return result;
};

/* end of file */
