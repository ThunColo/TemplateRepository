/**
 * ドメイン例外を配置するパッケージ。
 * アプリケーションの中で発生する想定の例外のうち、throws 宣言を書きたくない種類を配置する。
 * 
 * Exceptionの継承クラスは呼び出し元に処理を強制するが、
 * RuntimeExceptionは基本的に上位の例外ハンドラーで一括処理する。
 * よってビジネスルール違反の定義では、扱いやすいRuntimeExceptionを継承する。
 * 
 */
package com.myapp.template.domain.exception;