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
package com.res_system.commons.mvc.model.kind;

import java.util.ArrayList;
import java.util.List;

import com.res_system.commons.mvc.model.IListItem;

/**
 * <pre>
 * 種別クラス用共通処理クラス.
 * </pre>
 * @author res.
 */
public final class KindUtil {

    //---------------------------------------------- constructor.
    /** コンストラクタ. */
    private KindUtil() {}



    //---------------------------------------------- static [public] 共通処理.
    /**
     * 表示文字 を取得します.
     * @param values 種別リスト.
     * @param code 対象のコード.
     * @return 表示文字.
     */
    public static String getText(IKind[] values, final String code) {
        if (values != null && values.length > 0
                && code != null && code.length() > 0) {
            for (IKind kind : values) {
                if (kind.equals(code)) {
                    return kind.getText();
                }
            }
        }
        return "";
    }

    /**
     * 存在チェックします.
     * @param values 種別リスト.
     * @param code 対象のコード.
     * @return 表示文字.
     */
    public static boolean isExist(IKind[] values, final String code) {
        if (!"".equals(getText(values, code))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * リスト を取得します.
     * @param values 種別リスト.
     * @param code 対象のコード.
     * @return 表示文字.
     */
    public static List<IListItem> toItemList(IKind[] values) {
        List<IListItem> itemList = new ArrayList<>();
        for (IKind kind : values) {
            itemList.add(kind.toItem());
        }
        return itemList;
    }

}
