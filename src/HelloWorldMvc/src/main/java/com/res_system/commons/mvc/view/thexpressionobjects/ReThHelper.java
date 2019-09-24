/**
 *   Copyright (c) 2018 株式会社リスタート.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package com.res_system.commons.mvc.view.thexpressionobjects;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.res_system.commons.mvc.MvcUtil;
import com.res_system.commons.mvc.model.IListItem;

/**
 * <pre>
 * Thymeleaf ヘルパークラス.
 * </pre>
 * @author res.
 */
public final class ReThHelper {

    //---------------------------------------------- [public] id/name.
    /**
     * name属性を作成します.
     *
     * <br />使用例）
     * <br />[ th:name="${#h.name('~','~')}" (フォーム名,フィールド名)]
     *
     * @param formName フォーム名.
     * @param fieldName フィールド名.
     * @return name属性値.
     */
    public String name(String formName, String fieldName) {
        return MvcUtil.getName(fieldName);
    }

    /**
     * name属性を作成します.
     *
     * <br />使用例）
     * <br />[ th:name="${#h.name('~','~','~')}" (フォーム名,データ名,フィールド名)]
     *
     * @param formName フォーム名.
     * @param dataName データ名.
     * @param fieldName フィールド名.
     * @return name属性値.
     */
    public String name(String formName, String dataName, String fieldName) {
        return MvcUtil.getName(dataName, fieldName);
    }

    /**
     * name属性を作成します.
     *
     * <br />使用例）
     * <br />[ th:name="${#h.name('~','~',~,'~')}" (フォーム名,リスト名,リストIndex,フィールド名)]
     *
     * @param formName フォーム名.
     * @param listName リスト名.
     * @param listIndex リストIndex.
     * @param fieldName フィールド名.
     * @return name属性値.
     */
    public String name(String formName, String listName, int listIndex, String fieldName) {
        return MvcUtil.getName(listName, String.valueOf(listIndex), fieldName);
    }


    /**
     * id属性を作成します.
     *
     * <br />使用例）
     * <br />[ th:id="${#h.id('~','~')}" (フォーム名,フィールド名)]
     *
     * @param formName フォーム名.
     * @param fieldName フィールド名.
     * @return id属性値.
     */
    public String id(String formName, String fieldName) {
        return MvcUtil.getId(fieldName);
    }

    /**
     * id属性を作成します.
     *
     * <br />使用例）
     * <br />[ th:id="${#h.id('~','~','~')}" (フォーム名,データ名,フィールド名)]
     *
     * @param formName フォーム名.
     * @param dataName データ名.
     * @param fieldName フィールド名.
     * @return name属性値.
     */
    public String id(String formName, String dataName, String fieldName) {
        return MvcUtil.getId(dataName, fieldName);
    }

    /**
     * id属性を作成します.
     *
     * <br />使用例）
     * <br />[ th:id="${#h.id('~','~',~,'~')}" (フォーム名,リスト名,リストIndex,フィールド名)]
     *
     * @param formName フォーム名.
     * @param listName リスト名.
     * @param listIndex リストIndex.
     * @param fieldName フィールド名.
     * @return id属性値.
     */
    public String id(String formName, String listName, int listIndex, String fieldName) {
        return MvcUtil.getId(listName, String.valueOf(listIndex), fieldName);
    }


    /**
     * id属性を作成します(値付き).
     *
     * <br />使用例）
     * <br />[ th:id="${#h.idWithValue('~','~','~')}" (フォーム名,フィールド名,フィールド値)]
     *
     * @param formName フォーム名.
     * @param fieldName フィールド名.
     * @param value フィールド値.
     * @return id属性値.
     */
    public String idWithValue(String formName, String fieldName, String value) {
        return MvcUtil.getIdWithValue(fieldName, value);
    }

    /**
     * id属性を作成します(値付き).
     *
     * <br />使用例）
     * <br />[ th:id="${#h.idWithValue('~','~','~','~')}" (フォーム名,データ名,フィールド名,フィールド値)]
     *
     * @param formName フォーム名.
     * @param dataName データ名.
     * @param fieldName フィールド名.
     * @param value フィールド値.
     * @return name属性値.
     */
    public String idWithValue(String formName, String dataName, String fieldName, String value) {
        return MvcUtil.getIdWithValue(dataName, fieldName, value);
    }

    /**
     * id属性を作成します(値付き).
     *
     * <br />使用例）
     * <br />[ th:id="${#h.idWithValue('~','~',~,'~','~')}" (フォーム名,リスト名,リストIndex,フィールド名,フィールド値)]
     *
     * @param formName フォーム名.
     * @param listName リスト名.
     * @param listIndex リストIndex.
     * @param fieldName フィールド名.
     * @param value フィールド値.
     * @return id属性値.
     */
    public String idWithValue(String formName, String listName, int listIndex, String fieldName, String value) {
        return MvcUtil.getIdWithValue(listName, String.valueOf(listIndex), fieldName, value);
    }



    //---------------------------------------------- [public] list.
    /**
     * リストの値を取得します.
     * <br />使用例）
     * <br />[ th:text="${#h.list(~,~)}" (リスト,リストIndex)]
     *
     * @param list リスト.
     * @param index リストIndex.
     * @return 値.
     */
    public String list(List<String> list, int index) {
        if (!MvcUtil.isEmpty(list) && 0 <= index && index < list.size()) {
            String value = list.get(index);
            if (!MvcUtil.isEmpty(value)) {
                return value;
            } else {
                return "";
            }
        }
        return "";
    }

    /**
     * リストの値を取得します.
     * <br />使用例）
     * <br />[ th:text="${#h.listValue(~,~)}" (リスト,リストIndex)]
     *
     * @param list リスト.
     * @param index リストIndex.
     * @return 値.
     */
    public String listValue(List<IListItem> list, int index) {
        if (!MvcUtil.isEmpty(list) && 0 <= index && index < list.size()) {
            IListItem item = list.get(index);
            if (item != null && !MvcUtil.isEmpty(item.getValue())) {
                return item.getValue();
            } else {
                return "";
            }
        }
        return "";
    }

    /**
     * リストアイテムより対象の値の表示文字列を取得します.
     * <br />使用例）
     * <br />[ th:text="${#h.listText(~,~)}" (リスト,対象値)]
     *
     * @param list リストアイテム.
     * @param value 対象値.
     * @return 対象の表示文字列.
     */
    public String listText(List<IListItem> list, String value) {
        if (!MvcUtil.isEmpty(list) && !MvcUtil.isEmpty(value)) {
            for (IListItem item : list) {
                if (item != null && value.equals(item.getValue())) {
                    String text = item.getText();
                    if (!MvcUtil.isEmpty(text)) {
                        return text;
                    } else {
                        return "";
                    }
                }
            }
        }
        return "";
    }



    //---------------------------------------------- [public] check.
     /**
     * 対象の値がリスト内に存在する事を確認します.
     * <br />使用例）
     * <br />[ th:checked="${#h.isExists(~, ~)}" (リスト,対象値)]
     *
     * @param list リスト.
     * @param targetValue 対象値.
     * @return 存在有無(true:有 /false:無).
     */
    public boolean isExists(List<String> list, String targetValue) {
        if (!MvcUtil.isEmpty(list) && !MvcUtil.isEmpty(targetValue)) {
            return list.contains(targetValue);
        }
        return false;
    }

    /**
     * 対象の値がリストアイテム内に存在する事を確認します.
     * <br />使用例）
     * <br />[ th:checked="${#h.isExistsItem(~, ~)}" (リストアイテム,対象値)]
     *
     * @param list リストアイテム.
     * @param targetValue 対象値.
     * @return 存在有無(true:有 /false:無).
     */
    public boolean isExistsItem(List<IListItem> list, String targetValue) {
        if (!MvcUtil.isEmpty(list) && !MvcUtil.isEmpty(targetValue)) {
            for (IListItem item : list) {
                if (targetValue.equals(item.getValue())) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 対象の値がNULL又は空である事を確認します.
     * <br />使用例）
     * <br />[ th:if="${#h.isEmpty(~)}" (対象値)]
     *
     * @param targetValue 確認対象の値(ID).
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean isEmpty(Long targetValue) {
        return MvcUtil.isEmpty(targetValue);
    }

    /**
     * 対象の値がNULL又は空である事を確認します.
     * <br />使用例）
     * <br />[ th:if="${#h.isEmpty(~)}" (対象値)]
     *
     * @param targetValue 対象値.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean isEmpty(String targetValue) {
        return MvcUtil.isEmpty(targetValue);
    }

    /**
     * 対象の値がNULL又は空で無い事を確認します.
     * <br />使用例）
     * <br />[ th:if="${#h.isNotEmpty(~)}" (対象値)]
     *
     * @param targetValue 対象値.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean isNotEmpty(String targetValue) {
        return !MvcUtil.isEmpty(targetValue);
    }


    /**
     * 対象のリストがNULL又は空である事を確認します.
     * <br />使用例）
     * <br />[ th:if="${#h.isEmpty(~)}" (対象リスト)]
     *
     * @param targetList 対象値.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean isEmpty(List<?> targetList) {
        return MvcUtil.isEmpty(targetList);
    }

    /**
     * 対象のリストがNULL又は空で無い事を確認します.
     * <br />使用例）
     * <br />[ th:if="${#h.isNotEmpty(~)}" (対象リスト)]
     *
     * @param targetList 対象値.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean isNotEmpty(List<?> targetList) {
        return !MvcUtil.isEmpty(targetList);
    }


    /**
     * 対象のリストがNULL又は空である事を確認します.
     * <br />使用例）
     * <br />[ th:if="${#h.isEmpty(~)}" (対象リスト)]
     *
     * @param targetMap 対象値.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean isEmpty(Map<?,?> targetMap) {
        return MvcUtil.isEmpty(targetMap);
    }

    /**
     * 対象のリストがNULL又は空で無い事を確認します.
     * <br />使用例）
     * <br />[ th:if="${#h.isNotEmpty(~)}" (対象リスト)]
     *
     * @param targetMap 対象値.
     * @return 確認結果(true:OK, false:NG).
     */
    public boolean isNotEmpty(Map<?,?> targetMap) {
        return !MvcUtil.isEmpty(targetMap);
    }



    //---------------------------------------------- [public] etc.
    /**
     * JSON文字列に変換します.
     * <br />使用例）
     * <br />[ th:text="${#h.json(~)}" (対象オブジェクト)]
     *
     * @param target 対象オブジェクト.
     * @return JSON文字列.
     */
    public String json(Object target) {
        try {
            String json = MvcUtil.toJson(target);
            return json;
        } catch (JsonProcessingException e) {
            // 無視.
        }
        return "";
    }

}
