package com.cdacQuerybridge.admin_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cdacQuerybridge.admin_service.dto.AnswerDto;
import com.cdacQuerybridge.admin_service.dto.QueryDto;
import com.cdacQuerybridge.admin_service.dto.SupportDto;
import com.cdacQuerybridge.admin_service.dto.UserDto;
import com.cdacQuerybridge.admin_service.feign.AnswerClient;
import com.cdacQuerybridge.admin_service.feign.AuthClient;
import com.cdacQuerybridge.admin_service.feign.QueryClient;
import com.cdacQuerybridge.admin_service.feign.SupportClient;

@Service
public class AdminService {

    private final AuthClient auth;
    private final QueryClient query;
    private final AnswerClient answer;
    private final SupportClient support;

    public AdminService(AuthClient auth, QueryClient query,
                        AnswerClient answer, SupportClient support) {
        this.auth = auth;
        this.query = query;
        this.answer = answer;
        this.support = support;
    }

    public List<UserDto> users() {
        return auth.getAllUsers();
    }

    public void deleteUser(String email) {
        auth.deleteUser(email);
    }

    public List<QueryDto> queries() {
        return query.getAllQueries();
    }

    public void deleteQuery(Long id) {
        query.deleteQuery(id);
    }

    public List<AnswerDto> answers(Long qid) {
        return answer.getAnswers(qid);
    }

    public void deleteAnswer(Long aid) {
        answer.deleteAnswer(aid);
    }

    public List<SupportDto> tickets() {
        return support.getAllTickets();
    }

    public void resolve(Long id) {
        support.markResolved(id);
    }
}
