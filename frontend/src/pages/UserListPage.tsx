import { useEffect, useState } from "react";
import { userApi } from "../features/users/userApi";
import type { User } from "../types/user";

/**
 * ユーザー一覧ページのコンポーネント。
 *
 * @author nahaton 2026/07/19
 */

/**
 * ユーザーの一覧を取得し、表示する。
 * @returns {JSX.Element} - ユーザー一覧ページのJSX要素。
 */
export function UserListPage() {
  const [users, setUsers] = useState<User[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  /**
   * useEffectフックを使用して、コンポーネントのマウント時にユーザー一覧を取得する。
   * ユーザー一覧の取得が成功した場合は、取得したデータを状態に設定する。
   * 失敗した場合は、エラーメッセージを状態に設定する。
   * 最後に、読み込み状態をfalseに設定する。
   */
  useEffect(() => {
    userApi
      .listAll()
      .then((data) => setUsers(data))
      .catch(() => setError("ユーザー一覧の取得に失敗しました"))
      .finally(() => setLoading(false));
  }, []);

  if (loading) return <p>読み込み中...</p>;
  if (error) return <p>{error}</p>;

  return (
    <div>
      <h1>ユーザー一覧</h1>
      <ul>
        {users.map((user) => (
          <li key={user.id}>
            {user.name}（{user.email}）
          </li>
        ))}
      </ul>
    </div>
  );
}
