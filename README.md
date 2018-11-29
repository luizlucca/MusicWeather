# MusicWeather

Micro-Servicos:
 - Micro-Music: Micro Servico para consulta de musicas utilizando Spotify
 - Micro-Weather: Micro Servico para consulta de musicas utizando OpenWeather e HGBrasil
 - Micro-Music-Weather: Micro Servico para, dada uma cidade ou lat/lng retornar uma lista de musicas, por categoria, baseado na temperatura

Passor para utilizar a api:

Iniciar Micro-Music:
 -Entrar a pasta Micro-Music e executar: ./gradlew bootRun
 
Iniciar Micro-Weather:
 -Entrar a pasta Micro-Weather e executar: ./gradlew bootRun
 
Iniciar Micro-Music-Weather:
-Entrar a pasta Micro-Music-Weather e executar: ./gradlew bootRun

Depois de todos os micro-servicos em execucao, podemos chamar pelo Postman:
- localhost:8083/mw/byCity?city=Araraquara (consulta por cidade)
- localhost:8083/mw/byLatLng?lat=-22.0&lng=-47.0 (consulta por lat/lng)
