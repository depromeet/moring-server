package org.depromeet.sambad.moyeo.file.application;

import java.util.Optional;

import org.depromeet.sambad.moyeo.file.domain.File;

public interface FileRepository {

	File save(File file);

	Optional<File> findById(Long id);
}
