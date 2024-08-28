package org.depromeet.sambad.moring.domain.file.domain;

import org.depromeet.sambad.moring.domain.common.domain.BaseTimeEntity;
import org.depromeet.sambad.moring.domain.file.presentation.exception.ObjectStorageServerException;
import org.springframework.util.StringUtils;

import jakarta.persistence.Column;
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
	@Column(name = "file_id")
	private Long id;

	private String logicalName;

	private String physicalPath;

	/*
	 * TODO: ERD에 기반하면, 사용자 업로드 file과 시스템 file이 슈퍼-서브타입으로서 구분되어야 합니다.
	 *  추후 마이그레이션 작업을 수행할 예정입니다.
	 */
	@Column(columnDefinition = "TINYINT")
	private Boolean isDefault;

	private FileEntity(String logicalName, String physicalPath, Boolean isDefault) {
		this.logicalName = logicalName;
		this.physicalPath = physicalPath;
		this.isDefault = isDefault;
	}

	public static FileEntity of(String logicalName, String filePath) {
		if (!StringUtils.hasText(logicalName) || !StringUtils.hasText(filePath)) {
			throw new ObjectStorageServerException();
		}

		return new FileEntity(logicalName, filePath, false);
	}
}
