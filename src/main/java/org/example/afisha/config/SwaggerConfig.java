package org.example.afisha.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        Server afishaServer = new Server();
        afishaServer.setUrl("http://localhost:8080");
        afishaServer.setDescription("Сервер Афиши");

        Contact contact = new Contact();
        contact.setEmail("amir123@gmail.com");
        contact.setName("Amir");
        contact.setUrl("amirgilmanvofficial.fr");

        Info info = new Info()
                .title("Проект Афиша")
                .version("1.0")
                .contact(contact)
                .description("Api сервис для покупки билетов");
        return new OpenAPI().servers(List.of(afishaServer)).info(info);

    }
}
