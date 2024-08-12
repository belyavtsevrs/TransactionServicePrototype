package com.example.TransactionServicePrototype.Transaction.web.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springframework.context.annotation.Bean;

public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("My API").version("1.0"))
                .path("/greet", new PathItem()
                        .get(new Operation()
                                .summary("Greet the user")
                                .addParametersItem(new Parameter()
                                        .name("name")
                                        .in("query")
                                        .schema(new StringSchema()._default("Guest"))
                                        .description("Name of the user"))
                                .responses(new ApiResponses()
                                        .addApiResponse("200", new ApiResponse()
                                                .description("A greeting message")
                                                .content(new Content()
                                                        .addMediaType("application/json", new MediaType()
                                                                .schema(new StringSchema().example("Hello, Guest"))))))));
    }
}
