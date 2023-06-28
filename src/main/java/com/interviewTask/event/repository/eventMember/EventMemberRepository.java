package com.interviewTask.event.repository.eventMember;

import com.interviewTask.event.model.eventMember.EventMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventMemberRepository extends JpaRepository<EventMember, Long> {
}
