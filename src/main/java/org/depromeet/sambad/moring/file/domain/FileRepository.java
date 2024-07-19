package org.depromeet.sambad.moring.file.domain;

public interface FileRepository {

	FileEntity save(FileEntity fileEntity);

	boolean existsById(Long id);

	void deleteById(Long id);

	FileEntity findById(Long id);
}
