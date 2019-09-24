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

import com.res_system.commons.mvc.model.IListItem;

/**
 * <pre>
 * 種別クラス インターフェース.
 * </pre>
 * @author res.
 */
public interface IKind {
    /** 値 を取得します. */
    String getValue();
    /** 表示文字 を取得します. */
    String getText();
    /** 値のチェックします. */
    boolean equals(final String code);
    /** リストオブジェクトに変換します. */
    IListItem toItem();
}
