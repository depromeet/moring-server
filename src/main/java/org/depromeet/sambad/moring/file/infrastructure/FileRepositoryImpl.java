package org.depromeet.sambad.moring.file.infrastructure;

import lombok.RequiredArgsConstructor;
import org.depromeet.sambad.moring.file.domain.FileEntity;
import org.depromeet.sambad.moring.file.domain.FileRepository;
import org.depromeet.sambad.moring.file.presentation.exception.NotFoundFileException;
import org.springframework.stereotype.Repository;

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
}
