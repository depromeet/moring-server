package org.depromeet.sambad.moyeo.file.domain;

public interface FileRepository {

	FileEntity save(FileEntity fileEntity);

	boolean existsByLogicalName(String logicalName);

	void deleteByLogicalName(String logicalName);
}
