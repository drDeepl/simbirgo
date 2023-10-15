package ru.volgait.simbirgo;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.crypto.DirectEncrypter;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.jwk.OctetSequenceKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import ru.volgait.simbirgo.Token.AccessTokenJwsStringSerializer;
import ru.volgait.simbirgo.Token.JwtAuthenticationConfigurer;
import org.springframework.core.env.Environment;
import ru.volgait.simbirgo.Token.RefreshTokenJweStringSerializer;

import java.text.ParseException;


@SpringBootApplication
public class SimbirgoApplication {
	@Autowired
	Environment env;

	public static void main(String[] args) {
		SpringApplication.run(SimbirgoApplication.class, args);

	}

	@Bean
	public JwtAuthenticationConfigurer jwtAuthenticationConfigurer(@Value("${accessTokenKey}") String accessTokenKey, @Value("refreshTokenKey") String refreshTokenKey)
	throws ParseException, JOSEException {

		return new JwtAuthenticationConfigurer()
				.accessTokenStringSerializer(new AccessTokenJwsStringSerializer(new MACSigner(OctetSequenceKey.parse(accessTokenKey))))
				.refreshTokenStringSerializer(new RefreshTokenJweStringSerializer(new DirectEncrypter(OctetSequenceKey.parse(refreshTokenKey))));
	}

}
