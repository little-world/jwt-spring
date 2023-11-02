package com.example.security.config;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
public class TokenProvider {

  private static final Logger logger = LoggerFactory.getLogger(TokenProvider.class);

  private final SecretKey secretKey = Jwts.SIG.HS256.key().build();

  public String getUserNameFromToken(String token) {


    JwtParser parser = Jwts.parser()
        .verifyWith(secretKey)
        .build();
    Claims claims = parser.parseSignedClaims(token).getPayload();
    System.out.println(claims.getSubject());
    return claims.getSubject();
  }


  public String doGenerateToken(String username) {
    String token = Jwts.builder()
//       .encryptWith(secretKey, Jwts.ENC.A128CBC_HS256)
        .signWith(secretKey)
//        .issuer("me")
        .subject(username)
//        .claim("first", "eerste")
//        .issuedAt(new Date(System.currentTimeMillis()))
//        .expiration(new Date(System.currentTimeMillis() + 1500000L))

        .compact();

    System.out.println(token);
    return token;
  }

  public boolean validateToken(String authToken) {
    try {
      System.out.println("token: " + authToken);

      JwtParser parser = Jwts.parser()
         // .decryptWith(secretKey)
          .verifyWith(secretKey)
          .build();
      parser.parseSignedClaims(authToken);
      //parser.parseEncryptedContent(authToken);
      return true;
    } catch (SecurityException ex) {
      logger.error("Invalid JWT signature");
    } catch (MalformedJwtException ex) {
      logger.error("Invalid JWT token");
    } catch (ExpiredJwtException ex) {
      logger.error("Expired JWT token");
    } catch (UnsupportedJwtException ex) {
      logger.error("Unsupported JWT token");
    } catch (IllegalArgumentException ex) {
      logger.error("JWT claims string is empty.");
    }
    return false;
  }
}
