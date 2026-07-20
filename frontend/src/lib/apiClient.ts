import axios from "axios";

/**
 * APIクライアントの設定を行うモジュール。
 *
 * @author nahaton 2026/07/19
 * @update nahaton 2026/07/20 AxiosがリクエストにCookieを含めて送信する処理を追加。
 * @update nahaton 2026/07/20 401 Unauthorizedを受け取ったときの処理を設定するためのコールバック関数を追加
 */

/**
 * Axiosのインスタンスを作成し、APIのベースURLと共通ヘッダーを設定する。
 * withCredentialsをtrueに設定することで、リクエストにCookieを含めて送信する。
 * これにより、サーバー側でセッション管理が可能になる。
 */
export const apiClient = axios.create({
  baseURL: "http://localhost:8080/api",
  headers: {
    "Content-Type": "application/json",
  },
  withCredentials: true,
});

/**
 * 401 Unauthorizedを受け取ったときの処理を設定するためのコールバック関数。
 * 初期値はnullで、setUnauthorizedHandler関数を使用して設定することができる。
 * このコールバックは、ユーザーが認証されていない場合にログインページにリダイレクトするなどの処理を行うために使用される。
 */
let onUnauthorized: (() => void) | null = null;

/**
 * 401 Unauthorizedを受け取ったときの処理を設定する。
 *
 * @param handler - 401 Unauthorizedを受け取ったときに実行するコールバック関数
 */
export function setUnauthorizedHandler(handler: () => void) {
  onUnauthorized = handler;
}

/**
 * Axiosのレスポンスインターセプターを設定する。
 * レスポンスが401 Unauthorizedの場合、onUnauthorizedコールバックを実行する。
 * それ以外のエラーはそのままPromise.rejectで返す。
 *
 * @throws エラーが発生した場合にPromise.rejectで返す
 */
apiClient.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401 && onUnauthorized) {
      onUnauthorized();
    }
    return Promise.reject(error);
  },
);
