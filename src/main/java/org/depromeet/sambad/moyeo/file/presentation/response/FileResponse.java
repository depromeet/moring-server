package org.depromeet.sambad.moyeo.file.presentation.response;

import java.time.LocalDateTime;

import org.depromeet.sambad.moyeo.file.domain.FileEntity;

public record FileResponse(
	String url,
	String logicalName,
	LocalDateTime createAt,
	LocalDateTime updateAt

) {
	public static FileResponse of(String url, FileEntity fileEntity) {
		return new FileResponse(url, fileEntity.getLogicalName(), fileEntity.getCreateAt(), fileEntity.getUpdateAt());
	}
}
