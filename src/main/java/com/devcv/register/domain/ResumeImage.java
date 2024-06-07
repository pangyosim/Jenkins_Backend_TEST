package com.devcv.register.domain;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResumeImage {

    // S3URL
    private String resumeImgPath;
    // 썸네일 이미지 번호 설정
    private int ord;

    public void setOrd(int ord) {
        this.ord = ord;
    }

}