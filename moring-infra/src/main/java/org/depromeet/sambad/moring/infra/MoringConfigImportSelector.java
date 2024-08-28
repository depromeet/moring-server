package org.depromeet.sambad.moring.infra;

import java.util.Arrays;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;

class MoringConfigImportSelector implements DeferredImportSelector {

	@Override
	public String[] selectImports(AnnotationMetadata metadata) {
		return Arrays.stream(getValues(metadata))
			.map(MoringConfigGroup::getConfigClass)
			.map(Class::getName)
			.toArray(String[]::new);
	}

	private MoringConfigGroup[] getValues(AnnotationMetadata metadata) {
		Map<String, Object> attributes = metadata.getAnnotationAttributes(EnableMoringConfig.class.getName());
		return (MoringConfigGroup[])MapUtils.getObject(attributes, "value", new MoringConfigGroup[] {});
	}
}
