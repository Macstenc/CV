package pl.kul.kulzaki.Util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import pl.kul.kulzaki.Entity.Customer;

import java.rmi.ServerError;
import java.util.Date;

@Component
public class JwtTokenUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    public String generateToken(String username) {
        return JWT.create()
                .withSubject(username)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000)) // 10 minutes expiration
                .sign(Algorithm.HMAC256(secretKey));
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String getUsernameFromToken(String token) {
        try {
            return getDecodedToken(token).getSubject();
        } catch (TokenExpiredException e) {
            System.err.println("Token has expired");
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean isTokenExpired(String token) {
        final Date expiration = getDecodedToken(token).getExpiresAt();
        return expiration.before(new Date());
    }

    private DecodedJWT getDecodedToken(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secretKey))
                .build();
        return verifier.verify(token);
    }
}
