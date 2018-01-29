package com.res_system.re_employee_manager.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.res_system.commons.mvc.controller.response.HtmlResponse;
import com.res_system.commons.mvc.model.form.FormUtil;
import com.res_system.re_employee_manager.commons.controller.interceptor.Controller;
import com.res_system.re_employee_manager.commons.controller.interceptor.LoginAuthority;
import com.res_system.re_employee_manager.model.employee_expenses.EmployeeExpensesForm;
import com.res_system.re_employee_manager.model.employee_expenses.EmployeeExpensesModel;

/**
 * <pre>
 * 社員交通費情報管理 コントローラークラス.
 * </pre>
 * @author res.
 */
@Path("employee_expenses")
@Controller
@RequestScoped
@LoginAuthority
public class EmployeeExpensesController {

    //---------------------------------------------- const [private].
    /** テンプレート(詳細). */
    private static final String TEMPLATE_SHOW = "employee_expenses/show";



    //---------------------------------------------- [private] モデルクラス.
    /** メイン業務処理. */
    @Inject
    private EmployeeExpensesModel model;



    //---------------------------------------------- [public] アクション処理.
    /** デフォルト アクション. */
    @GET
    public HtmlResponse defaultAction() throws Exception {
        return show();
    }

    /** 詳細画面表示 アクション. */
    @Path("/show")
    @GET
    public HtmlResponse show() throws Exception {
        EmployeeExpensesForm form = FormUtil.make(EmployeeExpensesForm.class);
        model.find(form);
        model.setShowFormData(form);
        return new HtmlResponse(TEMPLATE_SHOW).add("form", form);
    }

    /** 戻る アクション. */
    @Path("/return")
    @GET
    public HtmlResponse doReturn() throws Exception {
        return new HtmlResponse().Redirect(model.getReturnUrl());
    }

}

