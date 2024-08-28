package org.depromeet.sambad.moring.infra.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.swagger.v3.oas.annotations.Parameter;

/**
 * 해당 Annotation을 부착한 파라미터에 UserId를 주입합니다.<br />
 * Controller의 Parameter는 모두 Swagger에 표시되기 때문에, @Parameter(hidden = true)를 추가합니다.
 *
 * @see org.depromeet.sambad.moring.infra.resolver.UserIdArgumentResolver
 */
@Parameter(hidden = true)
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserId {
}
