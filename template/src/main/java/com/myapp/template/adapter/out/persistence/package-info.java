/**
 * JPA Repositoryの実装クラスを配置する。
 * usecase/port/out で定義した UserRepository インターフェースの実装を提供する。
 * 
 * ドメインモデルとJPAエンティティを分ける理由は、ドメインの設計がDBのテーブル構造に縛られないようにするためである。
 * あるドメインオブジェクトが複数テーブルにまたがって永続化される場合でも、ドメイン側はその事情を知らずに済む。
 */
package com.myapp.template.adapter.out.persistence;