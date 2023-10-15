package ru.volgait.simbirgo.Token;

public record Tokens(String accessToken, String accessTokenExpiry, String refreshToken, String refreshTokenExpiry) {
}
