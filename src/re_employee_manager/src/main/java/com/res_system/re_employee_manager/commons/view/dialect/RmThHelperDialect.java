package com.res_system.re_employee_manager.commons.view.dialect;

import java.util.HashSet;
import java.util.Set;

import org.thymeleaf.context.IExpressionContext;
import org.thymeleaf.dialect.IExpressionObjectDialect;
import org.thymeleaf.expression.IExpressionObjectFactory;

import com.res_system.re_employee_manager.commons.view.thexpressionobjects.RmThHelper;

/**
 * <pre>
 * 社員管理システム用Thymeleaf 拡張設定.
 * ※ RmThHelperクラスをTemplateEngineに設定するためのクラスです。
 * </pre>
 * @author res.
 */
public class RmThHelperDialect implements IExpressionObjectDialect {

    /** Thymeleafで使用したい名前. */
    private static final String DIALECT_NAME = "rmh";

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
                    return new RmThHelper();
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