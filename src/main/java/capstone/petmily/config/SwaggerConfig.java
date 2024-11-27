package capstone.petmily.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI PetmilyAPI() {
        Info info = new Info()
                .title("Inha capstone petmily API")
                .description("Inha capstone petmily API 명세서")
                .version("1.0.0");

        return new OpenAPI()
                .addServersItem(new Server().url("/"))
                .info(info);
    }
}
