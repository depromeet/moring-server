package org.depromeet.sambad.moyeo.user.presentation.resolver;

import io.swagger.v3.oas.annotations.Parameter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 해당 Annotation을 부착한 파라미터에 UserId를 주입합니다.<br />
 * Controller의 Parameter는 모두 Swagger에 표시되기 때문에, @Parameter(hidden = true)를 추가합니다.
 *
 * @see UserIdArgumentResolver
 */
@Parameter(hidden = true)
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserId {
}
