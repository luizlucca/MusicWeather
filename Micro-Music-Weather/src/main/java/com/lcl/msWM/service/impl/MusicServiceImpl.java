package com.lcl.msWM.service.impl;

import com.lcl.msWM.exception.MusicBadRequestException;
import com.lcl.msWM.exception.MusicNotFoundException;
import com.lcl.msWM.exception.MusicServiceUnavailableException;
import com.lcl.msWM.model.DTO.MusicDTO;
import com.lcl.msWM.service.intDef.MusicService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author s2it_llucca
 * @version $Revision: $<br/>
 * $Id: $
 * @since 11/29/18 10:28 AM
 */
@Service
public class MusicServiceImpl implements MusicService {

    private final Logger log = LoggerFactory.getLogger(WeatherServiceImpl.class);

    @Value("${music.server.port}")
    private String musicServerPort;

    @Value("${url.music.base}")
    private String musicBaseUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Override

    public MusicDTO getTracksByCategory(final String category) {
        try {

            String uri = UriComponentsBuilder.fromHttpUrl(musicBaseUrl + "/byCategory?category={category}").port(musicServerPort)
                    .buildAndExpand(category).toUriString().trim();

            HttpEntity<MusicDTO> response = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    makeDefaultHttpAppJsonHeader(),
                    MusicDTO.class);

            return response.getBody();
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                log.error("E=Category Not Found, category={}", category, e);
                throw new MusicNotFoundException("Category not found", e);
            }

            if (e.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
                log.error("E=Music parameter error, category={}", category, e);
                throw new MusicBadRequestException("Music parameter error", e);
            }

            log.error("E=Unknow error, category={}", category, e);
            throw e;
        } catch (ResourceAccessException e) {
            log.error("E=Service unavailable, category={}", category, e);
            throw new MusicServiceUnavailableException("Service unavailable", e);
        } catch (Exception e) {
            log.error("E=Unknow error, category={}", category, e);
            throw e;
        }
    }

    @Override
    @HystrixCommand(fallbackMethod = "trackByTemperature", ignoreExceptions = {
            MusicNotFoundException.class, MusicBadRequestException.class, MusicServiceUnavailableException.class })
    public MusicDTO getTracksByTemperature(final Double temperature) {

        //Atendendo as regras de negocio

        // If temperature (celcius) is above 30 degrees, suggest tracks for party
        if (temperature > 30) {
            log.info("I=Search for party music, temperature={}", temperature);
            return getTracksByCategory("party");
        }

        //In case temperature is between 15 and 30 degrees, suggest pop music tracks
        if (temperature >= 15 && temperature <= 30) {
            log.info("I=Search for pop music, temperature={}", temperature);
            return getTracksByCategory("pop");
        }

        //If it's a bit chilly (between 10 and 14 degrees), suggest rock music tracks
        if (temperature >= 10 & temperature < 15) {
            log.info("I=Search for rock music, temperature={}", temperature);
            return getTracksByCategory("rock");
        }

        //Otherwise,if it 's freezing outside, suggests classical music tracks
        if (temperature < 10) {
            log.info("I=Search for classic music, temperature={}", temperature);
            return getTracksByCategory("classical");
        }

        return null;
    }

    //se abrir o circuito, ira retornar servico nao disponivel diretamente,
    //evitando chamada a API do Microservico de Musica
    private MusicDTO trackByTemperature(final Double temperature) {
        throw new MusicServiceUnavailableException("Service unavailable");

        //Aqui poderia ter um cache com algumas musicas pesquisadas recentemente, por categoria
        //e ao inves de retornar servico indisponivel, retornar as musicas previamente armazenadas
        //poderia ser usado o Redis para isso ou algum sistema de Cache mais simples, como nas libs do Guava
        //ao fiz por causa de tempo
    }

    private HttpEntity<?> makeDefaultHttpAppJsonHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        return new HttpEntity<>(headers);
    }
}
