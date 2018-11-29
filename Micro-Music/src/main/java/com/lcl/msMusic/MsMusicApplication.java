package com.lcl.msMusic;

import java.io.IOException;

import com.lcl.msMusic.exception.SpotifyAuthException;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MsMusicApplication {

    private final Logger log = LoggerFactory.getLogger(MsMusicApplication.class);

    @Value("${spotify.clientId}")
    private String spotifyClientId;

    @Value("${spotify.clientSecret}")
    private String spotifyClientSecret;

    public static void main(String[] args) throws IOException, SpotifyWebApiException {
        SpringApplication.run(MsMusicApplication.class, args);
    }

    @Bean
    public SpotifyApi spotifyApi() {
        try {
            log.info("I=Iniciando api spotify");
            SpotifyApi spotifyApi = new SpotifyApi.Builder()
                    .setClientId(spotifyClientId)
                    .setClientSecret(spotifyClientSecret)
                    .build();

            ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials()
                    .build();

            ClientCredentials clientCredentials = clientCredentialsRequest.execute();

            spotifyApi.setAccessToken(clientCredentials.getAccessToken());
            return spotifyApi;
        } catch (Exception e) {
            log.error("E=Erro ao iniciar spotify", e);
            throw new SpotifyAuthException();
        }
    }

}
