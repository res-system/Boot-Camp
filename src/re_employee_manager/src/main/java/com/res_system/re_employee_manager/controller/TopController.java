package com.res_system.re_employee_manager.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import com.res_system.commons.mvc.controller.response.HtmlResponse;
import com.res_system.commons.mvc.model.form.FormUtil;
import com.res_system.re_employee_manager.commons.controller.interceptor.Controller;
import com.res_system.re_employee_manager.commons.model.AuthModel;
import com.res_system.re_employee_manager.commons.model.data.AjaxResponse;
import com.res_system.re_employee_manager.model.top.TopForm;
import com.res_system.re_employee_manager.model.top.TopModel;

/**
 * <pre>
 * TOP画面 コントローラークラス.
 * </pre>
 * @author res.
 */
@Path("/")
@Controller
@RequestScoped
public class TopController {

    //---------------------------------------------- const [private].
    /** テンプレート. */
    private static final String TEMPLATE = "top/index";



    //---------------------------------------------- [private] モデルクラス.
    /** メイン業務処理. */
    @Inject
    private TopModel model;

    /** 認証処理 モデルクラス. */
    @Inject
    private AuthModel authModel;



    //---------------------------------------------- [public] アクション.
    /** デフォルト アクション. */
    @GET
    public HtmlResponse defaultAction() throws Exception {
        return index();
    }

    /** 画面表示 アクション. */
    @Path("/index")
    @GET
    public HtmlResponse index() throws Exception {
        TopForm form = FormUtil.make(TopForm.class);
        if (authModel.isLoginAuthOKwithReload()) {
            // 認証あり.
            form.setNext("/main_menu");
            return new HtmlResponse().Redirect(form.getNext());
        } else {
            // 認証なし.
            return new HtmlResponse(TEMPLATE).add("form", form);
        }
    }

    /** ログイン アクション. */
    @Path("/login")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public AjaxResponse doLogin(MultivaluedMap<String, String> params) throws Exception {
        TopForm form = FormUtil.make(TopForm.class, params);
        if (model.checkInput(form) && model.doLogin(form)) {
            return new AjaxResponse(AjaxResponse.OK, form).setMessageList(model.getMessageList());
        } else {
            return new AjaxResponse(AjaxResponse.NG, form).setMessageList(model.getMessageList());
        }
    }

    /** 次画面遷移 アクション. */
    @Path("/next")
    @POST
    public HtmlResponse doNext(MultivaluedMap<String, String> params) throws Exception {
        TopForm form = FormUtil.make(TopForm.class, params);
        form.setNext("/main_menu");
        return new HtmlResponse().Redirect(form.getNext());
    }



    //---------------------------------------------- [public] 共通アクション.
    /** ログアウト アクション. */
    @Path("/logout")
    @GET
    public HtmlResponse doLogout() throws Exception {
        authModel.clearLoginAuth();
        return new HtmlResponse().Redirect("/");
    }

    /** 認証エラー アクション. */
    @Path("/auth_error")
    @GET
    public HtmlResponse doAuthError() throws Exception {
        authModel.clearLoginAuth();
        TopForm form = FormUtil.make(TopForm.class);
        model.authError(form);
        return new HtmlResponse(TEMPLATE).add("form", form).add("messageList", model.getMessageList());
    }

    /** システムエラー アクション. */
    @Path("/system_error")
    @GET
    public HtmlResponse doSystemError() throws Exception {
        authModel.clearLoginAuth();
        TopForm form = FormUtil.make(TopForm.class);
        model.systemError(form);
        return new HtmlResponse(TEMPLATE).add("form", form).add("messageList", model.getMessageList());
    }

}
