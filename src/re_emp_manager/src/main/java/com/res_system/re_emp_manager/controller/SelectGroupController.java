package com.res_system.re_emp_manager.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import com.res_system.commons.mvc.model.form.FormUtil;
import com.res_system.re_emp_manager.commons.controller.interceptor.Controller;
import com.res_system.re_emp_manager.commons.controller.interceptor.LoginAuth;
import com.res_system.re_emp_manager.commons.data.AjaxResponse;
import com.res_system.re_emp_manager.model.select_group.SelectGroupForm;
import com.res_system.re_emp_manager.model.select_group.SelectGroupModel;

/**
 * グループ選択ダイアログ コントローラークラス.
 * @author res.
 */
@Controller
@Path("/select_group")
@LoginAuth
@RequestScoped
public class SelectGroupController {

    //---------------------------------------------- [private] モデルクラス.
    /** メイン業務処理. */
    @Inject
    private SelectGroupModel model;



    //---------------------------------------------- [public] アクション.
    /** リスト検索. */
    @Path("/find_list")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public AjaxResponse doFindList(MultivaluedMap<String, String> params) throws Exception {
        SelectGroupForm form = FormUtil.make(SelectGroupForm.class, params);
        String status;
        if (model.findList(form))
                { status = AjaxResponse.OK; } else { status = AjaxResponse.NG; }
        return new AjaxResponse(status).setForm(form).setMessageList(model.getMessageList());
    }

    /** チェック. */
    @Path("/check")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public AjaxResponse doCheck(MultivaluedMap<String, String> params) throws Exception {
        SelectGroupForm form = FormUtil.make(SelectGroupForm.class, params);
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
        SelectGroupForm form = FormUtil.make(SelectGroupForm.class, params);
        String status;
        if (model.checkInput(form) && model.insertData(form))
                { status = AjaxResponse.OK; } else { status = AjaxResponse.NG; }
        return new AjaxResponse(status).setForm(form).setMessageList(model.getMessageList());
    }

}
