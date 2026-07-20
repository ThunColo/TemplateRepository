import { useState, type FormEvent } from "react";
import { authApi } from "../features/auth/authApi";
import { useAuth } from "../features/auth/AuthContext";

/**
 * ログイン画面のコンポーネント。
 *
 * @author nahaton 2026/07/20
 */

export function LoginPage() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState<string | null>(null);
  const [submitting, setSubmitting] = useState(false);
  const { setUser } = useAuth();

  /**
   * フォームが送信されたときの処理。
   *
   * @param e - フォーム送信イベント
   * @throws エラーが発生した場合にエラーメッセージを設定
   */
  async function handleSubmit(e: FormEvent): Promise<void> {
    e.preventDefault();
    setError(null);
    setSubmitting(true);

    try {
      const result = await authApi.login(email, password);
      setUser(result); // コンテキストの状態を更新
    } catch {
      setError("メールアドレスまたはパスワードが正しくありません");
    } finally {
      setSubmitting(false);
    }
  }

  return (
    <div>
      <h1>ログイン</h1>
      <form onSubmit={handleSubmit}>
        <div>
          <label>
            メールアドレス
            <input
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
            />
          </label>
        </div>
        <div>
          <label>
            パスワード
            <input
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </label>
        </div>
        {error && <p style={{ color: "red" }}>{error}</p>}
        <button type="submit" disabled={submitting}>
          {submitting ? "ログイン中..." : "ログイン"}
        </button>
      </form>
    </div>
  );
}
