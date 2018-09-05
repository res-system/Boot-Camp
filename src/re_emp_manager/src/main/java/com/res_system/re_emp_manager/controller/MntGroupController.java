package com.res_system.re_emp_manager.controller;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.res_system.commons.mvc.controller.response.HtmlResponse;
import com.res_system.re_emp_manager.commons.controller.interceptor.Controller;
import com.res_system.re_emp_manager.commons.controller.interceptor.LoginAuthority;

/**
 * グループ情報メンテナンス画面 コントローラークラス.
 * @author res.
 */
@Path("/mnt_group")
@Controller
@LoginAuthority
@RequestScoped
public class MntGroupController {

    //---------------------------------------------- const [private].
    /** VIEWファイル [index] */
    private static final String TEMPLATE = "mnt_group/index";



    //---------------------------------------------- [private] モデルクラス.



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
        return new HtmlResponse(TEMPLATE);
   }



    //---------------------------------------------- [public] アクション(@POST[JSON]).

}
