import { createContext, useContext, useState, type ReactNode } from "react";
import type { LoginResponse } from "./authApi";

/**
 * 認証状態を管理するコンテキスト
 *
 * @author nahaton 2026/07/20
 */
interface AuthContextValue {
  user: LoginResponse | null;
  setUser: (user: LoginResponse | null) => void;
}

const AuthContext = createContext<AuthContextValue | undefined>(undefined);

/**
 * 認証状態を管理するためのコンテキストプロバイダー。
 *
 * @param children - コンポーネントの子要素
 */
export function AuthProvider({ children }: { children: ReactNode }) {
  const [user, setUser] = useState<LoginResponse | null>(null);

  return (
    <AuthContext.Provider value={{ user, setUser }}>
      {children}
    </AuthContext.Provider>
  );
}

/**
 * 認証状態を取得するためのカスタムフック。
 *
 * @returns 認証状態とその更新関数を含むコンテキスト値
 * @throws AuthProviderの外で使用された場合にエラーをスロー
 */
export function useAuth(): AuthContextValue {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error("useAuth must be used within an AuthProvider");
  }
  return context;
}
