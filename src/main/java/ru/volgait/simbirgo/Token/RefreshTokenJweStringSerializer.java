package ru.volgait.simbirgo.Token;

import com.nimbusds.jose.*;
import com.nimbusds.jwt.EncryptedJWT;
import com.nimbusds.jwt.JWTClaimsSet;;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.function.Function;

public class RefreshTokenJweStringSerializer implements Function<Token, String> {
    private static final Logger LOGGER =  LoggerFactory.getLogger(RefreshTokenJweStringSerializer.class);


    private  JWEEncrypter jweEncrypter;

    private JWEAlgorithm jweAlgorithm = JWEAlgorithm.DIR;

    private EncryptionMethod encryptionMethod = EncryptionMethod.A128GCM;

    public RefreshTokenJweStringSerializer(JWEAlgorithm jweAlgorithm){
        this.jweAlgorithm = jweAlgorithm;
    }

    public RefreshTokenJweStringSerializer(JWEEncrypter jweEncrypter){
        this.jweEncrypter = jweEncrypter;
    }

    public RefreshTokenJweStringSerializer(JWEAlgorithm jweAlgorithm, JWEEncrypter jweEncrypter, EncryptionMethod encryptionMethod){
        this.jweAlgorithm = jweAlgorithm;
        this.jweEncrypter = jweEncrypter;
        this.encryptionMethod = encryptionMethod;
    }
    @Override
    public String apply(Token token){
        JWEHeader jweHeader = new JWEHeader.Builder(this.jweAlgorithm, this.encryptionMethod)
                .keyID(token.id().toString())
                .build();
        JWTClaimsSet jwtClaimSet = new JWTClaimsSet.Builder()
                .jwtID(token.id().toString())
                .subject(token.subject())
                .issueTime(Date.from(token.createdAt()))
                .expirationTime(Date.from(token.expiresAt()))
                .claim("authorites", token.authorities())
                .build();
        EncryptedJWT encryptedJWT = new EncryptedJWT(jweHeader,jwtClaimSet);
        try{
            encryptedJWT.encrypt(this.jweEncrypter);
            return encryptedJWT.serialize();
        }
        catch(JOSEException exception){
            LOGGER.error(exception.getMessage(), exception);
            return null;
        }

    }

    public void setJweAlgorithm(JWEAlgorithm jweAlgorithm) {
        this.jweAlgorithm = jweAlgorithm;
    }

    public void setEncryptionMethod(EncryptionMethod encryptionMethod) {
        this.encryptionMethod = encryptionMethod;
    }

}
