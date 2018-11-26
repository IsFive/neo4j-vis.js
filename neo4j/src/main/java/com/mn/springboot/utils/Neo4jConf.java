package com.mn.springboot.utils;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * neo4j配置
 */
@Configuration
public class Neo4jConf {
    @Value("${neo4j.url}")
    private String url;
    @Value("${neo4j.username}")
    private String username;
    @Value("${neo4j.password}")
    private String password;

    @Bean(name = "driver")
    public Driver initDriver() {
        Driver driver;
        try {
            driver = GraphDatabase.driver(url, AuthTokens.basic(username, password));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return driver;
    }
}
