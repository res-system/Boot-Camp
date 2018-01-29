package com.res_system.re_employee_manager.commons.controller.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.interceptor.InterceptorBinding;

/**
 * <pre>
 * ログイン認証 アノテーション.
 *
 * ※ログイン認証チェックが必要なコントローラー・メソッドに設定します.
 * </pre>
 * @author res.
 */
@Inherited
@InterceptorBinding
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface LoginAuthority {}
