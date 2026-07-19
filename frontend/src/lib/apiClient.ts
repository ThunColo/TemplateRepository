import axios from "axios";

/**
 * APIクライアントの設定を行うモジュール。
 *
 * * @author nahaton 2026/07/19
 */

export const apiClient = axios.create({
  baseURL: "http://localhost:8080/api",
  headers: {
    "Content-Type": "application/json",
  },
});
