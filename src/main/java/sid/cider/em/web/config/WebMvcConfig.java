package sid.cider.em.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import sid.cider.em.common.util.StringUtils;

@Configuration
@PropertySources({
        @PropertySource("classpath:/resource.properties"),
        @PropertySource(value = "classpath:/resource-${spring.profiles.active}.properties", ignoreResourceNotFound = true)
})
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${resources.location:}")
    private String resourcesLocation;

    @Value("${resources.uri_path:}")
    private String resourcesUriPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (StringUtils.isNotEmpty(resourcesLocation)
                && StringUtils.isNotEmpty(resourcesUriPath)) {
            registry.addResourceHandler(resourcesUriPath + "/**")
                    .addResourceLocations("file://" + resourcesLocation);
        }
    }
}
