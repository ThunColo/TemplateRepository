package com.myapp.template.adapter.out.auth;

import com.myapp.template.domain.exception.InvalidTokenException;
import com.myapp.template.usecase.port.out.AuthProvider;
import com.myapp.template.usecase.port.out.TokenPayload;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;

/**
 * JWTを使用してトークンの発行と検証を行うための実装クラス。
 *
 * @author nahaton 2026/07/19
 */
@Component
public class JwtAuthProvider implements AuthProvider {

    private final PrivateKey privateKey; // 秘密鍵
    private final PublicKey publicKey; // 公開鍵
    private final Duration tokenValidity = Duration.ofHours(1); // トークンの有効期限（1時間）

    /**
     * JwtAuthProviderクラスのコンストラクタ。秘密鍵と公開鍵を読み込む。
     * 有効期限は1時間に設定していますが、これはテンプレートとしての初期値なので、必要に応じて調整してください
     *
     * @param privateKeyPath 秘密鍵のファイルパス
     * @param publicKeyPath 公開鍵のファイルパス
     * @throws IOException ファイルの読み込みに失敗した場合
     * @throws NoSuchAlgorithmException 鍵の生成に使用するアルゴリズムが存在しない場合
     * @throws InvalidKeySpecException 鍵の仕様が無効な場合
     */
    public JwtAuthProvider(
            @Value("${jwt.private-key-path}") String privateKeyPath,
            @Value("${jwt.public-key-path}") String publicKeyPath
    ) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        this.privateKey = loadPrivateKey(privateKeyPath);
        this.publicKey = loadPublicKey(publicKeyPath);
    }

    /**
     * ユーザーIDとメールアドレスを受け取り、JWTを発行する。
     *
     * @param userId ユーザーID
     * @param email ユーザーのメールアドレス
     * @return 発行されたJWTトークン
     */
    @Override
    public String issueToken(Long userId, String email) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + tokenValidity.toMillis());

        return Jwts.builder()
                .subject(String.valueOf(userId))
                .claim("email", email)
                .issuedAt(now)
                .expiration(expiry)
                .signWith(privateKey, Jwts.SIG.RS256)
                .compact();
    }

    /**
     * 渡されたトークンの署名を公開鍵で検証し、有効期限が切れていないかもチェックする。
     *
     * @param token 検証するJWTトークン
     * @return トークンのペイロード情報を含むTokenPayloadオブジェクト
     */
    @Override
    public TokenPayload verifyToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(publicKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            Long userId = Long.valueOf(claims.getSubject());
            String email = claims.get("email", String.class);
            return new TokenPayload(userId, email);

        } catch (ExpiredJwtException e) {
            throw new InvalidTokenException("Token has expired");
        } catch (JwtException e) {
            throw new InvalidTokenException("Invalid token: " + e.getMessage());
        }
    }

    /**
     * 秘密鍵を読み込む。
     *
     * @param path 秘密鍵のファイルパス
     * @return 読み込まれた秘密鍵
     */
    private PrivateKey loadPrivateKey(String path)
            throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        String content = readPemFile(path)
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");
        byte[] bytes = Base64.getDecoder().decode(content);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(bytes);
        return KeyFactory.getInstance("RSA").generatePrivate(spec);
    }

    /**
     * 公開鍵を読み込む。
     *
     * @param path 公開鍵のファイルパス
     * @return 読み込まれた公開鍵
     */
    private PublicKey loadPublicKey(String path)
            throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        String content = readPemFile(path)
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");
        byte[] bytes = Base64.getDecoder().decode(content);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(bytes);
        return KeyFactory.getInstance("RSA").generatePublic(spec);
    }

    /**
     * 指定されたパスからPEM形式のファイルを読み込む。
     *
     * @param path 読み込むPEMファイルのパス
     * @return 読み込まれたPEMファイルの内容
     */
    private String readPemFile(String path) throws IOException {
        return Files.readString(Path.of(path));
    }
}
