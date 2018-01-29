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
package com.res_system.commons.mvc.view;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;

import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import com.res_system.commons.mvc.MvcUtil;
import com.res_system.commons.mvc.view.dialect.ReThHelperDialect;
import com.res_system.commons.mvc.view.dialect.ReThProcessorDialect;

/**
 * <pre>
 * Thymeleaf アプリケーション用設定クラス.
 * </pre>
 * @author res.
 */
@ApplicationScoped
public class ThymeleafAppSetting {

    //---------------------------------------------- const [public].
    /** テンプレートエンジン設定[MODE] */
    public static final String TEMPLATE_MODE = "HTML";
    /** テンプレートエンジン設定[PREFIX] */
    public static final String TEMPLATE_PREFIX = "/view/";
    /** テンプレートエンジン設定[SUFFIX] */
    public static final String TEMPLATE_SUFFIX = ".html";

    /** プロパティ項目名. */
    public static final String PROPERTY_ITEM_NAME = "com.res_system.commons.mvc.view.dialects";



    //---------------------------------------------- properies [private].
    /** ClassLoaderTemplateResolver. */
    ClassLoaderTemplateResolver templateResolver;
    /** Thymeleaf 拡張クラス設定. */
    private Map<String, IDialect> dialects;

    //-- setter / getter. --//
    /** ClassLoaderTemplateResolver を取得します. */
    public ClassLoaderTemplateResolver getTemplateResolver() { return templateResolver; }
    /** Thymeleaf 拡張クラス設定 を取得します. */
    public Map<String, IDialect> getDialects() { return dialects; }



    //---------------------------------------------- constructor.
    /**
     * コンストラクタ.
     */
    public ThymeleafAppSetting() {
        super();
    }



    //---------------------------------------------- [public] インジェクション開始/終了.
    /** インジェクション完了後、コールバック. */
    @PostConstruct
    public void setup() {
        initTemplateResolver();
        initDialects();
    }

    /** インスタンスの破棄時、コールバック. */
    @PreDestroy
    public void destroy() {
    }



    //---------------------------------------------- [public] 初期化処理.
    /**
     * Thymeleaf templateResolver を初期化します.
     */
    public void initTemplateResolver() {
        //
        // ClassLoaderTemplateResolver.
        //
        if (templateResolver == null) {
            templateResolver = new ClassLoaderTemplateResolver();
            templateResolver.setTemplateMode(ThymeleafAppSetting.TEMPLATE_MODE);
            templateResolver.setPrefix(ThymeleafAppSetting.TEMPLATE_PREFIX);
            templateResolver.setSuffix(ThymeleafAppSetting.TEMPLATE_SUFFIX);
        }
    }

    /**
     * Thymeleaf 拡張設定 を初期化します.
     */
    public void initDialects() {
        //
        // Thymeleaf 拡張設定.
        // プロパティ「com.res_system.common.mvc.view.dialects」で指定された拡張オブジェクトを設定します.
        // (カンマ区切りで複数件指定あり).
        //
        if (dialects == null) {
            dialects = new ConcurrentHashMap<>();
            dialects.put(ReThHelperDialect.class.getName(), new ReThHelperDialect());
            dialects.put(ReThProcessorDialect.class.getName(), new ReThProcessorDialect());
        }
        String dialectClassNames = MvcUtil.getPropertyValue(MvcUtil.PROPERTY_FILE_NAME, PROPERTY_ITEM_NAME, "");
        if (!MvcUtil.isEmpty(dialectClassNames)) {
            String[] classNames = dialectClassNames.split(",");
            for (String className : classNames) {
                if (!MvcUtil.isEmpty(className)) {
                    try {
                        String newClassName = MvcUtil.Trim(className);
                        if (!MvcUtil.isEmpty(newClassName)) {
                            if (!dialects.containsKey(newClassName)) {
                                Class<?> myClass = Class.forName(newClassName);
                                dialects.put(newClassName, (IDialect) myClass.newInstance());
                            }
                        }
                    } catch (Exception e) {
                        // uncheked.
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

}