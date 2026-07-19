import { apiClient } from "../../lib/apiClient";
import type { User } from "../../types/user";

/**
 * APIクライアントのユーザー関連の操作を行うモジュール。
 *
 * @author nahaton 2026/07/19
 */

/**
 * ユーザーの一覧を取得する。
 * @returns {Promise<User[]>} - ユーザーのリストを返すPromise。
 */
export const userApi = {
  async listAll(): Promise<User[]> {
    const response = await apiClient.get<User[]>("/users");
    return response.data;
  },

  /**
   * ユーザーの詳細をIDで取得する。
   * @param {number} id - ユーザーの一意の識別子。
   * @returns {Promise<User>} - ユーザーの詳細を返すPromise。
   */
  async getById(id: number): Promise<User> {
    const response = await apiClient.get<User>(`/users/${id}`);
    return response.data;
  },

  /**
   * ユーザーを新規登録する。
   * @param {string} email - ユーザーのメールアドレス。
   * @param {string} password - ユーザーのパスワード。
   * @param {string} name - ユーザーの名前。
   * @returns {Promise<User>} - 登録されたユーザーの詳細を返すPromise。
   */
  async register(email: string, password: string, name: string): Promise<User> {
    const response = await apiClient.post<User>("/users", {
      email,
      password,
      name,
    });
    return response.data;
  },

  /**
   * ユーザーの情報を更新する。
   * @param {number} id - ユーザーの一意の識別子。
   * @param {string} name - 更新するユーザーの名前。
   * @returns {Promise<User>} - 更新されたユーザーの詳細を返すPromise。
   */
  async update(id: number, name: string): Promise<User> {
    const response = await apiClient.put<User>(`/users/${id}`, { name });
    return response.data;
  },

  /**
   * ユーザーを削除する。
   * @param {number} id - 削除するユーザーの一意の識別子。
   * @returns {Promise<void>} - 削除操作が完了したことを示すPromise。
   */
  async remove(id: number): Promise<void> {
    await apiClient.delete(`/users/${id}`);
  },
};
