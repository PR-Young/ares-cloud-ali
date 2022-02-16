package com.ares.core.config.swagger;

import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import springfox.documentation.RequestHandler;
import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.ModelPropertyBuilderPlugin;
import springfox.documentation.spi.schema.contexts.ModelPropertyContext;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.common.SwaggerPluginSupport;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static springfox.documentation.schema.Annotations.findPropertyAnnotation;
import static springfox.documentation.swagger.schema.ApiModelProperties.findApiModePropertyAnnotation;

/**
 * @description:
 * @author: Young 2020/02/02
 **/
@Slf4j
@EnableKnife4j
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Autowired
    private SwaggerProperties swaggerProperties;

    private static final String SPLIT_COMMA = ",";
    private static final String SPLIT_SEMICOLON = ";";
    private Class<?>[] ignoredParameterTypes = new Class[]{
            ServletRequest.class,
            ServletResponse.class,
            HttpServletRequest.class,
            HttpServletResponse.class,
            HttpSession.class,
            ApiIgnore.class
    };

    @Bean
    public Docket createRestApi() {
        String[] basePackages = getBasePackages();
        ApiSelectorBuilder apiSelectorBuilder = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select();
        if (ArrayUtils.isEmpty(basePackages)) {
            apiSelectorBuilder.apis(RequestHandlerSelectors.withClassAnnotation(Api.class));
        } else {
            apiSelectorBuilder.apis(basePackage(basePackages));
        }
        Docket docket = apiSelectorBuilder.paths(PathSelectors.any())
                .build()
                .enable(swaggerProperties.isEnable())
                .ignoredParameterTypes(ignoredParameterTypes)
                .globalOperationParameters(getParameters());
        return docket;

    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(swaggerProperties.getTitle()) //设置文档的标题
                .description(swaggerProperties.getDescription()) // 设置文档的描述
                .version(swaggerProperties.getVersion()) // 设置文档的版本信息
                .termsOfServiceUrl(swaggerProperties.getUrl())
                .contact(new Contact(swaggerProperties.getContactName(), swaggerProperties.getContactUrl(), swaggerProperties.getContactEmail()))
                .build();
    }

    private List<Parameter> getParameters() {
        List<SwaggerProperties.ParameterConfig> parameterConfig = swaggerProperties.getParameterConfig();
        if (CollectionUtils.isEmpty(parameterConfig)) {
            return null;
        }
        List<Parameter> parameters = new ArrayList<>();
        parameterConfig.forEach(parameter -> {
            parameters.add(new ParameterBuilder()
                    .name(parameter.getName())
                    .description(parameter.getDescription())
                    .modelRef(new ModelRef(parameter.getDateType()))
                    .parameterType(parameter.getType())
                    .required(parameter.isRequired())
                    .defaultValue(parameter.getDefaultValue())
                    .build());
        });
        return parameters;
    }

    private String[] getBasePackages() {
        String basePackage = swaggerProperties.getBasePackage();
        if (StringUtils.isBlank(basePackage)) {
            throw new RuntimeException("Swagger basePackage can not be null");
        }
        String[] basePackages = null;
        if (basePackage.contains(SPLIT_COMMA)) {
            basePackages = basePackage.split(SPLIT_COMMA);
        } else if (basePackage.contains(SPLIT_SEMICOLON)) {
            basePackages = basePackage.split(SPLIT_SEMICOLON);
        }
        return basePackages;
    }

    private Predicate<RequestHandler> basePackage(String[] basePackages) {
        return input -> declaringClass(input).transform(handlePackage(basePackages)).or(true);
    }

    private Function<Class<?>, Boolean> handlePackage(String[] basePackages) {
        return input -> {
            for (String basePackage : basePackages) {
                boolean isMatch = input.getPackage().getName().startsWith(basePackage);
                if (isMatch) {
                    return true;
                }
            }
            return false;
        };
    }

    @SuppressWarnings("deprecation")
    private Optional<? extends Class<?>> declaringClass(RequestHandler input) {
        return Optional.fromNullable(input.declaringClass());
    }

    @Component
    public static class ApiModelPropertyBuilderPlugin implements ModelPropertyBuilderPlugin {

        @Override
        public void apply(ModelPropertyContext context) {
            try {
                Optional<BeanPropertyDefinition> beanPropertyDefinitionOptional = context.getBeanPropertyDefinition();
                Optional<ApiModelProperty> annotation = Optional.absent();
                if (context.getAnnotatedElement().isPresent()) {
                    annotation = annotation.or(findApiModePropertyAnnotation(context.getAnnotatedElement().get()));
                }
                if (context.getBeanPropertyDefinition().isPresent()) {
                    annotation = annotation.or(findPropertyAnnotation(context.getBeanPropertyDefinition().get(), ApiModelProperty.class));
                }
                if (beanPropertyDefinitionOptional.isPresent()) {
                    BeanPropertyDefinition beanPropertyDefinition = beanPropertyDefinitionOptional.get();
                    if (annotation.isPresent() && annotation.get().position() != 0) {
                        return;
                    }
                    AnnotatedField annotatedField = beanPropertyDefinition.getField();
                    if (null == annotatedField) {
                        return;
                    }
                    Class<?> clazz = annotatedField.getDeclaringClass();
                    Field[] fields = clazz.getDeclaredFields();
                    Field field = clazz.getDeclaredField(annotatedField.getName());
                    boolean required = false;
                    NotNull notNull = field.getDeclaredAnnotation(NotNull.class);
                    NotBlank notBlank = field.getDeclaredAnnotation(NotBlank.class);
                    if (null != notNull || null != notBlank) {
                        required = true;
                    }
                    int position = ArrayUtils.indexOf(fields, field);
                    if (position != -1) {
                        context.getBuilder().position(position).required(required);
                    }
                }
            } catch (Exception e) {
                log.error("swagger ApiModelProperty handle failed:", e);
            }
        }

        @Override
        public boolean supports(DocumentationType documentationType) {
            return SwaggerPluginSupport.pluginDoesApply(documentationType);
        }
    }
}
