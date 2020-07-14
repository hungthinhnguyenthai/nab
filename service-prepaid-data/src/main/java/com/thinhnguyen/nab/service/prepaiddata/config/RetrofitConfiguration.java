package com.thinhnguyen.nab.service.prepaiddata.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.thinhnguyen.nab.service.prepaiddata.rest.TelecomRestClient;
import com.thinhnguyen.nab.service.prepaiddata.service.CipherService;
import lombok.RequiredArgsConstructor;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;


@Configuration
@RequiredArgsConstructor
public class RetrofitConfiguration {

    @Value("${app.token.http.request.header}")
    private String tokenHeader;

    @Value("${app.service.host}")
    private String serviceHost;

    @Value("${app.client.id}")
    private String clientId;

    @Value("${app.client.secret}")
    private String clientSecret;

    @Value("${app.rest.client.request.time-out.in.seconds}")
    private long requestExpires;

    @Autowired
    CipherService cipherService;

    @Autowired
    ObjectMapper objectMapper;

    @Bean
    public OkHttpClient externalHttpClient() {
        Map<String, String> headerMap = Maps.newHashMap();
        headerMap.put(tokenHeader, String.format("Bearer %s.%s", clientId, cipherService.hmacSign(clientId, clientSecret)));
        Interceptor headerInterceptor = chain -> {
            Request request = chain.request();
            Request.Builder builder = request.newBuilder();
            headerMap.forEach((name, value) -> builder.addHeader(name, value));
            request = builder.build();
            return chain.proceed(request);
        };
        return new OkHttpClient.Builder()
                .callTimeout(requestExpires, TimeUnit.SECONDS)
                .readTimeout(requestExpires, TimeUnit.SECONDS)
                .addInterceptor(headerInterceptor)
                .build();
    }

    @Bean
    public TelecomRestClient telecomRestClient() {
        return buildRestService(serviceHost, TelecomRestClient.class , this::externalHttpClient);
    }


    private <T> T buildRestService(String baseUrl, Class<T> clazz, Supplier<OkHttpClient> clientSupplier) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(clientSupplier.get())
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .build()
                .create(clazz);
    }

}
