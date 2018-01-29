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
 * input[checkbox]タグの属性(id,name,value)の設定を行います.
 *
 * 使用例）
 * [ re-th:checkbox = "~,~,~"     (フォーム名,フィールド名,フィールド値)]
 * 又は
 * [ re-th:checkbox = "~,~,~,~"   (フォーム名,データ名,フィールド名,フィールド値)]
 * 又は
 * [ re-th:checkbox = "~,~,~,~,~" (フォーム名,リスト名,リストIndex,フィールド名,フィールド値)]
 * </pre>
 * @author res.
 */
public class CheckboxProcessor extends AbstractAttributeTagProcessor {

    private static final String ATTR_NAME = "checkbox";
    private static final int PRECEDENCE = 10000;

    public CheckboxProcessor(final String dialectPrefix) {
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

    @Override
    protected void doProcess(
            final ITemplateContext context, final IProcessableElementTag tag,
            final AttributeName attributeName, final String attributeValue,
            final IElementTagStructureHandler structureHandler) {

        String listIndex = "";
        String id = "";
        String name = "";
        String value = "";
        String dataValue = "";
        String[] params = MvcUtil.splitAttrValues(attributeValue);

        if (params.length == 5) {
            value = MvcUtil.getValueString(context, params[4]);
            listIndex = MvcUtil.getValueString(context, String.format("${%s}", params[2]) );
            id = MvcUtil.getIdWithValue(params[1], listIndex, params[3], value);
            name = MvcUtil.getName(params[1], listIndex, params[3]);
            dataValue = MvcUtil.getValueString(context, String.format("${%s.%s}", params[0], name) );

        } else if (params.length == 4) {
            value = MvcUtil.getValueString(context, params[3]);
            id = MvcUtil.getIdWithValue(params[1], params[2], value);
            name = MvcUtil.getName(params[1], params[2]);
            dataValue = MvcUtil.getValueString(context, String.format("${%s.%s}", params[0], name) );

        } else if (params.length == 3) {
            value = MvcUtil.getValueString(context, params[2]);
            id = MvcUtil.getIdWithValue(params[1], value);
            name = MvcUtil.getName(params[1]);
            dataValue = MvcUtil.getValueString(context, String.format("${%s.%s}", params[0], name) );

        }
        if (value.equals(dataValue)) {
            structureHandler.setAttribute("checked", "checked");
        }

        structureHandler.setAttribute("id", id);
        structureHandler.setAttribute("name", name);
        structureHandler.setAttribute("value", HtmlEscape.escapeHtml5(value));
    }

}
