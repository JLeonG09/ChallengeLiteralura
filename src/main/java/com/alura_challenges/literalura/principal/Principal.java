package com.alura_challenges.literalura.principal;

import com.alura_challenges.literalura.service.ConsumoAPI;

public class Principal {
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "8d564095";
}
