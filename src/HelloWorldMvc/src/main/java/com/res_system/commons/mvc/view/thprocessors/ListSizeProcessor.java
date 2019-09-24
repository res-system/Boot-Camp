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
package com.res_system.commons.mvc.view.thprocessors;

import java.util.List;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;
import org.unbescape.html.HtmlEscape;

import com.res_system.commons.mvc.MvcUtil;

/**
 * <pre>
 * 入力リストのサイズを保存するhiddenタグを設定します.
 *
 * 使用例）
 * [ re-th:listSize = "~,~" (フォーム名,リスト名)]
 * </pre>
 * @author res.
 */
public class ListSizeProcessor extends AbstractAttributeTagProcessor {

    private static final String ATTR_NAME = "listSize";
    private static final int PRECEDENCE = 10000;

    public ListSizeProcessor(final String dialectPrefix) {
        super(
            TemplateMode.HTML, // このプロセッサはHTMLモードにのみ適用されます.
            dialectPrefix,     // マッチングのために名前に適用される接頭辞.
            null,              // タグ名なし：タグ名と一致.
            false,             // タグ名に適用する接頭辞はありません.
            ATTR_NAME,         // 一致する属性の名前.
            true,              // 属性名に方言接頭辞を適用する.
            PRECEDENCE,        // 優先順位（方言自身の優先順位の中で）.
            true);             // 後に一致した属性を削除する.
    }

    @SuppressWarnings("rawtypes")
    @Override
    protected void doProcess(
            final ITemplateContext context, final IProcessableElementTag tag,
            final AttributeName attributeName, final String attributeValue,
            final IElementTagStructureHandler structureHandler) {

        String id = "";
        String name = "";
        String value = "0";
        String[] params = MvcUtil.splitAttrValues(attributeValue);

        if (params.length == 2) {
            id = MvcUtil.getId(params[1]) + "_size";
            name = params[1].trim() + "_size";
            value = MvcUtil.getValueString(context, String.format("${%s.%s}", params[0], name) );
            if (value == null || value.length() <= 0) {
                Object valueObject = MvcUtil.getValue(context, String.format("${%s.%s}", params[0].trim(), params[1].trim()) );
                if (valueObject != null && valueObject instanceof List) {
                    value = String.valueOf(((List) valueObject).size());
                } else {
                    value = "0";
                }
            }
        }

        structureHandler.setAttribute("id", id);
        structureHandler.setAttribute("name", name);
        structureHandler.setAttribute("value", HtmlEscape.escapeHtml5(value));
    }

}
