package com.example.taskapi;

import com.example.taskapi.entity.Member;
import com.example.taskapi.entity.Milestone;
import com.example.taskapi.entity.Project;
import com.example.taskapi.repository.MemberRepository;
import com.example.taskapi.repository.MilestoneRepository;
import com.example.taskapi.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@SpringBootApplication
public class TaskapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskapiApplication.class, args);
    }

}
