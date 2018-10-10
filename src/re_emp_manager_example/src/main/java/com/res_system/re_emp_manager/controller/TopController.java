package com.res_system.re_emp_manager.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.res_system.commons.mvc.controller.response.HtmlResponse;
import com.res_system.commons.mvc.model.form.FormUtil;
import com.res_system.re_emp_manager.commons.controller.interceptor.Controller;
import com.res_system.re_emp_manager.model.top.TopForm;
import com.res_system.re_emp_manager.model.top.TopModel;

/**
 * TOP画面 コントローラークラス.
 * @author res.
 */
@Path("/")
@Controller
@RequestScoped
public class TopController {

    //---------------------------------------------- const [private].
    private static final String TEMPLATE_INDEX = "top/index";
    private static final String TEMPLATE_ERROR = "top/error";



    //---------------------------------------------- [private] モデルクラス.
    @Inject
    private TopModel model;



    //---------------------------------------------- [public] アクション(@GET).
    /** デフォルト. */
    @GET
    public HtmlResponse defaultAction() throws Exception {
        return new HtmlResponse().Redirect("/login");
    }

    /** テスト画面表示. */
    @Path("/test")
    @GET
    public HtmlResponse index() throws Exception {
        TopForm form = FormUtil.make(TopForm.class);
        return new HtmlResponse(TEMPLATE_INDEX).add("form", form);
    }

    /** メッセージ表示. */
    @Path("/show_message/{kbn}")
    @GET
    public HtmlResponse showMessage(@PathParam("kbn") final String messageKbn) throws Exception {
        TopForm form = FormUtil.make(TopForm.class);
        form.setMessageKbn(messageKbn);
        model.showMessage(form);
        return new HtmlResponse(TEMPLATE_ERROR).add("form", form)
                .add("messageList", model.getMessageList());
    }

}
