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
import com.res_system.re_employee_manager.commons.controller.interceptor.LoginAuthority;
import com.res_system.re_employee_manager.commons.model.data.AjaxResponse;
import com.res_system.re_employee_manager.model.password_change.PasswordChangeForm;
import com.res_system.re_employee_manager.model.password_change.PasswordChangeModel;

/**
 * <pre>
 * パスワード変更画面 コントローラークラス.
 * </pre>
 * @author res.
 */
@Path("password_change")
@Controller
@RequestScoped
@LoginAuthority
public class PasswordChangeController {

    //---------------------------------------------- const [private].
    /** テンプレート. */
    private static final String TEMPLATE = "password_change/index";



    //---------------------------------------------- [private] モデルクラス.
    /** メイン業務処理. */
    @Inject
    private PasswordChangeModel model;



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
        PasswordChangeForm form = FormUtil.make(PasswordChangeForm.class);
        return new HtmlResponse(TEMPLATE).add("form", form);
    }

    /** パスワード変更 アクション. */
    @Path("/change")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public AjaxResponse doChange(MultivaluedMap<String, String> params) throws Exception {
        PasswordChangeForm form = FormUtil.make(PasswordChangeForm.class, params);
        if (model.checkInput(form) && model.changePassword(form)) {
            return new AjaxResponse(AjaxResponse.OK, form).setMessageList(model.getMessageList());
        } else {
            return new AjaxResponse(AjaxResponse.NG, form).setMessageList(model.getMessageList());
        }
    }

}
