package org.depromeet.sambad.moring.domain.file.infrastructure;

import java.util.List;

import org.depromeet.sambad.moring.domain.file.domain.FileEntity;
import org.depromeet.sambad.moring.domain.file.domain.FileRepository;
import org.depromeet.sambad.moring.domain.file.presentation.exception.NotFoundFileException;
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
	public boolean existsById(Long id) {
		return fileJpaRepository.existsById(id);
	}

	@Override
	public void deleteById(Long id) {
		fileJpaRepository.deleteById(id);
	}

	@Override
	public FileEntity findById(Long id) {
		return fileJpaRepository.findById(id)
			.orElseThrow(NotFoundFileException::new);
	}

	@Override
	public List<FileEntity> findAllByIsDefaultTrue() {
		return fileJpaRepository.findAllByIsDefaultTrue();
	}
}
