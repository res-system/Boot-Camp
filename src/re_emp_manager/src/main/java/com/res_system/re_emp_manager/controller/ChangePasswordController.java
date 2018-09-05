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
import com.res_system.re_emp_manager.commons.controller.interceptor.LoginAuthority;
import com.res_system.re_emp_manager.commons.data.AjaxResponse;
import com.res_system.re_emp_manager.model.change_password.ChangePasswordForm;
import com.res_system.re_emp_manager.model.change_password.ChangePasswordModel;

/**
 * パスワード変更画面 コントローラークラス.
 * @author res.
 */
@Path("/change_password")
@Controller
@LoginAuthority
@RequestScoped
public class ChangePasswordController {

    //---------------------------------------------- const [private].
    private static final String TEMPLATE = "change_password/index";



    //---------------------------------------------- [private] モデルクラス.
    /** メイン業務処理. */
    @Inject
    private ChangePasswordModel model;



    //---------------------------------------------- [public] アクション.
    /** デフォルト. */
    @GET
    public HtmlResponse defaultAction(@PathParam("next") final String next) throws Exception {
        return index();
    }

    /** 画面表示. */
    @Path("/index")
    @GET
    public HtmlResponse index() throws Exception {
        ChangePasswordForm form = model.init(FormUtil.make(ChangePasswordForm.class));
        return new HtmlResponse(TEMPLATE).add("form", form);
    }



    //---------------------------------------------- [public] アクション(@POST[JSON]).
    /** チェック. */
    @Path("/check")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public AjaxResponse doCheck(MultivaluedMap<String, String> params) throws Exception {
        ChangePasswordForm form = FormUtil.make(ChangePasswordForm.class, params);
        String status;
        if (model.checkInput(form))
                { status = AjaxResponse.OK; } else { status = AjaxResponse.NG; }
        return new AjaxResponse(status).setForm(form).setMessageList(model.getMessageList());
    }

    /** パスワード変更. */
    @Path("/update")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public AjaxResponse doUpdate(MultivaluedMap<String, String> params) throws Exception {
        ChangePasswordForm form = FormUtil.make(ChangePasswordForm.class, params);
        String status;
        if (model.checkInput(form) && model.doUpdate(form))
                { status = AjaxResponse.OK; } else { status = AjaxResponse.NG; }
        return new AjaxResponse(status).setForm(form).setMessageList(model.getMessageList());
    }

}
