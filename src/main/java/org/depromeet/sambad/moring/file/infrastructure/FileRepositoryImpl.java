package org.depromeet.sambad.moring.file.infrastructure;

import lombok.RequiredArgsConstructor;
import org.depromeet.sambad.moring.file.domain.FileEntity;
import org.depromeet.sambad.moring.file.domain.FileRepository;
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
