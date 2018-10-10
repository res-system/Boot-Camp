package com.res_system.re_emp_manager.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.res_system.commons.mvc.controller.response.HtmlResponse;
import com.res_system.commons.mvc.model.form.FormUtil;
import com.res_system.re_emp_manager.commons.controller.interceptor.Controller;
import com.res_system.re_emp_manager.commons.controller.interceptor.LoginAuth;
import com.res_system.re_emp_manager.commons.controller.interceptor.Permission;
import com.res_system.re_emp_manager.model.main_menu.MainMenuForm;
import com.res_system.re_emp_manager.model.main_menu.MainMenuModel;

/**
 * メインメニュー画面 コントローラークラス.
 * @author res.
 */
@Path("/main_menu")
@Controller
@LoginAuth
@Permission
@RequestScoped
public class MainMenuController {

    //---------------------------------------------- const [private].
    private static final String TEMPLATE = "main_menu/index";



    //---------------------------------------------- [private] モデルクラス.
    /** メイン業務処理. */
    @Inject
    private MainMenuModel model;



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
        MainMenuForm form = model.init(FormUtil.make(MainMenuForm.class));
        return new HtmlResponse(TEMPLATE).add("form", form);
    }

    /** メッセージ表示. */
    @Path("/show_message/{kbn}")
    @GET
    public HtmlResponse showMessage(@PathParam("kbn") final String messageKbn) throws Exception {
        MainMenuForm form = model.init(FormUtil.make(MainMenuForm.class));
        form.setMessageKbn(messageKbn);
        model.showMessage(form);
        return new HtmlResponse(TEMPLATE).add("form", form)
                .add("messageList", model.getMessageList())
                ;
    }

}
