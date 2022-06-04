package uz.pdp.clickup.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Set;

@Component
public class JwtProvider {

    private final String secretWord = "ThisIsMySecretWordAndNoBodyKnowsIt";

    public String generateToken(String username){
        //twenty hours
        long expireTime = 72000_000;
        Date expireDate = new Date(System.currentTimeMillis() + expireTime);

        String token = Jwts
                .builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, secretWord)
                .compact();

        return token;
    }

    public String getUsernameFromToken(String token){
        String username;
        try {
            username = Jwts
                    .parser()
                    .setSigningKey(secretWord)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            return username;
        }catch (Exception e){
            return null;
        }
    }

}
