package com.nhnacademy.minidooray.account.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.nhnacademy.minidooray.account.domain.AccountStatusEnum;
import com.nhnacademy.minidooray.account.domain.GatewayAuthEnum;
import com.nhnacademy.minidooray.account.domain.IsRegisteredEnum;
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
class AccountControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    MemberService memberService;

    @Test
    @Order(1)
    void testGetAccounts() throws Exception {
        given(memberService.getMembers())
            .willReturn(
                List.of(new Member("nhnacademy", "nhnacademy@gmail.com", "1234", "nhn",
                        AccountStatusEnum.REGISTERED, GatewayAuthEnum.ADMIN, IsRegisteredEnum.HAS_PERMISSION),
                    new Member("kusun1020", "kusun1020@gmail.com", "0000", "ngs",
                        AccountStatusEnum.DORMANT, GatewayAuthEnum.USER, IsRegisteredEnum.NO_PERMISSION))
            );

        mockMvc.perform(get("/api"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].id", equalTo("nhnacademy")));
    }

}
