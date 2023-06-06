package com.nhnacademy.minidooray.account.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.nhnacademy.minidooray.account.domain.AccountStatus;
import com.nhnacademy.minidooray.account.domain.SystemAuth;
import com.nhnacademy.minidooray.account.domain.Member;
import com.nhnacademy.minidooray.account.service.MemberService;
import java.util.List;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AccountControllerDBTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    MemberService memberService;

//    @Tests
//    @Order(1)
//    void testGetAccounts() throws Exception {
//        given(memberService.getMember())
//            .willReturn(
//                List.of(new Member("nhnacademy", "nhnacademy@gmail.com", "1234", "nhn",
//                        AccountStatus.REGISTERED, SystemAuth.ADMIN),
//                    new Member("kusun1020", "kusun1020@gmail.com", "0000", "ngs",
//                        AccountStatus.DORMANT, SystemAuth.USER)
//            );
//
//        mockMvc.perform(get("/api"))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//            .andExpect(jsonPath("$[0].id", equalTo("nhnacademy")));
//    }

}
