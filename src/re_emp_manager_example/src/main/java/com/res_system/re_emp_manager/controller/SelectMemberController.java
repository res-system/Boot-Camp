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
import com.res_system.re_emp_manager.model.select_member.SelectMemberForm;
import com.res_system.re_emp_manager.model.select_member.SelectMemberModel;

/**
 * メンバー選択ダイアログ コントローラークラス.
 * @author res.
 */
@Controller
@Path("/select_member")
@LoginAuth
@RequestScoped
public class SelectMemberController {

    //---------------------------------------------- [private] モデルクラス.
    /** メイン業務処理. */
    @Inject
    private SelectMemberModel model;



    //---------------------------------------------- [public] アクション.
    /** リスト検索. */
    @Path("/find_list")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public AjaxResponse doFindList(MultivaluedMap<String, String> params) throws Exception {
        SelectMemberForm form = FormUtil.make(SelectMemberForm.class, params);
        String status;
        if (model.findList(form))
                { status = AjaxResponse.OK; } else { status = AjaxResponse.NG; }
        return new AjaxResponse(status).setForm(form).setMessageList(model.getMessageList());
    }

}
