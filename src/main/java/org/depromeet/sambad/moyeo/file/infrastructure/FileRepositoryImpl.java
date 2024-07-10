package org.depromeet.sambad.moyeo.file.infrastructure;

import java.util.Optional;

import org.depromeet.sambad.moyeo.file.application.FileRepository;
import org.depromeet.sambad.moyeo.file.domain.File;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class FileRepositoryImpl implements FileRepository {

	private final FileJpaRepository fileJpaRepository;

	@Override
	public File save(File file) {
		return fileJpaRepository.save(file);
	}

	@Override
	public Optional<File> findById(Long id) {
		return fileJpaRepository.findById(id);
	}
}
