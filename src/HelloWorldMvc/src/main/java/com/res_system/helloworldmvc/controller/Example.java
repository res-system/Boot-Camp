package com.res_system.helloworldmvc.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.res_system.commons.mvc.controller.response.HtmlResponse;
import com.res_system.commons.mvc.model.form.FormUtil;
import com.res_system.commons.mvc.view.ThAppTemplateEngine;
import com.res_system.helloworldmvc.commons.controller.interceptor.Logging;
import com.res_system.helloworldmvc.model.example.ExampleForm;
import com.res_system.helloworldmvc.model.example.ExampleModel;
import com.res_system.helloworldmvc.model.example.TestConversationBean;
import com.res_system.helloworldmvc.model.example.TestRequestBean;
import com.res_system.helloworldmvc.model.example.TestSessionBean;

/**
 * <pre>
 * コントローラークラス(テスト用).
 * </pre>
 * @author res.
 */
@RequestScoped
@Path("/example")
@Logging
public class Example {

    //---------------------------------------------- static [private].
    /** ロガー. */
    private static final Logger log = LogManager.getLogger(Example.class);



    //---------------------------------------------- [private] モデルクラス.
    /** モデルクラス(業務処理). */
    @Inject
    private ExampleModel model;
    /** Sessionスコープ確認モデルクラス. */
    @Inject
    private TestSessionBean sessionBean;
    /** Requestスコープ確認モデルクラス. */
    @Inject
    private TestRequestBean requestBean;
    /** Conversationスコープ確認モデルクラス. */
    @Inject
    private TestConversationBean conversationBean;

    @Inject
    private ThAppTemplateEngine thAppTemplateEngine;



    //---------------------------------------------- [private] Context.
    @Context
    private HttpServletRequest httpRequest;
    @Context
    private HttpServletResponse httpResponse;
    @Context
    private ServletContext servletContext;
    @Context
    private UriInfo uriInfo;



    //---------------------------------------------- [public] アクション処理(@GET).
    /**
     * 表示確認.
     * @return HtmlResponse.
     * @throws Exception
     */
    @GET
    public HtmlResponse html() throws Exception {
        log.debug("action: html ----------------");
        ExampleForm form = FormUtil.make(ExampleForm.class);
        return showView(form);
    }



    //---------------------------------------------- [public] アクション処理(@POST).
    /**
     * 画面データ受取確認.
     * @param params POSTデータ.
     * @return HtmlResponse.
     * @throws Exception
     */
    @POST
    @Path("/html_response_post")
    public HtmlResponse htmlResponsePost(MultivaluedMap<String, String> params) throws Exception {
        log.debug("action: html_response_post ----------------");
        ExampleForm form = FormUtil.make(ExampleForm.class, params);
        form.setResult("post:" + form.getMessage());
        return showView(form);
    }


    /**
     * CDIのスコープ確認(session/request).
     * @param params POSTデータ.
     * @return HtmlResponse.
     * @throws Exception
     */
    @POST
    @Path("/check_cdi_scope1")
    public HtmlResponse doCheckCdiScope1(MultivaluedMap<String, String> params) throws Exception {
        log.debug("action: check_cdi_scope ----------------");
        ExampleForm form = FormUtil.make(ExampleForm.class, params);
        if ("[SET]".equals(params.get("submit").toString())) {
            // POSTデータをCDIモジュールに設定。
            sessionBean.setMessage(form.getMessage());
            requestBean.setMessage(form.getMessage());
        }
        // CDIモジュールの内容確認.
        form.setResult("session:" + sessionBean.getMessage() + "(常に保持します)" 
                + " / request:" + requestBean.getMessage() + "(GET時は取得されません)");
        return showView(form);
    }

    /**
     * CDIのスコープ確認(conversation).
     * @param params POSTデータ.
     * @return HtmlResponse.
     * @throws Exception
     */
    @POST
    @Path("/check_cdi_scope2")
    public HtmlResponse doCheckCdiScope2(MultivaluedMap<String, String> params) throws Exception {
        log.debug("action: check_cdi_scope ----------------");
        ExampleForm form = FormUtil.make(ExampleForm.class, params);
        if ("[START]".equals(params.get("submit").toString())) {
            conversationBean.begin();
        } else if ("[END]".equals(params.get("submit").toString())) {
            conversationBean.end();
        }
        // CDIモジュールの内容確認.
        form.setResult("conversation:" + conversationBean.countUp());
        return showView(form);
    }


    /**
     * DBアクセス確認.
     * @param params POSTデータ.
     * @return HtmlResponse.
     * @throws Exception
     */
    @POST
    @Path("/check_db")
    public HtmlResponse doCheckDb(MultivaluedMap<String, String> params) throws Exception {
        log.debug("action: check_db ----------------");
        ExampleForm form = FormUtil.make(ExampleForm.class, params);
        if ("[FIND]".equals(params.get("submit").toString())) {
            model.find(form);
        } else if ("[UPDATE]".equals(params.get("submit").toString())) {
            model.update(form);
        }
        return showView(form);
    }


    //---------------------------------------------- [public] アクション処理(@POST[JSON]).
    /**
     * JSONレスポンス確認.
     * @param params POSTデータ.
     * @return HtmlResponse.
     * @throws Exception
     */
    @POST
    @Path("/json_post")
    @Produces(MediaType.APPLICATION_JSON)
    public ExampleForm jsonPost(MultivaluedMap<String, String> params) throws Exception {
        log.debug("action:json_post");
        ExampleForm form = new ExampleForm();
        form.setMessage("json_post");
        return form;
    }


    //---------------------------------------------- [public] アクション処理(@POST[例外]).
    /**
     * 例外マッピング確認.
     * @param params POSTデータ.
     * @return HtmlResponse.
     * @throws Exception
     */
    @POST
    @Path("/exception_post")
    public ExampleForm exceptionPost(MultivaluedMap<String, String> params) throws Exception {
        log.debug("action:exception_post");
        throw new Exception("Exceptionテスト");
    }


    //---------------------------------------------- [public] アクション処理(@POST[Redirect]).
    /**
     * Redirect確認.
     * @param params
     * @return
     * @throws Exception
     */
    @POST
    @Path("/redirect_post")
    public HtmlResponse redirectPost(MultivaluedMap<String, String> params) throws Exception {
        log.debug("action:redirect_post");
        return new HtmlResponse().Redirect("/crud");
    }


    //---------------------------------------------- [public] アクション処理(@POST[HTML]).
    /**
     * HTMLテキストレスポンス確認.
     * @param params
     * @return
     * @throws Exception
     */
    @POST
    @Path("/html_post")
    public String htmlPost(MultivaluedMap<String, String> params) throws Exception {
        log.debug("action:html_post");
        ExampleForm form = new ExampleForm();
        form.setMessage("html_post");
        return thAppTemplateEngine.makeHtmlText(new HtmlResponse("example/index").add("form", form), httpRequest, httpResponse, servletContext);
    }



    //---------------------------------------------- [private] 処理.
    /**
     * Viewの表示処理.
     * @param form
     * @return
     */
    private HtmlResponse showView(final ExampleForm form) {
        conversationBean.getCid();
        return new HtmlResponse("example/index").add("form", form);
    }
}