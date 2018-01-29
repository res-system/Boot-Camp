package com.res_system.re_employee_manager.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.res_system.commons.mvc.controller.response.HtmlResponse;
import com.res_system.commons.mvc.model.form.FormUtil;
import com.res_system.re_employee_manager.commons.controller.interceptor.Controller;
import com.res_system.re_employee_manager.commons.controller.interceptor.LoginAuthority;
import com.res_system.re_employee_manager.commons.model.SelectedEmpModel;
import com.res_system.re_employee_manager.model.main_menu.MainMenuForm;
import com.res_system.re_employee_manager.model.main_menu.MainMenuModel;

/**
 * <pre>
 * メインメニュー画面 コントローラークラス.
 * </pre>
 * @author res.
 */
@Path("main_menu")
@Controller
@RequestScoped
@LoginAuthority
public class MainMenuController {

    //---------------------------------------------- const [private].
    /** テンプレート. */
    private static final String TEMPLATE = "main_menu/index";



    //---------------------------------------------- [private] モデルクラス.
    /** メイン業務処理. */
    @Inject
    private MainMenuModel model;

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
        MainMenuForm form = FormUtil.make(MainMenuForm.class);
        selectedEmployeeModel.end();
        model.setMenuData(form);
        return new HtmlResponse(TEMPLATE).add("form", form);
    }


    /** メッセージ表示 アクション. */
    @Path("/show_message/{kbn}")
    @GET
    public HtmlResponse showMessage(@PathParam("kbn") final String messageKbn) throws Exception {
        MainMenuForm form = FormUtil.make(MainMenuForm.class);
        form.setMessageKbn(messageKbn);
        selectedEmployeeModel.end();
        model.setMenuData(form);
        model.showMessage(form);
        return new HtmlResponse(TEMPLATE).add("form", form).add("messageList", model.getMessageList());
    }

}
