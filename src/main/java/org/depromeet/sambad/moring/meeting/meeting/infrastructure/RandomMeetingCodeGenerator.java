package org.depromeet.sambad.moring.meeting.meeting.infrastructure;

import static java.util.UUID.*;

import org.depromeet.sambad.moring.meeting.meeting.application.MeetingCodeGenerator;
import org.depromeet.sambad.moring.meeting.meeting.domain.MeetingCode;
import org.springframework.stereotype.Service;

@Service
public class RandomMeetingCodeGenerator implements MeetingCodeGenerator {

    @Override
    public MeetingCode generate() {
        String code = randomUUID().toString()
                .substring(0, 6)
                .toUpperCase();

        return MeetingCode.from(code);
    }
}
