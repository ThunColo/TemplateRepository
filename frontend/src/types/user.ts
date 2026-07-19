/**
 * Userインタフェースを定義する。
 *
 *  @author nahaton 2026/07/19
 *
 * @typedef {Object} User
 * @property {number} id - ユーザーの一意の識別子。
 * @property {string} email - ユーザーのメールアドレス。
 * @property {string} name - ユーザーの名前。
 * @property {string} createdAt - ユーザーが作成された日時。
 * @property {string} updatedAt - ユーザーが最後に更新された日時。
 */
export interface User {
  id: number;
  email: string;
  name: string;
  createdAt: string;
  updatedAt: string;
}
