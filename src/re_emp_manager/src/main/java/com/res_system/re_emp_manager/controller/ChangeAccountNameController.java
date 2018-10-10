package com.res_system.re_emp_manager.controller;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.res_system.commons.mvc.controller.response.HtmlResponse;
import com.res_system.re_emp_manager.commons.controller.interceptor.Controller;
import com.res_system.re_emp_manager.commons.controller.interceptor.LoginAuth;
import com.res_system.re_emp_manager.commons.controller.interceptor.Permission;

/**
 * アカウント名変更画面 コントローラークラス.
 * @author res.
 */
@Path("/change_account_name")
@Controller
@LoginAuth
@Permission
@RequestScoped
public class ChangeAccountNameController {

    //---------------------------------------------- const [private].
    private static final String TEMPLATE = "change_account_name/index";



    //---------------------------------------------- [private] モデルクラス.
    /** メイン業務処理. */



    //---------------------------------------------- [public] アクション.
    /** デフォルト. */
    @GET
    public HtmlResponse defaultAction() throws Exception {
        return index();
    }

    /** 画面表示. */
    @Path("/index")
    @GET
    public HtmlResponse index() throws Exception {
        return new HtmlResponse(TEMPLATE);
    }

}
