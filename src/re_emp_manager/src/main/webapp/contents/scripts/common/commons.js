/**
 * @file commons.js
 * @author res.
 * @version 1.2.6 (2018.10.17)
 */
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
/** アクションロックフラグ. */
Commons.isLockAction = false;

/** 共通メッセージ. */
Commons.commonMessages = [];



//-------------------- イベント.
/** 画面ロード後のイベント. */
Commons.afterLoad = function(){};



//-------------------- URL処理.
/**
 * 会話IDを設定する.
 * @param {string} conversation_id - cid.
 */
Commons.setConversationId = function (conversation_id) {
    if (!$ReC.isStrBlk(conversation_id) && conversation_id !== 'null') {
        $('#conversation_id').val(conversation_id);
    } else {
        $('#conversation_id').val(''); 
    }
};

/**
 * ROOTURLを追加してURLを取得する.
 * @param {string} url - URL.
 * @param {boolean} is_cid - cidの接続有無.
 * @return {string} URL.
 */
Commons.url = function (url, is_cid) {
    if (!$ReC.isStrBlk(url)) {
        if (url.substr(0, 1) === '/') {
        // urlの先頭が'/'の場合.
            var root_url = $('#root_url').val(); 
            if (!$ReC.isStrBlk(root_url)
                    && (url.indexOf(root_url) === -1 ||
                        url.indexOf(root_url) > 0)) {
            // root_urlがあり、対象のURLの先頭がroot_urlではない場合.
                if (root_url.substr(-1) === '/') {
                // root_urlの終わりが'/'終わりの場合は、'/'を一つ削除する.
                    url = root_url + url.substr(1);
                } else {
                    url = root_url + url;
                }
            }
        }
        if (is_cid) {
            // @ConversationScoped対応.
            var conversation_id = $('#conversation_id').val();
            if (!$ReC.isStrBlk(conversation_id)) {
                url += ((url.indexOf('?') < 0) ? '?' : '&')
                        + 'cid=' + encodeURIComponent(conversation_id);
            }
        }
    }
    return url;
};



//-------------------- 画面遷移・アクション処理.
/**
 * 画面遷移する.
 * @param {string} url - URL.
 */
Commons.changeScreen = function (url) {
    if (!Commons.isChangingScreen && !Commons.isLockAction) {
        var sendUrl = Commons.url(url, true);
        if (!$ReC.isStrBlk(sendUrl)) {
            Commons.closeScreen(function(){ 
                        Commons.lockAction(); 
                        window.location.href = sendUrl; 
                    });
        }
    }
};

/**
 * 画面遷移する(Lock無).
 * @param {string} url - URL.
 */
Commons.changeScreenNoLock = function (url) {
    if (!Commons.isChangingScreen && !Commons.isLockAction) {
        var sendUrl = Commons.url(url, true);
        if (!$ReC.isStrBlk(sendUrl)) {
            window.location.href = sendUrl; 
        }
    }
};

/**
 * POST(送信)する.
 * @param {string} selector - 対象formのセレクタ.
 * @param {string} url - URL.
 */
Commons.post = function (selector, url) {
    if (!Commons.isChangingScreen && !Commons.isLockAction) {
        var sendUrl = Commons.url(url, true);
        if (!$ReC.isStrBlk(sendUrl)) {
            var form = $(selector);
            if (form.length > 0) {
                form.attr('action', sendUrl);
                form.attr('method', 'post');
                Commons.lockAction();
                form.submit();
                Commons.closeScreen();
            }
        }
    }
};

/**
 * POST(送信)する(Lock無).
 * @param {string} selector - 対象formのセレクタ.
 * @param {string} url - URL.
 */
Commons.post = function (selector, url) {
    if (!Commons.isChangingScreen && !Commons.isLockAction) {
        var sendUrl = Commons.url(url, true);
        if (!$ReC.isStrBlk(sendUrl)) {
            var form = $(selector);
            if (form.length > 0) {
                form.attr('action', sendUrl);
                form.attr('method', 'post');
                form.submit();
            }
        }
    }
};

/**
 * アクションを実行する.
 * @param {object} args - アクションのパラメタ.
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
Commons.action = function (args) {
    if (!$ReC.isEmpty(args)) {
        if (!Commons.isChangingScreen && !Commons.isLockAction) {
            var sendUrl = Commons.url(args.url, true);
            if (!$ReC.isStrBlk(sendUrl)) {
                // Ajaxアクション判定フラグ設定.
                sendUrl += ((sendUrl.indexOf('?') < 0) ? '?' : '&') + 'is_ajax=1';
                // TYPE.
                var type = (!$ReC.isStrBlk(args.type)) ? args.type : 'POST';
                // ロックの有無.
                var isLock = ($ReC.isBool(args.isLock)) ? args.isLock : true;
                // タイムアウト.
                var timeout = (args.timeout != null) ? args.timeout : 60000*5;

                //-- 送信前の処理. --//
                var beforeSendFunc = function(xhr, settings) {
                    var beforeSendSubFunc = (args.beforeSend != null) 
                                              ? args.beforeSend 
                                              : function(xhr, settings) { return true; };
                    if (beforeSendSubFunc(xhr, settings) !== false) {
                        if (isLock) { Commons.lockScreen(); }
                        return true;
                    } else {
                        return false;
                    }
                };
                //-- 通信成功時の処理. --//
                var successFunc = function (result, status, xhr) {
                    if (isLock) { Commons.unlockScreen(); }
                    if (args.success != null) { args.success(result, status, xhr); }
                };
                //-- 通信失敗時の処理. --//
                var errorFunc = function (xhr, status, errorThrown) {
                    if (isLock) { Commons.unlockScreen(); }
                    if (args.error != null) { args.error(xhr, status, errorThrown); } 
                    else {
                        var ajax_error_url = $('#ajax_error_url').val();
                        if ($ReC.isStrBlk(ajax_error_url)) { ajax_error_url = '/system_error'; }
                        Commons.changeScreen(ajax_error_url);
                    }
                };
                //-- 応答後の処理. --//
                var completeFunc = function (xhr, status) {
                    Commons.showPageTopBottom();
                    if (args.complete != null) { args.complete(xhr, status); }
                };

                // アクション実行.
                $.ajax({
                    url: sendUrl,
                    type: type,
                    data: args.data,
                    timeout: timeout,           // 単位はミリ秒.
                    beforeSend: beforeSendFunc, // 送信前の処理.
                    success: successFunc,       // 通信成功時の処理.
                    error: errorFunc,           // 通信失敗時の処理.
                    complete: completeFunc      // 応答後の処理.
                });

            }
        }
    }
};

/**
 * アップロードアクションを実行する.
 * @param {object} args - アクションのパラメタ.
 * <pre>
 *  {string} url        - URL.
 *  {string} [isLock]   - ロックの有無(true).
 *  {number} [timeout]  - タイムアウト時間.
 *  {string} [selector] - 対象formのセレクタ.
 *  {function} [success(result, status, xhr)]       - 成功時の処理Function.
 *  {function} [error(xhr, status, errorThrown)]    - 失敗時の処理Function.
 * </pre>
 */
Commons.upload = function (args) {
    if (!$ReC.isEmpty(args)) {
        if (!Commons.isChangingScreen && !Commons.isLockAction) {
            var sendUrl = Commons.url(args.url, true);
            if (!$ReC.isStrBlk(sendUrl) && args.selector) {
                // Ajaxアクション判定フラグ設定.
                sendUrl += ((sendUrl.indexOf('?') < 0) ? '?' : '&') + 'is_ajax=1';
                // フォームデータを取得.
                var formdata = new FormData($(args.selector).get(0));
                // ロックの有無.
                var isLock = ($ReC.isBool(args.isLock)) ? args.isLock : true;
                // タイムアウト.
                var timeout = (args.timeout != null) ? args.timeout : 60000*5;

                // POSTでアップロード.
                if (isLock) { Commons.lockScreen(); }
                $.ajax({
                    url: sendUrl,
                    type: "POST",
                    data: formdata,
                    timeout: timeout,
                    cache: false,
                    contentType: false,
                    processData: false,
                    dataType: "html"
                })
                // 成功時.
                .done(function(result, status, xhr) {
                    if (isLock) { Commons.unlockScreen(); }
                    if (args.success != null) { args.success($ReC.parseJSON(result), status, xhr); }
                })
                // 失敗時.
                .fail(function(xhr, status, errorThrown) {
                    if (isLock) { Commons.unlockScreen(); }
                    if (args.error != null) { args.error(xhr, status, errorThrown); } 
                    else {
                        var ajax_error_url = $('#ajax_error_url').val();
                        if ($ReC.isStrBlk(ajax_error_url)) { ajax_error_url = '/system_error'; }
                        Commons.changeScreen(ajax_error_url);
                    }
                });
            }
        }
    }
};

/**
 * アクションをロックします.
 */
Commons.lockAction = function () { 
    Commons.isLockAction = true;
};

/**
 * アクションのロックを解除します.
 */
Commons.unlockAction = function () { 
    Commons.isLockAction = false;
};



//-------------------- 画面処理.
/**
 * 画面を開きます.
 * @param {function} [callback()] - callback処理.
 */
Commons.openScreen = function (callback) {
    if (!Commons.isChangingScreen) {
        Commons.isChangingScreen = true;
        $('.loading').hide('slow');
        $('.main-parts').css({'visibility':'visible'}).animate({opacity: 1}, 'fast');
        $('#main_contents').css({'visibility':'visible'}).animate({opacity: 1}, 'fast'
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
        $('.main-parts').animate({opacity: 0, 'visibility':'hidden'}, 'fast');
        $('#main_contents').animate({opacity: 0, 'visibility':'hidden'}, 'fast' 
                , function() { 
                      Commons.isChangingScreen = false;
                      if (callback != null) { callback(); }
                  });
    }
};


/**
 * 画面全体の入力項目無効化.
 */
Commons.lockInput = function () {
    $('body').find('button').attr('disabled', true);
    $('body').find('a').attr('disabled', true);
    $('body').find('select:not([tabindex="-1"])').attr('disabled', true);
    $('body').find('textarea:not([tabindex="-1"])').attr('disabled', true);
    $('body').find('input:not([type="hidden"]):not([tabindex="-1"])').attr('disabled', true);
};

/**
 * 画面全体の入力項目活性化.
 */
Commons.unlockInput = function () {
    $('body').find('button').attr('disabled', false);
    $('body').find('a').attr('disabled', false);
    $('body').find('select:not([tabindex="-1"])').attr('disabled', false);
    $('body').find('textarea:not([tabindex="-1"])').attr('disabled', false);
    $('body').find('input:not([type="hidden"]):not([tabindex="-1"])').attr('disabled', false);
};


/**
 * 画面操作をロックにする.
 * @param {boolean} isNoLockInput - 入力項目無効化有無.
 */
Commons.lockScreen = function (isNoLockInput) {
    Commons.lockAction();
    if (!isNoLockInput) { Commons.lockInput(); }
    var target = $('#' + Commons.LOCKID);
    if (target.length === 0) { 
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
    }
};

/**
 * 画面操作ロックを解除する.
 * @param {boolean} isNoLockInput - 入力項目無効化有無.
 */
Commons.unlockScreen = function (isNoLockInput) {
    var target = $('#' + Commons.LOCKID);
    if (target.length > 0) { target.remove(); }
    if (!isNoLockInput) { Commons.unlockInput(); }
    Commons.unlockAction();
};


/**
 * スクロールをリセットします.
 * @param {string} selector - 対象Tagのセレクタ.
 */
Commons.scrollReset = function (selector) {
    if ((!$ReC.isStrBlk(selector) && '#main_contents' !== selector)) {
        if (!Commons.isScrolling && $(selector) != null) {
            Commons.isScrolling = true;
            $(selector).animate({scrollTop:0}, '1'
                    , function(){ Commons.isScrolling = false });
        }
    } else {
        Commons.scrollTop();
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


/**
 * コンボボックスの中身を変更します.
 * @param {string} selector - セレクタ.
 * @param {array} list - ListItemリスト.
 */
Commons.changeOptions = function (selector, list) {
    var target = $(selector);
    if (target.length > 0 && list != null && list.length > 0) {
        target.empty();
        $(list).each(function(i){
            target.append($("<option />").val(list[i].value).text(list[i].text));
        });
    }
};

/**
 * テキストエリアのカーソル位置にテキストを挿入します.
 * @param {string} selector - 対象textareaのセレクタ.
 * @param {string} value - 設定する値.
 */
Commons.setTextareaVal = function (selector, value) {
    var textarea = $(selector);
    if (textarea.length > 0 && !$ReC.isStrBlk(value)) {
        var textareaValue = textarea.val();
        var len = textareaValue.length;
        var pos = textarea[0].selectionStart;
        textarea.val(textareaValue.substr(0, pos) + value + textareaValue.substr(pos, len));
    }
};



//-------------------- POSTデータ処理.
/**
 * オブジェクトから@DataParam用の項目をformオブジェクトに設定します.
 * @param {object} form - formオブジェクト.
 * @param {object} obj - 対象のオブジェクト.
 * @param {string} dataName - データ名.
 * @return {object} formオブジェクト.
 */
Commons.setParamForm = function (form, obj, dataName) {
    if (form != null && obj != null && $ReC.isObj(obj) && !$ReC.isStrBlk(dataName)) {
        for (var name in obj) {
            form.append($('<input type="hidden" />').attr({name: dataName + '.' + name, value: obj[name]}));
        }
    }
    return form;
};

/**
 * オブジェクトから@DataParam用のformオブジェクトを作成します.
 * @param {object} obj - 対象のオブジェクト.
 * @param {string} dataName - データ名.
 * @return {object} formオブジェクト.
 */
Commons.makeDataParamForm = function (obj, dataName) {
    return Commons.setParamForm($('<form/>'), obj, dataName);
};

/**
 * オブジェクトから@DataParam用のPostデータを作成します.
 * @param {object} obj - 対象のオブジェクト.
 * @param {string} dataName - データ名.
 * @return {string} Postデータ.
 */
Commons.serializeDataParam = function (obj, dataName) {
    var form = Commons.makeDataParamForm(obj, dataName);
    if (form != null) {
        return form.serialize();
    }
    return '';
};


/**
 * リストから@ListParam用の項目をformオブジェクトに設定します.
 * @param {object} form - formオブジェクト.
 * @param {object} list - 対象のリスト.
 * @param {string} listName - リスト名.
 * @return {object} formオブジェクト.
 */
Commons.setListParamForm = function (form, list, listName) {
    if (form != null && !$ReC.isAryBlk(list) && !$ReC.isStrBlk(listName)) {
        for (var index in list) {
            for (var name in list[index]) {
                form.append($('<input type="hidden" />').attr(
                        {name: listName + '[' + index + '].' + name, value: list[index][name]}));
            }
        }
        form.append($('<input type="hidden" />').attr({name: listName + '_size', value: list.length}));
    }
    return form;
};

/**
 * リストから@ListParam用のformオブジェクトを作成します.
 * @param {object} list - 対象のリスト.
 * @param {string} listName - リスト名.
 * @return {object} formオブジェクト.
 */
Commons.makeListParamForm = function (list, listName) {
    return Commons.setListParamForm($('<form/>'), list, listName);
};

/**
 * リストから@ListParam用のPostデータを作成します.
 * @param {object} list - 対象のリスト.
 * @param {string} listName - リスト名.
 * @return {string} Postデータ.
 */
Commons.serializeListParam = function (list, listName) {
    var form = Commons.makeListParamForm(list, listName);
    if (form != null) {
        return form.serialize();
    }
    return '';
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
                var messageTag = Commons.escapeHTMLCL(msgData.text);
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
        // スクロールTOP.
        Commons.scrollReset(selector);
    }
}

/**
 * 共通メッセージを取得します.
 * @param {string} [0] - メッセージコード.
 * @param {string} [1...] - 埋め込み文字.
 */
Commons.getCommonMessage = function () {
    var args = Array.prototype.slice.call(arguments);
    if (args.length > 0) {
        var msg = Commons.commonMessages[args[0]];
        if (!$ReC.isStrBlk(msg)) {
            for (var i = 1, imax = args.length; i < imax; i++) {
                msg = msg.replace('%s', args[i]);
            }
        }
        return msg;
    }
    return '';
};



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
 * @param {object} args - ページング設定のパラメタ.
 * <pre>
 *  {string} tbl - セレクタ(テーブルID).
 *  {function} [selectFunc(row,col,e) as bool]  - 設定時の処理Function.
 * </pre>
 */
Commons.setSelectTable = function (args) {
    var listRow = $(args.tbl + ' tbody tr td');
    if (listRow.length > 0) {

        // クリック時設定.
        listRow.on('click', function (event) {

            // 選択解除.
            var allTr = $(args.tbl + ' tbody').find('tr');
            allTr.removeClass('selected-row');
            allTr.addClass('unselected-row');

            // 行選択.
            var tr = $(this).parents('tr');
            if (tr.hasClass('unselected-row')) {
                if (args.selectFunc != null) {
                    var row = $(args.tbl + ' tr').index(tr);
                    var col = tr.find('td').index($(this)) + 1;
                    // 選択処理.
                    if (args.selectFunc(row, col, event)) {
                        Commons.setSelectedRow(tr);
                    }
                    return;
                } else {
                    Commons.setSelectedRow(tr);
                }
            }

        });

        // ヘッダクリック時選択全解除.
        var hdRow = $(args.tbl + ' thead tr th');
        if (hdRow.length > 0) {
            hdRow.on('click', function (e) {
              var allTr = $(args.tbl + ' tbody').find('tr');
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



//-------------------- 入力内容変更フラグ処理.
/**
 * 入力内容変更設定.
 */
Commons.ChangeInputOn = function () { 
    Commons.isChangedInput = true;
};

/**
 * 入力内容変更設定初期化.
 */
Commons.resetChangeInput = function () { 
    Commons.isChangedInput = false;
};

/**
 * 入力内容変更時の確認.
 * @param {string} title - タイトル.
 * @param {string} msg - メッセージ.
 * @param {function} func() - 実行する処理.
 */
Commons.checkChangedInput = function (title, msg, func) {
    if (Commons.isChangedInput) {
        ModalConfirm.show({
              title: title
            , msgData: {button: ModalConfirm.BTN_YES_NO, text: msg + '。\n入力内容が破棄されますがよろしいですか？'}
            , btnYesFunc: function() { func(); }
            , focusButton: ModalConfirm.BTN_NO
        });
    } else { 
        func();
    }
};



//-------------------- 初期設定処理.
/**
 * 画面初期設定.
 */
Commons.initScreen = function () {
  $ReC.inputTab($ReC.findAll('.tab_input'));
  Commons.setDatePicker();
  Commons.setATagLink();
  Commons.setChangeScreenLink();
  Commons.setPostLink();
  Commons.setPageTopBottom();
  Commons.setAccordionMenu();
  Commons.setChangeInput();
  Commons.openScreen(Commons.afterLoad);
};

/**
 * 入力内容変更設定.
 */
Commons.setChangeInput = function () {
    $('.form-control').on('change', function (event) {
        Commons.ChangeInputOn();
    });
};

/**
 * スクロールボタン表示制御設定.
 */
Commons.setPageTopBottom = function () {
    $(window).on('load resize', function() { Commons.showPageTopBottom(); });
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
 * 画面遷移リンクタグ設定.
 */
Commons.setChangeScreenLink = function () {
    $('.change_screenlink').on('click', function (event) {
        // URL.
        var actionUrl = $(this).attr('data-action');
        if ($ReC.isStrBlk(actionUrl)) {
            actionUrl = $(this).children('input[name="data_action"]').val();
        }
        // Title.
        var actionTitle = $(this).attr('data-action-title');
        if ($ReC.isStrBlk(actionTitle)) {
            actionTitle = $(this).children('input[name="data_action_title"]').val();
        }
        if ($ReC.isStrBlk(actionTitle)) { actionTitle = '画面変更'; }
        // Name.
        var actionName = $(this).attr('data-action-name');
        if ($ReC.isStrBlk(actionName)) {
            actionName = $(this).children('input[name="data_action_name"]').val();
        }
        if ($ReC.isStrBlk(actionName)) { actionName = '画面を遷移します'; }
        if (!$ReC.isStrBlk(actionUrl)) {
            Commons.checkChangedInput(actionTitle, actionName, function() { Commons.changeScreen(actionUrl); });
        }
    });
};

/**
 * POSTリンクタグ設定.
 */
Commons.setPostLink = function () {
    $('.postlink').on('click', function (event) {
        // URL.
        var actionUrl = $(this).attr('data-action');
        if ($ReC.isStrBlk(actionUrl)) {
            actionUrl = $(this).children('input[name="data_action"]').val();
        }
        // Title.
        var actionTitle = $(this).attr('data-action-title');
        if ($ReC.isStrBlk(actionTitle)) {
            actionTitle = $(this).children('input[name="data_action_title"]').val();
        }
        if ($ReC.isStrBlk(actionTitle)) { actionTitle = '送信'; }
        // Name.
        var actionName = $(this).attr('data-action-name');
        if ($ReC.isStrBlk(actionName)) {
            actionName = $(this).children('input[name="data_action_name"]').val();
        }
        if ($ReC.isStrBlk(actionName)) { actionName = '送信します'; }
        // form.
        var formName = $(this).attr('data-form-name');
        if ($ReC.isStrBlk(formName)) {
            formName = $(this).children('input[name="data_form_name"]').val();
        }
        // POST.
        if (!$ReC.isStrBlk(actionUrl)) {
            var form;
            if (!$ReC.isStrBlk(formName) && $(formName).length > 0) { form = $(formName)[0]; } 
            else { form = $(this).parents('form')[0]; }
            Commons.checkChangedInput(actionTitle, actionName, function() { Commons.post(form, actionUrl); });
        }
    });
};

/**
 * 画面遷移リンクタグ設定.
 */
Commons.setATagLink = function () {
    $('a.link').on('click', function (event) {
        event.preventDefault();
        var actionUrl = $(this).attr('href');
        if (!$ReC.isStrBlk(actionUrl)) {
            Commons.checkChangedInput('画面遷移', '画面を遷移します', function() { Commons.changeScreen(actionUrl); });
        }
        return false;
    });
};

/**
 * アコーディオンメニューを設定します.
 */
Commons.setAccordionMenu = function () {
    $('.accordion-menu-title').on('click', function(){
        Commons.ctrlAccordionMenu($(this));
    });
};

/**
 * アコーディオンメニューを制御します.
 * @param {object} target - 対象のJQueryオブジェクト.
 */
Commons.ctrlAccordionMenu = function (target) {
    if (target != null && target.length > 0) {
        target.next('.accordion-menu-inner').slideToggle(800);
        target.next('.accordion-menu-inner').toggleClass('anime');
        target.next('.accordion-menu-inner').toggleClass('anime-reverse');
        target.next('.accordion-menu-title').toggleClass('show');
        target.next('.accordion-menu-title').toggleClass('hide');
    }
};



//-------------------- ファイル処理.
/**
 * ファイルを取得します.
 * @param {object} target - 対象のfileタグ.
 * @return {object} ファイルオブジェクト.
 */
Commons.getFile = function (target) {
    if (target != null) {
        if ($ReC.isHTML(target)) {
            if (target.files.length > 0) {
                return target.files[0];
            }
        } else if (target.length > 0) {
            return Commons.getFile(target[0]);
        }
    }
    return null;
};

/**
 * ファイルをチェックします.
 * @param {object} args - ページング設定のパラメタ.
 * <pre>
 *  {object} file - 対象のfileタグ.
 *  {string} size - 対象ファイル最大値.
 *  {string[]} types - 対象ファイルのタイプ.
 * </pre>
 * @return {int} チェック結果
 *         (0:OK, 1:必須エラー, 2:サイズエラー, 3:タイプエラー, 9:その他エラー).
 */
Commons.checkFile = function (args) {
    if (args != null) {
        var file = args.file;
        // 必須チェック.
        if (file == null || $ReC.isStrBlk(file.name)) {
            return 1;
        }
        // サイズチェック.
        if (args.size == null) { file.size = 3145728; } // 3M.
        if (file.size === 0 || file.size > args.size) {
            return 2;
        }
        // タイプチェック.
        if (args.types == null) { args.types = ['image/gif', 'image/jpeg', 'image/png']; }
        if (args.types.length > 0) {
            var isHit = false;
            for (var i = 0, imax = args.types.length; i < imax; i++) {
                if (args.types[i] === file.type) {
                    isHit = true;
                    break;
                }
            }
            if (!isHit) {
                return 3;
            }
        }
        // OK.
        return 0;
    } 
    // NG.
    return 9;
};

/**
 * サムネイルを表示します.
 * @param {object} target - 対象のfileタグ.
 * @param {string} selector - セレクタ(imgタグ).
 */
Commons.thumbnail = function (target, selector) {
    var file = Commons.getFile(target);
    if (file != null && Commons.checkFile({file:file}) === 0) {
        var reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = function() {
            $(selector).show();
            $(selector).attr('src', reader.result);
            return;
        }
    }
    $(selector).hide();
};



//-------------------- HTMLエスケープ処理.
/**
 * HTMLエスケープ処理を行います.
 * @param {string} target - 対象文字列.
 * @return {string} 編集後の文字列.
 */
Commons.escapeHTML = function (target) {
    if (target === null) {
        return "";
    } else {
        return $('<span />').text(target).html();
    }
};

/**
 * HTMLエスケープ処理を行います.（改行有り）
 * @param {string} target - 対象文字列.
 * @return {string} 編集後の文字列.
 */
Commons.escapeHTMLCL = function (target) {
    if (target === null) {
        return "";
    } else {
        return $('<span />').text(target).html().replace(/\r?\n/g,'<br />');
    }
};

/**
 * HTMLアンエスケープ処理を行います.
 * @param {string} target - 対象文字列.
 * @return {string} 編集後の文字列.
 */
Commons.unescapeHTML = function (target) {
    if (target === null) {
        return "";
    } else {
        return $('<span />').html(target).text();
    }
};



//-------------------- その他処理.
/**
 * ページングの設定.
 * @param {object} args - ページング設定のパラメタ.
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
Commons.setPaging = function (args) {
    if (args != null 
            && args.list != null 
            && args.page != null 
            && args.totalPage != null) {

        var pagingList = $(args.list + ' ');
        if (pagingList.length > 0) {

            // 初期化.
            pagingList.html('');
            var page = $ReC.toInt($(args.page).val());
            var total_page = $ReC.toInt($(args.totalPage).val());
            var total_size = $ReC.toInt($(args.totalSize).val());
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
                $(args.list + ' li a.paging_prev').on('click', function (event) {
                    if (paging_prev_class === '') {
                        var selectPage = page - 1;
                        $(args.page).val(selectPage);
                        if ( args.event != null) { args.event(selectPage); }
                    } 
                });
                $(args.list + ' li a.paging_page').on('click', function (event) {
                    var selectPage = $ReC.toInt($(this).html());
                    if (selectPage !== page) {
                        $(args.page).val(selectPage);
                        if ( args.event != null) { args.event(selectPage); }
                    }
                });
                $(args.list + ' li a.paging_next').on('click', function (event) {
                    if (paging_next_class === '') {
                        var selectPage = page + 1;
                        $(args.page).val(selectPage);
                        if ( args.event != null) { args.event(selectPage); }
                    }
                });

                // Title.
                if (paginationTitle.length > 0) {
                    paginationTitle.show();
                    paginationTitle.find('.pagination-title-page').html(page);
                    paginationTitle.find('.pagination-title-total-page').html(total_page);
                    if ($(args.totalSize).length > 0) {
                        paginationTitle.find('.pagination-title-total-size').html(total_size);
                    }
                }
            }
        }
    }
};

/* end of file */