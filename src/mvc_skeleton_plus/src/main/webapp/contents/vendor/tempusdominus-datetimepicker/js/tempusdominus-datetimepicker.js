/*@preserve
 * Tempus Dominus DateTime Picker v5.0.1 (https://tempusdominus.github.io/datetimepicker)
 * Copyright 2016-2018 Jonathan Peterson
 * Licensed under MIT (https://github.com/tempusdominus/datetimepicker/blob/master/LICENSE)
 */

if (typeof jQuery === 'undefined') {
  throw new Error('Tempus Dominus Date Time Picker\'s requires jQuery. jQuery must be included before Tempus Dominus Date Time Picker\'s JavaScript.');
}

+function ($) {
  var version = $.fn.jquery.split(' ')[0].split('.');
  if ((version[0] < 2 && version[1] < 9) || (version[0] === 1 && version[1] === 9 && version[2] < 1) || (version[0] >= 4)) {
    throw new Error('Tempus Dominus Date Time Picker\'s requires at least jQuery v3.0.0 but less than v4.0.0');
  }
}(jQuery);


if (typeof moment === 'undefined') {
  throw new Error('Tempus Dominus Date Time Picker\'s requires moment.js. Moment.js must be included before Tempus Dominus Date Time Picker\'s JavaScript.');
}

var version = moment.version.split('.')
if ((version[0] <= 2 && version[1] < 17) || (version[0] >= 3)) {
  throw new Error('Tempus Dominus Date Time Picker\'s requires at least moment.js v2.17.0 but less than v3.0.0');
}

+function () {

"use strict";

function _typeof(obj) { if (typeof Symbol === "function" && typeof Symbol.iterator === "symbol") { _typeof = function _typeof(obj) { return typeof obj; }; } else { _typeof = function _typeof(obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; }; } return _typeof(obj); }

function _possibleConstructorReturn(self, call) { if (call && (_typeof(call) === "object" || typeof call === "function")) { return call; } return _assertThisInitialized(self); }

function _assertThisInitialized(self) { if (self === void 0) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return self; }

function _getPrototypeOf(o) { _getPrototypeOf = Object.setPrototypeOf ? Object.getPrototypeOf : function _getPrototypeOf(o) { return o.__proto__ || Object.getPrototypeOf(o); }; return _getPrototypeOf(o); }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function"); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, writable: true, configurable: true } }); if (superClass) _setPrototypeOf(subClass, superClass); }

function _setPrototypeOf(o, p) { _setPrototypeOf = Object.setPrototypeOf || function _setPrototypeOf(o, p) { o.__proto__ = p; return o; }; return _setPrototypeOf(o, p); }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } }

function _createClass(Constructor, protoProps, staticProps) { if (protoProps) _defineProperties(Constructor.prototype, protoProps); if (staticProps) _defineProperties(Constructor, staticProps); return Constructor; }

// ReSharper disable once InconsistentNaming
var DateTimePicker = function ($, moment) {
  // ReSharper disable InconsistentNaming
  var NAME = 'datetimepicker',
      DATA_KEY = "".concat(NAME),
      EVENT_KEY = ".".concat(DATA_KEY),
      DATA_API_KEY = '.data-api',
      Selector = {
    DATA_TOGGLE: "[data-toggle=\"".concat(DATA_KEY, "\"]")
  },
      ClassName = {
    INPUT: "".concat(NAME, "-input")
  },
      Event = {
    CHANGE: "change".concat(EVENT_KEY),
    BLUR: "blur".concat(EVENT_KEY),
    KEYUP: "keyup".concat(EVENT_KEY),
    KEYDOWN: "keydown".concat(EVENT_KEY),
    FOCUS: "focus".concat(EVENT_KEY),
    CLICK_DATA_API: "click".concat(EVENT_KEY).concat(DATA_API_KEY),
    //emitted
    UPDATE: "update".concat(EVENT_KEY),
    ERROR: "error".concat(EVENT_KEY),
    HIDE: "hide".concat(EVENT_KEY),
    SHOW: "show".concat(EVENT_KEY)
  },
      DatePickerModes = [{
    CLASS_NAME: 'days',
    NAV_FUNCTION: 'M',
    NAV_STEP: 1
  }, {
    CLASS_NAME: 'months',
    NAV_FUNCTION: 'y',
    NAV_STEP: 1
  }, {
    CLASS_NAME: 'years',
    NAV_FUNCTION: 'y',
    NAV_STEP: 10
  }, {
    CLASS_NAME: 'decades',
    NAV_FUNCTION: 'y',
    NAV_STEP: 100
  }],
      KeyMap = {
    'up': 38,
    38: 'up',
    'down': 40,
    40: 'down',
    'left': 37,
    37: 'left',
    'right': 39,
    39: 'right',
    'tab': 9,
    9: 'tab',
    'escape': 27,
    27: 'escape',
    'enter': 13,
    13: 'enter',
    'pageUp': 33,
    33: 'pageUp',
    'pageDown': 34,
    34: 'pageDown',
    'shift': 16,
    16: 'shift',
    'control': 17,
    17: 'control',
    'space': 32,
    32: 'space',
    't': 84,
    84: 't',
    'delete': 46,
    46: 'delete'
  },
      ViewModes = ['times', 'days', 'months', 'years', 'decades'],
      keyState = {},
      keyPressHandled = {};
  var MinViewModeNumber = 0,
      Default = {
    timeZone: '',
    format: false,
    dayViewHeaderFormat: 'MMMM YYYY',
    extraFormats: false,
    stepping: 1,
    minDate: false,
    maxDate: false,
    useCurrent: true,
    collapse: true,
    locale: moment.locale(),
    defaultDate: false,
    disabledDates: false,
    enabledDates: false,
    icons: {
      time: 'fa fa-clock-o',
      date: 'fa fa-calendar',
      up: 'fa fa-arrow-up',
      down: 'fa fa-arrow-down',
      previous: 'fa fa-chevron-left',
      next: 'fa fa-chevron-right',
      today: 'fa fa-calendar-check-o',
      clear: 'fa fa-delete',
      close: 'fa fa-times'
    },
    tooltips: {
      today: 'Go to today',
      clear: 'Clear selection',
      close: 'Close the picker',
      selectMonth: 'Select Month',
      prevMonth: 'Previous Month',
      nextMonth: 'Next Month',
      selectYear: 'Select Year',
      prevYear: 'Previous Year',
      nextYear: 'Next Year',
      selectDecade: 'Select Decade',
      prevDecade: 'Previous Decade',
      nextDecade: 'Next Decade',
      prevCentury: 'Previous Century',
      nextCentury: 'Next Century',
      pickHour: 'Pick Hour',
      incrementHour: 'Increment Hour',
      decrementHour: 'Decrement Hour',
      pickMinute: 'Pick Minute',
      incrementMinute: 'Increment Minute',
      decrementMinute: 'Decrement Minute',
      pickSecond: 'Pick Second',
      incrementSecond: 'Increment Second',
      decrementSecond: 'Decrement Second',
      togglePeriod: 'Toggle Period',
      selectTime: 'Select Time',
      selectDate: 'Select Date'
    },
    useStrict: false,
    sideBySide: false,
    daysOfWeekDisabled: false,
    calendarWeeks: false,
    viewMode: 'days',
    toolbarPlacement: 'default',
    buttons: {
      showToday: false,
      showClear: false,
      showClose: false
    },
    widgetPositioning: 'auto',
    widgetParent: null,
    ignoreReadonly: false,
    keepOpen: false,
    focusOnShow: true,
    inline: false,
    keepInvalid: false,
    keyBinds: {
      up: function up() {
        if (!this.widget) {
          return false;
        }

        var d = this._dates[0] || this.getMoment();

        if (this.widget.find('.datepicker').is(':visible')) {
          this.date(d.clone().subtract(7, 'd'));
        } else {
          this.date(d.clone().add(this.stepping(), 'm'));
        }

        return true;
      },
      down: function down() {
        if (!this.widget) {
          this.show();
          return false;
        }

        var d = this._dates[0] || this.getMoment();

        if (this.widget.find('.datepicker').is(':visible')) {
          this.date(d.clone().add(7, 'd'));
        } else {
          this.date(d.clone().subtract(this.stepping(), 'm'));
        }

        return true;
      },
      'control up': function controlUp() {
        if (!this.widget) {
          return false;
        }

        var d = this._dates[0] || this.getMoment();

        if (this.widget.find('.datepicker').is(':visible')) {
          this.date(d.clone().subtract(1, 'y'));
        } else {
          this.date(d.clone().add(1, 'h'));
        }

        return true;
      },
      'control down': function controlDown() {
        if (!this.widget) {
          return false;
        }

        var d = this._dates[0] || this.getMoment();

        if (this.widget.find('.datepicker').is(':visible')) {
          this.date(d.clone().add(1, 'y'));
        } else {
          this.date(d.clone().subtract(1, 'h'));
        }

        return true;
      },
      left: function left() {
        if (!this.widget) {
          return false;
        }

        var d = this._dates[0] || this.getMoment();

        if (this.widget.find('.datepicker').is(':visible')) {
          this.date(d.clone().subtract(1, 'd'));
        }

        return true;
      },
      right: function right() {
        if (!this.widget) {
          return false;
        }

        var d = this._dates[0] || this.getMoment();

        if (this.widget.find('.datepicker').is(':visible')) {
          this.date(d.clone().add(1, 'd'));
        }

        return true;
      },
      pageUp: function pageUp() {
        if (!this.widget) {
          return false;
        }

        var d = this._dates[0] || this.getMoment();

        if (this.widget.find('.datepicker').is(':visible')) {
          this.date(d.clone().subtract(1, 'M'));
        }

        return true;
      },
      pageDown: function pageDown() {
        if (!this.widget) {
          return false;
        }

        var d = this._dates[0] || this.getMoment();

        if (this.widget.find('.datepicker').is(':visible')) {
          this.date(d.clone().add(1, 'M'));
        }

        return true;
      },
      enter: function enter() {
        if (!this.widget) {
          return false;
        }

        this.hide();
        return true;
      },
      escape: function escape() {
        if (!this.widget) {
          return false;
        }

        this.hide();
        return true;
      },
      'control space': function controlSpace() {
        if (!this.widget) {
          return false;
        }

        if (this.widget.find('.timepicker').is(':visible')) {
          this.widget.find('.btn[data-action="togglePeriod"]').click();
        }

        return true;
      },
      t: function t() {
        if (!this.widget) {
          return false;
        }

        this.date(this.getMoment());
        return true;
      },
      'delete': function _delete() {
        if (!this.widget) {
          return false;
        }

        this.clear();
        return true;
      }
    },
    debug: false,
    allowInputToggle: false,
    disabledTimeIntervals: false,
    disabledHours: false,
    enabledHours: false,
    viewDate: false,
    allowMultidate: false,
    multidateSeparator: ','
  }; // ReSharper restore InconsistentNaming
  // ReSharper disable once DeclarationHides
  // ReSharper disable once InconsistentNaming

  var DateTimePicker =
  /*#__PURE__*/
  function () {
    /** @namespace eData.dateOptions */

    /** @namespace moment.tz */
    function DateTimePicker(element, options) {
      _classCallCheck(this, DateTimePicker);

      this._options = this._getOptions(options);
      this._element = element;
      this._dates = [];
      this._datesFormatted = [];
      this._viewDate = null;
      this.unset = true;
      this.component = false;
      this.widget = false;
      this.use24Hours = null;
      this.actualFormat = null;
      this.parseFormats = null;
      this.currentViewMode = null;

      this._int();
    }
    /**
     * @return {string}
     */


    _createClass(DateTimePicker, [{
      key: "_int",
      //private
      value: function _int() {
        var targetInput = this._element.data('target-input');

        if (this._element.is('input')) {
          this.input = this._element;
        } else if (targetInput !== undefined) {
          if (targetInput === 'nearest') {
            this.input = this._element.find('input');
          } else {
            this.input = $(targetInput);
          }
        }

        this._dates = [];
        this._dates[0] = this.getMoment();
        this._viewDate = this.getMoment().clone();
        $.extend(true, this._options, this._dataToOptions());
        this.options(this._options);

        this._initFormatting();

        if (this.input !== undefined && this.input.is('input') && this.input.val().trim().length !== 0) {
          this._setValue(this._parseInputDate(this.input.val().trim()), 0);
        } else if (this._options.defaultDate && this.input !== undefined && this.input.attr('placeholder') === undefined) {
          this._setValue(this._options.defaultDate, 0);
        }

        if (this._options.inline) {
          this.show();
        }
      }
    }, {
      key: "_update",
      value: function _update() {
        if (!this.widget) {
          return;
        }

        this._fillDate();

        this._fillTime();
      }
    }, {
      key: "_setValue",
      value: function _setValue(targetMoment, index) {
        var oldDate = this.unset ? null : this._dates[index];
        var outpValue = ''; // case of calling setValue(null or false)

        if (!targetMoment) {
          if (!this._options.allowMultidate || this._dates.length === 1) {
            this.unset = true;
            this._dates = [];
            this._datesFormatted = [];
          } else {
            outpValue = "".concat(this._element.data('date'), ",");
            outpValue = outpValue.replace("".concat(oldDate.format(this.actualFormat), ","), '').replace(',,', '').replace(/,\s*$/, '');

            this._dates.splice(index, 1);

            this._datesFormatted.splice(index, 1);
          }

          if (this.input !== undefined) {
            this.input.val(outpValue);
            this.input.trigger('input');
          }

          this._element.data('date', outpValue);

          this._notifyEvent({
            type: DateTimePicker.Event.CHANGE,
            date: false,
            oldDate: oldDate
          });

          this._update();

          return;
        }

        targetMoment = targetMoment.clone().locale(this._options.locale);

        if (this._hasTimeZone()) {
          targetMoment.tz(this._options.timeZone);
        }

        if (this._options.stepping !== 1) {
          targetMoment.minutes(Math.round(targetMoment.minutes() / this._options.stepping) * this._options.stepping).seconds(0);
        }

        if (this._isValid(targetMoment)) {
          this._dates[index] = targetMoment;
          this._datesFormatted[index] = targetMoment.format('YYYY-MM-DD');
          this._viewDate = targetMoment.clone();

          if (this._options.allowMultidate && this._dates.length > 1) {
            for (var i = 0; i < this._dates.length; i++) {
              outpValue += "".concat(this._dates[i].format(this.actualFormat)).concat(this._options.multidateSeparator);
            }

            outpValue = outpValue.replace(/,\s*$/, '');
          } else {
            outpValue = this._dates[index].format(this.actualFormat);
          }

          if (this.input !== undefined) {
            this.input.val(outpValue);
            this.input.trigger('input');
          }

          this._element.data('date', outpValue);

          this.unset = false;

          this._update();

          this._notifyEvent({
            type: DateTimePicker.Event.CHANGE,
            date: this._dates[index].clone(),
            oldDate: oldDate
          });
        } else {
          if (!this._options.keepInvalid) {
            if (this.input !== undefined) {
              this.input.val("".concat(this.unset ? '' : this._dates[index].format(this.actualFormat)));
              this.input.trigger('input');
            }
          } else {
            this._notifyEvent({
              type: DateTimePicker.Event.CHANGE,
              date: targetMoment,
              oldDate: oldDate
            });
          }

          this._notifyEvent({
            type: DateTimePicker.Event.ERROR,
            date: targetMoment,
            oldDate: oldDate
          });
        }
      }
    }, {
      key: "_change",
      value: function _change(e) {
        var val = $(e.target).val().trim(),
            parsedDate = val ? this._parseInputDate(val) : null;

        this._setValue(parsedDate);

        e.stopImmediatePropagation();
        return false;
      } //noinspection JSMethodCanBeStatic

    }, {
      key: "_getOptions",
      value: function _getOptions(options) {
        options = $.extend(true, {}, Default, options);
        return options;
      }
    }, {
      key: "_hasTimeZone",
      value: function _hasTimeZone() {
        return moment.tz !== undefined && this._options.timeZone !== undefined && this._options.timeZone !== null && this._options.timeZone !== '';
      }
    }, {
      key: "_isEnabled",
      value: function _isEnabled(granularity) {
        if (typeof granularity !== 'string' || granularity.length > 1) {
          throw new TypeError('isEnabled expects a single character string parameter');
        }

        switch (granularity) {
          case 'y':
            return this.actualFormat.indexOf('Y') !== -1;

          case 'M':
            return this.actualFormat.indexOf('M') !== -1;

          case 'd':
            return this.actualFormat.toLowerCase().indexOf('d') !== -1;

          case 'h':
          case 'H':
            return this.actualFormat.toLowerCase().indexOf('h') !== -1;

          case 'm':
            return this.actualFormat.indexOf('m') !== -1;

          case 's':
            return this.actualFormat.indexOf('s') !== -1;

          case 'a':
          case 'A':
            return this.actualFormat.toLowerCase().indexOf('a') !== -1;

          default:
            return false;
        }
      }
    }, {
      key: "_hasTime",
      value: function _hasTime() {
        return this._isEnabled('h') || this._isEnabled('m') || this._isEnabled('s');
      }
    }, {
      key: "_hasDate",
      value: function _hasDate() {
        return this._isEnabled('y') || this._isEnabled('M') || this._isEnabled('d');
      }
    }, {
      key: "_dataToOptions",
      value: function _dataToOptions() {
        var eData = this._element.data();

        var dataOptions = {};

        if (eData.dateOptions && eData.dateOptions instanceof Object) {
          dataOptions = $.extend(true, dataOptions, eData.dateOptions);
        }

        $.each(this._options, function (key) {
          var attributeName = "date".concat(key.charAt(0).toUpperCase()).concat(key.slice(1)); //todo data api key

          if (eData[attributeName] !== undefined) {
            dataOptions[key] = eData[attributeName];
          } else {
            delete dataOptions[key];
          }
        });
        return dataOptions;
      }
    }, {
      key: "_notifyEvent",
      value: function _notifyEvent(e) {
        if (e.type === DateTimePicker.Event.CHANGE && e.date && e.date.isSame(e.oldDate) || !e.date && !e.oldDate) {
          return;
        }

        this._element.trigger(e);
      }
    }, {
      key: "_viewUpdate",
      value: function _viewUpdate(e) {
        if (e === 'y') {
          e = 'YYYY';
        }

        this._notifyEvent({
          type: DateTimePicker.Event.UPDATE,
          change: e,
          viewDate: this._viewDate.clone()
        });
      }
    }, {
      key: "_showMode",
      value: function _showMode(dir) {
        if (!this.widget) {
          return;
        }

        if (dir) {
          this.currentViewMode = Math.max(MinViewModeNumber, Math.min(3, this.currentViewMode + dir));
        }

        this.widget.find('.datepicker > div').hide().filter(".datepicker-".concat(DatePickerModes[this.currentViewMode].CLASS_NAME)).show();
      }
    }, {
      key: "_isInDisabledDates",
      value: function _isInDisabledDates(testDate) {
        return this._options.disabledDates[testDate.format('YYYY-MM-DD')] === true;
      }
    }, {
      key: "_isInEnabledDates",
      value: function _isInEnabledDates(testDate) {
        return this._options.enabledDates[testDate.format('YYYY-MM-DD')] === true;
      }
    }, {
      key: "_isInDisabledHours",
      value: function _isInDisabledHours(testDate) {
        return this._options.disabledHours[testDate.format('H')] === true;
      }
    }, {
      key: "_isInEnabledHours",
      value: function _isInEnabledHours(testDate) {
        return this._options.enabledHours[testDate.format('H')] === true;
      }
    }, {
      key: "_isValid",
      value: function _isValid(targetMoment, granularity) {
        if (!targetMoment.isValid()) {
          return false;
        }

        if (this._options.disabledDates && granularity === 'd' && this._isInDisabledDates(targetMoment)) {
          return false;
        }

        if (this._options.enabledDates && granularity === 'd' && !this._isInEnabledDates(targetMoment)) {
          return false;
        }

        if (this._options.minDate && targetMoment.isBefore(this._options.minDate, granularity)) {
          return false;
        }

        if (this._options.maxDate && targetMoment.isAfter(this._options.maxDate, granularity)) {
          return false;
        }

        if (this._options.daysOfWeekDisabled && granularity === 'd' && this._options.daysOfWeekDisabled.indexOf(targetMoment.day()) !== -1) {
          return false;
        }

        if (this._options.disabledHours && (granularity === 'h' || granularity === 'm' || granularity === 's') && this._isInDisabledHours(targetMoment)) {
          return false;
        }

        if (this._options.enabledHours && (granularity === 'h' || granularity === 'm' || granularity === 's') && !this._isInEnabledHours(targetMoment)) {
          return false;
        }

        if (this._options.disabledTimeIntervals && (granularity === 'h' || granularity === 'm' || granularity === 's')) {
          var found = false;
          $.each(this._options.disabledTimeIntervals, function () {
            if (targetMoment.isBetween(this[0], this[1])) {
              found = true;
              return false;
            }
          });

          if (found) {
            return false;
          }
        }

        return true;
      }
    }, {
      key: "_parseInputDate",
      value: function _parseInputDate(inputDate) {
        if (this._options.parseInputDate === undefined) {
          if (!moment.isMoment(inputDate)) {
            inputDate = this.getMoment(inputDate);
          }
        } else {
          inputDate = this._options.parseInputDate(inputDate);
        } //inputDate.locale(this.options.locale);


        return inputDate;
      }
    }, {
      key: "_keydown",
      value: function _keydown(e) {
        var handler = null,
            index,
            index2,
            keyBindKeys,
            allModifiersPressed;
        var pressedKeys = [],
            pressedModifiers = {},
            currentKey = e.which,
            pressed = 'p';
        keyState[currentKey] = pressed;

        for (index in keyState) {
          if (keyState.hasOwnProperty(index) && keyState[index] === pressed) {
            pressedKeys.push(index);

            if (parseInt(index, 10) !== currentKey) {
              pressedModifiers[index] = true;
            }
          }
        }

        for (index in this._options.keyBinds) {
          if (this._options.keyBinds.hasOwnProperty(index) && typeof this._options.keyBinds[index] === 'function') {
            keyBindKeys = index.split(' ');

            if (keyBindKeys.length === pressedKeys.length && KeyMap[currentKey] === keyBindKeys[keyBindKeys.length - 1]) {
              allModifiersPressed = true;

              for (index2 = keyBindKeys.length - 2; index2 >= 0; index2--) {
                if (!(KeyMap[keyBindKeys[index2]] in pressedModifiers)) {
                  allModifiersPressed = false;
                  break;
                }
              }

              if (allModifiersPressed) {
                handler = this._options.keyBinds[index];
                break;
              }
            }
          }
        }

        if (handler) {
          if (handler.call(this)) {
            e.stopPropagation();
            e.preventDefault();
          }
        }
      } //noinspection JSMethodCanBeStatic,SpellCheckingInspection

    }, {
      key: "_keyup",
      value: function _keyup(e) {
        keyState[e.which] = 'r';

        if (keyPressHandled[e.which]) {
          keyPressHandled[e.which] = false;
          e.stopPropagation();
          e.preventDefault();
        }
      }
    }, {
      key: "_indexGivenDates",
      value: function _indexGivenDates(givenDatesArray) {
        // Store given enabledDates and disabledDates as keys.
        // This way we can check their existence in O(1) time instead of looping through whole array.
        // (for example: options.enabledDates['2014-02-27'] === true)
        var givenDatesIndexed = {},
            self = this;
        $.each(givenDatesArray, function () {
          var dDate = self._parseInputDate(this);

          if (dDate.isValid()) {
            givenDatesIndexed[dDate.format('YYYY-MM-DD')] = true;
          }
        });
        return Object.keys(givenDatesIndexed).length ? givenDatesIndexed : false;
      }
    }, {
      key: "_indexGivenHours",
      value: function _indexGivenHours(givenHoursArray) {
        // Store given enabledHours and disabledHours as keys.
        // This way we can check their existence in O(1) time instead of looping through whole array.
        // (for example: options.enabledHours['2014-02-27'] === true)
        var givenHoursIndexed = {};
        $.each(givenHoursArray, function () {
          givenHoursIndexed[this] = true;
        });
        return Object.keys(givenHoursIndexed).length ? givenHoursIndexed : false;
      }
    }, {
      key: "_initFormatting",
      value: function _initFormatting() {
        var format = this._options.format || 'L LT',
            self = this;
        this.actualFormat = format.replace(/(\[[^\[]*])|(\\)?(LTS|LT|LL?L?L?|l{1,4})/g, function (formatInput) {
          return self._dates[0].localeData().longDateFormat(formatInput) || formatInput; //todo taking the first date should be ok
        });
        this.parseFormats = this._options.extraFormats ? this._options.extraFormats.slice() : [];

        if (this.parseFormats.indexOf(format) < 0 && this.parseFormats.indexOf(this.actualFormat) < 0) {
          this.parseFormats.push(this.actualFormat);
        }

        this.use24Hours = this.actualFormat.toLowerCase().indexOf('a') < 1 && this.actualFormat.replace(/\[.*?]/g, '').indexOf('h') < 1;

        if (this._isEnabled('y')) {
          MinViewModeNumber = 2;
        }

        if (this._isEnabled('M')) {
          MinViewModeNumber = 1;
        }

        if (this._isEnabled('d')) {
          MinViewModeNumber = 0;
        }

        this.currentViewMode = Math.max(MinViewModeNumber, this.currentViewMode);

        if (!this.unset) {
          this._setValue(this._dates[0], 0);
        }
      }
    }, {
      key: "_getLastPickedDate",
      value: function _getLastPickedDate() {
        return this._dates[this._getLastPickedDateIndex()];
      }
    }, {
      key: "_getLastPickedDateIndex",
      value: function _getLastPickedDateIndex() {
        return this._dates.length - 1;
      } //public

    }, {
      key: "getMoment",
      value: function getMoment(d) {
        var returnMoment;

        if (d === undefined || d === null) {
          returnMoment = moment(); //TODO should this use format? and locale?
        } else if (this._hasTimeZone()) {
          // There is a string to parse and a default time zone
          // parse with the tz function which takes a default time zone if it is not in the format string
          returnMoment = moment.tz(d, this.parseFormats, this._options.locale, this._options.useStrict, this._options.timeZone);
        } else {
          returnMoment = moment(d, this.parseFormats, this._options.locale, this._options.useStrict);
        }

        if (this._hasTimeZone()) {
          returnMoment.tz(this._options.timeZone);
        }

        return returnMoment;
      }
    }, {
      key: "toggle",
      value: function toggle() {
        return this.widget ? this.hide() : this.show();
      }
    }, {
      key: "ignoreReadonly",
      value: function ignoreReadonly(_ignoreReadonly) {
        if (arguments.length === 0) {
          return this._options.ignoreReadonly;
        }

        if (typeof _ignoreReadonly !== 'boolean') {
          throw new TypeError('ignoreReadonly () expects a boolean parameter');
        }

        this._options.ignoreReadonly = _ignoreReadonly;
      }
    }, {
      key: "options",
      value: function options(newOptions) {
        if (arguments.length === 0) {
          return $.extend(true, {}, this._options);
        }

        if (!(newOptions instanceof Object)) {
          throw new TypeError('options() this.options parameter should be an object');
        }

        $.extend(true, this._options, newOptions);
        var self = this;
        $.each(this._options, function (key, value) {
          if (self[key] !== undefined) {
            self[key](value);
          }
        });
      }
    }, {
      key: "date",
      value: function date(newDate, index) {
        index = index || 0;

        if (arguments.length === 0) {
          if (this.unset) {
            return null;
          }

          if (this._options.allowMultidate) {
            return this._dates.join(this._options.multidateSeparator);
          } else {
            return this._dates[index].clone();
          }
        }

        if (newDate !== null && typeof newDate !== 'string' && !moment.isMoment(newDate) && !(newDate instanceof Date)) {
          throw new TypeError('date() parameter must be one of [null, string, moment or Date]');
        }

        this._setValue(newDate === null ? null : this._parseInputDate(newDate), index);
      }
    }, {
      key: "format",
      value: function format(newFormat) {
        if (arguments.length === 0) {
          return this._options.format;
        }

        if (typeof newFormat !== 'string' && (typeof newFormat !== 'boolean' || newFormat !== false)) {
          throw new TypeError("format() expects a string or boolean:false parameter ".concat(newFormat));
        }

        this._options.format = newFormat;

        if (this.actualFormat) {
          this._initFormatting(); // reinitialize formatting

        }
      }
    }, {
      key: "timeZone",
      value: function timeZone(newZone) {
        if (arguments.length === 0) {
          return this._options.timeZone;
        }

        if (typeof newZone !== 'string') {
          throw new TypeError('newZone() expects a string parameter');
        }

        this._options.timeZone = newZone;
      }
    }, {
      key: "dayViewHeaderFormat",
      value: function dayViewHeaderFormat(newFormat) {
        if (arguments.length === 0) {
          return this._options.dayViewHeaderFormat;
        }

        if (typeof newFormat !== 'string') {
          throw new TypeError('dayViewHeaderFormat() expects a string parameter');
        }

        this._options.dayViewHeaderFormat = newFormat;
      }
    }, {
      key: "extraFormats",
      value: function extraFormats(formats) {
        if (arguments.length === 0) {
          return this._options.extraFormats;
        }

        if (formats !== false && !(formats instanceof Array)) {
          throw new TypeError('extraFormats() expects an array or false parameter');
        }

        this._options.extraFormats = formats;

        if (this.parseFormats) {
          this._initFormatting(); // reinit formatting

        }
      }
    }, {
      key: "disabledDates",
      value: function disabledDates(dates) {
        if (arguments.length === 0) {
          return this._options.disabledDates ? $.extend({}, this._options.disabledDates) : this._options.disabledDates;
        }

        if (!dates) {
          this._options.disabledDates = false;

          this._update();

          return true;
        }

        if (!(dates instanceof Array)) {
          throw new TypeError('disabledDates() expects an array parameter');
        }

        this._options.disabledDates = this._indexGivenDates(dates);
        this._options.enabledDates = false;

        this._update();
      }
    }, {
      key: "enabledDates",
      value: function enabledDates(dates) {
        if (arguments.length === 0) {
          return this._options.enabledDates ? $.extend({}, this._options.enabledDates) : this._options.enabledDates;
        }

        if (!dates) {
          this._options.enabledDates = false;

          this._update();

          return true;
        }

        if (!(dates instanceof Array)) {
          throw new TypeError('enabledDates() expects an array parameter');
        }

        this._options.enabledDates = this._indexGivenDates(dates);
        this._options.disabledDates = false;

        this._update();
      }
    }, {
      key: "daysOfWeekDisabled",
      value: function daysOfWeekDisabled(_daysOfWeekDisabled) {
        if (arguments.length === 0) {
          return this._options.daysOfWeekDisabled.splice(0);
        }

        if (typeof _daysOfWeekDisabled === 'boolean' && !_daysOfWeekDisabled) {
          this._options.daysOfWeekDisabled = false;

          this._update();

          return true;
        }

        if (!(_daysOfWeekDisabled instanceof Array)) {
          throw new TypeError('daysOfWeekDisabled() expects an array parameter');
        }

        this._options.daysOfWeekDisabled = _daysOfWeekDisabled.reduce(function (previousValue, currentValue) {
          currentValue = parseInt(currentValue, 10);

          if (currentValue > 6 || currentValue < 0 || isNaN(currentValue)) {
            return previousValue;
          }

          if (previousValue.indexOf(currentValue) === -1) {
            previousValue.push(currentValue);
          }

          return previousValue;
        }, []).sort();

        if (this._options.useCurrent && !this._options.keepInvalid) {
          for (var i = 0; i < this._dates.length; i++) {
            var tries = 0;

            while (!this._isValid(this._dates[i], 'd')) {
              this._dates[i].add(1, 'd');

              if (tries === 31) {
                throw 'Tried 31 times to find a valid date';
              }

              tries++;
            }

            this._setValue(this._dates[i], i);
          }
        }

        this._update();
      }
    }, {
      key: "maxDate",
      value: function maxDate(_maxDate) {
        if (arguments.length === 0) {
          return this._options.maxDate ? this._options.maxDate.clone() : this._options.maxDate;
        }

        if (typeof _maxDate === 'boolean' && _maxDate === false) {
          this._options.maxDate = false;

          this._update();

          return true;
        }

        if (typeof _maxDate === 'string') {
          if (_maxDate === 'now' || _maxDate === 'moment') {
            _maxDate = this.getMoment();
          }
        }

        var parsedDate = this._parseInputDate(_maxDate);

        if (!parsedDate.isValid()) {
          throw new TypeError("maxDate() Could not parse date parameter: ".concat(_maxDate));
        }

        if (this._options.minDate && parsedDate.isBefore(this._options.minDate)) {
          throw new TypeError("maxDate() date parameter is before this.options.minDate: ".concat(parsedDate.format(this.actualFormat)));
        }

        this._options.maxDate = parsedDate;

        for (var i = 0; i < this._dates.length; i++) {
          if (this._options.useCurrent && !this._options.keepInvalid && this._dates[i].isAfter(_maxDate)) {
            this._setValue(this._options.maxDate, i);
          }
        }

        if (this._viewDate.isAfter(parsedDate)) {
          this._viewDate = parsedDate.clone().subtract(this._options.stepping, 'm');
        }

        this._update();
      }
    }, {
      key: "minDate",
      value: function minDate(_minDate) {
        if (arguments.length === 0) {
          return this._options.minDate ? this._options.minDate.clone() : this._options.minDate;
        }

        if (typeof _minDate === 'boolean' && _minDate === false) {
          this._options.minDate = false;

          this._update();

          return true;
        }

        if (typeof _minDate === 'string') {
          if (_minDate === 'now' || _minDate === 'moment') {
            _minDate = this.getMoment();
          }
        }

        var parsedDate = this._parseInputDate(_minDate);

        if (!parsedDate.isValid()) {
          throw new TypeError("minDate() Could not parse date parameter: ".concat(_minDate));
        }

        if (this._options.maxDate && parsedDate.isAfter(this._options.maxDate)) {
          throw new TypeError("minDate() date parameter is after this.options.maxDate: ".concat(parsedDate.format(this.actualFormat)));
        }

        this._options.minDate = parsedDate;

        for (var i = 0; i < this._dates.length; i++) {
          if (this._options.useCurrent && !this._options.keepInvalid && this._dates[i].isBefore(_minDate)) {
            this._setValue(this._options.minDate, i);
          }
        }

        if (this._viewDate.isBefore(parsedDate)) {
          this._viewDate = parsedDate.clone().add(this._options.stepping, 'm');
        }

        this._update();
      }
    }, {
      key: "defaultDate",
      value: function defaultDate(_defaultDate) {
        if (arguments.length === 0) {
          return this._options.defaultDate ? this._options.defaultDate.clone() : this._options.defaultDate;
        }

        if (!_defaultDate) {
          this._options.defaultDate = false;
          return true;
        }

        if (typeof _defaultDate === 'string') {
          if (_defaultDate === 'now' || _defaultDate === 'moment') {
            _defaultDate = this.getMoment();
          } else {
            _defaultDate = this.getMoment(_defaultDate);
          }
        }

        var parsedDate = this._parseInputDate(_defaultDate);

        if (!parsedDate.isValid()) {
          throw new TypeError("defaultDate() Could not parse date parameter: ".concat(_defaultDate));
        }

        if (!this._isValid(parsedDate)) {
          throw new TypeError('defaultDate() date passed is invalid according to component setup validations');
        }

        this._options.defaultDate = parsedDate;

        if (this._options.defaultDate && this._options.inline || this.input !== undefined && this.input.val().trim() === '') {
          this._setValue(this._options.defaultDate, 0);
        }
      }
    }, {
      key: "locale",
      value: function locale(_locale) {
        if (arguments.length === 0) {
          return this._options.locale;
        }

        if (!moment.localeData(_locale)) {
          throw new TypeError("locale() locale ".concat(_locale, " is not loaded from moment locales!"));
        }

        this._options.locale = _locale;

        for (var i = 0; i < this._dates.length; i++) {
          this._dates[i].locale(this._options.locale);
        }

        this._viewDate.locale(this._options.locale);

        if (this.actualFormat) {
          this._initFormatting(); // reinitialize formatting

        }

        if (this.widget) {
          this.hide();
          this.show();
        }
      }
    }, {
      key: "stepping",
      value: function stepping(_stepping) {
        if (arguments.length === 0) {
          return this._options.stepping;
        }

        _stepping = parseInt(_stepping, 10);

        if (isNaN(_stepping) || _stepping < 1) {
          _stepping = 1;
        }

        this._options.stepping = _stepping;
      }
    }, {
      key: "useCurrent",
      value: function useCurrent(_useCurrent) {
        var useCurrentOptions = ['year', 'month', 'day', 'hour', 'minute'];

        if (arguments.length === 0) {
          return this._options.useCurrent;
        }

        if (typeof _useCurrent !== 'boolean' && typeof _useCurrent !== 'string') {
          throw new TypeError('useCurrent() expects a boolean or string parameter');
        }

        if (typeof _useCurrent === 'string' && useCurrentOptions.indexOf(_useCurrent.toLowerCase()) === -1) {
          throw new TypeError("useCurrent() expects a string parameter of ".concat(useCurrentOptions.join(', ')));
        }

        this._options.useCurrent = _useCurrent;
      }
    }, {
      key: "collapse",
      value: function collapse(_collapse) {
        if (arguments.length === 0) {
          return this._options.collapse;
        }

        if (typeof _collapse !== 'boolean') {
          throw new TypeError('collapse() expects a boolean parameter');
        }

        if (this._options.collapse === _collapse) {
          return true;
        }

        this._options.collapse = _collapse;

        if (this.widget) {
          this.hide();
          this.show();
        }
      }
    }, {
      key: "icons",
      value: function icons(_icons) {
        if (arguments.length === 0) {
          return $.extend({}, this._options.icons);
        }

        if (!(_icons instanceof Object)) {
          throw new TypeError('icons() expects parameter to be an Object');
        }

        $.extend(this._options.icons, _icons);

        if (this.widget) {
          this.hide();
          this.show();
        }
      }
    }, {
      key: "tooltips",
      value: function tooltips(_tooltips) {
        if (arguments.length === 0) {
          return $.extend({}, this._options.tooltips);
        }

        if (!(_tooltips instanceof Object)) {
          throw new TypeError('tooltips() expects parameter to be an Object');
        }

        $.extend(this._options.tooltips, _tooltips);

        if (this.widget) {
          this.hide();
          this.show();
        }
      }
    }, {
      key: "useStrict",
      value: function useStrict(_useStrict) {
        if (arguments.length === 0) {
          return this._options.useStrict;
        }

        if (typeof _useStrict !== 'boolean') {
          throw new TypeError('useStrict() expects a boolean parameter');
        }

        this._options.useStrict = _useStrict;
      }
    }, {
      key: "sideBySide",
      value: function sideBySide(_sideBySide) {
        if (arguments.length === 0) {
          return this._options.sideBySide;
        }

        if (typeof _sideBySide !== 'boolean') {
          throw new TypeError('sideBySide() expects a boolean parameter');
        }

        this._options.sideBySide = _sideBySide;

        if (this.widget) {
          this.hide();
          this.show();
        }
      }
    }, {
      key: "viewMode",
      value: function viewMode(_viewMode) {
        if (arguments.length === 0) {
          return this._options.viewMode;
        }

        if (typeof _viewMode !== 'string') {
          throw new TypeError('viewMode() expects a string parameter');
        }

        if (DateTimePicker.ViewModes.indexOf(_viewMode) === -1) {
          throw new TypeError("viewMode() parameter must be one of (".concat(DateTimePicker.ViewModes.join(', '), ") value"));
        }

        this._options.viewMode = _viewMode;
        this.currentViewMode = Math.max(DateTimePicker.ViewModes.indexOf(_viewMode) - 1, DateTimePicker.MinViewModeNumber);

        this._showMode();
      }
    }, {
      key: "calendarWeeks",
      value: function calendarWeeks(_calendarWeeks) {
        if (arguments.length === 0) {
          return this._options.calendarWeeks;
        }

        if (typeof _calendarWeeks !== 'boolean') {
          throw new TypeError('calendarWeeks() expects parameter to be a boolean value');
        }

        this._options.calendarWeeks = _calendarWeeks;

        this._update();
      }
    }, {
      key: "buttons",
      value: function buttons(_buttons) {
        if (arguments.length === 0) {
          return $.extend({}, this._options.buttons);
        }

        if (!(_buttons instanceof Object)) {
          throw new TypeError('buttons() expects parameter to be an Object');
        }

        $.extend(this._options.buttons, _buttons);

        if (typeof this._options.buttons.showToday !== 'boolean') {
          throw new TypeError('buttons.showToday expects a boolean parameter');
        }

        if (typeof this._options.buttons.showClear !== 'boolean') {
          throw new TypeError('buttons.showClear expects a boolean parameter');
        }

        if (typeof this._options.buttons.showClose !== 'boolean') {
          throw new TypeError('buttons.showClose expects a boolean parameter');
        }

        if (this.widget) {
          this.hide();
          this.show();
        }
      }
    }, {
      key: "keepOpen",
      value: function keepOpen(_keepOpen) {
        if (arguments.length === 0) {
          return this._options.keepOpen;
        }

        if (typeof _keepOpen !== 'boolean') {
          throw new TypeError('keepOpen() expects a boolean parameter');
        }

        this._options.keepOpen = _keepOpen;
      }
    }, {
      key: "focusOnShow",
      value: function focusOnShow(_focusOnShow) {
        if (arguments.length === 0) {
          return this._options.focusOnShow;
        }

        if (typeof _focusOnShow !== 'boolean') {
          throw new TypeError('focusOnShow() expects a boolean parameter');
        }

        this._options.focusOnShow = _focusOnShow;
      }
    }, {
      key: "inline",
      value: function inline(_inline) {
        if (arguments.length === 0) {
          return this._options.inline;
        }

        if (typeof _inline !== 'boolean') {
          throw new TypeError('inline() expects a boolean parameter');
        }

        this._options.inline = _inline;
      }
    }, {
      key: "clear",
      value: function clear() {
        this._setValue(null); //todo

      }
    }, {
      key: "keyBinds",
      value: function keyBinds(_keyBinds) {
        if (arguments.length === 0) {
          return this._options.keyBinds;
        }

        this._options.keyBinds = _keyBinds;
      }
    }, {
      key: "debug",
      value: function debug(_debug) {
        if (typeof _debug !== 'boolean') {
          throw new TypeError('debug() expects a boolean parameter');
        }

        this._options.debug = _debug;
      }
    }, {
      key: "allowInputToggle",
      value: function allowInputToggle(_allowInputToggle) {
        if (arguments.length === 0) {
          return this._options.allowInputToggle;
        }

        if (typeof _allowInputToggle !== 'boolean') {
          throw new TypeError('allowInputToggle() expects a boolean parameter');
        }

        this._options.allowInputToggle = _allowInputToggle;
      }
    }, {
      key: "keepInvalid",
      value: function keepInvalid(_keepInvalid) {
        if (arguments.length === 0) {
          return this._options.keepInvalid;
        }

        if (typeof _keepInvalid !== 'boolean') {
          throw new TypeError('keepInvalid() expects a boolean parameter');
        }

        this._options.keepInvalid = _keepInvalid;
      }
    }, {
      key: "datepickerInput",
      value: function datepickerInput(_datepickerInput) {
        if (arguments.length === 0) {
          return this._options.datepickerInput;
        }

        if (typeof _datepickerInput !== 'string') {
          throw new TypeError('datepickerInput() expects a string parameter');
        }

        this._options.datepickerInput = _datepickerInput;
      }
    }, {
      key: "parseInputDate",
      value: function parseInputDate(_parseInputDate2) {
        if (arguments.length === 0) {
          return this._options.parseInputDate;
        }

        if (typeof _parseInputDate2 !== 'function') {
          throw new TypeError('parseInputDate() should be as function');
        }

        this._options.parseInputDate = _parseInputDate2;
      }
    }, {
      key: "disabledTimeIntervals",
      value: function disabledTimeIntervals(_disabledTimeIntervals) {
        if (arguments.length === 0) {
          return this._options.disabledTimeIntervals ? $.extend({}, this._options.disabledTimeIntervals) : this._options.disabledTimeIntervals;
        }

        if (!_disabledTimeIntervals) {
          this._options.disabledTimeIntervals = false;

          this._update();

          return true;
        }

        if (!(_disabledTimeIntervals instanceof Array)) {
          throw new TypeError('disabledTimeIntervals() expects an array parameter');
        }

        this._options.disabledTimeIntervals = _disabledTimeIntervals;

        this._update();
      }
    }, {
      key: "disabledHours",
      value: function disabledHours(hours) {
        if (arguments.length === 0) {
          return this._options.disabledHours ? $.extend({}, this._options.disabledHours) : this._options.disabledHours;
        }

        if (!hours) {
          this._options.disabledHours = false;

          this._update();

          return true;
        }

        if (!(hours instanceof Array)) {
          throw new TypeError('disabledHours() expects an array parameter');
        }

        this._options.disabledHours = this._indexGivenHours(hours);
        this._options.enabledHours = false;

        if (this._options.useCurrent && !this._options.keepInvalid) {
          for (var i = 0; i < this._dates.length; i++) {
            var tries = 0;

            while (!this._isValid(this._dates[i], 'h')) {
              this._dates[i].add(1, 'h');

              if (tries === 24) {
                throw 'Tried 24 times to find a valid date';
              }

              tries++;
            }

            this._setValue(this._dates[i], i);
          }
        }

        this._update();
      }
    }, {
      key: "enabledHours",
      value: function enabledHours(hours) {
        if (arguments.length === 0) {
          return this._options.enabledHours ? $.extend({}, this._options.enabledHours) : this._options.enabledHours;
        }

        if (!hours) {
          this._options.enabledHours = false;

          this._update();

          return true;
        }

        if (!(hours instanceof Array)) {
          throw new TypeError('enabledHours() expects an array parameter');
        }

        this._options.enabledHours = this._indexGivenHours(hours);
        this._options.disabledHours = false;

        if (this._options.useCurrent && !this._options.keepInvalid) {
          for (var i = 0; i < this._dates.length; i++) {
            var tries = 0;

            while (!this._isValid(this._dates[i], 'h')) {
              this._dates[i].add(1, 'h');

              if (tries === 24) {
                throw 'Tried 24 times to find a valid date';
              }

              tries++;
            }

            this._setValue(this._dates[i], i);
          }
        }

        this._update();
      }
    }, {
      key: "viewDate",
      value: function viewDate(newDate) {
        if (arguments.length === 0) {
          return this._viewDate.clone();
        }

        if (!newDate) {
          this._viewDate = (this._dates[0] || this.getMoment()).clone();
          return true;
        }

        if (typeof newDate !== 'string' && !moment.isMoment(newDate) && !(newDate instanceof Date)) {
          throw new TypeError('viewDate() parameter must be one of [string, moment or Date]');
        }

        this._viewDate = this._parseInputDate(newDate);

        this._viewUpdate();
      }
    }, {
      key: "allowMultidate",
      value: function allowMultidate(_allowMultidate) {
        if (typeof _allowMultidate !== 'boolean') {
          throw new TypeError('allowMultidate() expects a boolean parameter');
        }

        this._options.allowMultidate = _allowMultidate;
      }
    }, {
      key: "multidateSeparator",
      value: function multidateSeparator(_multidateSeparator) {
        if (arguments.length === 0) {
          return this._options.multidateSeparator;
        }

        if (typeof _multidateSeparator !== 'string' || _multidateSeparator.length > 1) {
          throw new TypeError('multidateSeparator expects a single character string parameter');
        }

        this._options.multidateSeparator = _multidateSeparator;
      }
    }], [{
      key: "NAME",
      get: function get() {
        return NAME;
      }
      /**
       * @return {string}
       */

    }, {
      key: "DATA_KEY",
      get: function get() {
        return DATA_KEY;
      }
      /**
       * @return {string}
       */

    }, {
      key: "EVENT_KEY",
      get: function get() {
        return EVENT_KEY;
      }
      /**
       * @return {string}
       */

    }, {
      key: "DATA_API_KEY",
      get: function get() {
        return DATA_API_KEY;
      }
    }, {
      key: "DatePickerModes",
      get: function get() {
        return DatePickerModes;
      }
    }, {
      key: "ViewModes",
      get: function get() {
        return ViewModes;
      }
      /**
       * @return {number}
       */

    }, {
      key: "MinViewModeNumber",
      get: function get() {
        return MinViewModeNumber;
      }
    }, {
      key: "Event",
      get: function get() {
        return Event;
      }
    }, {
      key: "Selector",
      get: function get() {
        return Selector;
      }
    }, {
      key: "Default",
      get: function get() {
        return Default;
      },
      set: function set(value) {
        Default = value;
      }
    }, {
      key: "ClassName",
      get: function get() {
        return ClassName;
      }
    }]);

    return DateTimePicker;
  }();

  return DateTimePicker;
}(jQuery, moment); //noinspection JSUnusedGlobalSymbols
// ReSharper disable PossiblyUnassignedProperty

/* global DateTimePicker, Popper */


var TempusDominusBootstrap4 = function ($) {
  // eslint-disable-line no-unused-vars
  // ReSharper disable once InconsistentNaming
  var JQUERY_NO_CONFLICT = $.fn[DateTimePicker.NAME],
      toolbarPlacements = ['default', 'top', 'bottom'],
      getSelectorFromElement = function getSelectorFromElement($element) {
    var selector = $element.data('target'),
        $selector;

    if (!selector) {
      selector = $element.attr('href') || '';
      selector = /^#[a-z]/i.test(selector) ? selector : null;
    }

    $selector = $(selector);

    if ($selector.length === 0) {
      return $selector;
    }

    if (!$selector.data(DateTimePicker.DATA_KEY)) {
      $.extend({}, $selector.data(), $(this).data());
    }

    return $selector;
  }; // ReSharper disable once InconsistentNaming


  var TempusDominusBootstrap4 =
  /*#__PURE__*/
  function (_DateTimePicker) {
    _inherits(TempusDominusBootstrap4, _DateTimePicker);

    function TempusDominusBootstrap4(element, options) {
      var _this;

      _classCallCheck(this, TempusDominusBootstrap4);

      _this = _possibleConstructorReturn(this, _getPrototypeOf(TempusDominusBootstrap4).call(this, element, options));

      _this._init();

      return _this;
    }

    _createClass(TempusDominusBootstrap4, [{
      key: "_init",
      value: function _init() {
        if (this._element.hasClass('input-group')) {
          var datepickerButton = this._element.find('.datepickerbutton');

          if (datepickerButton.length === 0) {
            this.component = this._element.find('[data-toggle="datetimepicker"]');
          } else {
            this.component = datepickerButton;
          }
        }
      }
    }, {
      key: "_getDatePickerTemplate",
      value: function _getDatePickerTemplate() {
        var headTemplate = $('<thead>').append($('<tr>').append($('<th>').addClass('prev').attr('data-action', 'previous').append($('<span>').addClass(this._options.icons.previous))).append($('<th>').addClass('picker-switch').attr('data-action', 'pickerSwitch').attr('colspan', "".concat(this._options.calendarWeeks ? '6' : '5'))).append($('<th>').addClass('next').attr('data-action', 'next').append($('<span>').addClass(this._options.icons.next)))),
            contTemplate = $('<tbody>').append($('<tr>').append($('<td>').attr('colspan', "".concat(this._options.calendarWeeks ? '8' : '7'))));
        return [$('<div>').addClass('datepicker-days').append($('<table>').addClass('table table-sm').append(headTemplate).append($('<tbody>'))), $('<div>').addClass('datepicker-months').append($('<table>').addClass('table-condensed').append(headTemplate.clone()).append(contTemplate.clone())), $('<div>').addClass('datepicker-years').append($('<table>').addClass('table-condensed').append(headTemplate.clone()).append(contTemplate.clone())), $('<div>').addClass('datepicker-decades').append($('<table>').addClass('table-condensed').append(headTemplate.clone()).append(contTemplate.clone()))];
      }
    }, {
      key: "_getTimePickerMainTemplate",
      value: function _getTimePickerMainTemplate() {
        var topRow = $('<tr>'),
            middleRow = $('<tr>'),
            bottomRow = $('<tr>');

        if (this._isEnabled('h')) {
          topRow.append($('<td>').append($('<a>').attr({
            href: '#',
            tabindex: '-1',
            'title': this._options.tooltips.incrementHour
          }).addClass('btn').attr('data-action', 'incrementHours').append($('<span>').addClass(this._options.icons.up))));
          middleRow.append($('<td>').append($('<span>').addClass('timepicker-hour').attr({
            'data-time-component': 'hours',
            'title': this._options.tooltips.pickHour
          }).attr('data-action', 'showHours')));
          bottomRow.append($('<td>').append($('<a>').attr({
            href: '#',
            tabindex: '-1',
            'title': this._options.tooltips.decrementHour
          }).addClass('btn').attr('data-action', 'decrementHours').append($('<span>').addClass(this._options.icons.down))));
        }

        if (this._isEnabled('m')) {
          if (this._isEnabled('h')) {
            topRow.append($('<td>').addClass('separator'));
            middleRow.append($('<td>').addClass('separator').html(':'));
            bottomRow.append($('<td>').addClass('separator'));
          }

          topRow.append($('<td>').append($('<a>').attr({
            href: '#',
            tabindex: '-1',
            'title': this._options.tooltips.incrementMinute
          }).addClass('btn').attr('data-action', 'incrementMinutes').append($('<span>').addClass(this._options.icons.up))));
          middleRow.append($('<td>').append($('<span>').addClass('timepicker-minute').attr({
            'data-time-component': 'minutes',
            'title': this._options.tooltips.pickMinute
          }).attr('data-action', 'showMinutes')));
          bottomRow.append($('<td>').append($('<a>').attr({
            href: '#',
            tabindex: '-1',
            'title': this._options.tooltips.decrementMinute
          }).addClass('btn').attr('data-action', 'decrementMinutes').append($('<span>').addClass(this._options.icons.down))));
        }

        if (this._isEnabled('s')) {
          if (this._isEnabled('m')) {
            topRow.append($('<td>').addClass('separator'));
            middleRow.append($('<td>').addClass('separator').html(':'));
            bottomRow.append($('<td>').addClass('separator'));
          }

          topRow.append($('<td>').append($('<a>').attr({
            href: '#',
            tabindex: '-1',
            'title': this._options.tooltips.incrementSecond
          }).addClass('btn').attr('data-action', 'incrementSeconds').append($('<span>').addClass(this._options.icons.up))));
          middleRow.append($('<td>').append($('<span>').addClass('timepicker-second').attr({
            'data-time-component': 'seconds',
            'title': this._options.tooltips.pickSecond
          }).attr('data-action', 'showSeconds')));
          bottomRow.append($('<td>').append($('<a>').attr({
            href: '#',
            tabindex: '-1',
            'title': this._options.tooltips.decrementSecond
          }).addClass('btn').attr('data-action', 'decrementSeconds').append($('<span>').addClass(this._options.icons.down))));
        }

        if (!this.use24Hours) {
          topRow.append($('<td>').addClass('separator'));
          middleRow.append($('<td>').append($('<button>').addClass('btn btn-primary').attr({
            'data-action': 'togglePeriod',
            tabindex: '-1',
            'title': this._options.tooltips.togglePeriod
          })));
          bottomRow.append($('<td>').addClass('separator'));
        }

        return $('<div>').addClass('timepicker-picker').append($('<table>').addClass('table-condensed').append([topRow, middleRow, bottomRow]));
      }
    }, {
      key: "_getTimePickerTemplate",
      value: function _getTimePickerTemplate() {
        var hoursView = $('<div>').addClass('timepicker-hours').append($('<table>').addClass('table-condensed')),
            minutesView = $('<div>').addClass('timepicker-minutes').append($('<table>').addClass('table-condensed')),
            secondsView = $('<div>').addClass('timepicker-seconds').append($('<table>').addClass('table-condensed')),
            ret = [this._getTimePickerMainTemplate()];

        if (this._isEnabled('h')) {
          ret.push(hoursView);
        }

        if (this._isEnabled('m')) {
          ret.push(minutesView);
        }

        if (this._isEnabled('s')) {
          ret.push(secondsView);
        }

        return ret;
      }
    }, {
      key: "_getToolbar",
      value: function _getToolbar() {
        var row = [];

        if (this._options.buttons.showToday) {
          row.push($('<td>').append($('<a>').attr({
            href: '#',
            tabindex: '-1',
            'data-action': 'today',
            'title': this._options.tooltips.today
          }).append($('<span>').addClass(this._options.icons.today))));
        }

        if (!this._options.sideBySide && this._hasDate() && this._hasTime()) {
          var title, icon;

          if (this._options.viewMode === 'times') {
            title = this._options.tooltips.selectDate;
            icon = this._options.icons.date;
          } else {
            title = this._options.tooltips.selectTime;
            icon = this._options.icons.time;
          }

          row.push($('<td>').append($('<a>').attr({
            href: '#',
            tabindex: '-1',
            'data-action': 'togglePicker',
            'title': title
          }).append($('<span>').addClass(icon))));
        }

        if (this._options.buttons.showClear) {
          row.push($('<td>').append($('<a>').attr({
            href: '#',
            tabindex: '-1',
            'data-action': 'clear',
            'title': this._options.tooltips.clear
          }).append($('<span>').addClass(this._options.icons.clear))));
        }

        if (this._options.buttons.showClose) {
          row.push($('<td>').append($('<a>').attr({
            href: '#',
            tabindex: '-1',
            'data-action': 'close',
            'title': this._options.tooltips.close
          }).append($('<span>').addClass(this._options.icons.close))));
        }

        return row.length === 0 ? '' : $('<table>').addClass('table-condensed').append($('<tbody>').append($('<tr>').append(row)));
      }
    }, {
      key: "_getTemplate",
      value: function _getTemplate() {
        var template = $('<div>').addClass('bootstrap-datetimepicker-widget dropdown-menu'),
            dateView = $('<div>').addClass('datepicker').append(this._getDatePickerTemplate()),
            timeView = $('<div>').addClass('timepicker').append(this._getTimePickerTemplate()),
            content = $('<ul>').addClass('list-unstyled'),
            toolbar = $('<li>').addClass("picker-switch".concat(this._options.collapse ? ' accordion-toggle' : '')).append(this._getToolbar());

        if (this._options.inline) {
          template.removeClass('dropdown-menu');
        }

        if (this.use24Hours) {
          template.addClass('usetwentyfour');
        }

        if (this._isEnabled('s') && !this.use24Hours) {
          template.addClass('wider');
        }

        if (this._options.sideBySide && this._hasDate() && this._hasTime()) {
          template.addClass('timepicker-sbs');

          if (this._options.toolbarPlacement === 'top') {
            template.append(toolbar);
          }

          template.append($('<div>').addClass('row').append(dateView.addClass('col-md-6')).append(timeView.addClass('col-md-6')));

          if (this._options.toolbarPlacement === 'bottom' || this._options.toolbarPlacement === 'default') {
            template.append(toolbar);
          }

          return template;
        }

        if (this._options.toolbarPlacement === 'top') {
          content.append(toolbar);
        }

        if (this._hasDate()) {
          content.append($('<li>').addClass(this._options.collapse && this._hasTime() ? 'collapse' : '').addClass(this._options.collapse && this._hasTime() && this._options.viewMode === 'times' ? '' : 'show').append(dateView));
        }

        if (this._options.toolbarPlacement === 'default') {
          content.append(toolbar);
        }

        if (this._hasTime()) {
          content.append($('<li>').addClass(this._options.collapse && this._hasDate() ? 'collapse' : '').addClass(this._options.collapse && this._hasDate() && this._options.viewMode === 'times' ? 'show' : '').append(timeView));
        }

        if (this._options.toolbarPlacement === 'bottom') {
          content.append(toolbar);
        }

        return template.append(content);
      }
    }, {
      key: "_place",
      value: function _place(e) {
        var self = e && e.data && e.data.picker || this;

        if (self._options.sideBySide) {
          self._element.append(self.widget);

          return;
        }

        if (self._options.widgetParent) {
          self._options.widgetParent.append(self.widget);
        } else if (self._element.is('input')) {
          self._element.after(self.widget).parent();
        } else {
          self._element.children().first().after(self.widget);
        }

        var reference = self.component[0];

        if (!reference) {
          reference = self._element;
        } // ReSharper disable once ConstructorCallNotUsed


        new Popper(reference, self.widget[0], {
          placement: 'bottom-start'
        });
      }
    }, {
      key: "_fillDow",
      value: function _fillDow() {
        var row = $('<tr>'),
            currentDate = this._viewDate.clone().startOf('w').startOf('d');

        if (this._options.calendarWeeks === true) {
          row.append($('<th>').addClass('cw').text('#'));
        }

        while (currentDate.isBefore(this._viewDate.clone().endOf('w'))) {
          row.append($('<th>').addClass('dow').text(currentDate.format('dd')));
          currentDate.add(1, 'd');
        }

        this.widget.find('.datepicker-days thead').append(row);
      }
    }, {
      key: "_fillMonths",
      value: function _fillMonths() {
        var spans = [],
            monthsShort = this._viewDate.clone().startOf('y').startOf('d');

        while (monthsShort.isSame(this._viewDate, 'y')) {
          spans.push($('<span>').attr('data-action', 'selectMonth').addClass('month').text(monthsShort.format('MMM')));
          monthsShort.add(1, 'M');
        }

        this.widget.find('.datepicker-months td').empty().append(spans);
      }
    }, {
      key: "_updateMonths",
      value: function _updateMonths() {
        var monthsView = this.widget.find('.datepicker-months'),
            monthsViewHeader = monthsView.find('th'),
            months = monthsView.find('tbody').find('span'),
            self = this;
        monthsViewHeader.eq(0).find('span').attr('title', this._options.tooltips.prevYear);
        monthsViewHeader.eq(1).attr('title', this._options.tooltips.selectYear);
        monthsViewHeader.eq(2).find('span').attr('title', this._options.tooltips.nextYear);
        monthsView.find('.disabled').removeClass('disabled');

        if (!this._isValid(this._viewDate.clone().subtract(1, 'y'), 'y')) {
          monthsViewHeader.eq(0).addClass('disabled');
        }

        monthsViewHeader.eq(1).text(this._viewDate.year());

        if (!this._isValid(this._viewDate.clone().add(1, 'y'), 'y')) {
          monthsViewHeader.eq(2).addClass('disabled');
        }

        months.removeClass('active');

        if (this._getLastPickedDate().isSame(this._viewDate, 'y') && !this.unset) {
          months.eq(this._getLastPickedDate().month()).addClass('active');
        }

        months.each(function (index) {
          if (!self._isValid(self._viewDate.clone().month(index), 'M')) {
            $(this).addClass('disabled');
          }
        });
      }
    }, {
      key: "_getStartEndYear",
      value: function _getStartEndYear(factor, year) {
        var step = factor / 10,
            startYear = Math.floor(year / factor) * factor,
            endYear = startYear + step * 9,
            focusValue = Math.floor(year / step) * step;
        return [startYear, endYear, focusValue];
      }
    }, {
      key: "_updateYears",
      value: function _updateYears() {
        var yearsView = this.widget.find('.datepicker-years'),
            yearsViewHeader = yearsView.find('th'),
            yearCaps = this._getStartEndYear(10, this._viewDate.year()),
            startYear = this._viewDate.clone().year(yearCaps[0]),
            endYear = this._viewDate.clone().year(yearCaps[1]);

        var html = '';
        yearsViewHeader.eq(0).find('span').attr('title', this._options.tooltips.prevDecade);
        yearsViewHeader.eq(1).attr('title', this._options.tooltips.selectDecade);
        yearsViewHeader.eq(2).find('span').attr('title', this._options.tooltips.nextDecade);
        yearsView.find('.disabled').removeClass('disabled');

        if (this._options.minDate && this._options.minDate.isAfter(startYear, 'y')) {
          yearsViewHeader.eq(0).addClass('disabled');
        }

        yearsViewHeader.eq(1).text("".concat(startYear.year(), "-").concat(endYear.year()));

        if (this._options.maxDate && this._options.maxDate.isBefore(endYear, 'y')) {
          yearsViewHeader.eq(2).addClass('disabled');
        }

        html += "<span data-action=\"selectYear\" class=\"year old".concat(!this._isValid(startYear, 'y') ? ' disabled' : '', "\">").concat(startYear.year() - 1, "</span>");

        while (!startYear.isAfter(endYear, 'y')) {
          html += "<span data-action=\"selectYear\" class=\"year".concat(startYear.isSame(this._getLastPickedDate(), 'y') && !this.unset ? ' active' : '').concat(!this._isValid(startYear, 'y') ? ' disabled' : '', "\">").concat(startYear.year(), "</span>");
          startYear.add(1, 'y');
        }

        html += "<span data-action=\"selectYear\" class=\"year old".concat(!this._isValid(startYear, 'y') ? ' disabled' : '', "\">").concat(startYear.year(), "</span>");
        yearsView.find('td').html(html);
      }
    }, {
      key: "_updateDecades",
      value: function _updateDecades() {
        var decadesView = this.widget.find('.datepicker-decades'),
            decadesViewHeader = decadesView.find('th'),
            yearCaps = this._getStartEndYear(100, this._viewDate.year()),
            startDecade = this._viewDate.clone().year(yearCaps[0]),
            endDecade = this._viewDate.clone().year(yearCaps[1]);

        var minDateDecade = false,
            maxDateDecade = false,
            endDecadeYear,
            html = '';
        decadesViewHeader.eq(0).find('span').attr('title', this._options.tooltips.prevCentury);
        decadesViewHeader.eq(2).find('span').attr('title', this._options.tooltips.nextCentury);
        decadesView.find('.disabled').removeClass('disabled');

        if (startDecade.year() === 0 || this._options.minDate && this._options.minDate.isAfter(startDecade, 'y')) {
          decadesViewHeader.eq(0).addClass('disabled');
        }

        decadesViewHeader.eq(1).text("".concat(startDecade.year(), "-").concat(endDecade.year()));

        if (this._options.maxDate && this._options.maxDate.isBefore(endDecade, 'y')) {
          decadesViewHeader.eq(2).addClass('disabled');
        }

        if (startDecade.year() - 10 < 0) {
          html += '<span>&nbsp;</span>';
        } else {
          html += "<span data-action=\"selectDecade\" class=\"decade old\" data-selection=\"".concat(startDecade.year() + 6, "\">").concat(startDecade.year() - 10, "</span>");
        }

        while (!startDecade.isAfter(endDecade, 'y')) {
          endDecadeYear = startDecade.year() + 11;
          minDateDecade = this._options.minDate && this._options.minDate.isAfter(startDecade, 'y') && this._options.minDate.year() <= endDecadeYear;
          maxDateDecade = this._options.maxDate && this._options.maxDate.isAfter(startDecade, 'y') && this._options.maxDate.year() <= endDecadeYear;
          html += "<span data-action=\"selectDecade\" class=\"decade".concat(this._getLastPickedDate().isAfter(startDecade) && this._getLastPickedDate().year() <= endDecadeYear ? ' active' : '').concat(!this._isValid(startDecade, 'y') && !minDateDecade && !maxDateDecade ? ' disabled' : '', "\" data-selection=\"").concat(startDecade.year() + 6, "\">").concat(startDecade.year(), "</span>");
          startDecade.add(10, 'y');
        }

        html += "<span data-action=\"selectDecade\" class=\"decade old\" data-selection=\"".concat(startDecade.year() + 6, "\">").concat(startDecade.year(), "</span>");
        decadesView.find('td').html(html);
      }
    }, {
      key: "_fillDate",
      value: function _fillDate() {
        var daysView = this.widget.find('.datepicker-days'),
            daysViewHeader = daysView.find('th'),
            html = [];
        var currentDate, row, clsName, i;

        if (!this._hasDate()) {
          return;
        }

        daysViewHeader.eq(0).find('span').attr('title', this._options.tooltips.prevMonth);
        daysViewHeader.eq(1).attr('title', this._options.tooltips.selectMonth);
        daysViewHeader.eq(2).find('span').attr('title', this._options.tooltips.nextMonth);
        daysView.find('.disabled').removeClass('disabled');
        daysViewHeader.eq(1).text(this._viewDate.format(this._options.dayViewHeaderFormat));

        if (!this._isValid(this._viewDate.clone().subtract(1, 'M'), 'M')) {
          daysViewHeader.eq(0).addClass('disabled');
        }

        if (!this._isValid(this._viewDate.clone().add(1, 'M'), 'M')) {
          daysViewHeader.eq(2).addClass('disabled');
        }

        currentDate = this._viewDate.clone().startOf('M').startOf('w').startOf('d');

        for (i = 0; i < 42; i++) {
          //always display 42 days (should show 6 weeks)
          if (currentDate.weekday() === 0) {
            row = $('<tr>');

            if (this._options.calendarWeeks) {
              row.append("<td class=\"cw\">".concat(currentDate.week(), "</td>"));
            }

            html.push(row);
          }

          clsName = '';

          if (currentDate.isBefore(this._viewDate, 'M')) {
            clsName += ' old';
          }

          if (currentDate.isAfter(this._viewDate, 'M')) {
            clsName += ' new';
          }

          if (this._options.allowMultidate) {
            var index = this._datesFormatted.indexOf(currentDate.format('YYYY-MM-DD'));

            if (index !== -1) {
              if (currentDate.isSame(this._datesFormatted[index], 'd') && !this.unset) {
                clsName += ' active';
              }
            }
          } else {
            if (currentDate.isSame(this._getLastPickedDate(), 'd') && !this.unset) {
              clsName += ' active';
            }
          }

          if (!this._isValid(currentDate, 'd')) {
            clsName += ' disabled';
          }

          if (currentDate.isSame(this.getMoment(), 'd')) {
            clsName += ' today';
          }

          if (currentDate.day() === 0 || currentDate.day() === 6) {
            clsName += ' weekend';
          }

          row.append("<td data-action=\"selectDay\" data-day=\"".concat(currentDate.format('L'), "\" class=\"day").concat(clsName, "\">").concat(currentDate.date(), "</td>"));
          currentDate.add(1, 'd');
        }

        daysView.find('tbody').empty().append(html);

        this._updateMonths();

        this._updateYears();

        this._updateDecades();
      }
    }, {
      key: "_fillHours",
      value: function _fillHours() {
        var table = this.widget.find('.timepicker-hours table'),
            currentHour = this._viewDate.clone().startOf('d'),
            html = [];

        var row = $('<tr>');

        if (this._viewDate.hour() > 11 && !this.use24Hours) {
          currentHour.hour(12);
        }

        while (currentHour.isSame(this._viewDate, 'd') && (this.use24Hours || this._viewDate.hour() < 12 && currentHour.hour() < 12 || this._viewDate.hour() > 11)) {
          if (currentHour.hour() % 4 === 0) {
            row = $('<tr>');
            html.push(row);
          }

          row.append("<td data-action=\"selectHour\" class=\"hour".concat(!this._isValid(currentHour, 'h') ? ' disabled' : '', "\">").concat(currentHour.format(this.use24Hours ? 'HH' : 'hh'), "</td>"));
          currentHour.add(1, 'h');
        }

        table.empty().append(html);
      }
    }, {
      key: "_fillMinutes",
      value: function _fillMinutes() {
        var table = this.widget.find('.timepicker-minutes table'),
            currentMinute = this._viewDate.clone().startOf('h'),
            html = [],
            step = this._options.stepping === 1 ? 5 : this._options.stepping;

        var row = $('<tr>');

        while (this._viewDate.isSame(currentMinute, 'h')) {
          if (currentMinute.minute() % (step * 4) === 0) {
            row = $('<tr>');
            html.push(row);
          }

          row.append("<td data-action=\"selectMinute\" class=\"minute".concat(!this._isValid(currentMinute, 'm') ? ' disabled' : '', "\">").concat(currentMinute.format('mm'), "</td>"));
          currentMinute.add(step, 'm');
        }

        table.empty().append(html);
      }
    }, {
      key: "_fillSeconds",
      value: function _fillSeconds() {
        var table = this.widget.find('.timepicker-seconds table'),
            currentSecond = this._viewDate.clone().startOf('m'),
            html = [];

        var row = $('<tr>');

        while (this._viewDate.isSame(currentSecond, 'm')) {
          if (currentSecond.second() % 20 === 0) {
            row = $('<tr>');
            html.push(row);
          }

          row.append("<td data-action=\"selectSecond\" class=\"second".concat(!this._isValid(currentSecond, 's') ? ' disabled' : '', "\">").concat(currentSecond.format('ss'), "</td>"));
          currentSecond.add(5, 's');
        }

        table.empty().append(html);
      }
    }, {
      key: "_fillTime",
      value: function _fillTime() {
        var toggle, newDate;
        var timeComponents = this.widget.find('.timepicker span[data-time-component]');

        if (!this.use24Hours) {
          toggle = this.widget.find('.timepicker [data-action=togglePeriod]');
          newDate = this._getLastPickedDate().clone().add(this._getLastPickedDate().hours() >= 12 ? -12 : 12, 'h');
          toggle.text(this._getLastPickedDate().format('A'));

          if (this._isValid(newDate, 'h')) {
            toggle.removeClass('disabled');
          } else {
            toggle.addClass('disabled');
          }
        }

        timeComponents.filter('[data-time-component=hours]').text(this._getLastPickedDate().format("".concat(this.use24Hours ? 'HH' : 'hh')));
        timeComponents.filter('[data-time-component=minutes]').text(this._getLastPickedDate().format('mm'));
        timeComponents.filter('[data-time-component=seconds]').text(this._getLastPickedDate().format('ss'));

        this._fillHours();

        this._fillMinutes();

        this._fillSeconds();
      }
    }, {
      key: "_doAction",
      value: function _doAction(e, action) {
        var lastPicked = this._getLastPickedDate();

        if ($(e.currentTarget).is('.disabled')) {
          return false;
        }

        action = action || $(e.currentTarget).data('action');

        switch (action) {
          case 'next':
            {
              var navFnc = DateTimePicker.DatePickerModes[this.currentViewMode].NAV_FUNCTION;

              this._viewDate.add(DateTimePicker.DatePickerModes[this.currentViewMode].NAV_STEP, navFnc);

              this._fillDate();

              this._viewUpdate(navFnc);

              break;
            }

          case 'previous':
            {
              var _navFnc = DateTimePicker.DatePickerModes[this.currentViewMode].NAV_FUNCTION;

              this._viewDate.subtract(DateTimePicker.DatePickerModes[this.currentViewMode].NAV_STEP, _navFnc);

              this._fillDate();

              this._viewUpdate(_navFnc);

              break;
            }

          case 'pickerSwitch':
            this._showMode(1);

            break;

          case 'selectMonth':
            {
              var month = $(e.target).closest('tbody').find('span').index($(e.target));

              this._viewDate.month(month);

              if (this.currentViewMode === DateTimePicker.MinViewModeNumber) {
                this._setValue(lastPicked.clone().year(this._viewDate.year()).month(this._viewDate.month()), this._getLastPickedDateIndex());

                if (!this._options.inline) {
                  this.hide();
                }
              } else {
                this._showMode(-1);

                this._fillDate();
              }

              this._viewUpdate('M');

              break;
            }

          case 'selectYear':
            {
              var year = parseInt($(e.target).text(), 10) || 0;

              this._viewDate.year(year);

              if (this.currentViewMode === DateTimePicker.MinViewModeNumber) {
                this._setValue(lastPicked.clone().year(this._viewDate.year()), this._getLastPickedDateIndex());

                if (!this._options.inline) {
                  this.hide();
                }
              } else {
                this._showMode(-1);

                this._fillDate();
              }

              this._viewUpdate('YYYY');

              break;
            }

          case 'selectDecade':
            {
              var _year = parseInt($(e.target).data('selection'), 10) || 0;

              this._viewDate.year(_year);

              if (this.currentViewMode === DateTimePicker.MinViewModeNumber) {
                this._setValue(lastPicked.clone().year(this._viewDate.year()), this._getLastPickedDateIndex());

                if (!this._options.inline) {
                  this.hide();
                }
              } else {
                this._showMode(-1);

                this._fillDate();
              }

              this._viewUpdate('YYYY');

              break;
            }

          case 'selectDay':
            {
              var day = this._viewDate.clone();

              if ($(e.target).is('.old')) {
                day.subtract(1, 'M');
              }

              if ($(e.target).is('.new')) {
                day.add(1, 'M');
              }

              var selectDate = day.date(parseInt($(e.target).text(), 10)),
                  index;

              if (this._options.allowMultidate) {
                index = this._datesFormatted.indexOf(selectDate.format('YYYY-MM-DD'));

                if (index !== -1) {
                  this._setValue(null, index); //deselect multidate

                } else {
                  this._setValue(selectDate, this._getLastPickedDateIndex() + 1);
                }
              } else {
                this._setValue(selectDate, this._getLastPickedDateIndex());
              }

              if (!this._hasTime() && !this._options.keepOpen && !this._options.inline && !this._options.allowMultidate) {
                this.hide();
              }

              break;
            }

          case 'incrementHours':
            {
              var newDate = lastPicked.clone().add(1, 'h');

              if (this._isValid(newDate, 'h')) {
                this._setValue(newDate, this._getLastPickedDateIndex());
              }

              break;
            }

          case 'incrementMinutes':
            {
              var _newDate = lastPicked.clone().add(this._options.stepping, 'm');

              if (this._isValid(_newDate, 'm')) {
                this._setValue(_newDate, this._getLastPickedDateIndex());
              }

              break;
            }

          case 'incrementSeconds':
            {
              var _newDate2 = lastPicked.clone().add(1, 's');

              if (this._isValid(_newDate2, 's')) {
                this._setValue(_newDate2, this._getLastPickedDateIndex());
              }

              break;
            }

          case 'decrementHours':
            {
              var _newDate3 = lastPicked.clone().subtract(1, 'h');

              if (this._isValid(_newDate3, 'h')) {
                this._setValue(_newDate3, this._getLastPickedDateIndex());
              }

              break;
            }

          case 'decrementMinutes':
            {
              var _newDate4 = lastPicked.clone().subtract(this._options.stepping, 'm');

              if (this._isValid(_newDate4, 'm')) {
                this._setValue(_newDate4, this._getLastPickedDateIndex());
              }

              break;
            }

          case 'decrementSeconds':
            {
              var _newDate5 = lastPicked.clone().subtract(1, 's');

              if (this._isValid(_newDate5, 's')) {
                this._setValue(_newDate5, this._getLastPickedDateIndex());
              }

              break;
            }

          case 'togglePeriod':
            {
              this._setValue(lastPicked.clone().add(lastPicked.hours() >= 12 ? -12 : 12, 'h'), this._getLastPickedDateIndex());

              break;
            }

          case 'togglePicker':
            {
              var $this = $(e.target),
                  $link = $this.closest('a'),
                  $parent = $this.closest('ul'),
                  expanded = $parent.find('.show'),
                  closed = $parent.find('.collapse:not(.show)'),
                  $span = $this.is('span') ? $this : $this.find('span');
              var collapseData;

              if (expanded && expanded.length) {
                collapseData = expanded.data('collapse');

                if (collapseData && collapseData.transitioning) {
                  return true;
                }

                if (expanded.collapse) {
                  // if collapse plugin is available through bootstrap.js then use it
                  expanded.collapse('hide');
                  closed.collapse('show');
                } else {
                  // otherwise just toggle in class on the two views
                  expanded.removeClass('show');
                  closed.addClass('show');
                }

                $span.toggleClass(this._options.icons.time + ' ' + this._options.icons.date);

                if ($span.hasClass(this._options.icons.date)) {
                  $link.attr('title', this._options.tooltips.selectDate);
                } else {
                  $link.attr('title', this._options.tooltips.selectTime);
                }
              }
            }
            break;

          case 'showPicker':
            this.widget.find('.timepicker > div:not(.timepicker-picker)').hide();
            this.widget.find('.timepicker .timepicker-picker').show();
            break;

          case 'showHours':
            this.widget.find('.timepicker .timepicker-picker').hide();
            this.widget.find('.timepicker .timepicker-hours').show();
            break;

          case 'showMinutes':
            this.widget.find('.timepicker .timepicker-picker').hide();
            this.widget.find('.timepicker .timepicker-minutes').show();
            break;

          case 'showSeconds':
            this.widget.find('.timepicker .timepicker-picker').hide();
            this.widget.find('.timepicker .timepicker-seconds').show();
            break;

          case 'selectHour':
            {
              var hour = parseInt($(e.target).text(), 10);

              if (!this.use24Hours) {
                if (lastPicked.hours() >= 12) {
                  if (hour !== 12) {
                    hour += 12;
                  }
                } else {
                  if (hour === 12) {
                    hour = 0;
                  }
                }
              }

              this._setValue(lastPicked.clone().hours(hour), this._getLastPickedDateIndex());

              if (!this._isEnabled('a') && !this._isEnabled('m') && !this._options.keepOpen && !this._options.inline) {
                this.hide();
              } else {
                this._doAction(e, 'showPicker');
              }

              break;
            }

          case 'selectMinute':
            this._setValue(lastPicked.clone().minutes(parseInt($(e.target).text(), 10)), this._getLastPickedDateIndex());

            if (!this._isEnabled('a') && !this._isEnabled('s') && !this._options.keepOpen && !this._options.inline) {
              this.hide();
            } else {
              this._doAction(e, 'showPicker');
            }

            break;

          case 'selectSecond':
            this._setValue(lastPicked.clone().seconds(parseInt($(e.target).text(), 10)), this._getLastPickedDateIndex());

            if (!this._isEnabled('a') && !this._options.keepOpen && !this._options.inline) {
              this.hide();
            } else {
              this._doAction(e, 'showPicker');
            }

            break;

          case 'clear':
            this.clear();
            break;

          case 'close':
            this.hide();
            break;

          case 'today':
            {
              var todaysDate = this.getMoment();

              if (this._isValid(todaysDate, 'd')) {
                this._setValue(todaysDate, this._getLastPickedDateIndex());
              }

              break;
            }
        }

        return false;
      } //public

    }, {
      key: "hide",
      value: function hide() {
        var transitioning = false;

        if (!this.widget) {
          return;
        } // Ignore event if in the middle of a picker transition


        this.widget.find('.collapse').each(function () {
          var collapseData = $(this).data('collapse');

          if (collapseData && collapseData.transitioning) {
            transitioning = true;
            return false;
          }

          return true;
        });

        if (transitioning) {
          return;
        }

        if (this.component && this.component.hasClass('btn')) {
          this.component.toggleClass('active');
        }

        this.widget.hide();
        $(window).off('resize', this._place());
        this.widget.off('click', '[data-action]');
        this.widget.off('mousedown', false);
        this.widget.remove();
        this.widget = false;

        this._notifyEvent({
          type: DateTimePicker.Event.HIDE,
          date: this._getLastPickedDate().clone()
        });

        if (this.input !== undefined) {
          this.input.blur();
        }

        this._viewDate = this._getLastPickedDate().clone();
      }
    }, {
      key: "show",
      value: function show() {
        var currentMoment;
        var useCurrentGranularity = {
          'year': function year(m) {
            return m.month(0).date(1).hours(0).seconds(0).minutes(0);
          },
          'month': function month(m) {
            return m.date(1).hours(0).seconds(0).minutes(0);
          },
          'day': function day(m) {
            return m.hours(0).seconds(0).minutes(0);
          },
          'hour': function hour(m) {
            return m.seconds(0).minutes(0);
          },
          'minute': function minute(m) {
            return m.seconds(0);
          }
        };

        if (this.input !== undefined) {
          if (this.input.prop('disabled') || !this._options.ignoreReadonly && this.input.prop('readonly') || this.widget) {
            return;
          }

          if (this.input.val() !== undefined && this.input.val().trim().length !== 0) {
            this._setValue(this._parseInputDate(this.input.val().trim()), 0);
          } else if (this.unset && this._options.useCurrent) {
            currentMoment = this.getMoment();

            if (typeof this._options.useCurrent === 'string') {
              currentMoment = useCurrentGranularity[this._options.useCurrent](currentMoment);
            }

            this._setValue(currentMoment, 0);
          }
        } else if (this.unset && this._options.useCurrent) {
          currentMoment = this.getMoment();

          if (typeof this._options.useCurrent === 'string') {
            currentMoment = useCurrentGranularity[this._options.useCurrent](currentMoment);
          }

          this._setValue(currentMoment, 0);
        }

        this.widget = this._getTemplate();

        this._fillDow();

        this._fillMonths();

        this.widget.find('.timepicker-hours').hide();
        this.widget.find('.timepicker-minutes').hide();
        this.widget.find('.timepicker-seconds').hide();

        this._update();

        this._showMode();

        $(window).on('resize', {
          picker: this
        }, this._place);
        this.widget.on('click', '[data-action]', $.proxy(this._doAction, this)); // this handles clicks on the widget

        this.widget.on('mousedown', false);

        if (this.component && this.component.hasClass('btn')) {
          this.component.toggleClass('active');
        }

        this._place();

        this.widget.show();

        if (this.input !== undefined && this._options.focusOnShow && !this.input.is(':focus')) {
          this.input.focus();
        }

        this._notifyEvent({
          type: DateTimePicker.Event.SHOW
        });
      }
    }, {
      key: "destroy",
      value: function destroy() {
        this.hide(); //todo doc off?

        this._element.removeData(DateTimePicker.DATA_KEY);

        this._element.removeData('date');
      }
    }, {
      key: "disable",
      value: function disable() {
        this.hide();

        if (this.component && this.component.hasClass('btn')) {
          this.component.addClass('disabled');
        }

        if (this.input !== undefined) {
          this.input.prop('disabled', true); //todo disable this/comp if input is null
        }
      }
    }, {
      key: "enable",
      value: function enable() {
        if (this.component && this.component.hasClass('btn')) {
          this.component.removeClass('disabled');
        }

        if (this.input !== undefined) {
          this.input.prop('disabled', false); //todo enable comp/this if input is null
        }
      }
    }, {
      key: "toolbarPlacement",
      value: function toolbarPlacement(_toolbarPlacement) {
        if (arguments.length === 0) {
          return this._options.toolbarPlacement;
        }

        if (typeof _toolbarPlacement !== 'string') {
          throw new TypeError('toolbarPlacement() expects a string parameter');
        }

        if (toolbarPlacements.indexOf(_toolbarPlacement) === -1) {
          throw new TypeError("toolbarPlacement() parameter must be one of (".concat(toolbarPlacements.join(', '), ") value"));
        }

        this._options.toolbarPlacement = _toolbarPlacement;

        if (this.widget) {
          this.hide();
          this.show();
        }
      }
    }, {
      key: "widgetPositioning",
      value: function widgetPositioning(_widgetPositioning) {
        if (arguments.length === 0) {
          return this._options.widgetPositioning;
        }

        if (typeof _widgetPositioning !== 'string') {
          throw new TypeError('widgetPositioning() expects a string parameter');
        }

        this._options.widgetPositioning = _widgetPositioning;

        this._update();
      }
    }, {
      key: "widgetParent",
      value: function widgetParent(_widgetParent) {
        if (arguments.length === 0) {
          return this._options.widgetParent;
        }

        if (typeof _widgetParent === 'string') {
          _widgetParent = $(_widgetParent);
        }

        if (_widgetParent !== null && typeof _widgetParent !== 'string' && !(_widgetParent instanceof $)) {
          throw new TypeError('widgetParent() expects a string or a jQuery object parameter');
        }

        this._options.widgetParent = _widgetParent;

        if (this.widget) {
          this.hide();
          this.show();
        }
      } //static

    }], [{
      key: "_jQueryHandleThis",
      value: function _jQueryHandleThis(me, option, argument) {
        var data = $(me).data(DateTimePicker.DATA_KEY);

        if (_typeof(option) === 'object') {
          $.extend({}, DateTimePicker.Default, option);
        }

        if (!data) {
          data = new TempusDominusBootstrap4($(me), option);
          $(me).data(DateTimePicker.DATA_KEY, data);
        }

        if (typeof option === 'string') {
          if (data[option] === undefined) {
            throw new Error("No method named \"".concat(option, "\""));
          }

          if (argument === undefined) {
            return data[option]();
          } else {
            return data[option](argument);
          }
        }
      }
    }, {
      key: "_jQueryInterface",
      value: function _jQueryInterface(option, argument) {
        if (this.length === 1) {
          return TempusDominusBootstrap4._jQueryHandleThis(this[0], option, argument);
        }

        return this.each(function () {
          TempusDominusBootstrap4._jQueryHandleThis(this, option, argument);
        });
      }
    }]);

    return TempusDominusBootstrap4;
  }(DateTimePicker);
  /**
  * ------------------------------------------------------------------------
  * jQuery
  * ------------------------------------------------------------------------
  */


  $(document).on(DateTimePicker.Event.CLICK_DATA_API, DateTimePicker.Selector.DATA_TOGGLE, function () {
    var $target = getSelectorFromElement($(this));

    if ($target.length === 0) {
      return;
    }

    TempusDominusBootstrap4._jQueryInterface.call($target, 'toggle');
  }).on(DateTimePicker.Event.CHANGE, ".".concat(DateTimePicker.ClassName.INPUT), function (event) {
    var $target = getSelectorFromElement($(this));

    if ($target.length === 0) {
      return;
    }

    TempusDominusBootstrap4._jQueryInterface.call($target, '_change', event);
  }).on(DateTimePicker.Event.BLUR, ".".concat(DateTimePicker.ClassName.INPUT), function (event) {
    var $target = getSelectorFromElement($(this)),
        config = $target.data(DateTimePicker.DATA_KEY);

    if ($target.length === 0) {
      return;
    }

    if (config._options.debug || window.debug) {
      return;
    }

    TempusDominusBootstrap4._jQueryInterface.call($target, 'hide', event);
  }).on(DateTimePicker.Event.KEYDOWN, ".".concat(DateTimePicker.ClassName.INPUT), function (event) {
    var $target = getSelectorFromElement($(this));

    if ($target.length === 0) {
      return;
    }

    TempusDominusBootstrap4._jQueryInterface.call($target, '_keydown', event);
  }).on(DateTimePicker.Event.KEYUP, ".".concat(DateTimePicker.ClassName.INPUT), function (event) {
    var $target = getSelectorFromElement($(this));

    if ($target.length === 0) {
      return;
    }

    TempusDominusBootstrap4._jQueryInterface.call($target, '_keyup', event);
  }).on(DateTimePicker.Event.FOCUS, ".".concat(DateTimePicker.ClassName.INPUT), function (event) {
    var $target = getSelectorFromElement($(this)),
        config = $target.data(DateTimePicker.DATA_KEY);

    if ($target.length === 0) {
      return;
    }

    if (!config._options.allowInputToggle) {
      return;
    }

    TempusDominusBootstrap4._jQueryInterface.call($target, 'show', event);
  });
  $.fn[DateTimePicker.NAME] = TempusDominusBootstrap4._jQueryInterface;
  $.fn[DateTimePicker.NAME].Constructor = TempusDominusBootstrap4;

  $.fn[DateTimePicker.NAME].noConflict = function () {
    $.fn[DateTimePicker.NAME] = JQUERY_NO_CONFLICT;
    return TempusDominusBootstrap4._jQueryInterface;
  };

  return TempusDominusBootstrap4;
}(jQuery);

}();
