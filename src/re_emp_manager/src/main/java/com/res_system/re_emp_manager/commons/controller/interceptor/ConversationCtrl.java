package com.res_system.re_emp_manager.commons.controller.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.interceptor.InterceptorBinding;

/**
 * <pre>
 * 会話スコープコントローラークラス アノテーション.
 *
 * ※会話スコープが必要なコントローラクラスに必ず設定します.
 * </pre>
 * @author res.
 */
@Inherited
@InterceptorBinding
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface ConversationCtrl {}
