package com.example.newhealthcare.model.network.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//응답은 암호호화 한 코드
public class DoctorApiResponse {
    private String doctorId;

    //    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
//            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    private String password;

    @Email(message = "이메일 형식에 맞지 않습니다.")
    private String email;

    private String name;

    private String phone;

    private String major;

    private String code;

}
