package org.depromeet.sambad.moring.file.presentation.annotation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 필드에 해당 어노테이션을 부착할 시, 해당 필드의 값을 전체 파일 URL로 변환하여 반환합니다.<br />
 * e.g., "/path/to/file" -> "http://example.com/path/to/file"
 *
 * @see FullFileUrlSerializer
 * @see FullFileUrlAnnotationIntrospector
 * @see org.depromeet.sambad.moring.common.config.JacksonConfig
 */
@Target({FIELD, METHOD, PARAMETER, TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
public @interface FullFileUrl {
}
