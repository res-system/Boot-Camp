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

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.dialect.IDialect;

import com.res_system.commons.mvc.controller.response.HtmlResponse;

/**
 * <pre>
 * Thymeleaf アプリケーション用テンプレートエンジン処理クラス.
 * </pre>
 * @author res.
 */
@Dependent
public class ThAppTemplateEngine {

    //---------------------------------------------- instance variable [private].
   /** Thymeleaf TemplateEngine. */
    private TemplateEngine templateEngine;



    //---------------------------------------------- instance variable [private] モデルクラス.
    /** Thymeleaf アプリケーション用設定クラス. */
    @Inject
    private ThymeleafAppSetting thAppSetting;



    //---------------------------------------------- constructor.
    /**
     * コンストラクタ.
     */
    public ThAppTemplateEngine() {
        super();
    }



    //---------------------------------------------- instance method [public] インジェクション開始/終了.
    /** インジェクション完了後、コールバック. */
    @PostConstruct
    public void setup() {
    }

    /** インスタンスの破棄時、コールバック. */
    @PreDestroy
    public void destroy() {
    }



    //---------------------------------------------- instance method [public] 初期化処理.
    /**
     * 初期化します.
     */
    public TemplateEngine getTemplateEngine() {
        //
        // TemplateEngine
        //
        // http://www.thymeleaf.org/apidocs/thymeleaf/3.0.0.BETA01/org/thymeleaf/TemplateEngine.html
        // Creation and configuration of TemplateEngine instances is expensive,
        // so it is recommended to create only one instance of this class
        // (or at least one instance per dialect/configuration)
        // and use it to process multiple templates.
        //
        templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(thAppSetting.getTemplateResolver());
        for (IDialect dialect : thAppSetting.getDialects().values()) {
            templateEngine.addDialect(dialect);
        }
        return templateEngine;
    }



    //---------------------------------------------- instance method [public] HTML作成処理.
    /**
     * HTMLテキストを出力します(Context).
     * @param response HtmlResponse.
     * @param writer Writer.
     * @throws IOException
     */
    public void flushHtmlText(HtmlResponse response, Writer writer) throws IOException {

        // パラメタを設定します.
        Context context = new Context();
        Map<String, Object> params = response.getParams();
        if (params != null) {
            params.forEach((k, v) -> context.setVariable(k, v));
        }

        // HTMLを作成し、レスポンスに設定します.
        getTemplateEngine().process(response.getTemplate(), context, writer);
        writer.flush();

    }

    /**
     * HTMLテキストを出力します(WebContext).
     * @param response HtmlResponse.
     * @param writer Writer.
     * @param req HttpServletRequest.
     * @param res HttpServletResponse.
     * @param con ServletContext.
     * @throws IOException
     */
    public void flushHtmlText(HtmlResponse response, Writer writer,
            HttpServletRequest req, HttpServletResponse res, ServletContext con) throws IOException {

        // パラメタを設定します.
        WebContext webContext = new WebContext(req, res, con);
        Map<String, Object> params = response.getParams();
        if (params != null) {
            params.forEach((k, v) -> webContext.setVariable(k, v));
        }

        // HTMLを作成し、レスポンスに設定します.
        getTemplateEngine().process(response.getTemplate(), webContext, writer);
        writer.flush();

    }

    /**
     * HTMLテキストを作成します(Context).
     * @param response HtmlResponse.
     * @return HTMLテキスト.
     * @throws IOException
     */
    public String makeHtmlText(HtmlResponse response) throws IOException {

        // パラメタを設定します.
        Context context = new Context();
        Map<String, Object> params = response.getParams();
        if (params != null) {
            params.forEach((k, v) -> context.setVariable(k, v));
        }

        // HTMLを作成します.
        return getTemplateEngine().process(response.getTemplate(), context);

    }

    /**
     * HTMLテキストを作成します(WebContext).
     * @param response HtmlResponse.
     * @return HTMLテキスト.
     * @param req HttpServletRequest.
     * @param res HttpServletResponse.
     * @param con ServletContext.
     * @throws IOException
     */
    public String makeHtmlText(HtmlResponse response,
            HttpServletRequest req, HttpServletResponse res, ServletContext con) throws IOException {

        // パラメタを設定します.
        WebContext webContext = new WebContext(req, res, con);
        Map<String, Object> params = response.getParams();
        if (params != null) {
            params.forEach((k, v) -> webContext.setVariable(k, v));
        }

        // HTMLを作成します.
        return getTemplateEngine().process(response.getTemplate(), webContext);

    }

}