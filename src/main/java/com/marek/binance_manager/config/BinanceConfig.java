package com.marek.binance_manager.config;

import com.binance.connector.client.impl.SpotClientImpl;
import com.binance.connector.futures.client.impl.CMFuturesClientImpl;
import com.binance.connector.futures.client.impl.FuturesClientImpl;
import com.binance.connector.futures.client.impl.UMFuturesClientImpl;
import com.binance.connector.futures.client.utils.ProxyAuth;
import okhttp3.Authenticator;
import okhttp3.Credentials;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;
import java.net.Proxy;

@Configuration
public class BinanceConfig {

    @Value("${binance.testnet.futuresApikey}")
    private String futuresApiKey;

    @Value("${binance.testnet.futuresSeckey}")
    private String futuresSecretKey;

    @Value("${binance.testnet.spotApiKey}")
    private String spotApiKey;

    @Value("${binance.testnet.spotSecKey}")
    private String spotSecretKey;

    @Value("${binance.testnet.futuresUrl}")
    private String futuresBinanceUrl;

    @Value("${binance.testnet.spotUrl}")
    private String spotBinanceUrl;


    @Value("${binance.proxy.enabled}")
    private boolean isProxyEnabled;

    @Value("${binance.proxy.ip}")
    private String proxyIp;

    @Value("${binance.proxy.port}")
    private int proxyPort;

    @Value("${binance.proxy.user}")
    private String proxyUser;

    @Value("${binance.proxy.password}")
    private String proxyPassword;

    @Bean
    public UMFuturesClientImpl umClient() {
        UMFuturesClientImpl client = new UMFuturesClientImpl(futuresApiKey, futuresSecretKey, futuresBinanceUrl);
        setProxy(client);

        return client;
    }

    @Bean
    public CMFuturesClientImpl cmClient() {
        CMFuturesClientImpl client = new CMFuturesClientImpl(futuresApiKey, futuresSecretKey, futuresBinanceUrl);
        setProxy(client);

        return client;
    }

    @Bean
    public SpotClientImpl spotClient() {
        SpotClientImpl client = new SpotClientImpl(spotApiKey, spotSecretKey, spotBinanceUrl);
        setProxy(client);

        return client;
    }

    private void setProxy(FuturesClientImpl client) {
        if (isProxyEnabled) {
            Proxy proxyConn = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyIp, proxyPort));
            Authenticator auth = null;
            if (!proxyUser.isBlank()) {
                auth = (route, response) -> {
                    if (response.request().header("Proxy-Authorization") != null) {
                        return null;
                    }
                    String credential = Credentials.basic(proxyUser, proxyPassword);
                    return response.request().newBuilder().header("Proxy-Authorization", credential).build();
                };
            }
            ProxyAuth proxy = new ProxyAuth(proxyConn, auth);
            client.setProxy(proxy);
        }
    }

    private void setProxy(SpotClientImpl client) {
        if (isProxyEnabled) {
            Proxy proxyConn = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyIp, proxyPort));
            Authenticator auth = null;
            if (!proxyUser.isBlank()) {
                auth = (route, response) -> {
                    if (response.request().header("Proxy-Authorization") != null) {
                        return null;
                    }
                    String credential = Credentials.basic(proxyUser, proxyPassword);
                    return response.request().newBuilder().header("Proxy-Authorization", credential).build();
                };
            }
            com.binance.connector.client.utils.ProxyAuth proxy = new com.binance.connector.client.utils.ProxyAuth(proxyConn, auth);
            client.setProxy(proxy);
        }
    }


}

