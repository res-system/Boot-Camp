package com.res_system.re_emp_manager.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import com.res_system.commons.mvc.controller.response.HtmlResponse;
import com.res_system.commons.mvc.model.form.FormUtil;
import com.res_system.re_emp_manager.commons.controller.interceptor.Controller;
import com.res_system.re_emp_manager.commons.data.AjaxResponse;
import com.res_system.re_emp_manager.model.login.LoginForm;
import com.res_system.re_emp_manager.model.login.LoginModel;

/**
 * ログイン画面 コントローラークラス.
 * @author res.
 */
@Path("/login")
@Controller
@RequestScoped
public class LoginController {

    //---------------------------------------------- const [private].
    private static final String TEMPLATE = "login/index";



    //---------------------------------------------- [private] モデルクラス.
    /** メイン業務処理. */
    @Inject
    private LoginModel model;



    //---------------------------------------------- [public] アクション(@GET).
    /** デフォルト. */
    @GET
    public HtmlResponse defaultAction() throws Exception {
        return index();
    }

    /** 画面表示. */
    @Path("/index")
    @GET
    public HtmlResponse index() throws Exception {
        return index("");
    }

    /** 画面表示. */
    @Path("/index/{code}")
    @GET
    public HtmlResponse index(@PathParam("code") final String code) throws Exception {
        LoginForm form = model.init(FormUtil.make(LoginForm.class));
        form.setCode(code);
        if (model.isLogin(form)) {
            // 認証あり.
            return new HtmlResponse().Redirect(form.getNext());
        } else {
            // 認証なし.
            return new HtmlResponse(TEMPLATE).add("form", form);
        }
    }



    //---------------------------------------------- [public] アクション(@POST).
    /** 次画面遷移 アクション. */
    @Path("/next")
    @POST
    public HtmlResponse doNext(MultivaluedMap<String, String> params) throws Exception {
        LoginForm form = FormUtil.make(LoginForm.class, params);
        return new HtmlResponse().Redirect(form.getNext());
    }

    /** ログアウト. */
    @Path("/logout_post")
    @POST
    public HtmlResponse doLogoutPost() throws Exception {
        LoginForm form = model.init(FormUtil.make(LoginForm.class));
        model.logout(form);
        return new HtmlResponse().Redirect(form.getNext());
    }



    //---------------------------------------------- [public] アクション(@POST[JSON]).
    /** ログイン. */
    @Path("/login")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public AjaxResponse doLogin(MultivaluedMap<String, String> params) throws Exception {
        LoginForm form = FormUtil.make(LoginForm.class, params);
        String status;
        if (model.checkInput(form) && model.doLogin(form))
                { status = AjaxResponse.OK; } else { status = AjaxResponse.NG; }
        return new AjaxResponse(status).setForm(form).setMessageList(model.getMessageList());
    }

}
