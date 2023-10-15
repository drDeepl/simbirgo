package ru.volgait.simbirgo.Token;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.function.Function;

public class JwtAuthenticationConfigurer extends AbstractHttpConfigurer<JwtAuthenticationConfigurer, HttpSecurity> {

    private Function<Token,String> refreshTokenStringSerializer = Object::toString;

    private Function<Token,String> accessTokenStringSerializer = Object::toString;


    @Override
    public void init(HttpSecurity builder) throws Exception {
        CsrfConfigurer csrfConfigurer = builder.getConfigurer(CsrfConfigurer.class);
        if(csrfConfigurer != null){
            csrfConfigurer.ignoringRequestMatchers(new AntPathRequestMatcher("/jwt/tokens", "POST"));
        }

    }

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        RequestJwtTokensFilter filter = new RequestJwtTokensFilter();
        filter.setAccessTokenStringSerializer(this.accessTokenStringSerializer);
        filter.setRefreshTokenStringSerializer(this.refreshTokenStringSerializer);
        builder.addFilterAfter(filter, ExceptionTranslationFilter.class);
    }

    public JwtAuthenticationConfigurer refreshTokenStringSerializer(Function<Token, String> refreshTokenStringSerializer) {
        this.refreshTokenStringSerializer = refreshTokenStringSerializer;
        return this;
    }

    public JwtAuthenticationConfigurer accessTokenStringSerializer(Function<Token, String> accessTokenStringSerializer) {
        this.accessTokenStringSerializer = accessTokenStringSerializer;
        return this;
    }
}
