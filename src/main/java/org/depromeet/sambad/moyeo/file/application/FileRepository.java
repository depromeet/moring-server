package org.depromeet.sambad.moyeo.file.application;

import org.depromeet.sambad.moyeo.file.domain.File;

import java.util.Optional;

public interface FileRepository {

    File save(File file);

    Optional<File> findById(Long id);
}
