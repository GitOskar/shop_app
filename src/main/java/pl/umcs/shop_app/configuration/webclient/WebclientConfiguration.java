package pl.umcs.shop_app.configuration.webclient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import pl.umcs.shop_app.domain.nbp.properties.NbpCurrencyRateProperties;
import pl.umcs.shop_app.domain.payu.properties.PayuProperties;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

import static io.netty.handler.codec.http.HttpHeaders.Values.APPLICATION_JSON;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

@Configuration
public class WebclientConfiguration {

    @Bean
    public WebClient nbpWebClient(NbpCurrencyRateProperties nbpCurrencyRateProperties) {
        return WebClient.builder()
                .baseUrl(nbpCurrencyRateProperties.getDownloadUrl())
                .defaultHeader(CONTENT_TYPE, APPLICATION_JSON)
                .clientConnector(new ReactorClientHttpConnector(HttpClient.create()
                        .responseTimeout(Duration.ofMillis(nbpCurrencyRateProperties.getTimeoutMs()))))
                .build();
    }

    @Bean
    public WebClient payuWebClient(PayuProperties payuProperties) {
        return WebClient.builder()
                .baseUrl(payuProperties.getPaymentUrl())
                .defaultHeader(CONTENT_TYPE, APPLICATION_JSON)
                .clientConnector(new ReactorClientHttpConnector(HttpClient.create()
                        .responseTimeout(Duration.ofMillis(payuProperties.getTimeoutMs()))))
                .build();
    }
}
