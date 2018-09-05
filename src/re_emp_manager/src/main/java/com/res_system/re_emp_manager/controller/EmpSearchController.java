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
import com.res_system.re_emp_manager.model.emp_search.EmpSearchForm;
import com.res_system.re_emp_manager.model.emp_search.EmpSearchModel;

/**
 * 社員情報検索画面 コントローラークラス.
 * @author res.
 */
@Path("/emp_search")
@Controller
@LoginAuthority
@RequestScoped
public class EmpSearchController {

    //---------------------------------------------- const [private].
    /** VIEWファイル [index] */
    private static final String TEMPLATE = "emp_search/index";



    //---------------------------------------------- [private] モデルクラス.
    /** メイン業務処理. */
    @Inject
    private EmpSearchModel model;



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
        EmpSearchForm form = model.initIndex(FormUtil.make(EmpSearchForm.class));
        return new HtmlResponse(TEMPLATE).add("form", form);
   }

    /** 戻り時画面表示. */
    @Path("/return")
    @GET
    public HtmlResponse doReturn() throws Exception {
        EmpSearchForm form = model.initReturn(FormUtil.make(EmpSearchForm.class));
        return new HtmlResponse(TEMPLATE).add("form", form);
   }



    //---------------------------------------------- [public] アクション(@POST[JSON]).
    /** リスト検索. */
    @Path("/find_list")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public AjaxResponse doFindList(MultivaluedMap<String, String> params) throws Exception {
        EmpSearchForm form = FormUtil.make(EmpSearchForm.class, params);
        String status;
        if (model.findList(form))
                { status = AjaxResponse.OK; } else { status = AjaxResponse.NG; }
        return new AjaxResponse(status).setForm(form).setMessageList(model.getMessageList());
    }

}
