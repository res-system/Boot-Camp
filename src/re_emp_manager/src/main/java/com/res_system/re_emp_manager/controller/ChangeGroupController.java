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
import com.res_system.re_emp_manager.commons.controller.interceptor.LoginAuthority;
import com.res_system.re_emp_manager.commons.data.AjaxResponse;
import com.res_system.re_emp_manager.model.change_group.ChangeGroupForm;
import com.res_system.re_emp_manager.model.change_group.ChangeGroupModel;

/**
 * グループ変更画面 コントローラークラス.
 * @author res.
 */
@Path("/change_group")
@Controller
@LoginAuthority
@RequestScoped
public class ChangeGroupController {

    //---------------------------------------------- const [private].
    /** VIEWファイル [index] */
    private static final String TEMPLATE = "change_group/index";



    //---------------------------------------------- [private] モデルクラス.
    /** メイン業務処理. */
    @Inject
    private ChangeGroupModel model;



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
        ChangeGroupForm form = model.init(FormUtil.make(ChangeGroupForm.class));
        return new HtmlResponse(TEMPLATE).add("form", form);
    }



    //---------------------------------------------- [public] アクション(@POST[JSON]).
    /** リスト検索. */
    @Path("/find_list")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public AjaxResponse doFindList(MultivaluedMap<String, String> params) throws Exception {
        ChangeGroupForm form = FormUtil.make(ChangeGroupForm.class, params);
        String status;
        if (model.findList(form))
                { status = AjaxResponse.OK; } else { status = AjaxResponse.NG; }
        return new AjaxResponse(status).setForm(form).setMessageList(model.getMessageList());
    }

    /** グループ変更. */
    @Path("/change_group")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public AjaxResponse doChangeGroup(MultivaluedMap<String, String> params) throws Exception {
        ChangeGroupForm form = FormUtil.make(ChangeGroupForm.class, params);
        String status;
        if (model.changeGroup(form))
                { status = AjaxResponse.OK; } else { status = AjaxResponse.NG; }
        return new AjaxResponse(status).setForm(form).setMessageList(model.getMessageList());
    }

}
