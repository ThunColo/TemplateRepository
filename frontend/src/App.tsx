import { useEffect } from "react";
import { AuthProvider, useAuth } from "./features/auth/AuthContext";
import { setUnauthorizedHandler } from "./lib/apiClient";
import { LoginPage } from "./pages/LoginPage";
import { UserListPage } from "./pages/UserListPage";

/**
 * アプリケーションのメインコンポーネント。
 *
 *@author nahaton 2026/07/19
 *@update nahaton 2026/07/20 401 Unauthorizedを受け取ったときの処理を設定するためのコールバック関数を追加
 */

/**
 * ユーザーの認証状態に応じて表示するページを切り替える。
 */
function AppContent() {
  const { user, setUser } = useAuth();

  useEffect(() => {
    setUnauthorizedHandler(() => setUser(null));
  }, [setUser]);

  if (!user) {
    return <LoginPage />;
  }

  return <UserListPage />;
}

/**
 * アプリケーションのルートコンポーネント。
 * AuthProviderでアプリケーション全体をラップし、認証状態を管理する。
 * AppContentコンポーネントをレンダリングし、ユーザーの認証状態に応じて表示するページを切り替える。
 */
function App() {
  return (
    <AuthProvider>
      <AppContent />
    </AuthProvider>
  );
}

export default App;
