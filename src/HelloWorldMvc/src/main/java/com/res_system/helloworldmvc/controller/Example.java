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



    //---------------------------------------------- [public] アクション処理.
    /**
     * 表示確認.
     * @return HtmlResponse.
     * @throws Exception
     */
    @GET
    public HtmlResponse html() throws Exception {
        log.debug("action:html");
        ExampleForm form = FormUtil.make(ExampleForm.class);
        return new HtmlResponse("example/index").add("form", form);
    }

    /**
     * 画面データ受取確認.
     * @param params POSTデータ.
     * @return HtmlResponse.
     * @throws Exception
     */
    @POST
    @Path("/html_response_post")
    public HtmlResponse htmlResponsePost(MultivaluedMap<String, String> params) throws Exception {
        log.debug("html_response_post");
        ExampleForm form = FormUtil.make(ExampleForm.class, params);
        // POSTデータをCDIモジュールに設定。
        sessionBean.setMessage(form.getMessage());
        requestBean.setMessage(form.getMessage());
        // CDIモジュールの内容確認.
        form.setMessage("session:" + sessionBean.getMessage() + " / request:" + requestBean.getMessage());
        return new HtmlResponse("example/index").add("form", form);
    }

    /**
     * CDIのスコープ確認.
     * @return HtmlResponse.
     * @throws Exception
     */
    @POST
    @Path("/reload")
    public HtmlResponse reload() throws Exception {
        log.debug("action:reload");
        ExampleForm form = FormUtil.make(ExampleForm.class);
        // CDIモジュールの内容確認.
        form.setMessage("session:" + sessionBean.getMessage() + " / request:" + requestBean.getMessage());
        return new HtmlResponse("example/index").add("form", form);
    }

    /**
     * DBアクセス確認(取得).
     * @return HtmlResponse.
     * @throws Exception
     */
    @POST
    @Path("/find")
    public HtmlResponse find() throws Exception {
        log.debug("action:find");
        ExampleForm form = FormUtil.make(ExampleForm.class);
        model.find(form);
        return new HtmlResponse("example/index").add("form", form);
    }

    /**
     * DBアクセス確認(更新).
     * @param params POSTデータ.
     * @return HtmlResponse.
     * @throws Exception
     */
    @POST
    @Path("/update")
    public HtmlResponse update(MultivaluedMap<String, String> params) throws Exception {
        log.debug("action:update");
        ExampleForm form = FormUtil.make(ExampleForm.class, params);
        model.update(form);
        return new HtmlResponse("example/index").add("form", form);
    }

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

}