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
package com.res_system.commons.mvc.view.dialect;

import java.util.HashSet;
import java.util.Set;

import org.thymeleaf.context.IExpressionContext;
import org.thymeleaf.dialect.IExpressionObjectDialect;
import org.thymeleaf.expression.IExpressionObjectFactory;

import com.res_system.commons.mvc.view.thexpressionobjects.ReThHelper;

/**
 * <pre>
 * Thymeleaf 拡張設定.
 * ※ ReThHelperクラスをTemplateEngineに設定するためのクラスです。
 * </pre>
 * @author res.
 */
public class ReThHelperDialect implements IExpressionObjectDialect {

    /** Thymeleafで使用したい名前. */
    private static final String DIALECT_NAME = "h";

    /** 名前管理するSet. */
    @SuppressWarnings("serial")
    private static final Set<String> ALL_EXPRESSION_NAMES =
            new HashSet<String>() {
                {add(DIALECT_NAME);}
            };

    @Override
    public IExpressionObjectFactory getExpressionObjectFactory() {
        return new IExpressionObjectFactory() {

            @Override
            public Set<String> getAllExpressionObjectNames() {
                return ALL_EXPRESSION_NAMES;
            }

            @Override
            public Object buildObject(IExpressionContext context, String expressionObjectName) {
                // 独自Utilityのインスタンスと名前を紐付け
                if(expressionObjectName.equals(DIALECT_NAME)){
                    return new ReThHelper();
                }
                return null;
            }

            @Override
            public boolean isCacheable(String expressionObjectName) {
                // 必要に応じて実装
                return false;
            }

        };
    }

    @Override
    public String getName() {
        return DIALECT_NAME;
    }
}