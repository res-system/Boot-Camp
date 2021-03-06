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
import com.res_system.re_emp_manager.commons.controller.interceptor.LoginAuth;
import com.res_system.re_emp_manager.commons.controller.interceptor.Permission;
import com.res_system.re_emp_manager.commons.data.AjaxResponse;
import com.res_system.re_emp_manager.model.change_account_name.ChangeAccountNameForm;
import com.res_system.re_emp_manager.model.change_account_name.ChangeAccountNameModel;

/**
 * アカウント名変更画面 コントローラークラス.
 * @author res.
 */
@Path("/change_account_name")
@Controller
@LoginAuth
@Permission
@RequestScoped
public class ChangeAccountNameController {

    //---------------------------------------------- const [private].
    private static final String TEMPLATE = "change_account_name/index";



    //---------------------------------------------- [private] モデルクラス.
    /** メイン業務処理. */
    @Inject
    private ChangeAccountNameModel model;



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
        ChangeAccountNameForm form = model.init(FormUtil.make(ChangeAccountNameForm.class));
        return new HtmlResponse(TEMPLATE).add("form", form);
    }



    //---------------------------------------------- [public] アクション(@POST[JSON]).
    /** チェック. */
    @Path("/check")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public AjaxResponse doCheck(MultivaluedMap<String, String> params) throws Exception {
        ChangeAccountNameForm form = FormUtil.make(ChangeAccountNameForm.class, params);
        String status;
        if (model.checkInput(form))
                { status = AjaxResponse.OK; } else { status = AjaxResponse.NG; }
        return new AjaxResponse(status).setForm(form).setMessageList(model.getMessageList());
    }

    /** 更新. */
    @Path("/update")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public AjaxResponse doUpdate(MultivaluedMap<String, String> params) throws Exception {
        ChangeAccountNameForm form = FormUtil.make(ChangeAccountNameForm.class, params);
        String status;
        if (model.checkInput(form) && model.updateData(form))
                { status = AjaxResponse.OK; } else { status = AjaxResponse.NG; }
        return new AjaxResponse(status).setForm(form).setMessageList(model.getMessageList());
    }

}
