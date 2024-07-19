package org.depromeet.sambad.moring.common.config;

import static org.springframework.security.config.Elements.*;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
			.info(apiInfo())
			.addSecurityItem(securityRequirement())
			.servers(List.of(
				openApiServer("http://localhost:8080", "Moring API LOCAL"),
				openApiServer("https://dev-api.moring.one", "Moring API DEV")))
			.components(components());
	}

	private SecurityRequirement securityRequirement() {
		return new SecurityRequirement().addList(JWT);
	}

	private Components components() {
		return new Components().addSecuritySchemes(JWT, securityScheme());
	}

	private SecurityScheme securityScheme() {
		return new SecurityScheme()
			.name(JWT)
			.type(SecurityScheme.Type.HTTP)
			.scheme("bearer")
			.bearerFormat(JWT);
	}

	private Info apiInfo() {
		return new Info()
			.title("MORING API")
			.description("""
				우리 친해져요! 모임 관리 서비스 MORING API 입니다.\n\n
				{{HOST}}/login 접속 후, 카카오 로그인을 수행하세요.\n\n
				쿠키에 저장된 JWT 토큰을 하단 Authorize 버튼에서 입력하세요.\n\n
				""");
	}

	private Server openApiServer(String url, String description) {
		return new Server().url(url).description(description);
	}
}
