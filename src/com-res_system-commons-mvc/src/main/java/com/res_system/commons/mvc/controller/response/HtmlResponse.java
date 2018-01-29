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
package com.res_system.commons.mvc.controller.response;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * Html Response クラス.
 *
 * Htmlでのレスポンス用のデータクラスです.
 * </pre>
 * @author res.
 */
public class HtmlResponse {

    //---------------------------------------------- properies [private].
    /** テンプレート. */
    private String template;
    /** パラメタ. */
    private Map<String,Object> params;
    /** 遷移先URL. */
    private String redirectUrl;

    //-- setter / getter. --//
    /** テンプレート を取得します. */
    public String getTemplate() { return template; }
    /** テンプレート を設定します. */
    public void setTemplate(String template) { this.template = template; }
    /** パラメタ を取得します. */
    public Map<String, Object> getParams() { return params; }
    /** パラメタ を設定します. */
    public void setParams(Map<String, Object> params) { this.params = params; }
    /** 遷移先URL を取得します. */
    public String getRedirectUrl() { return redirectUrl; }



    //---------------------------------------------- constructor.
    /**
     * コンストラクタ.
     */
    public HtmlResponse() {
        this("");
    }

    /**
     * コンストラクタ.
     * @param template テンプレート.
     */
    public HtmlResponse(String template) {
        this.template = template;
        this.params = new HashMap<>();
    }



    //---------------------------------------------- [public] パラメタ処理.
    /**
     * パラメタの追加.
     * @param paramName パラメタ名.
     * @param paramValue パラメタ値.
     * @return 自インスタンス.
     */
    public HtmlResponse add(String paramName, Object paramValue) {
        this.params.put(paramName, paramValue);
        return this;
    }



    //---------------------------------------------- [public] 画面遷移処理.
    /**
     * 画面遷移処理.
     * @param url 遷移先URL.
     * @return 自インスタンス.
     */
    public HtmlResponse Redirect(String url) {
        this.redirectUrl = url;
        return this;
    }

}