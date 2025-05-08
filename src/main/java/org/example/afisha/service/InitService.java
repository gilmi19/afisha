package org.example.afisha.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;

@Profile("init")
@Component
@Slf4j
@RequiredArgsConstructor
public class InitService {

    @Value("${init.file-name}")
    private String filePath;
    private final JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void init() throws IOException {
        String sqlScripts = readFile(filePath);
        log.info("Init data completed");
        jdbcTemplate.execute(sqlScripts);
    }

    private String readFile(String filePath) throws IOException {
        StringBuilder result = new StringBuilder();
        try (FileReader fileReader = new FileReader(filePath)) {
            while (fileReader.ready()) {
                char symbol = (char) fileReader.read();
                result.append(symbol);
            }
        } catch (IOException e) {
            log.warn("Bad file path {}", filePath);
            throw new IOException("Bad file path" + filePath);
        }
        return result.toString();
    }
}
