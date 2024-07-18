package org.depromeet.sambad.moring.file.domain;

import org.depromeet.sambad.moring.common.domain.BaseTimeEntity;
import org.depromeet.sambad.moring.file.presentation.exception.ObjectStorageServerException;
import org.springframework.util.StringUtils;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "file")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FileEntity extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String logicalName;

	private String physicalPath;

	private FileEntity(String logicalName, String physicalPath) {
		this.logicalName = logicalName;
		this.physicalPath = physicalPath;
	}

	public static FileEntity of(String logicalName, String filePath) {
		if (!StringUtils.hasText(logicalName) || !StringUtils.hasText(filePath)) {
			throw new ObjectStorageServerException();
		}

		return new FileEntity(logicalName, filePath);
	}
}
