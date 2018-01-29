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
import com.res_system.re_employee_manager.commons.model.data.AjaxResponse;
import com.res_system.re_employee_manager.model.employee_info.EmployeeInfoForm;
import com.res_system.re_employee_manager.model.employee_info.EmployeeInfoModel;

/**
 * <pre>
 * 社員個人情報管理画面 コントローラークラス..
 * </pre>
 * @author res.
 */
@Path("employee_info")
@Controller
@RequestScoped
@LoginAuthority
public class EmployeeInfoController {

    //---------------------------------------------- const [private].
    /** テンプレート(詳細). */
    private static final String TEMPLATE_SHOW = "employee_info/show";
    /** テンプレート(入力). */
    private static final String TEMPLATE_INPUT = "employee_info/input";



    //---------------------------------------------- [private] モデルクラス.
    /** メイン業務処理. */
    @Inject
    private EmployeeInfoModel model;



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
        EmployeeInfoForm form = FormUtil.make(EmployeeInfoForm.class);
        model.find(form);
        model.setShowFormData(form);
        return new HtmlResponse(TEMPLATE_SHOW).add("form", form);
    }

    /** 新規追加画面表示 アクション. */
    @Path("/add")
    @GET
    @UpdateAuthority
    public HtmlResponse doAdd() throws Exception {
        EmployeeInfoForm form = FormUtil.make(EmployeeInfoForm.class);
        model.find(form);
        model.setInputFormData(form);
        return new HtmlResponse(TEMPLATE_INPUT).add("form", form).add("return_url", model.getReturnUrl());
    }

    /** 入力画面表示 アクション. */
    @Path("/input")
    @GET
    @UpdateAuthority
    public HtmlResponse input() throws Exception {
        EmployeeInfoForm form = FormUtil.make(EmployeeInfoForm.class);
        model.find(form);
        model.setInputFormData(form);
        return new HtmlResponse(TEMPLATE_INPUT).add("form", form).add("return_url", "/employee_info/show");
    }


    /** 入力内容反映 アクション. */
    @Path("/update")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @UpdateAuthority
    public AjaxResponse update(MultivaluedMap<String, String> params) throws Exception {
        EmployeeInfoForm form = FormUtil.make(EmployeeInfoForm.class, params);
        if (model.checkInput(form) && model.save(form)) {
            return new AjaxResponse(AjaxResponse.OK, form).setMessageList(model.getMessageList());
        } else {
            return new AjaxResponse(AjaxResponse.NG, form).setMessageList(model.getMessageList());
        }
    }


    /** 完了画面表示 アクション. */
    @Path("/complete")
    @GET
    @UpdateAuthority
    public HtmlResponse complete() throws Exception {
        EmployeeInfoForm form = FormUtil.make(EmployeeInfoForm.class);
        model.find(form);
        model.complete(form);
        return new HtmlResponse(TEMPLATE_SHOW).add("form", form)
                .add("messageList", model.getMessageList());
    }

    /** 戻る アクション. */
    @Path("/return")
    @GET
    public HtmlResponse doReturn() throws Exception {
        return new HtmlResponse().Redirect(model.getReturnUrl());
    }

}

