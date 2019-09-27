package com.res_system.mvc_skeleton.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.res_system.commons.mvc.controller.response.HtmlResponse;
import com.res_system.commons.mvc.model.form.FormUtil;
import com.res_system.mvc_skeleton.commons.controller.interceptor.Controller;
import com.res_system.mvc_skeleton.model.top.TopForm;
import com.res_system.mvc_skeleton.model.top.TopModel;

/**
 * TOP画面 コントローラークラス.
 * @author res.
 */
@Path("/")
@Controller
@RequestScoped
public class TopController {

    //---------------------------------------------- const [private].
    private static final String TEMPLATE = "top/index";



    //---------------------------------------------- [private] モデルクラス.
    /** メイン業務処理. */
    @Inject
    private TopModel model;



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
        TopForm form = FormUtil.make(TopForm.class);
        return new HtmlResponse(TEMPLATE).add("form", form);
    }

    /** メッセージ表示. */
    @Path("/show_message/{kbn}")
    @GET
    public HtmlResponse showMessage(@PathParam("kbn") final String messageKbn) throws Exception {
        TopForm form = FormUtil.make(TopForm.class).setMessageKbn(messageKbn);
        model.showMessage(form);
        return new HtmlResponse(TEMPLATE).add("form", form)
                .add("messageList", model.getMessageList());
    }



}
