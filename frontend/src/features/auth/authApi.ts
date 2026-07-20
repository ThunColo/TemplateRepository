import { apiClient } from "../../lib/apiClient";

/**
 * APIの認証関連のエンドポイントを呼び出すモジュール。
 *
 * @author nahaton 2026/07/20
 */
export interface LoginResponse {
  userId: number; // ユーザーID
  email: string; // ユーザーのメールアドレス
  name: string; // ユーザーの名前
}

/**
 * 認証関連のAPI呼び出しをまとめたオブジェクト。
 * loginメソッドはユーザーのログインを行い、ログイン成功時にはユーザー情報を返す。
 * logoutメソッドはユーザーのログアウトを行う。
 */
export const authApi = {
  async login(email: string, password: string): Promise<LoginResponse> {
    const response = await apiClient.post<LoginResponse>("/auth/login", {
      // APIのログインエンドポイントにPOSTリクエストを送信
      email,
      password,
    });
    return response.data;
  },

  async logout(): Promise<void> {
    // APIのログアウトエンドポイントにPOSTリクエストを送信
    await apiClient.post("/auth/logout");
  },
};
