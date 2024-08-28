package org.depromeet.sambad.moring.domain.meeting.member.infrastructure;

import java.util.List;

import org.depromeet.sambad.moring.domain.meeting.member.domain.Hobby;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HobbyJpaRepository extends JpaRepository<Hobby, Long> {

	List<Hobby> findByIdIn(List<Long> ids);
}
