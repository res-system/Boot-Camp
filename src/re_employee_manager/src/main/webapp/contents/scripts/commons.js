/**
 * @file commons.js
 * @author res.
 * @version 1.0.0 (2018.01.15)
 */
/** @namespace */
var Commons = { };

//-------------------- 定数.
/** ON. */
Commons.ON = '1';
/** OFF. */
Commons.OFF = '0';
/** 元の値の保存名. */
Commons.BEFORE = 'data-before-value';

/** ロックID. */
Commons.LOCKID = 'action-lock';

/** メッセージ種別[情報]. */
Commons.MSG_INFO = "I";
/** メッセージ種別[注意]. */
Commons.MSG_WORN = "W";
/** メッセージ種別[警告]. */
Commons.MSG_ERROR = "E";




//-------------------- 変数.
/** スクロール処理実行フラグ. */
Commons.isScrolling = false;
/** 画面変更中フラグ. */
Commons.isChangingScreen = false;
/** 入力内容変更フラグ. */
Commons.isChangedInput = false;


//-------------------- イベント.
/** 画面ロード後のイベント. */
Commons.afterLoad = function(){};



//-------------------- 画面遷移・アクション処理.
/**
 * ROOTURLを追加してURLを取得する.
 * @param {string} url - URL.
 * @return {string} URL.
 */
Commons.url = function (url) {
    if (!$ReC.isStrBlk(url) && url.substr(0, 1) === '/') {
    // urlの先頭が'/'の場合.
        var root_url = $('#root_url').val(); 
        if (!$ReC.isStrBlk(root_url)
                && (url.indexOf(root_url) === -1 ||
                    url.indexOf(root_url) > 0)) {
        // root_urlがあり、対象のURLの先頭がroot_urlではない場合.
            if (root_url.substr(-1) === '/') {
            // root_urlの終わりが'/'終わりの場合は、'/'を一つ削除する.
                return root_url + url.substr(1);
            } else {
                return root_url + url;
            }
        }
    }
    return url;
};

/**
 * 画面遷移する.
 * @param {string} url - URL.
 */
Commons.changeScreen = function (url) {
    if (!Commons.isChangingScreen) {
        var sendUrl = Commons.url(url);
        if (!$ReC.isStrBlk(sendUrl)) {
            Commons.closeScreen(function(){ window.location.href = sendUrl; });
        }
    }
};

/**
 * POST(送信)する.
 * @param {string} selector - 対象formのセレクタ.
 * @param {string} url - URL.
 */
Commons.post = function (selector, url) {
    if (!Commons.isChangingScreen) {
        var sendUrl = Commons.url(url);
        if (!$ReC.isStrBlk(sendUrl)) {
            var form = $(selector);
            if (form.length > 0) {
                Commons.closeScreen(function(){
                    form.attr('action', sendUrl);
                    form.attr('method', 'post');
                    form.submit();
                });
            }
        }
    }
};

/**
 * アクションを実行する.
 * @param {object} arge - アクションのパラメタ.
 * <pre>
 *  {string} url        - URL.
 *  {string} [type]     - POST or GET.
 *  {string} [isLock]   - ロックの有無(true).
 *  {number} [timeout]  - タイムアウト時間.
 *  {any}    [data]     - 送信データ.
 *  {function} [beforeSend(xhr, settings) as bool]  - 送信前の処理Function.
 *  {function} [success(result, status, xhr)]       - 成功時の処理Function.
 *  {function} [error(xhr, status, errorThrown)]    - 失敗時の処理Function.
 *  {function} [complete(xhr, status)]              - 応答後の処理Function.
 * </pre>
 */
Commons.action = function (arge) {
    if (!$ReC.isEmpty(arge)) {
        var sendUrl = Commons.url(arge.url);
        if (!$ReC.isStrBlk(sendUrl)) {
            // Ajaxアクション判定フラグ設定.
            sendUrl += ((sendUrl.indexOf('?') < 0) ? '?' : '&') + 'is_ajax=1';
            // TYPE.
            var type = (!$ReC.isStrBlk(arge.type)) ? arge.type : 'POST';
            // ロックの有無.
            var isLock = ($ReC.isBool(arge.isLock)) ? arge.isLock : true;
            // タイムアウト.
            var timeout = (arge.timeout != null) ? arge.timeout : 60000*5;

            //-- 送信前の処理. --//
            var beforeSendSubFunc = (arge.beforeSend != null) ? arge.beforeSend 
                                  : function(xhr, settings) {};
            var beforeSendFunc = function(xhr, settings) {
                if (beforeSendSubFunc(xhr, settings) !== false) {
                    if (isLock) { Commons.lockScreen(); }
                    return true;
                } else {
                    return false;
                }
            };

            //-- 通信成功時の処理. --//
            var successFunc = (arge.success != null) ? arge.success 
                            : function (result, status, xhr) {};
            //-- 通信失敗時の処理. --//
            var errorFunc = (arge.error != null) ? arge.error 
                          : function (xhr, status, errorThrown) {
                                Commons.changeScreen('/system_error');
                            };

            //-- 応答後の処理. --//
            var completeSubFunc = (arge.complete != null) ? arge.complete 
                                : function(xhr, status) {};
            var completeFunc = function (xhr, status) {
                if (isLock) { Commons.unlockScreen(); }
                Commons.showPageTopBottom();
            };


            // アクション実行.
            $.ajax({
                url: sendUrl,
                type: type,
                data: arge.data,
                timeout: timeout,           // 単位はミリ秒.
                beforeSend: beforeSendFunc, // 送信前の処理.
                success: successFunc,       // 通信成功時の処理.
                error: errorFunc,           // 通信失敗時の処理.
                complete: completeFunc      // 応答後の処理.
            });

        }
    }
};



//-------------------- 画面処理.
/**
 * 画面全体の入力項目無効化.
 */
Commons.lockInput = function () {
    $('body').find('button').attr('disabled', true);
    $('body').find('a').attr('disabled', true);
    $('body').find('select').attr('disabled', true);
    $('body').find('textarea').attr('disabled', true);
    $('body').find(':input:not([type="hidden"]):not([tabindex="-1"])').attr('disabled', true);
};

/**
 * 画面全体の入力項目活性化.
 */
Commons.unlockInput = function () {
    $('body').find('button').attr('disabled', false);
    $('body').find('a').attr('disabled', false);
    $('body').find('select').attr('disabled', false);
    $('body').find('textarea').attr('disabled', false);
    $('body').find(':input:not([type="hidden"]):not([tabindex="-1"])').attr('disabled', false);
};

/**
 * 画面を開きます.
 * @param {function} [callback()] - callback処理.
 */
Commons.openScreen = function (callback) {
    if (!Commons.isChangingScreen) {
        Commons.isChangingScreen = true;
        $('.loading').hide('slow');
        $('.main-parts').css({'visibility':'visible'}).animate({opacity: 1}, 200);
        $('#main_contents').css({'visibility':'visible'}).animate({opacity: 1}, 200
               , function() { 
                      Commons.unlockInput();
                      Commons.isChangingScreen = false;
                      Commons.showPageTopBottom();
                      if (callback != null) { callback(); }
                  });
    }
};

/**
 * 画面を閉じます.
 * @param {function} [callback()] - callback処理.
 */
Commons.closeScreen = function (callback) {
    if (!Commons.isChangingScreen) {
        Commons.isChangingScreen = true;
        Commons.lockInput();
        Commons.hidePageTopBottom();
        $('.loading').show('slow');
        $('.main-parts').animate({opacity: 0, 'visibility':'hidden'}, 400);
        $('#main_contents').animate({opacity: 0, 'visibility':'hidden'}, 400 
                , function() { 
                      Commons.isChangingScreen = false;
                      if (callback != null) { callback(); }
                  });
    }
};


/**
 * 画面操作をロックにする.
 */
Commons.lockScreen = function () {
    Commons.lockInput();
    var divTag = $('<div />').attr('id', Commons.LOCKID);
    divTag.css('z-index', '9999')
          .css('position', 'fixed')
          .css('top', '0px').css('left', '0px')
          .css('height', '100%').css('width', '100%')
          .css('background-color', 'ghostwhite').css('opacity', '0.6');
    var divStyle = 'position:fixed;top:30%;left:50%;z-index:10000;';
    var imgUrl = Commons.url('/contents/images/commons/loading.gif');
    divTag.html('<div style="' + divStyle + '">Loading...<br /><img src="' + imgUrl + '"></div>');
    $('body').append(divTag);
};

/**
 * 画面操作ロックを解除する.
 */
Commons.unlockScreen = function () {
    var target = $('#' + Commons.LOCKID);
    if (target.length > 0) { target.remove(); }
    Commons.unlockInput();
};


/**
 * スクロールをリセットします.
 * @param {string} selector - 対象Tagのセレクタ.
 */
Commons.scrollReset = function (selector) {
    if (!Commons.isScrolling && $(selector) != null) {
        Commons.isScrolling = true;
        $(selector).animate({scrollTop:0}, '1'
                , function(){ Commons.isScrolling = false });
    }
};

/**
 * 画面TOPまでスクロールします.
 */
Commons.scrollTop = function () {
    if (!Commons.isScrolling) {
        Commons.isScrolling = true;
        $('html,body').animate({scrollTop:0}, 'fast'
                , function(){ Commons.isScrolling = false });
    }
};

/**
 * 画面下までスクロールします.
 */
Commons.scrollBottom = function () {
    if (!Commons.isScrolling) {
        Commons.isScrolling = true;
        $('html,body').animate({scrollTop:document.body.scrollHeight}, 'fast'
                , function(){ Commons.isScrolling = false });
    }
};


/**
 * スクロールバーの有無を確認します.
 * @return {boolean} スクロールバーの有無.
 */
Commons.hasScrollBar = function (selector) {
    var target = $(selector);
    if (target.length > 0) {
        var clientHeight = target[0].clientHeight;
        var scrollHeight = target[0].scrollHeight;
        return ((scrollHeight - clientHeight) > 0);
    }
    return false;
}

/**
 * スクロールボタン表示.
 */
Commons.showPageTopBottom = function () {
    if (Commons.hasScrollBar('body') || Commons.hasScrollBar('html')) {
        $('#to_page_top').fadeIn('fast');
        $('#to_page_bottom').fadeIn('fast');
        $('#to_page_top').on('click', function(){ Commons.scrollTop(); });
        $('#to_page_bottom').on('click', function(){ Commons.scrollBottom(); });
    } else {
        $('#to_page_top').fadeOut('fast');
        $('#to_page_bottom').fadeOut('fast');
    }
};

/**
 * スクロールボタン非表示.
 */
Commons.hidePageTopBottom = function () {
    $('#to_page_top').fadeOut('fast');
    $('#to_page_bottom').fadeOut('fast');
};



//-------------------- 項目処理.
/**
 * フォーカス処理.
 * @param {string} selector - セレクタ.
 */
Commons.focus = function (selector) {
    var target = $(selector);
    if (target.length > 0) {
        Commons.unlockScreen();
        target.focus();
    }
};

/**
 * 入力制御処理.
 * @param {string} selector - セレクタ.
 * @param {boolean} value - true or false.
 */
Commons.disabled = function (selector, value) {
    var target = $(selector);
    if (target.length > 0) {
        target.attr('disabled', value);
        if (value === true) {
            target.attr('tabindex', '-1');
            target.addClass('readonly');
        } else {
            target.attr('tabindex', '');
            target.removeClass('readonly');
        }
    }
};



//-------------------- メッセージ処理.
/**
 * メッセージを閉じます.
 * @param {string} [selector] - セレクタ.
 */
Commons.closeMessage = function (selector) {
    var messageAreaSelector;
    if (!$ReC.isStrBlk(selector)) {
        messageAreaSelector = selector + ' div.message-box';
    } else {
        messageAreaSelector = 'div.message-box';
    }
    var msgArea = $(messageAreaSelector);
    if (msgArea.length > 0) {
        msgArea.fadeOut('fast');
        var msgBox = $(messageAreaSelector + ' p');
        if (msgBox.length > 0) {
            msgBox.html('');
        }
    }
    Commons.removeErrorItem();
};

/**
 * メッセージを表示します.
 * @param {string} selector - セレクタ.
 * @param {object} msgData - メッセージデータ.
 */
Commons.showMessage = function (selector, msgData) {
    if (msgData != null && !$ReC.isStrBlk(msgData.text)) {

        // メッセージ種別判別.
        var kind;
        if (!$ReC.isStrBlk(msgData.kind)) {
            kind = msgData.kind;
        } else {
            kind = Commons.MSG_ERROR;
        }
        var messageAreaSelector = selector + ' div.message-box';
        if (kind === Commons.MSG_INFO) {
            messageAreaSelector += '.alert-info';
        } else if (kind === Commons.MSG_WORN) {
            messageAreaSelector += '.alert-warning';
        } else {
            messageAreaSelector += '.alert-danger';
        }

        var msgArea = $(messageAreaSelector);
        if (msgArea.length > 0) {
            var msgBox = $(messageAreaSelector + ' p');
            if (msgBox.length > 0) {

                // エラー時の項目背景色変更.
                var errorItem = '';
                if (kind === Commons.MSG_ERROR && !$ReC.isStrBlk(msgData.selector)) {
                    errorItem = Commons.setErrorItem(msgData.selector);
                }

                // メッセージ作成.
                var messageTag = $ReC.escapeHTML(msgData.text);
                if (!$ReC.isStrBlk(errorItem)) {
                    messageTag = '<a href="javascript:void(0)" class="message-focus" data-selector="' + errorItem + '">' + messageTag + '</a>';
                } else {
                    messageTag = '<span>' + messageTag + '</span>';
                }
                if (!$ReC.isStrBlk(msgBox.html())) {
                    msgBox.html(msgBox.html() + '<br />' + messageTag);
                } else {
                    msgBox.html(messageTag);
                }
                $('.message-focus').on('click', function (e) {
                    var selector = $(this).attr('data-selector');
                    Commons.focus(selector);
                });

                // メッセージ表示.
                msgArea.fadeIn('fast');

                // 閉じるボタン設定.
                var msgClose = $(messageAreaSelector + ' button.close');
                if (msgClose.length > 0) {
                    msgClose.on('click', function (event) {
                        msgArea.fadeOut('fast');
                        msgBox.html('');
                    });
                }

            } // msgBox check.
        } // msgArea check.

    } // msgData check.
};

/**
 * メッセージを表示します.
 * @param {string} selector - セレクタ.
 * @param {Array} msgDataList - メッセージデータリスト.
 */
Commons.showMessages = function (selector, msgDataList) {
    // メッセージ表示.
    if (!$ReC.isAryBlk(msgDataList)) {
        for (var i = 0, imax = msgDataList.length; i < imax; i++) {
            Commons.showMessage(selector, msgDataList[i]);
        }
    }
    // スクロールTOP.
    if ((!$ReC.isStrBlk(selector) && '#main_contents' !== selector)) {
        Commons.scrollReset(selector);
    } else {
        Commons.scrollTop();
    }
}



//-------------------- エラー処理.
/**
 * 項目にエラー設定を行います.
 * @param {string} selector - セレクタ.
 * @return {string} 先頭対象項目のセレクタ.
 */
Commons.setErrorItem = function (selector) {
    var sel = '';
    if (!$ReC.isStrBlk(selector)) {
        if (selector.indexOf(',') >= 0) {
            // 複数選択.
            var ret = '';
            var selectors = selector.split(',');
            for (var i = 0, imax = selectors.length; i < imax; i++) {
                sel = Commons.setErrorItem(selectors[i]);
                if ($ReC.isStrBlk(ret)) {
                  ret = sel;
                }
            }
            return ret;
        } else {
            // 単体.
            var sel = selector.trim() + '.form-control:not(.readonly):visible';
            var errorErrorItem = $(sel);
            if (errorErrorItem.length > 0) {
                errorErrorItem.addClass('alert-danger');
                return sel;
            } else {
                return '';
            }
        }
    }
    return sel;
};

/**
 * 項目のエラー設定を解除します.
 */
Commons.removeErrorItem = function () {
    var errorErrorItem = $('.form-control.alert-danger');
    if (errorErrorItem.length > 0) {
        errorErrorItem.removeClass('alert-danger');
    }
};



//-------------------- テーブル処理.
/**
 * テーブル行をクリアします.
 * @param {string} selector - セレクタ(テーブルID).
 */
Commons.clearTableRow = function (selector) {
    var tbody = $(selector + " tbody");
    if (tbody.length > 0) {
        tbody.html('');
    }
};


/**
 * 選択テーブル設定.
 *
 * @param {object} arge - ページング設定のパラメタ.
 * <pre>
 *  {string} tbl - セレクタ(テーブルID).
 *  {function} [selectFunc(row,col) as bool]  - 設定時の処理Function.
 * </pre>
 */
Commons.setSelectTable = function (arge) {
    var listRow = $(arge.tbl + ' tbody tr td');
    if (listRow.length > 0) {

        // クリック時設定.
        listRow.on('click', function (e) {

            // 選択解除.
            var allTr = $(arge.tbl + ' tbody').find('tr');
            allTr.removeClass('selected-row');
            allTr.addClass('unselected-row');

            // 行選択.
            var tr = $(this).parents('tr');
            if (tr.hasClass('unselected-row')) {
                if (arge.selectFunc != null) {
                    var row = $(arge.tbl + ' tr').index(tr);
                    var col = tr.find('td').index($(this)) + 1;
                    // 選択処理.
                    if (arge.selectFunc(row, col)) {
                        Commons.setSelectedRow(tr);
                    }
                    return;
                } else {
                    Commons.setSelectedRow(tr);
                }
            }

        });

        // ヘッダクリック時選択全解除.
        var hdRow = $(arge.tbl + ' thead tr th');
        if (hdRow.length > 0) {
            hdRow.on('click', function (e) {
              var allTr = $(arge.tbl + ' tbody').find('tr');
              allTr.removeClass('selected-row');
              allTr.addClass('unselected-row');
            });
        }

    }
};

/**
 * 行選択.
 * @param {HTML element} tr - TRタグ.
 */
Commons.setSelectedRow = function (tr) {
    tr.removeClass('unselected-row');
    tr.addClass('selected-row');
};

/**
 * 選択行を取得します.
 * @param {string} selector - セレクタ(テーブルID).
 * @return {Object} 選択行.
 */
Commons.getSelectedTr = function (selector) {
    return $(selector + ' tbody tr.selected-row');
};

/**
 * 選択行番号を取得します.
 * @param {string} selector - セレクタ(テーブルID).
 * @return {number} 選択行番号.
 */
Commons.getSelectedRow = function (selector) {
    var selectedRowTr = Commons.getSelectedTr(selector);
    if (selectedRowTr.length > 0) {
        return $(selector + ' tr').index(selectedRowTr);
    } else {
      return 0;
    }
};



//-------------------- 初期設定処理.
/**
 * コンテンツヘッダーの高さ調整.
 */
Commons.adjustFixedHeader = function () {
    $(window).on('load resize', function(){
        var height = $('.navbar').height() + 24;
        $('body').css('padding-top', height); 
        Commons.scrollTop();
    });
};

/**
 * スクロールボタン表示制御設定.
 */
Commons.setPageTopBottom = function () {
    $(window).on('load resize', function() { Commons.showPageTopBottom(); });
};

/**
 * 画面遷移リンクタグ設定.
 */
Commons.setChangeScreenLink = function () {
    $('a.change_screenlink').on('click', function (event) {
        var actionUrl = $(this).attr('data-action');
        if ($ReC.isStrBlk(actionUrl)) {
            actionUrl = $(this).children('input[name="data_action"]').val();
        }
        if ($ReC.isStrBlk(actionUrl)) {
            actionUrl = $(this).next('input[name="data_action"]').val();
        }
        if (!$ReC.isStrBlk(actionUrl)) { 
          Commons.changeScreen(actionUrl); 
        }
    });
};

/**
 * 日付入力設定処理.
 */
Commons.setDatePicker = function () {
    var datePicker = $('.date-picker');
    if (datePicker.length > 0) {
        datePicker.datepicker({language:'ja', format:'yyyy/mm/dd', autoclose:true});
    }
};

/**
 * ログアウト処理設定.
 */
Commons.setLogout = function () {
    var logoutUrl = $('#logout_url').val();
    if ($ReC.isStrBlk(logoutUrl)) {
        $('#btn_logout').css({'visibility':'hidden'});
    } else {
        //-- ログアウト ボタン押下処理. --//
        $('#btn_logout').on('click', function (event) {
            var logoutUrl = $('#logout_url').val();
            if (!$ReC.isStrBlk(logoutUrl)) {
                ModalConfirm.show({
                     title:'ログアウト'
                    ,msgData:{button:ModalConfirm.BTN_YES_NO, text:'ログアウトします。\nよろしいですか？'}
                    ,btnYesFunc: function () { Commons.changeScreen(logoutUrl); }
                    ,focusButton: ModalConfirm.BTN_NO
                });
            }
        });
    }
};

/**
 * 再表示処理設定.
 */
Commons.setReset = function () {
    var resetUrl = $('#reset_url').val();
    if ($ReC.isStrBlk(resetUrl)) {
        $('#btn_reset').css({'visibility':'hidden'});
    } else {
        //-- 再表示 押下処理. --//
        $('#btn_reset').on('click', function (event) {
            var resetUrl = $('#reset_url').val();
            if (!$ReC.isStrBlk(resetUrl)) {
                if (Commons.isChangedInput) {
                    ModalConfirm.show({
                         title:'初期化'
                        ,msgData:{button:ModalConfirm.BTN_YES_NO, text:'画面を初期化します。\n入力内容が破棄されますがよろしいですか？'}
                        ,btnYesFunc: function () { Commons.changeScreen(resetUrl); }
                        ,focusButton: ModalConfirm.BTN_NO
                    });
                } else { 
                    Commons.changeScreen(resetUrl); 
                }
            }
        });
    }
};

/**
 * 戻る処理設定.
 */
Commons.setReturn = function () {
    var returnUrl = $('#return_url').val();
    if ($ReC.isStrBlk(returnUrl)) {
        $('#btn_return').css({'visibility':'hidden'});
    } else {
        //-- 戻る ボタン押下処理. --//
        $('#btn_return').on('click', function (event) {
            var returnUrl = $('#return_url').val();
            if (!$ReC.isStrBlk(returnUrl)) {
                if (Commons.isChangedInput) {
                    ModalConfirm.show({
                         title:'戻る'
                        ,msgData:{button:ModalConfirm.BTN_YES_NO, text:'前の画面に戻ります。\n入力内容が破棄されますがよろしいですか？'}
                        ,btnYesFunc: function () { Commons.changeScreen(returnUrl); }
                        ,focusButton: ModalConfirm.BTN_NO
                    });
                } else { 
                    Commons.changeScreen(returnUrl);
                }
            }
        });
    }
};

/**
 * 入力内容変更設定.
 */
Commons.setChangeInput = function () {
    Commons.isChangedInput = false;
    $('.form-control').on('change', function (event) {
        Commons.isChangedInput = true;
    });
};



//-------------------- その他処理.
/**
 * ページングの設定.
 * @param {object} arge - ページング設定のパラメタ.
 * <pre>
 *  {string} list - セレクタ(リスト).
 *  {string} page - セレクタ(現在ページ).
 *  {string} totalPage - セレクタ(総ページ数).
 *  {string} totalSize - セレクタ(総件数).
 *  {function} event(selectPage) - ページボタン押下時の処理Function.
 *
 *  ※「.pagination-title」要素にページ数と総件数を表示します。
 *    「.pagination-title」内の
 *      「.pagination-title-page」に件数
 *      「.pagination-title-total-page」に総ページ数
 *      「.pagination-title-total-size」に総件数が表示されます。
 *
 * </pre>
 */
Commons.setPaging = function (arge) {
    if (arge != null 
            && arge.list != null 
            && arge.page != null 
            && arge.totalPage != null) {

        var pagingList = $(arge.list + ' ');
        if (pagingList.length > 0) {

            // 初期化.
            pagingList.html('');
            var page = $ReC.toInt($(arge.page).val());
            var total_page = $ReC.toInt($(arge.totalPage).val());
            var paginationTitle = pagingList.parent().find('.pagination-title');
            if (paginationTitle.length > 0) { paginationTitle.hide(); }

            if (total_page > 1) {

                // 範囲設定
                var MAX = 7;
                var RANGE = 3;
                var CENTER = 4;
                var paging_min = 1;
                var paging_max = MAX;
                if (page < CENTER) {
                    paging_min = 1;
                    paging_max = (total_page > MAX) ? MAX : total_page;
                } else {
                    paging_min = page - RANGE;
                    paging_max = (total_page > (page + RANGE)) ? (page + RANGE) : total_page;
                    if ((paging_max - paging_min) < (MAX - 1)) {
                      paging_min = ((paging_max - (MAX - 1)) > 1) ? (paging_max - (MAX - 1)) : 1;
                    }
                }

                // CSS.
                var paging_class = '';
                var paging_prev_class = (page <= 1) ? ' disabled' : '';
                var paging_next_class = (page >= total_page) ? ' disabled' : '';

                // HTML.
                var pagingHtml = '';
                pagingHtml += '<li class="page-item ' + paging_prev_class + '">'
                            + '<a class="page-link paging_prev" href="javascript:void(0)" tabindex="-1">前へ</a></li>';
                for (var i = paging_min; i <= paging_max; i++) {
                    paging_class = (i === page) ? 'active' : '';
                    pagingHtml += '<li class="page-item ' + paging_class + '" >'
                                + '<a class="page-link paging_page" href="javascript:void(0)" tabindex="-1">' + i + '</a></li>';
                }
                pagingHtml += '<li class="page-item ' + paging_next_class + '">'
                            + '<a class="page-link paging_next" href="javascript:void(0)" tabindex="-1">次へ</a></li>';
                pagingList.append(pagingHtml);  

                // Event.
                $(arge.list + ' li a.paging_prev').on('click', function (event) {
                    if (paging_prev_class === '') {
                        var selectPage = page - 1;
                        $(arge.page).val(selectPage);
                        if ( arge.event != null) { arge.event(selectPage); }
                    } 
                });
                $(arge.list + ' li a.paging_page').on('click', function (event) {
                    var selectPage = $ReC.toInt($(this).html());
                    if (selectPage !== page) {
                        $(arge.page).val(selectPage);
                        if ( arge.event != null) { arge.event(selectPage); }
                    }
                });
                $(arge.list + ' li a.paging_next').on('click', function (event) {
                    if (paging_next_class === '') {
                        var selectPage = page + 1;
                        $(arge.page).val(selectPage);
                        if ( arge.event != null) { arge.event(selectPage); }
                    }
                });

                // Title.
                if (paginationTitle.length > 0) {
                    paginationTitle.show();
                    paginationTitle.find('.pagination-title-page').html(page);
                    paginationTitle.find('.pagination-title-total-page').html(total_page);
                    if ($(arge.totalSize).length > 0) {
                        var totalSize = $ReC.toInt($(arge.totalSize).val());
                        paginationTitle.find('.pagination-title-total-size').html(totalSize);
                    }
                }

            }

        }
    }
}

