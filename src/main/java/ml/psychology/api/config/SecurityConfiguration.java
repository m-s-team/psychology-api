package ml.psychology.api.config;

import ml.psychology.api.security.oauth2.AudienceValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration {

    @Value("${auth0.audience}")
    private String audience;
    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuerUri;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web ->
                web
                        .ignoring()
                        .antMatchers("/swagger-ui/**")
                        .antMatchers("/doc.html")
                        .antMatchers("/favicon.ico")
                        .antMatchers("/v3/api-docs/**")
                        .antMatchers("/actuator/**");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, Environment env) throws Exception {
        http
                .csrf().disable()
                .cors().and()
                .headers().frameOptions().disable();

        if (env.acceptsProfiles(Profiles.of("dev")))
            http.authorizeRequests().antMatchers("/h2-console/**").permitAll();

        http
                .authorizeRequests()
                .antMatchers("/favicon.ico").permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2ResourceServer().jwt();

        return http.build();
    }

    @Bean
    WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(
                                "https://api.psychology.ml",      // Documentation (Swagger UI and ReDoc)
                                "https://www.psychology.ml",      // Web Application
                                "http://localhost:4200",          // Angular Dev. Env.
                                "https://localhost:4200",         // Angular Dev. Env. (TLS)
                                "http://localhost:3000",          // React Dev. Env.
                                "https://localhost:3000"          // React Dev. Env. (TLS)
                        );
            }
        };
    }

    @Bean
    JwtDecoder jwtDecoder() {
        NimbusJwtDecoder jwtDecoder = JwtDecoders.fromOidcIssuerLocation(issuerUri);

        OAuth2TokenValidator<Jwt> audienceValidator = new AudienceValidator(audience);
        OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(issuerUri);
        OAuth2TokenValidator<Jwt> withAudience = new DelegatingOAuth2TokenValidator<>(withIssuer, audienceValidator);

        jwtDecoder.setJwtValidator(withAudience);

        return jwtDecoder;
    }
}
