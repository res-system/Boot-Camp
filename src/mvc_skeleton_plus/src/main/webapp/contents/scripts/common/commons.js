/**
 * @file commons.js
 * @author res.
 * @version 1.5.3 (2019.11.15)
 * <pre>
 * 「JavaScript Standard Style」の規約に基本的に則りますが、以下の項目は変更します。
 *  ・文の最後は必ず「;」とする。
 *  ・見易くする為の1行以上の改行も容認する。
 *  ・処理の途中の改行の場合、インデントは2つ取る。
 * 「jQuery JavaScript Library v3.3.1」を使用しています。
 * 「com-res_system-commons.js @version 1.1.2」を前提に作成しています。
 * </pre>
 */
var Commons = { };

//-------------------- 定数.
/** ON. */
Commons.ON = '1';
/** OFF. */
Commons.OFF = '0';

/** ロックID. */
Commons.LOCKID = 'action-lock';
/** ロック画像. */
Commons.LOCKIMG = '/contents/images/commons/loading.gif';

/** メッセージ種別[情報]. */
Commons.MSG_INFO = 'I';
/** メッセージ種別[注意]. */
Commons.MSG_WORN = 'W';
/** メッセージ種別[警告]. */
Commons.MSG_ERROR = 'E';



//-------------------- 変数.
/** スクロール処理実行フラグ. */
Commons.isScrolling = false;
/** 入力内容変更フラグ. */
Commons.isChangedInput = false;
/** ロックフラグ. */
Commons.isLock = false;

/** 共通メッセージ. */
Commons.commonMessages = [];



//-------------------- イベント.
/** 画面ロード後のイベント. */
Commons.afterLoad = function () {};
/** 画面閉じる前のイベント. */
Commons.beforeClose = function (sendUrl) { return true; };



//-------------------- HTMLエスケープ処理.
/**
 * HTMLエスケープ処理を行います.
 * @param {string} target - 対象文字列.
 * @return {string} 編集後の文字列.
 */
Commons.escapeHTML = function (target) {
      if (target == null) {
        return ''
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
      if (target == null) {
        return ''
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
      if (target == null) {
        return '';
      } else {
        return $('<span />').html(target).text();
      }
    };



//-------------------- URL処理.
/**
 * 会話IDを設定する.
 * @param {string} [conversation_id] - cid.
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
 * @param {boolean} [is_cid] - cidの接続有無.
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
      if (Commons.isLock !== true) {
        var sendUrl = Commons.url(url, true);
        if (!$ReC.isStrBlk(sendUrl)) {
          Commons.closeScreen(function () {
                if (Commons.beforeClose(sendUrl)) {
                  Commons.lockScreen();
                  setTimeout(Commons.unlockScreen, 10000);
                  window.location.href = sendUrl; 
                }
              });
        }
      }
    };

/**
 * 画面遷移する(Lock無).
 * @param {string} url - URL.
 */
Commons.changeScreenNoLock = function (url) {
      if (Commons.isLock !== true) {
        var sendUrl = Commons.url(url, true);
        if (!$ReC.isStrBlk(sendUrl)) {
          if (Commons.beforeClose(sendUrl)) { 
            window.location.href = sendUrl; 
          }
        }
      }
    };


/**
 * POST(送信)する.
 * @param {string} selector - 対象formのセレクタ.
 * @param {string} url - URL.
 */
Commons.post = function (selector, url) {
      if (Commons.isLock !== true) {
        var sendUrl = Commons.url(url, true);
        if (!$ReC.isStrBlk(sendUrl)) {
          var form = $(selector);
          if (form.length > 0) {
            if (Commons.beforeClose(sendUrl)) { 
              form.prop('action', sendUrl);
              form.prop('method', 'post');
              Commons.lockScreen();
              setTimeout(Commons.unlockScreen, 10000);
              form.submit();
            }
          }
        }
      }
    };

/**
 * POST(送信)する(Lock無).
 * @param {string} selector - 対象formのセレクタ.
 * @param {string} url - URL.
 */
Commons.postNoLock = function (selector, url) {
      if (Commons.isLock !== true) {
        var sendUrl = Commons.url(url, true);
        if (!$ReC.isStrBlk(sendUrl)) {
          var form = $(selector);
          if (form.length > 0) {
            if (Commons.beforeClose(sendUrl)) { 
              form.prop('action', sendUrl);
              form.prop('method', 'post');
              form.submit();
            }
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
        if (Commons.isLock !== true) {
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
            var beforeSendFunc = function (xhr, settings) {
                  var beforeSendSubFunc = (args.beforeSend != null) 
                      ? args.beforeSend 
                      : function (xhr, settings) { return true; };
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
                  if (args.error != null) { 
                    args.error(xhr, status, errorThrown); 
                  } else {
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

          } // /sendUrl check.
        } // /Commons.isLock check.
      } // /args check.
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
        if (Commons.isLock !== true) {
          var sendUrl = Commons.url(args.url, true);
          if (!$ReC.isStrBlk(sendUrl)) {

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
                type: 'POST',
                data: formdata,
                timeout: timeout,
                cache: false,
                contentType: false,
                processData: false,
                dataType: 'html'
                })
                // 成功時.
                .done(function (result, status, xhr) {
                      if (isLock) { Commons.unlockScreen(); }
                      if (args.success != null) { args.success($ReC.parseJSON(result), status, xhr); }
                    })
                // 失敗時.
                .fail(function (xhr, status, errorThrown) {
                      if (isLock) { Commons.unlockScreen(); }
                      if (args.error != null) { 
                        args.error(xhr, status, errorThrown); 
                      } else {
                        var ajax_error_url = $('#ajax_error_url').val();
                        if ($ReC.isStrBlk(ajax_error_url)) { ajax_error_url = '/system_error'; }
                        Commons.changeScreen(ajax_error_url);
                      }
                    });

          } // /sendUrl check.
        } // /Commons.isLock check.
      } // /args check.
    };



//-------------------- ロック処理.
/**
 * アクションをロックします.
 */
Commons.lock = function () { Commons.isLock = true; };

/**
 * アクションのロックを解除します.
 */
Commons.unlock = function () { Commons.isLock = false; };


/**
 * 画面全体の入力項目無効化.
 */
Commons.lockInput = function () {
      $('body').find('button:not([tabindex=-1])').prop('disabled', true);
      $('body').find('select:not([tabindex=-1])').prop('disabled', true);
      $('body').find('textarea:not([tabindex=-1])').prop('disabled', true);
      $('body').find('input:not([type=hidden]):not([tabindex=-1])').prop('disabled', true);
    };

/**
 * 画面全体の入力項目活性化.
 */
Commons.unlockInput = function () {
      $('body').find('button:not([tabindex=-1])').prop('disabled', false);
      $('body').find('select:not([tabindex=-1])').prop('disabled', false);
      $('body').find('textarea:not([tabindex=-1])').prop('disabled', false);
      $('body').find('input:not([type=hidden]):not([tabindex=-1])').prop('disabled', false);
    };


/**
 * 画面操作をロックにする.
 * @param {boolean} isNoLockInput - 入力項目無効化有無.
 */
Commons.lockScreen = function (isNoLockInput) {
      Commons.lock();
      if (!isNoLockInput) { Commons.lockInput(); }
      var target = $('#' + Commons.LOCKID);
      if (target.length === 0) { 
        var divTag = $('<div />').prop('id', Commons.LOCKID);
        divTag.css('z-index', '9999')
            .css('position', 'fixed')
            .css('top', '0px').css('left', '0px')
            .css('height', '100%').css('width', '100%')
            .css('background-color', '#fafafa').css('opacity', '0.2');
        var divStyle = 'position:fixed;top:30%;left:50%;z-index:10000;';
        var imgUrl = Commons.url(Commons.LOCKIMG);
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
      Commons.unlock();
    };



//-------------------- 画面処理.
/**
 * 画面を開きます.
 * @param {function} [callback()] - callback処理.
 */
Commons.openScreen = function (callback) {
      $('.main_parts').css({'visibility':'visible'});
      $('#main_contents').css({'visibility':'visible'});
      Commons.showPageTopBottom();
      if (callback != null) { callback(); }
    };

/**
 * 画面を閉じます.
 * @param {function} [callback()] - callback処理.
 */
Commons.closeScreen = function (callback) {
      if (callback != null) { callback(); }
    };


/**
 * スクロールをリセットします.
 * @param {string} selector - 対象Tagのセレクタ.
 */
Commons.scrollReset = function (selector) {
      if (!$ReC.isStrBlk(selector) && '#main_contents' !== selector) {
        if ($(selector).length > 0) {
          $(selector).animate({scrollTop:0}, '1');
        }
      } else { Commons.scrollTop(); }
    };


/**
 * 画面TOPまでスクロールします.
 */
Commons.scrollTop = function () {
      if (!Commons.isScrolling) {
        Commons.isScrolling = true;
        $('html,body').animate({scrollTop:0}, 'fast',
            function () { Commons.isScrolling = false });
      }
    };

/**
 * 画面下までスクロールします.
 */
Commons.scrollBottom = function () {
      if (!Commons.isScrolling) {
        Commons.isScrolling = true;
        $('html,body').animate({scrollTop:document.body.scrollHeight}, 'fast',
            function () { Commons.isScrolling = false });
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
    };

/**
 * スクロールボタン表示.
 */
Commons.showPageTopBottom = function () {
      if (!$('body').hasClass('no-scroll') && (Commons.hasScrollBar('body') || Commons.hasScrollBar('html'))) {
        $('#to_page_top').fadeIn('fast');
        $('#to_page_bottom').fadeIn('fast');
        $('#to_page_top').off('click.Commons_showPageTopBottom');
        $('#to_page_top').on( 'click.Commons_showPageTopBottom', function () { Commons.scrollTop(); });
        $('#to_page_bottom').off('click.Commons_showPageTopBottom');
        $('#to_page_bottom').on( 'click.Commons_showPageTopBottom', function () { Commons.scrollBottom(); });
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
                  { name: listName + '[' + index + '].' + name, value: list[index][name] }));
          }
        }
        form.append($('<input type="hidden" />').attr({ name: listName + '_size', value: list.length }));
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
            $('.message-focus').off('click.Commons_showMessage');
            $('.message-focus').on( 'click.Commons_showMessage', function (e) {
                  var selector = $(this).attr('data-selector');
                  Commons.focus(selector);
                });

            // メッセージ表示.
            msgArea.fadeIn('fast');

            // 閉じるボタン設定.
            var msgClose = $(messageAreaSelector + ' button.close');
            if (msgClose.length > 0) {
              msgClose.off('click.Commons_showMessage');
              msgClose.on( 'click.Commons_showMessage', function (event) {
                    msgArea.fadeOut('fast');
                    msgBox.html('');
                  });
            }

          } // /msgBox check.
        } // /msgArea check.
      } // /msgData check.
    };

/**
 * メッセージにフォーカスを当てます.
 * @param {string} selector - セレクタ.
 */
Commons.focusMessages = function (selector) {
      // フォーカス処理.
      var messageAreaSelector = selector + ' div.message-box';
      if ($(messageAreaSelector + ' .message-focus:first').length > 0) {
        Commons.focus(messageAreaSelector + ' .message-focus:first');
      }
      // スクロールTOP.
      Commons.scrollReset(selector);
    };

/**
 * メッセージを表示します.
 * @param {string} selector - セレクタ.
 * @param {Array} msgDataList - メッセージデータリスト.
 */
Commons.showMessages = function (selector, msgDataList) {
      if (!$ReC.isAryBlk(msgDataList)) {
        // メッセージ表示.
        for (var i = 0, imax = msgDataList.length; i < imax; i++) {
          Commons.showMessage(selector, msgDataList[i]);
        }
        // フォーカス処理.
        Commons.focusMessages(selector);
      }
    };

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
          var sel = selector.trim() + '.form-control:not(.readonly)';
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



//-------------------- 項目処理.
/**
 * タブレット判定.
 */
Commons.isTablet = function () {
      if (navigator.userAgent.match(/(iPhone|iPad|iPod|Android)/i)) {
        // スマホ・タブレット（iOS・Android）の場合の処理を記述
        return true;
      } else {
        return false;
      }
    };

/**
 * フォーカス処理.
 * @param {string} selector - セレクタ.
 */
Commons.focus = function (selector) {
      var target = $(selector);
      if ($(selector).prop('tabindex') === -1) {
        if ($(selector).parents('div[tabindex=0]').length > 0) {
          target = $(selector).parents('div[tabindex=0]');
        }
      }
      if (target.length > 0) {
        if (Commons.isTablet()) {
          // スマホ・タブレット（iOS・Android）の場合の処理を記述
          if (target.prop('tagName') === 'INPUT' 
              || target.prop('tagName') === 'SELECT' 
              || target.prop('tagName') === 'TEXTAREA') {
            if (target.prev('label').length > 0) {
              target.prev('label').focus();
            }
          } else {
            $(target[0]).focus();
          }
        } else {
          // PCの場合の処理を記述
          $(target[0]).focus();
        }
      }
    };

/**
 * 最初の項目にフォーカスを当てます.
 * @param {string} selector - セレクタ.
 */
Commons.firstFocus = function (selector) {
      var target = selector + ' .form-control:visible:not([tabindex="-1"]):first';
      if ($(target).length > 0) {
        Commons.focus(target);
      } else {
        target = selector 
            + ' button:visible:not([tabindex="-1"]):first' 
            + ','
            + selector + ' a:visible:not([tabindex="-1"]):first';
        if ($(target).length > 0) {
          Commons.focus(target);
        }
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
        target.prop('disabled', value);
        if (value === true) {
          target.prop('tabindex', '-1');
          target.addClass('readonly');
          target.addClass('disabled');
        } else {
          target.prop('tabindex', '');
          target.removeClass('readonly');
          target.removeClass('disabled');
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
        $(list).each(function (i) {
              target.append($('<option />').val(list[i].value).text(list[i].text));
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



//-------------------- 入力内容変更フラグ処理.
/**
 * 入力内容変更設定.
 */
Commons.ChangeInputOn = function () { Commons.isChangedInput = true; };

/**
 * 入力内容変更設定初期化.
 */
Commons.resetChangeInput = function () { Commons.isChangedInput = false; };

/**
 * 入力内容変更時の確認.
 * @param {string} title - タイトル.
 * @param {string} msg - メッセージ.
 * @param {function} [yesFunc()] - 「はい」ボタン押下時処理.
 * @param {function} [noFunc()] - 「いいえ」ボタン押下時処理.
 */
Commons.checkChangedInput = function (title, msg, yesFunc, noFunc) {
      if (Commons.isChangedInput) {
        ModalConfirm.show({ 
            title: title, 
            msgData: {button: ModalConfirm.BTN_YES_NO, text: msg + '。\n入力内容が破棄されますがよろしいですか？'}, 
            btnYesFunc: function () { if (yesFunc) { yesFunc(); } }, 
            btnNoFunc: function () { if (noFunc) { noFunc(); } },
            focusButton: ModalConfirm.BTN_NO 
            });
      } else { 
        yesFunc();
      }
    };



//-------------------- 初期設定処理.
/**
 * スクロールボタン表示制御設定.
 */
Commons.setPageTopBottom = function () {
      $(window).off('load.Commons_setPageTopBottom resize.Commons_setPageTopBottom');
      $(window).on( 'load.Commons_setPageTopBottom resize.Commons_setPageTopBottom', function () { Commons.showPageTopBottom(); });
    };

/**
 * 入力内容変更設定.
 * @param {string} selector - セレクタ(対象のフォーム).
 */
Commons.setChangeInput = function (selector) {
      var target = '';
      if (selector) { target = selector + ' '; }
      $(target + '.form-control').off('change.Commons_setChangeInput');
      $(target + '.form-control').on( 'change.Commons_setChangeInput', function (event) { Commons.ChangeInputOn(); });
    };

/**
 * 画面遷移リンクタグ設定.
 * @param {string} selector - セレクタ(対象のフォーム).
 */
Commons.setATagLink = function (selector) {
      var target = '';
      if (selector) { target = selector + ' '; }
      $(target + '.link').off('click.Commons_setATagLink');
      $(target + '.link').on( 'click.Commons_setATagLink', function (event) {
            // URL.
            var actionUrl = $(this).attr('data-action');
            if ($ReC.isStrBlk(actionUrl)) {
              actionUrl = $(this).children('input[name="data_action"]').val();
            }
            if ($ReC.isStrBlk(actionUrl)) { actionUrl = $(this).attr('href'); }
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
            // Link.
            if (!$ReC.isStrBlk(actionUrl) && actionUrl !== 'javascript:void(0)') {
              Commons.checkChangedInput(actionTitle, actionName, function () { Commons.changeScreen(actionUrl); });
            }
            return false;
          });
    };

/**
 * POSTリンクタグ設定.
 * @param {string} selector - セレクタ(対象のフォーム).
 */
Commons.setPostLink = function (selector) {
      var target = '';
      if (selector) { target = selector + ' '; }
      $(target + '.postlink').off('click.Commons_setPostLink');
      $(target + '.postlink').on( 'click.Commons_setPostLink', function (event) {
            // URL.
            var actionUrl = $(this).attr('data-action');
            if ($ReC.isStrBlk(actionUrl)) {
              actionUrl = $(this).children('input[name="data_action"]').val();
            }
            if ($ReC.isStrBlk(actionUrl)) { actionUrl = $(this).attr('href'); }
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
            if (!$ReC.isStrBlk(actionUrl) && actionUrl !== 'javascript:void(0)') {
              var form;
              if (!$ReC.isStrBlk(formName) && $(formName).length > 0) { 
                form = $(formName)[0]; 
              } else { 
                form = $(this).parents('form')[0]; 
              }
              if (!$ReC.isStrBlk(actionName)) {
                ModalConfirm.show({ 
                      title: actionTitle, 
                      msgData: { button:ModalConfirm.BTN_YES_NO, text: actionName + '。\nよろしいですか？' }, 
                      btnYesFunc: function () { 
                            Commons.post(form, actionUrl); 
                          }, 
                      focusButton: ModalConfirm.BTN_NO
                    });
              } else {
                Commons.post(form, actionUrl);
              }
            }
            return false;
          });
    };

/**
 * アコーディオンメニューを設定します.
 * @param {string} selector - セレクタ(対象のフォーム).
 */
Commons.setAccordionMenu = function (selector) {
      var target = '';
      if (selector) { target = selector + ' '; }
      $(target + '.accordion-menu-title').off('click.Commons_setAccordionMenu');
      $(target + '.accordion-menu-title').on( 'click.Commons_setAccordionMenu', function (){
            Commons.ctrlAccordionMenu($(this));
          });
    };

/**
 * アコーディオンメニューを制御します.
 * @param {object} target - 対象のJQueryオブジェクト.
 */
Commons.ctrlAccordionMenu = function (target) {
      if (target != null && target.length > 0) {
        target.next('.accordion-menu-inner').slideToggle('fast');
        target.next('.accordion-menu-inner').toggleClass('anime');
        target.next('.accordion-menu-inner').toggleClass('anime-reverse');
        target.next('.accordion-menu-title').toggleClass('fast');
        target.next('.accordion-menu-title').toggleClass('hide');
      }
    };



//-------------------- ファイル処理.
/**
 * ファイルを設定します.
 * @param {string} selector - セレクタ(対象のフォーム).
 */
Commons.setFile = function (selector) {
      var target = '';
      if (selector) { target = selector + ' '; }
      $(target + 'input[type=file]').off('change.Commons_setFile');
      $(target + 'input[type=file]').on( 'change.Commons_setFile', function (){
            Commons.setFileName(this);
            var thumbnail = $(this).nextAll('img.thumbnail');
            if (thumbnail.length > 0) {
              Commons.thumbnail(this, thumbnail[0]);
            }
          });
    };

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
 *  {string} [size] - 対象ファイル最大値.
 *  {string[]} [types] - 対象ファイルのタイプ.
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
        if (file.size === 0 || file.size > args.size) { return 2; }
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
          if (!isHit) { return 3; }
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
        reader.onload = function () {
              $(selector).show();
              $(selector).prop('src', reader.result);
              return;
            };
      }
      $(selector).hide();
    };

/**
 * ファイル名の設定.
 * @param {object} target - 対象のfileタグ.
 * @return {int} チェック結果
 *         (0:OK, 1:必須エラー, 2:サイズエラー, 3:タイプエラー, 9:その他エラー).
 */
Commons.setFileName = function (target) {
      var file = Commons.getFile(target);
      if (file != null) {
        var result = Commons.checkFile({file:file});
        if (result === 0) {
          $('div.file label').addClass('changed');
          $('div.file .filename').html(file.name);
          return 0;
        } else {
          $('div.file label').removeClass('changed');
          $('div.file .filename').html('');
          $(target).val('');
          return result;
        }
      }
      return 1;
    };



//-------------------- テーブル処理.
/**
 * テーブル行をクリアします.
 * @param {string} selector - セレクタ(テーブルID).
 */
Commons.clearTableRow = function (selector) {
      var tbody = $(selector + ' tbody');
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
        listRow.off('click.Commons_setSelectTable');
        listRow.on( 'click.Commons_setSelectTable', function (event) {
              // 選択解除.
              Commons.clearSelectedRow($(args.tbl + ' tbody').find('tr'));
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
          hdRow.off('click.Commons_setSelectTable');
          hdRow.on( 'click.Commons_setSelectTable', function (e) {
                Commons.clearSelectedRow($(args.tbl + ' tbody').find('tr'));
              });
        }

      }
    };

/**
 * 行選択解除.
 * @param {HTML element} allTr - 全TRタグ.
 */
Commons.clearSelectedRow = function (allTr) {
      allTr.removeClass('selected-row');
      allTr.addClass('unselected-row');
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

/**
 * 選択グリッド項目を取得します.
 * @param {Object} event - イベント発生項目.
 * @return {number} 選択グリッド項目.
 */
Commons.getSelectedDivTd = function (event) {
      var eventTarget =  $(event.target);
      if (eventTarget.length > 0) {
        var row = eventTarget.parents('td div.row');
        var td;
        if (eventTarget.prop('tagName')==='DIV' && eventTarget.hasClass('td')) {
          td = eventTarget;
        } else {
          td = eventTarget.parents('div.td');
        }
        return row.find('div.td').index(td) + 1;
      }
      return 0;
    };



//-------------------- 入力制限処理.
/**
 * 値が無い場合、背景色をエラー色に変更します.
 * @param {string} target - 対象文字列.
 * @return {boolean} - チェック結果.
 */
Commons.checkRequired = function (target) {
      var obj = $(target);
      if (obj.length > 0) {
        var value = obj.val();
        if ($ReC.isStrBlk(value)) {
          obj.addClass('alert-danger');
          return false;
        } else if (obj.hasClass('alert-danger') 
            && !obj.hasClass('input-error')) {
          obj.removeClass('alert-danger');
        }
      }
      return true;
    };

/**
 * 半角文字以外を排除します.
 * @param {string} target - 対象文字列.
 * @return {boolean} - チェック結果.
 */
Commons.checkHalf = function (target) {
      var obj = $(target);
      if (obj.length > 0) {
        obj.val( Commons.toHalfCharOnly(obj.val()) );
      }
      return true;
    };

/**
 * 全角カナ文字以外を排除します.
 * @param {string} target - 対象文字列..
 * @return {boolean} - チェック結果.
 */
Commons.checkKana = function (target) {
      var obj = $(target);
      if (obj.length > 0) {
        obj.val( Commons.toKanaOnly(obj.val()) );
      }
      return true;
    };

/**
 * 電話番号以外を排除します.
 * @param {string} target - 対象文字列.
 * @return {boolean} - チェック結果.
 */
Commons.checkTel = function (target) {
      var obj = $(target);
      if (obj.length > 0) {
        var value = Commons.toNumeric(obj.val());
        if (value && !value.match(/^0\d{1,4}-*\d{1,4}-*\d{3,4}$/)) {
          obj.addClass('alert-danger');
          obj.addClass('input-error');
          return false;
        } else if (obj.hasClass('alert-danger')) {
          obj.removeClass('alert-danger');
          obj.removeClass('input-error');
        }
        obj.val(value);
      }
      return true;
    };

/**
 * 半角数値以外を排除します.
 * @param {string} target - 対象文字列.
 * @return {boolean} - チェック結果.
 */
Commons.checkNumeric = function (target) {
      var obj = $(target);
      if (obj.length > 0) {
        var value = Commons.toNumeric(obj.val());
        if (value && !value.match(/^[+-]?(?:\d+\.?\d*|\.\d+)$/)) {
          obj.addClass('alert-danger');
          obj.addClass('input-error');
          return false;
        } else if (obj.hasClass('alert-danger')) {
          obj.removeClass('alert-danger');
          obj.removeClass('input-error');
        }
        obj.val(value);
      }
      return true;
    };

/**
 * テキストエリアの入力チェックを行います.
 * @param {string} target - 対象文字列.
 * @return {boolean} - チェック結果.
 */
Commons.checkTextarea = function (target) {
      var obj = $(target);
      if (obj.length > 0) {
        var maxlength = $ReC.toInt(obj.attr('data-maxlength'));
        if (maxlength) {
          if (obj.val().length > maxlength){
            obj.val(obj.val().substr(0, maxlength));
            return false;
          }
        }
      }
      return true;
    };


/**
 * カンマ編集を行います.
 * @param {string} target - 対象文字列.
 */
Commons.blurMoney = function (target) {
      var obj = $(target);
      if (obj.length > 0) {
        obj.val(Commons.editComma(obj.val()));
      }
    };
/**
 * カンマを除去します.
 * @param {string} target - 対象文字列.
 */
Commons.focusMoney = function (target) {
      var obj = $(target);
      if (obj.length > 0) {
        obj.val(Commons.removeComma(obj.val()));
      }
    };



//-------------------- 変換処理.
/**
 * カンマ編集を行います.
 * @param {string} value - 対象文字列.
 * @return {string} - 返還後の文字列.
 */
Commons.editComma = function (value) {
      if (value) {
        value = value.replace(/(\d)(?=(\d\d\d)+$)/g, '$1,');
      }
      return value;
    };
/**
 * カンマを除去します.
 * @param {string} value - 対象文字列.
 * @return {string} - 返還後の文字列.
 */
Commons.removeComma = function (value) {
      if (value) {
        value = value.replace(/,|\\|\s/g, '');
      }
      return value;
    };

/**
 * 先頭と末尾の空白と改行を削除します.
 * @param {string} value - 対象文字列.
 * @return {string} - 返還後の文字列.
 */
Commons.trim = function (value) {
      if (value) {
        return (value + '').replace(/^[\s　]*/, '').replace(/[\s　]*$/, '');
      } else { return ''; }
    };

/**
 * 空白と改行を削除します.
 * @param {string} value - 対象文字列.
 * @return {string} - 返還後の文字列.
 */
Commons.delSpace = function (value) {
      if (value) {
        return (value + '').replace(/[\s　]+/g, '');
      } else { return ''; }
    };


/**
 * 半角を全角に変換します.
 * @param {string} value - 対象文字列.
 * @return {string} - 返還後の文字列.
 */
Commons.toDbyteChar = function (value) {
      if (value) {
        return (value + '').replace(/[!-~]/g, 
            function (s) { return String.fromCharCode(s.charCodeAt(0) + 0xFEE0); });
      } else { return ''; }
    };


/**
 * 全角を半角のみに変換します.
 * @param {string} value - 対象文字列.
 * @return {string} - 返還後の文字列.
 */
Commons.toHalfCharOnly = function (value) {
      value = Commons.toHalfChar(value);
      value = Commons.delDbyteChar(value);
      return value;
    };
/**
 * 全角を半角に変換します.
 * @param {string} value - 対象文字列.
 * @return {string} - 返還後の文字列.
 */
Commons.toHalfChar = function (value) {
      if (value) {
        return (value + '').replace(/[！-～]/g, 
            function (s) { return String.fromCharCode(s.charCodeAt(0) - 0xFEE0); });
      } else { return ''; }
    };
/**
 * 全角を削除します.
 * @param {string} value - 対象文字列.
 * @return {string} - 返還後の文字列.
 */
Commons.delDbyteChar = function (value) {
      if (value) {
        return (value + '').replace(/[^!-~\s]/g, '');
      } else { return ''; }
    };


/**
 * 全角カタカナのみに変換します.
 * @param {string} value - 対象文字列.
 * @return {string} - 返還後の文字列.
 */
Commons.toKanaOnly = function (value) {
      if (value) {
        return Commons.toKana(value).replace(/[^ァ-ン０-９ー\s]+/g, '');
      } else { return ''; }
    };
/**
 * 全角カタカナに変換します.
 * @param {string} value - 対象文字列.
 * @return {string} - 返還後の文字列.
 */
Commons.toKana = function (value) {
      if (value) {
        return (value + '').replace(/[ぁ-ん]/g, 
            function (s) { return String.fromCharCode(s.charCodeAt(0) + 0x60); });
      } else { return ''; }
    };



/**
 * 数値として使用される文字にする.
 * @param {string} value - 対象文字列.
 * @return {string} - 返還後の文字列.
 */
Commons.toNumeric = function (value) {
      value = Commons.delSpace(value);
      value = Commons.toHalfCharOnly(value);
      if (value) {
        return value.replace(/[^-.0-9]/g, '');
      } else { return ''; }
    };



//-------------------- 日付処理.
/**
 * 現在日付を取得します.
 * @return {string} 現在日付(yyyy/MM/dd).
 */
Commons.nowStr = function () {
      var dt = new Date();
      var y = dt.getFullYear();
      var m = ("00" + (dt.getMonth()+1)).slice(-2);
      var d = ("00" + dt.getDate()).slice(-2);
      return y + "/" + m + "/" + d;
    };

/**
 * 曜日を取得します.
 * @return {string} 曜日.
 */
Commons.getYobi = function (date) {
      var result = '';
      if (!$ReC.isStrBlk(date) && date.length >= 10) {
        var dt = new Date(
            $ReC.toInt(date.substr(0, 4), 2000), 
            $ReC.toInt(date.substr(5, 2), 0) -1, 
            $ReC.toInt(date.substr(8, 2), 1));
        result = '日月火水木金土'[dt.getDay()];
      }
      return result;
    };

/**
 * 日付をフォーマットします.
 * @param {object} date - 対象の日付.
 * @param {string} format - フォーマット文字列.
 * @return {string} 編集後の日付.
 */
Commons.formatDate = function (date, format) {
      var target_date = null;
      if ($ReC.isDate(date)) {
        target_date = date;
      } else {
        target_date = new Date(date);
      }
      if ($ReC.isDate(target_date)) {
        var format_str = '';
        if (!$ReC.isStrBlk(format)) {
          format_str = format;
        } else {
          format_str = 'yyyy/MM/dd';
        }
        format_str = format_str.replace(/yyyy/g, date.getFullYear());
        format_str = format_str.replace(/MM/g, ('00' + (date.getMonth() + 1)).slice( -2 ));
        format_str = format_str.replace(/dd/g, ('00' + date.getDate()).slice( -2 ));
        format_str = format_str.replace(/HH/g, ('00' + date.getHours()).slice( -2 ));
        format_str = format_str.replace(/mm/g, ('00' + date.getMinutes()).slice( -2 ));
        format_str = format_str.replace(/ss/g, ('00' + date.getSeconds()).slice( -2 ));
        return format_str;
      }
      return '';
    };



//-------------------- ダイアログ設定処理.
/**
 * ダイアログ基本イベント設定を行います.
 * @param {string} targetMain - セレクタ(対象ダイアログ).
 * @param {function} showFunc() - [ダイアログ 表示前処理Function].
 * @param {function} shownFunc() - [ダイアログ 表示時処理Function].
 * @param {function} hideFunc() - [ダイアログ 閉じる前処理Function].
 * @param {function} hiddenFunc() - [ダイアログ 閉じた後処理Function].
 */
Commons.setModalEvent = function (targetMain, showFunc, shownFunc, hideFunc, hiddenFunc) {
      //-- ダイアログ 表示前処理. --//
      $(targetMain).off('show.bs.modal');
      $(targetMain).on( 'show.bs.modal', function (event) {
            if (event.namespace === 'bs.modal') { 
              $(targetMain).data('is_open', true);
              $(targetMain).data('is_init', true);
              if (showFunc) { showFunc(); }
            }
          });
      //-- ダイアログ 表示時処理. --//
      $(targetMain).off('shown.bs.modal');
      $(targetMain).on( 'shown.bs.modal', function (event) {
            if ($(targetMain).data('is_open') === false) {
              $(targetMain).modal('hide');
            } else {
              $(targetMain).animate({ scrollTop: 0 }, 'fast');
              $(targetMain).data('is_init', false);
              if (shownFunc) { shownFunc(); }
            }
          });
      //-- ダイアログ 閉じる前処理. --//
      $(targetMain).off('hide.bs.modal');
      $(targetMain).on( 'hide.bs.modal', function (event) {
            if ($(targetMain).data('is_open') === true) {
              if (Commons.isChangedInput) {
                Commons.checkChangedInput('閉じる', 'ダイアログを閉じます', 
                    function () { 
                      if (hideFunc) { 
                        if (hideFunc()) {
                          Commons.resetChangeInput();
                          $(targetMain).data('is_open', false);
                          $(targetMain).modal('hide'); 
                        } 
                      } else {
                        Commons.resetChangeInput();
                        $(targetMain).data('is_open', false);
                        $(targetMain).modal('hide'); 
                      }
                    });
                return false;
              } else {
                if (hideFunc) { 
                  if (hideFunc()) {
                    Commons.resetChangeInput();
                    $(targetMain).data('is_open', false);
                    $(targetMain).modal('hide'); 
                  } 
                } else {
                  Commons.resetChangeInput();
                  $(targetMain).data('is_open', false);
                  $(targetMain).modal('hide'); 
                }
                return false;
              }
            } else {
              return true;
            }
          });
      //-- ダイアログ 閉じた後処理. --//
      $(targetMain).off('hidden.bs.modal');
      $(targetMain).on( 'hidden.bs.modal', function (event) {
            $('body').addClass('modal-open');
            $(targetMain).data('is_open', false);
            $(targetMain).data('is_init', false);
            if (hiddenFunc) { hiddenFunc(); }
          });

      //-- ダイアログ TOPへスクロール処理. --//
      $(targetMain + ' .btn_scroll_top').off('click.Commons_setModalEvent');
      $(targetMain + ' .btn_scroll_top').on( 'click.Commons_setModalEvent', function (event) {
            $(targetMain).animate({ scrollTop: 0 }, 'fast');
          });
    };

/**
 * ダイアログ初期化中判定.
 * @param {string} targetMain - セレクタ(対象ダイアログ).
 * @return {string} 判定結果.
 */
Commons.isInitModal = function (targetMain) {
      if ($(targetMain).data('is_init') === true) {
        return true;
      } else {
        return false;
      }
    };

/**
 * ダイアログを閉じます。
 * @param {string} targetMain - セレクタ(対象ダイアログ).
 */
Commons.hideModal = function (targetMain) {
      $(targetMain).data('is_open', false);
      $(targetMain).modal('hide');
    };



/* end of file */