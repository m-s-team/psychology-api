package ml.psychology.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web ->
                web
                        .ignoring()
                        .antMatchers("/swagger-ui/**")
                        .antMatchers("/v3/api-docs/**")
                        .antMatchers("/actuator/**");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, Environment env) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable();

        if (env.acceptsProfiles(Profiles.of("dev")))
            http.authorizeRequests().antMatchers("/h2-console/**").permitAll();

        http.authorizeRequests().anyRequest().authenticated();

        return http.build();
    }
}
