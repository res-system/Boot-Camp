package com.res_system.re_emp_manager.commons.controller.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 * ログイン認証なし アノテーション.
 *
 * ※ログイン認証が不要なアクションに設定します.
 * </pre>
 * @author res.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface NonAuth {}
