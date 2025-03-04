package com.alten.shop.security.provider;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.alten.shop.domain.UserPrincipal;
import com.alten.shop.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.JWTVerifier;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TokenProvider {
	
	private static final String GET_COMM_LLC ="SHOP";
	private static final String PRODUCT_MANAGEMENT_SERVICE ="PRODUCT_MANAGEMENT_SERVICE";
    private static final long ACCESS_TOKEN_EXPIRATION_TIME = 60_000; //432_000_000; //30_000;//; //1_800_000;
    public static final String TOKEN_CANNOT_BE_VERIFIED = "Token cannot be verified";
    public static final String AUTHORITIES = "authorities";


	@Value("${jwt.secret}")
    private String secret;
	
	private final UserService userService;
	


    
    public String createAccessToken(UserPrincipal userPrincipal) {
         return JWT.create()
        		.withIssuer(GET_COMM_LLC)
        		.withAudience(PRODUCT_MANAGEMENT_SERVICE)
         		  .withIssuedAt(new Date()).withSubject(userPrincipal.getUser().getEmail()).withArrayClaim(AUTHORITIES, getClaimsFromUser(userPrincipal))
                  .withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_TIME))
                  .sign(Algorithm.HMAC512(secret.getBytes()));

        }
    

    public String getSubject(String token, HttpServletRequest request) {
        try {
            return  getJWTVerifier().verify(token).getSubject();
        } catch (TokenExpiredException exception) {
            request.setAttribute("expiredMessage", exception.getMessage());
            throw exception;
        } catch (InvalidClaimException exception) {
            request.setAttribute("invalidClaim", exception.getMessage());
            throw exception;
        } catch (Exception exception) {
            throw exception;
        }
    }

    public List<GrantedAuthority>getAuthorities(String token) {
        String[] claims = getClaimsFromToken(token);
        return Arrays.stream(claims).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
    
    public Authentication getAuthentication(String email, List<GrantedAuthority> authorities, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken userPasswordAuthToken = new UsernamePasswordAuthenticationToken(userService.getUserByEmail(email), null, authorities);
        userPasswordAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return userPasswordAuthToken;
    }

    public boolean isTokenValid(String email, String token) {
        JWTVerifier verifier = getJWTVerifier();
        return !Objects.isNull(email) && !isTokenExpired(verifier, token);
    }
    

    
    private boolean isTokenExpired(JWTVerifier verifier, String token) {
        Date expiration = verifier.verify(token).getExpiresAt();
        return expiration.before(new Date());
    }
    
	private String[] getClaimsFromToken(String token) {
		
		JWTVerifier verifier=getJWTVerifier();
 		return verifier.verify(token).getClaim(AUTHORITIES).asArray(String.class);
	}


	private JWTVerifier getJWTVerifier() {
		
		JWTVerifier verifier;
        try {
            Algorithm algorithm = Algorithm.HMAC512(secret);
            verifier = JWT.require(algorithm).withIssuer(GET_COMM_LLC).build();
        }catch (JWTVerificationException exception) { throw new JWTVerificationException(TOKEN_CANNOT_BE_VERIFIED); }
        return verifier;
	}


	private String[] getClaimsFromUser(UserPrincipal userPrincipal) {
         return userPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray(String[]::new);
	}

 

}
