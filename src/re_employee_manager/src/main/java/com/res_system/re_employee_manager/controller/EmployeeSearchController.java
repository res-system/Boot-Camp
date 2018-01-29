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
import com.res_system.re_employee_manager.commons.controller.interceptor.UpdateAuthority;
import com.res_system.re_employee_manager.commons.model.SelectedEmpModel;
import com.res_system.re_employee_manager.commons.model.data.AjaxResponse;
import com.res_system.re_employee_manager.model.employee_search.EmployeeSearchForm;
import com.res_system.re_employee_manager.model.employee_search.EmployeeSearchModel;

/**
 * <pre>
 * 社員情報検索[ 一覧 ]画面 コントローラークラス.
 * </pre>
 * @author res.
 */
@Path("employee_search")
@Controller
@RequestScoped
@LoginAuthority
public class EmployeeSearchController {

    //---------------------------------------------- const [private].
    /** テンプレート. */
    private static final String TEMPLATE = "employee_search/index";



    //---------------------------------------------- [private] モデルクラス.
    /** メイン業務処理. */
    @Inject
    private EmployeeSearchModel model;

    /** 社員選択処理 モデルクラス. */
    @Inject
    private SelectedEmpModel selectedEmployeeModel;



    //---------------------------------------------- [public] アクション処理.
    /** デフォルト アクション. */
    @GET
    public HtmlResponse defaultAction() throws Exception {
        return index();
    }

    /** 画面表示 アクション. */
    @Path("/index")
    @GET
    public HtmlResponse index() throws Exception {
        EmployeeSearchForm form = FormUtil.make(EmployeeSearchForm.class);
        model.setShowFormData(form);
        return new HtmlResponse(TEMPLATE).add("form", form);
    }

    /** 検索 アクション. */
    @Path("/search")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public AjaxResponse doSearch(MultivaluedMap<String, String> params) throws Exception {
        EmployeeSearchForm form = FormUtil.make(EmployeeSearchForm.class, params);
        if (model.search(form)) {
            return new AjaxResponse(AjaxResponse.OK, form).setMessageList(model.getMessageList());
        } else {
            return new AjaxResponse(AjaxResponse.NG, form).setMessageList(model.getMessageList());
        }
    }

    /** 更新 アクション. */
    @Path("/update")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @UpdateAuthority
    public AjaxResponse doUpdate(MultivaluedMap<String, String> params) throws Exception {
        EmployeeSearchForm form = FormUtil.make(EmployeeSearchForm.class, params);
        if (model.update(form)) {
            return new AjaxResponse(AjaxResponse.OK, form).setMessageList(model.getMessageList());
        } else {
            return new AjaxResponse(AjaxResponse.NG, form).setMessageList(model.getMessageList());
        }
    }

    /** 完了画面表示 アクション. */
    @Path("/complete")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @UpdateAuthority
    public AjaxResponse complete(MultivaluedMap<String, String> params) throws Exception {
        EmployeeSearchForm form = FormUtil.make(EmployeeSearchForm.class, params);
        if (model.complete(form)) {
            return new AjaxResponse(AjaxResponse.OK, form).setMessageList(model.getMessageList());
        } else {
            return new AjaxResponse(AjaxResponse.NG, form).setMessageList(model.getMessageList());
        }
    }

    /** 社員選択 アクション. */
    @Path("/select")
    @POST
    public HtmlResponse doSelect(MultivaluedMap<String, String> params) throws Exception {
        EmployeeSearchForm form = FormUtil.make(EmployeeSearchForm.class, params);
        selectedEmployeeModel.begin();
        selectedEmployeeModel.setData(form.getData());
        selectedEmployeeModel.setReturnUrl("/employee_search/return");
        return new HtmlResponse().Redirect("/employee_info/show");
    }

    /** 社員追加 アクション. */
    @Path("/add")
    @POST
    public HtmlResponse doAdd(MultivaluedMap<String, String> params) throws Exception {
        EmployeeSearchForm form = FormUtil.make(EmployeeSearchForm.class, params);
        selectedEmployeeModel.begin();
        selectedEmployeeModel.setData(form.getData());
        selectedEmployeeModel.setReturnUrl("/employee_search/return");
        return new HtmlResponse().Redirect("/employee_info/add");
    }

    /** 戻る アクション. */
    @Path("/return")
    @GET
    public HtmlResponse doReturn() throws Exception {
        EmployeeSearchForm form = FormUtil.make(EmployeeSearchForm.class);
        form.setData(selectedEmployeeModel.getData());
        selectedEmployeeModel.end();
        model.setShowFormData(form);
        return new HtmlResponse(TEMPLATE).add("form", form);
    }

}
