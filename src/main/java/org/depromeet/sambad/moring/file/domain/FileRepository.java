package org.depromeet.sambad.moring.file.domain;

import java.util.List;

public interface FileRepository {

	FileEntity save(FileEntity fileEntity);

	boolean existsById(Long id);

	void deleteById(Long id);

	FileEntity findById(Long id);

	List<FileEntity> findAllByIsDefaultTrue();
}
