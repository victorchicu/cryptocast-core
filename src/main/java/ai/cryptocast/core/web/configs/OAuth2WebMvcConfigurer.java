package ai.cryptocast.core.web.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class OAuth2WebMvcConfigurer implements WebMvcConfigurer {
    private final long MAX_AGE_SECS = 3600;

    private String[] allowedOrigins = new String[]{"http://localhost:4200", "http://localhost:8080"};

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(allowedOrigins)
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(MAX_AGE_SECS);
    }

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/**/*")
//                .addResourceLocations("classpath:/static/")
//                .resourceChain(true)
//                .addResolver(new PathResourceResolver() {
//                    @Override
//                    protected Resource getResource(String resourcePath, Resource location) throws IOException {
//                        Resource requestedResource = location.createRelative(resourcePath);
//                        return requestedResource.exists() && requestedResource.isReadable()
//                                ? requestedResource
//                                : new ClassPathResource("/static/index.html");
//                    }
//                });
//    }
}
