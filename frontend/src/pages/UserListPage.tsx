import { useEffect, useState } from "react";
import { userApi } from "../features/users/userApi";
import { authApi } from "../features/auth/authApi";
import { useAuth } from "../features/auth/AuthContext";
import type { User } from "../types/user";

/**
 * ユーザー一覧ページのコンポーネント。
 *
 * @author nahaton 2026/07/19
 * @update nahaton 2026/07/20 ログアウト処理を追加
 */

/**
 * ユーザー一覧ページを表示するコンポーネント。
 * ユーザー一覧を取得し、表示する。
 * ログアウトボタンを押すとログアウト処理を行い、ログインページに遷移する。
 */
export function UserListPage() {
  const [users, setUsers] = useState<User[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const { user, setUser } = useAuth();

  useEffect(() => {
    userApi
      .listAll()
      .then((data) => setUsers(data))
      .catch(() => setError("ユーザー一覧の取得に失敗しました"))
      .finally(() => setLoading(false));
  }, []);

  /**
   * ログアウト処理を行う関数。
   * authApi.logout()を呼び出してサーバー側のセッションを破棄し、setUser(null)でクライアント側の認証状態をリセットする。
   * サーバー側の処理が失敗しても、画面上はログアウト状態に戻す。
   */
  async function handleLogout() {
    try {
      await authApi.logout();
    } finally {
      // サーバー側の処理が失敗しても、画面上はログアウト状態に戻す
      setUser(null);
    }
  }

  if (loading) return <p>読み込み中...</p>; // ローディング中の表示
  if (error) return <p>{error}</p>; // エラーが発生した場合の表示

  return (
    <div>
      <header
        style={{
          display: "flex",
          justifyContent: "space-between",
          alignItems: "center",
        }}
      >
        <h1>ユーザー一覧</h1>
        <div>
          <span>{user?.name} でログイン中</span>
          <button onClick={handleLogout} style={{ marginLeft: "1rem" }}>
            ログアウト
          </button>
        </div>
      </header>
      <ul>
        {users.map((u) => (
          <li key={u.id}>
            {u.name}({u.email})
          </li>
        ))}
      </ul>
    </div>
  );
}
