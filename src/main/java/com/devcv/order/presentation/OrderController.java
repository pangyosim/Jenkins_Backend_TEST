package com.devcv.order.presentation;

import com.devcv.common.exception.ErrorCode;
import com.devcv.common.exception.ForbiddenException;
import com.devcv.member.application.MemberService;
import com.devcv.member.domain.Member;
import com.devcv.order.application.OrderService;
import com.devcv.order.domain.Order;
import com.devcv.order.domain.dto.*;
import com.devcv.resume.application.ResumeService;
import com.devcv.resume.domain.Resume;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final ResumeService resumeService;
    private final MemberService memberService;

    @GetMapping("/resumes/{resume-id}/checkout")
    public ResponseEntity<OrderSheet> checkoutResume(@AuthenticationPrincipal UserDetails userDetails,
                                                     @PathVariable("resume-id") Long resumeId) {
        Member member = extractMember(userDetails);
        Resume resume = resumeService.findByResumeId(resumeId);
        return ResponseEntity.ok().body(orderService.getOrderSheet(member, resume));
    }

    @PostMapping("/orders")
    public ResponseEntity<Void> createOrder(@AuthenticationPrincipal UserDetails userDetails,
                                            @RequestBody CartOrderRequest cartOrderRequest) {
        Member member = extractMember(userDetails);
        Order order = orderService.createOrder(member, cartOrderRequest);
        return ResponseEntity.created(URI.create(String.valueOf(order.getOrderId()))).build();
    }

    @GetMapping("/orders/{order-id}")
    public ResponseEntity<OrderResponse> getOrderResponse(@AuthenticationPrincipal UserDetails userDetails,
                                                          @PathVariable("order-id") Long orderId) {
        Member member = extractMember(userDetails);
        OrderResponse response = orderService.getOrderResponse(orderId, member);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/members/{member-id}/orders")
    public ResponseEntity<OrderListResponse> getOrderListResponse(@AuthenticationPrincipal UserDetails userDetails,
                                                                  @PathVariable("member-id") Long memberId) {
        Member member = extractMember(userDetails, memberId);
        return ResponseEntity.ok().body(orderService.getOrderListByMember(member));
    }

    private Member extractMember(UserDetails userDetails) {
        Long memberId = Long.valueOf(userDetails.getUsername());
        return memberService.findMemberBymemberId(memberId);
    }

    private Member extractMember(UserDetails userDetails, Long requestId) {
        Long memberId = Long.valueOf(userDetails.getUsername());
        if (!memberId.equals(requestId)) {
            throw new ForbiddenException(ErrorCode.MEMBER_MISMATCH_EXCEPTION);
        }
        return memberService.findMemberBymemberId(memberId);
    }
}