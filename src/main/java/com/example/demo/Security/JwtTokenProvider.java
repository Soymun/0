package com.example.demo.Security;

import com.example.demo.Entity.Role;
import com.example.demo.Service.Impl.UserServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.security.secret}")
    private String secret;

    @Value("${jwt.security.validate}")
    private Long validate;

    private final UserServiceImpl userService;

    public JwtTokenProvider(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostConstruct
    public void init(){
        secret = Base64.getEncoder().encodeToString(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String createToken(String userName, Role role){
        Claims claims = Jwts.claims().setSubject(userName);
        claims.put("role", role);

        Date issue = new Date();
        Date ex = new Date(issue.getTime() + validate);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(issue)
                .setExpiration(ex)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public String getEmail(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public Authentication authentication(String token){
        UserDetails userDetails = userService.loadUserByUsername(getEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String resolveToken(HttpServletRequest httpServletRequest){
        String bearer = httpServletRequest.getHeader("Authorization");
        if(bearer != null && bearer.startsWith("Bearer ")){
            return bearer.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token){
        try {
            Jws<Claims> jws = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return !jws.getBody().getExpiration().before(new Date());
        }
        catch (Exception e){
            return false;
        }
    }
}
