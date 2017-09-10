package ch.teko.ntb.business.util;

import ch.teko.ntb.dto.oUser;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by antic-software-ing on 09.09.2017.
 * helper class to create and validate token
 */
public class TokenHandler {
  private static final int TOKEN_EXPIRES = 10080;
  private static final String SECRET_KEY = "fg65050!$$'09053245äewfmjärgjysrägietä34345r$ä,lg$säet,ghk$dyfpgk";

  public static String createToken(oUser userDto) throws Exception {
    String token;
    Calendar exp = Calendar.getInstance();
    exp.add(Calendar.MINUTE, TOKEN_EXPIRES);
    int userId = userDto.getId();

    try {
      Algorithm algorithmHS = Algorithm.HMAC512(SECRET_KEY);
      token = JWT.create()
          .withIssuedAt(new Date())
          .withExpiresAt(exp.getTime())
          .withClaim(ETokenClaims.USER_ID.name(), userId)
          .sign(algorithmHS);
    } catch (UnsupportedEncodingException exception) {
      throw new Exception("Token error: UTF-8 encoding is not supported");
    } catch (JWTCreationException exception) {
      throw new Exception("Token error: Invalid token or could not convert Claims");
    }

    return token;
  }

  public static int getUserIdByToken(String token) throws Exception {
    try {
      DecodedJWT jwt = decodeToken(token);
      int userId = jwt.getClaim(ETokenClaims.USER_ID.name()).asInt();

      return userId;
    } catch (UnsupportedEncodingException exception) {
      System.err.println(exception.getMessage());
      throw new Exception("Token error: UTF-8 encoding is not supported");
    } catch (JWTVerificationException exception) {
      System.err.println(exception.getMessage());
      throw new Exception("Token error: Invalid token or could not convert Claims");
    }
  }

  private static DecodedJWT decodeToken(String token) throws UnsupportedEncodingException {
    JWTVerifier verifier = JWT.require(Algorithm.HMAC512(SECRET_KEY)).build();
    return verifier.verify(token);
  }
}
