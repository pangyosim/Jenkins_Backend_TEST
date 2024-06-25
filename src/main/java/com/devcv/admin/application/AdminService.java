package com.devcv.admin.application;

import com.devcv.admin.repository.AdminResumeRepository;
import com.devcv.common.exception.ErrorCode;
import com.devcv.common.exception.NotFoundException;
import com.devcv.event.domain.Event;
import com.devcv.event.domain.dto.EventRequest;
import com.devcv.event.repository.EventRepository;
import com.devcv.resume.domain.Resume;
import com.devcv.resume.domain.dto.PaginatedResumeResponse;
import com.devcv.resume.domain.dto.ResumeDto;
import com.devcv.resume.domain.dto.ResumeResponse;
import com.devcv.resume.domain.enumtype.ResumeStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final EventRepository eventRepository;
    private final AdminResumeRepository adminResumeRepository;

    public Event createEvent(EventRequest eventRequest) {
        Event event = Event.of(eventRequest.name(), eventRequest.startDate(), eventRequest.endDate());
        return eventRepository.save(event);
    }

    public PaginatedResumeResponse getResumesByStatus(String inputStatus, Pageable pageable) {
        ResumeStatus status = ResumeStatus.valueOf(inputStatus);
        Page<Resume> resumePage = adminResumeRepository.findAllByStatus(status, pageable);
        List<ResumeDto> resumeDtos = resumePage.getContent()
                .stream()
                .map(ResumeDto::from)
                .toList();
        int currentPage = resumePage.getNumber() + 1;
        int totalPages = resumePage.getTotalPages();
        int startPage = Math.max(1, currentPage - 2);
        int endPage = Math.min(totalPages, currentPage + 2);
        return new PaginatedResumeResponse(
                resumeDtos,
                resumePage.getTotalElements(),
                resumePage.getNumberOfElements(),
                currentPage,
                totalPages,
                resumePage.getSize(),
                startPage,
                endPage
        );
    }

    public ResumeResponse getResume(Long resumeId) {
        Resume resume = adminResumeRepository.findByResumeId(resumeId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.RESUME_NOT_FOUND));
        return ResumeResponse.from(resume);
    }
}