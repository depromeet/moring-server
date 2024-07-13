package org.depromeet.sambad.moyeo.file.infrastructure;

import lombok.RequiredArgsConstructor;
import org.depromeet.sambad.moyeo.file.domain.FileEntity;
import org.depromeet.sambad.moyeo.file.domain.FileRepository;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class FileRepositoryImpl implements FileRepository {
	private final FileJpaRepository fileJpaRepository;

	@Override
	public FileEntity save(FileEntity fileEntity) {
		return fileJpaRepository.save(fileEntity);
	}
}
