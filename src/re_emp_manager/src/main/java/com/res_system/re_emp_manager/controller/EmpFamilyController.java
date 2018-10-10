package com.res_system.re_emp_manager.controller;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.res_system.commons.mvc.controller.response.HtmlResponse;
import com.res_system.re_emp_manager.commons.controller.interceptor.Controller;
import com.res_system.re_emp_manager.commons.controller.interceptor.EmpConvCtrl;
import com.res_system.re_emp_manager.commons.controller.interceptor.LoginAuth;
import com.res_system.re_emp_manager.commons.controller.interceptor.Permission;

/**
 * 社員家族情報管理画面 コントローラークラス.
 * @author res.
 */
@Path("/emp_family")
@Controller
@LoginAuth
@Permission
@EmpConvCtrl
@RequestScoped
public class EmpFamilyController {

    //---------------------------------------------- const [private].
    /** VIEWファイル [index] */
    private static final String TEMPLATE = "emp_family/index";



    //---------------------------------------------- [private] モデルクラス.



    //---------------------------------------------- [public] アクション(@GET).
    /** 初期表示. */
    @Path("/init")
    @GET
    public HtmlResponse doInit() throws Exception {
        return new HtmlResponse(TEMPLATE);
    }

    /** 画面表示. */
    @Path("/show")
    @GET
    public HtmlResponse doShow() throws Exception {
        return new HtmlResponse(TEMPLATE);
   }



    //---------------------------------------------- [public] アクション(@POST[JSON]).

}
