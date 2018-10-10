package com.res_system.re_emp_manager.controller;

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
import com.res_system.re_emp_manager.commons.controller.interceptor.Controller;
import com.res_system.re_emp_manager.commons.controller.interceptor.LoginAuth;
import com.res_system.re_emp_manager.commons.controller.interceptor.Permission;
import com.res_system.re_emp_manager.commons.data.AjaxResponse;
import com.res_system.re_emp_manager.model.change_account.ChangeAccountForm;
import com.res_system.re_emp_manager.model.change_account.ChangeAccountModel;

/**
 * グループアカウント変更画面 コントローラークラス.
 * @author res.
 */
@Path("/change_account")
@Controller
@LoginAuth
@Permission
@RequestScoped
public class ChangeAccountController {

    //---------------------------------------------- const [private].
    private static final String TEMPLATE = "change_account/index";



    //---------------------------------------------- [private] モデルクラス.
    /** メイン業務処理. */
    @Inject
    private ChangeAccountModel model;



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
        ChangeAccountForm form = model.init(FormUtil.make(ChangeAccountForm.class));
        return new HtmlResponse(TEMPLATE).add("form", form);
    }



    //---------------------------------------------- [public] アクション(@POST[JSON]).
    /** リスト検索. */
    @Path("/find_list")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public AjaxResponse doFindList(MultivaluedMap<String, String> params) throws Exception {
        ChangeAccountForm form = FormUtil.make(ChangeAccountForm.class, params);
        String status;
        if (model.findList(form))
                { status = AjaxResponse.OK; } else { status = AjaxResponse.NG; }
        return new AjaxResponse(status).setForm(form).setMessageList(model.getMessageList());
    }


    /** データ件数チェック. */
    @Path("/check_size")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public AjaxResponse doCheckSize(MultivaluedMap<String, String> params) throws Exception {
        ChangeAccountForm form = FormUtil.make(ChangeAccountForm.class, params);
        String status;
        if (model.checkSize(form))
                { status = AjaxResponse.OK; } else { status = AjaxResponse.NG; }
        return new AjaxResponse(status).setForm(form).setMessageList(model.getMessageList());
    }

    /** チェック. */
    @Path("/check")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public AjaxResponse doCheck(MultivaluedMap<String, String> params) throws Exception {
        ChangeAccountForm form = FormUtil.make(ChangeAccountForm.class, params);
        String status;
        if (model.checkInput(form))
                { status = AjaxResponse.OK; } else { status = AjaxResponse.NG; }
        return new AjaxResponse(status).setForm(form).setMessageList(model.getMessageList());
    }


    /** 新規追加. */
    @Path("/insert")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public AjaxResponse doInsert(MultivaluedMap<String, String> params) throws Exception {
        ChangeAccountForm form = FormUtil.make(ChangeAccountForm.class, params);
        String status;
        if (model.checkInput(form) && model.insertData(form))
                { status = AjaxResponse.OK; } else { status = AjaxResponse.NG; }
        return new AjaxResponse(status).setForm(form).setMessageList(model.getMessageList());
    }

    /** 更新. */
    @Path("/update")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public AjaxResponse doUpdate(MultivaluedMap<String, String> params) throws Exception {
        ChangeAccountForm form = FormUtil.make(ChangeAccountForm.class, params);
        String status;
        if (model.checkInput(form) && model.updateData(form))
                { status = AjaxResponse.OK; } else { status = AjaxResponse.NG; }
        return new AjaxResponse(status).setForm(form).setMessageList(model.getMessageList());
    }

    /** 削除. */
    @Path("/delete")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public AjaxResponse doDelete(MultivaluedMap<String, String> params) throws Exception {
        ChangeAccountForm form = FormUtil.make(ChangeAccountForm.class, params);
        String status;
        if (model.checkInput(form) && model.deleteData(form))
                { status = AjaxResponse.OK; } else { status = AjaxResponse.NG; }
        return new AjaxResponse(status).setForm(form).setMessageList(model.getMessageList());
    }

}
