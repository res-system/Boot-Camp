package com.res_system.mvc_skeleton.commons.view.dialect;

import java.util.HashSet;
import java.util.Set;

import org.thymeleaf.context.IExpressionContext;
import org.thymeleaf.dialect.IExpressionObjectDialect;
import org.thymeleaf.expression.IExpressionObjectFactory;

import com.res_system.mvc_skeleton.commons.view.thexpressionobjects.ExThHelper;

/**
 * Thymeleaf 拡張設定クラス.
 * <pre>
 * ※ ExThHelperクラスをTemplateEngineに設定するためのクラスです。
 * </pre>
 * @author res.
 */
public class ExThHelperDialect implements IExpressionObjectDialect {

    /** Thymeleafで使用したい名前. */
    private static final String DIALECT_NAME = "exh";

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
                    return new ExThHelper();
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