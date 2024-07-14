package org.depromeet.sambad.moyeo.file.infrastructure;

import org.depromeet.sambad.moyeo.file.domain.FileEntity;
import org.depromeet.sambad.moyeo.file.domain.FileRepository;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class FileRepositoryImpl implements FileRepository {
	private final FileJpaRepository fileJpaRepository;

	@Override
	public FileEntity save(FileEntity fileEntity) {
		return fileJpaRepository.save(fileEntity);
	}

	@Override
	public boolean existsByLogicalName(String logicalName) {
		return fileJpaRepository.existsByLogicalName(logicalName);
	}

	@Override
	public void deleteByLogicalName(String logicalName) {
		fileJpaRepository.deleteByLogicalName(logicalName);
	}
}
