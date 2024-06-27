package org.depromeet.sambad.moyeo.file.infrastructure;

import org.depromeet.sambad.moyeo.file.domain.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileJpaRepository extends JpaRepository<File, Long> {
}
