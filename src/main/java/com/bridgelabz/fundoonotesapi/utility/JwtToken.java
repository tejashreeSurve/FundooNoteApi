package com.bridgelabz.fundoonotesapi.utility;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bridgelabz.fundoonotesapi.exception.InvalideTokenException;
import com.bridgelabz.fundoonotesapi.message.MessageInfo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * @author Tejashree Surve
 * Purpose : This class used for token operation.
 */
@Component
public class JwtToken {
	
	@Autowired
	MessageInfo message;
	
	// Algorithm
	SignatureAlgorithm alorithm = SignatureAlgorithm.HS256;
	
	// secret Key used by Algorithm
	static String secretKey = "iamsecretkey";

	// To generate Token
	public String generateToken(String email) {
		return Jwts.builder().setId(email).setIssuedAt(new Date(System.currentTimeMillis()))
				.signWith(alorithm, secretKey).compact();
	}

	// To decode Token
	public String getToken(String token) {
		Claims claim = null;
		try {
			claim = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			throw new InvalideTokenException(message.Invalide_Token);
		}
		return claim.getId();
	}
}
