package com.res_system.re_employee_manager.controller;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.res_system.commons.mvc.controller.response.HtmlResponse;
import com.res_system.re_employee_manager.commons.controller.interceptor.Controller;
import com.res_system.re_employee_manager.commons.controller.interceptor.LoginAuthority;

/**
 * <pre>
 * アカウント情報管理画面 コントローラークラス.
 * </pre>
 * @author res.
 */
@Path("account_info")
@Controller
@RequestScoped
@LoginAuthority
public class AccountInfoController {

    //---------------------------------------------- const [private].
    /** テンプレート(詳細). */
    private static final String TEMPLATE = "account_info/index";



    //---------------------------------------------- [private] モデルクラス.
    /** メイン業務処理. */



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
        return new HtmlResponse(TEMPLATE);
    }

}
