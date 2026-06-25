# Java Spring Boot テンプレート

クリーンアーキテクチャに基づいた Spring Boot + PostgreSQL の個人開発用テンプレートリポジトリです。

## 技術スタック

- Java 17
- Spring Boot 4.0
- PostgreSQL 17
- Spring Data JPA
- Flyway（DBマイグレーション）
- Spring Security（フェーズ2で有効化予定）
- Docker Compose

## アーキテクチャ

クリーンアーキテクチャに基づく4層構造を採用しています。

```
src/main/java/com/myapp/template/
├── domain/         # ビジネスルール（外部依存ゼロ）
├── usecase/        # ユースケースとポート（インターフェース）
├── adapter/        # 外部との接続点（Controller、JPA実装など）
└── config/         # Spring 設定
```

依存の方向は外側から内側への一方向です。`adapter` は `usecase` を知っており、`usecase` は `domain` を知っていますが、その逆はありません。

詳細は `docs/ARCHITECTURE.md` を参照してください（後述）。

## 環境構築

### 前提

- Java 17 以上
- Maven 3.9 以上
- Docker Desktop

### 手順

1. リポジトリをクローン

```bash
   git clone <このリポジトリのURL>
   cd template
```

2. 環境変数ファイルを準備

```bash
   cp .env.example .env
```

   `.env` を開き、`POSTGRES_PASSWORD` を任意の値に変更してください。

3. Docker Desktop を起動

4. アプリケーションを起動

```bash
   mvn spring-boot:run
```

   初回起動時に PostgreSQL のコンテナイメージがダウンロードされるため、数分かかります。

### 注意点

- このプロジェクトの PostgreSQL コンテナは **ポート 5433** で稼働します。ローカル環境の PostgreSQL（5432）と競合させないため、意図的にずらしています。ローカルに PostgreSQL が入っていない環境では `compose.yaml` と `application.yml` のポート設定を 5432 に戻しても動作します。

## API エンドポイント

現状はユーザー管理のCRUDのみ実装済みです。認証認可はフェーズ2で追加予定です。

| メソッド | パス | 説明 |
|---------|------|------|
| POST | /api/users | ユーザー登録 |
| GET | /api/users | ユーザー一覧取得 |
| GET | /api/users/{id} | ユーザー単体取得 |
| PUT | /api/users/{id} | ユーザー更新 |
| DELETE | /api/users/{id} | ユーザー削除 |

## DB マイグレーション

`src/main/resources/db/migration/` 配下に Flyway のマイグレーションファイルを配置します。命名規則は `V{バージョン番号}__{説明}.sql` です。

アプリケーション起動時に未適用のマイグレーションが自動実行されます。

## 進捗

このテンプレートはフェーズ1（バックエンドCRUDの完成）まで終了しています。今後のフェーズは以下を予定しています。

- フェーズ2：認証認可（Spring Security + JWT）
- フェーズ3：フロントエンド（React + TypeScript）
- フェーズ4：テスト整備、ドキュメント整備

## ライセンス

任意で設定してください。