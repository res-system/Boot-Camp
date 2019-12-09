/**
 * @file biz_commons.js
 * @author res.
 * @version 1.0.1 (2019.11.15)
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
/** 処理モード[確認]. */
BizCommons.MODE_CONF = 'mode_conf';
/** 処理モード[確認(新規)]. */
BizCommons.MODE_CONFA = 'mode_conf_add';
/** 処理モード[確認(更新)]. */
BizCommons.MODE_CONFU = 'mode_conf_upd';
/** 処理モード[固定]. */
BizCommons.MODE_FIXED = 'mode_fixed';



//-------------------- 変数.



//-------------------- 設定処理.
/**
 * 画面項目初期設定.
 * @param {string} selector - セレクタ(対象の画面).
 */
BizCommons.refreshScreen = function (selector) {
      var target = '';
      if (selector) { target = selector + ' '; }
      // tooltip.
      $(target + '[data-toggle="tooltip"]').tooltip();
    };

/**
 * 画面項目初期設定.
 * @param {string} selector - セレクタ(対象のフォーム).
 */
BizCommons.initFormControl = function (selector) {
      var target = '';
      if (selector) { target = selector + ' '; }

      // チェックボックス.
      BizCommons.checkbox(target + 'input[type="checkbox"].form-control');
      // ラジオボタン.
      BizCommons.radio(target + 'input[type="radio"].form-control');
      // ドロップダウン.
      BizCommons.dropdownSelect(target + ' .dropdown-select');
      // 日付入力.
      BizCommons.setDatetimePicker(target);

      // フォーカス.
      $(target + 'input.form-control').focus(function (){ $(this).select(); });

      Commons.setFile(target);
      Commons.setATagLink(target);
      Commons.setPostLink(target);
      Commons.setAccordionMenu(target);
      Commons.setChangeInput(target);

      BizCommons.refreshScreen(target);
    };



//-------------------- 入力項目部品処理.
/**
 * ドロップダウンの設定を行います.
 * @param {string} selector - セレクタ.
 */
BizCommons.dropdownSelect = function (selector) {
      $(selector).each(function (i, elm) {
            //-- ドロップダウン設定. -- //
            // 表示文字列設定.
            function setText() {
              var dropdownText = $(elm).find('.dropdown-text');
              if (dropdownText.length > 0) {
                dropdownText.text('');
                dropdownText.prop('title', '');
                var selectedText = '';
                var delimiter = $(elm).find('.dropdown-delimiter').text();
                if (!delimiter) { delimiter = '/'; }
                else if (delimiter.length > 1) { delimiter = delimiter.substring(0, 1); }
                $(elm).find('input:checked').each(
                    function (i, elm) {
                      selectedText += delimiter + $(elm).next('.indicator').text();
                    });
                if (selectedText.length > 0) {
                  dropdownText.text(selectedText.substr(1));
                  dropdownText.prop('title', selectedText.substr(1));
                } else {
                  var placeholder = $(elm).find('.dropdown-placeholder');
                  if (placeholder.length > 0) {
                    dropdownText.text(placeholder.text());
                    dropdownText.prop('title', placeholder.text());
                  }
                }
              }
            }
            // 選択窓開く.
            function show() {
              var collapse = $(elm).find('.collapse');
              if (collapse.length > 0) { collapse.collapse('show'); }
            }
            // 選択窓閉じる.
            function hide() {
              var collapse = $(elm).find('.collapse');
              if (collapse.length > 0) { collapse.collapse('hide'); }
            }

            //-- ドロップダウン部品イベント. --//
            // 選択テキスト取得.
            elm.getSelectedText = function () {
                  var selectedText = '';
                  var delimiter = $(elm).find('.dropdown-delimiter').text();
                  if (!delimiter) { delimiter = '/'; }
                  else if (delimiter.length > 1) { delimiter = delimiter.substring(0, 1); }
                  $(elm).find('input:checked').each(
                      function (i, elm) {
                        selectedText += delimiter + $(elm).next('.indicator').text();
                      });
                  if (selectedText.length > 0) {
                    selectedText = selectedText.substr(1);
                  }
                  return selectedText;
                };
            // 変更時イベント.
            elm.change = function (event) {};
            // 子要素設定.
            elm.refresh = function (event) {
                  // 子要素設定.
                  $(elm).find('a,input,button').each(function (i, inputElm) { 
                        $(inputElm).addClass('dropdown-select-item');
                        $(inputElm).prop('tabindex', -1);
                      });
                  // 入力設定.
                  $(elm).find('input').each(function (i, inputElm) { 
                        $(inputElm).off('change.BizCommons_dropdownSelect');
                        $(inputElm).on( 'change.BizCommons_dropdownSelect', 
                            function (event) { 
                              if (elm.change) { elm.change(event); }
                              setText();
                            });
                        
                        inputElm.setDropdownText = setText;
                        inputElm.hideDropdown = hide;
                        inputElm.showDropdown = show;
                      });
                  BizCommons.checkbox($(elm).find('input[type="checkbox"].form-control'));  
                  BizCommons.radio(   $(elm).find('input[type="radio"].form-control'));  
                  setText();
                };
            // 表示文字列設定.
            elm.setDropdownText = setText;
            // 閉じる.
            elm.hideDropdown = hide;
            // 開く.
            elm.showDropdown = show;

            //-- ドロップダウン部品設定. --//

            // エンターキー押下.
            $(elm).off('keypress.BizCommons_dropdownSelect');
            $(elm).on( 'keypress.BizCommons_dropdownSelect', function (event) {
                  if (event.which !== 13) { return true; }
                  var collapse = $(elm).find('.collapse');
                  if (collapse.length > 0) {
                    collapse.collapse('toggle'); 
                    Commons.focus(collapse);
                  }
                  return true;
                });

            // 全選択設定.
            $(elm).find('.dropdown-select-all').off('click.BizCommons_dropdownSelect');
            $(elm).find('.dropdown-select-all').on( 'click.BizCommons_dropdownSelect', function (event) {
                  var checkBox = $(elm).find('input[type=checkbox].dropdown-select-item');
                  if (!checkBox.prop('disabled')) {
                    var value = !(checkBox.prop('checked'));
                    checkBox.prop('checked', value);
                  }
                  if (elm.change) { elm.change(event); }
                  setText();
                });

            // 初期設定.
            $(elm).prop('tabindex', 0);
            $(elm).off('focusout.BizCommons_dropdownSelect');
            $(elm).on( 'focusout.BizCommons_dropdownSelect', function (event) { 
                  setText(); 
                  if (!$(elm).hasClass('static')) { hide(); } 
                });
            elm.refresh();
            hide();

          });
    };

/**
 * チェックボックスの設定を行います.
 * @param {string} selector - セレクタ.
 */
BizCommons.checkbox = function (selector) {
      $(selector).each(function (i, elm) { 
            elm.setValue = function (value) {
                  if ($.isArray(value)) { 
                    $(this).val(value); 
                  } else { 
                    if (value != null) { $(this).val(value.split(',')); } else { $(this).val([]); } 
                  }
                  if ($(elm).hasClass('dropdown-select-item')) {
                    elm.setDropdownText();
                    elm.hideDropdown();
                  }
                };
          });
    };

/**
 * ラジオボタンの設定を行います.
 * @param {string} selector - セレクタ.
 */
BizCommons.radio = function (selector) {
      $(selector).each(function (i, elm) { 
            elm.setValue = function (value) { 
                  if ($.isArray(value)) { 
                    $(this).val(value); 
                  } else { 
                    if (value != null) { $(this).val(value.split(',')); } else { $(this).val([]); } 
                  }
                  if ($(elm).hasClass('dropdown-select-item')) {
                    elm.setDropdownText();
                    elm.hideDropdown();
                  }
                };
          });
    };



//-------------------- tempusdominus-datetimepicker処理.
/**
 * 日付入力設定処理.
 * @param {string} selector - セレクタ(対象のフォーム).
 */
BizCommons.setDatetimePicker = function (selector) {
      var target = '';
      if (selector) { target = selector + ' '; }

      // 日時入力.
      $(target + '.datetimepicker').datetimepicker({ viewMode: 'days', 
          dayViewHeaderFormat:'YYYY年 MMMM', buttons: { showToday: true, showClose: true } });
      $(target + '.datetimepicker .datetimepicker-input').off('change.BizCommons_setDatetimePicker');
      $(target + '.datetimepicker .datetimepicker-input').on( 'change.BizCommons_setDatetimePicker', function (event) {
            Commons.checkHalf(this);
            if (this.value.indexOf('/') === -1) {
              var value = $ReC.toInt(Commons.toHalfCharOnly(this.value));
              if (value) {
                var dt = new Date();
                var y = dt.getFullYear();
                if (value < 100) {
                  var m = ("00" + (dt.getMonth()+1)).slice(-2);
                  var d = ("00" + value).slice(-2);
                  this.value = y + "/" + m + "/" + d;
                } else if (value < 10000) {
                  var m = ("00" + Math.floor(value / 100)).slice(-2);
                  var d = ("00" + value % 100).slice(-2);
                  this.value = y + "/" + m + "/" + d;
                } else if (value < 1000000) {
                  y = "20" + ("00" + Math.floor(value / 10000)).slice(-2);
                  var m = ("00" + Math.floor((value % 10000) / 100)).slice(-2);
                  var d = ("00" + value % 100).slice(-2);
                  this.value = y + "/" + m + "/" + d;
                }
              }
            }
            return true;
          });
      $(target + '.datetimepicker').off('change.BizCommons_setDatetimePicker');
      $(target + '.datetimepicker').on( 'change.BizCommons_setDatetimePicker', function (event) { Commons.ChangeInputOn(); });

      // 日付入力.
      $(target + '.datepicker').datetimepicker({ viewMode: 'days', 
          dayViewHeaderFormat:'YYYY年 MMMM', format:'L', buttons: { showToday: true, showClose: true } });
      $(target + '.datepicker .datetimepicker-input').off('change.BizCommons_setDatetimePicker');
      $(target + '.datepicker .datetimepicker-input').on( 'change.BizCommons_setDatetimePicker', function (event) {
            Commons.checkHalf(this);
            if (this.value.indexOf('/') === -1) {
              var value = $ReC.toInt(Commons.toHalfCharOnly(this.value));
              if (value) {
                var dt = new Date();
                var y = dt.getFullYear();
                if (value < 100) {
                  var m = ("00" + (dt.getMonth()+1)).slice(-2);
                  var d = ("00" + value).slice(-2);
                  this.value = y + "/" + m + "/" + d;
                } else if (value < 10000) {
                  var m = ("00" + Math.floor(value / 100)).slice(-2);
                  var d = ("00" + value % 100).slice(-2);
                  this.value = y + "/" + m + "/" + d;
                } else if (value < 1000000) {
                  y = "20" + ("00" + Math.floor(value / 10000)).slice(-2);
                  var m = ("00" + Math.floor((value % 10000) / 100)).slice(-2);
                  var d = ("00" + value % 100).slice(-2);
                  this.value = y + "/" + m + "/" + d;
                }
              }
            }
            return true;
          });
      $(target + '.datepicker').off('change.BizCommons_setDatetimePicker');
      $(target + '.datepicker').on( 'change.BizCommons_setDatetimePicker', function (event) { Commons.ChangeInputOn(); });

      // 年月入力.
      $(target + '.monthpicker').datetimepicker({ viewMode: 'months', 
          format: 'YYYY/MM', buttons: { showToday: true, showClose: true } });
      $(target + '.monthpicker .datetimepicker-input').off('change.BizCommons_setDatetimePicker');
      $(target + '.monthpicker .datetimepicker-input').on( 'change.BizCommons_setDatetimePicker', function (event) {
            Commons.checkHalf(this);
            if (this.value.indexOf('/') === -1) {
              var value = $ReC.toInt(Commons.toHalfCharOnly(this.value));
              if (value) {
                var dt = new Date();
                var y = dt.getFullYear();
                if (value < 100) {
                  var m = ("00" + value).slice(-2);
                  this.value = y + "/" + m;
                } else if (value < 10000) {
                  var y = "20" + ("00" + Math.floor(value / 100)).slice(-2);
                  var m = ("00" + value % 100).slice(-2);
                  this.value = y + "/" + m;
                }
              }
            }
            return true;
          });
      $(target + '.monthpicker').off('change.BizCommons_setDatetimePicker');
      $(target + '.monthpicker').on( 'change.BizCommons_setDatetimePicker', function (event) { Commons.ChangeInputOn(); });

      // 年入力.
      $(target + '.yearpicker .datetimepicker-input').off('change.BizCommons_setDatetimePicker');
      $(target + '.yearpicker .datetimepicker-input').on( 'change.BizCommons_setDatetimePicker', function (event) {
            Commons.checkHalf(this);
            var value = $ReC.toInt(Commons.toHalfCharOnly(this.value));
            if (value) {
              this.value = value;
            } else {
              this.value = '';
            }
            return true;
          });
      $(target + '.yearpicker').off('change.BizCommons_setDatetimePicker');
      $(target + '.yearpicker').on( 'change.BizCommons_setDatetimePicker', function (event) { Commons.ChangeInputOn(); });

      // 時間入力.
      $(target + '.timepicker').datetimepicker({ format: 'LT', buttons: { showToday: false, showClose: true } });
      $(target + '.timepicker .datetimepicker-input').off('change.BizCommons_setDatetimePicker');
      $(target + '.timepicker .datetimepicker-input').on( 'change.BizCommons_setDatetimePicker', function (event) {
            Commons.checkHalf(this);
            if (this.value) {
              this.value = Commons.toNumeric(this.value);
            }
            return true;
          });
      $(target + '.timepicker').off('change.BizCommons_setDatetimePicker');
      $(target + '.timepicker').on( 'change.BizCommons_setDatetimePicker', function (event) { Commons.ChangeInputOn(); });
    };

/**
 * 月の加算を行います.
 * @param {jQuery} input - 対象月テキスト.
 * @param {number} add - 加算月数.
 */
BizCommons.addtMonth = function (input, add) {
        if (input.length > 0) {
          var date = $ReC.toDate(input.val() + '/01');
          if ($ReC.isDate(date) && add) {
            date.setMonth(date.getMonth() + add);
            input.val(date.getFullYear() + '/' + ('0' + (date.getMonth() + 1)).slice(-2));
          }
        }
    };

/**
 * 月選択ボタン押下処理を行います.
 * @param {jQuery} btn - 選択ボタン.
 * @param {number} add - 加算数.
 */
BizCommons.clickSelectMonthBtn = function (btn, add) {
        if (btn.length > 0) {
          var target = btn.parents('.select_month');
          BizCommons.addtMonth(target.find('.datetimepicker-input'), add);
        }
    };

/**
 * 年選択ボタン押下処理を行います.
 * @param {jQuery} btn - 選択ボタン.
 * @param {number} add - 加算数.
 */
BizCommons.clickSelectYearBtn = function (btn, add) {
        if (btn.length > 0) {
          var target = btn.parents('.select_year');
          var value = $ReC.toInt(Commons.toHalfCharOnly(target.find('.datetimepicker-input').val()));
          if (value) { target.find('.datetimepicker-input').val(value + add); }
        }
    };



/* end of file */