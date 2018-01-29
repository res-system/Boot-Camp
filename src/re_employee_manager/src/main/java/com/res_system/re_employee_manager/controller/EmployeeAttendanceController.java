package com.res_system.re_employee_manager.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.res_system.commons.mvc.controller.response.HtmlResponse;
import com.res_system.commons.mvc.model.form.FormUtil;
import com.res_system.re_employee_manager.commons.controller.interceptor.Controller;
import com.res_system.re_employee_manager.commons.controller.interceptor.LoginAuthority;
import com.res_system.re_employee_manager.model.employee_attendance.EmployeeAttendanceForm;
import com.res_system.re_employee_manager.model.employee_attendance.EmployeeAttendanceModel;

/**
 * <pre>
 * 社員勤怠情報管理画面 コントローラークラス.
 * </pre>
 * @author res.
 */
@Path("employee_attendance")
@Controller
@RequestScoped
@LoginAuthority
public class EmployeeAttendanceController {

    //---------------------------------------------- const [private].
    /** テンプレート(詳細). */
    private static final String TEMPLATE_SHOW = "employee_attendance/show";



    //---------------------------------------------- [private] モデルクラス.
    /** メイン業務処理. */
    @Inject
    private EmployeeAttendanceModel model;



    //---------------------------------------------- [public] アクション処理.
    /** デフォルト アクション. */
    @GET
    public HtmlResponse defaultAction() throws Exception {
        return show();
    }

    /** 画面表示 アクション. */
    @Path("/show")
    @GET
    public HtmlResponse show() throws Exception {
        EmployeeAttendanceForm form = FormUtil.make(EmployeeAttendanceForm.class);
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

