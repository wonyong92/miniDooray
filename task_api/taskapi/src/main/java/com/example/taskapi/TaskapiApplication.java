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

    @Bean
    public CommandLineRunner commandLineRunner() {
        return new CommandLineRunner() {
            @Autowired
            MemberRepository memberRepository;
            @Autowired
            ProjectRepository projectRepository;
            @Autowired
            MilestoneRepository milestoneRepository;

            @Override
            public void run(String... args) throws Exception {
                List<Member> memberList
                        = List.of(
                        new Member("nikki", "nikki@aaa.com", "12345", "T"),
                        new Member("jason", "jason@aaa.com", "12345", "T"),
                        new Member("jacob", "jacob@aaa.com", "12345", "T"),
                        new Member("nicole", "nicole@aaa.com", "12345", "T"),
                        new Member("tiffany", "tiffany@aaa.com", "12345", "T"),
                        new Member("heather ", "heather@aaa.com", "12345", "T"),
                        new Member("foo", "foo@aaa.com", "12345", "T")
                );
                memberRepository.saveAll(memberList);


                Member adminUser1 = memberRepository.findById("nikki").orElseThrow(() -> new IllegalStateException("Not found"));
                Member adminUser2 = memberRepository.findById("nicole").orElseThrow(() -> new IllegalStateException("Not found"));
                Member adminUser3 = memberRepository.findById("foo").orElseThrow(() -> new IllegalStateException("Not found"));


                List<Project> projectList
                        = List.of(
                        new Project(1, "3기 교육", Project.Status.ACTIVATE, adminUser1),
                        new Project(2, "3기 과제", Project.Status.ACTIVATE, adminUser2),
                        new Project(3, "2기 교육", Project.Status.DORMANCY, adminUser3)
                );

                projectRepository.saveAll(projectList);

                Project project = projectRepository.findById(1).orElseThrow(() -> new IllegalStateException("Not found"));

                List<Milestone> milestoneList
                        = List.of(
                        new Milestone(1, "servlet-jsp", LocalDate.of(2023, 4, 3), LocalDate.of(2023, 4, 9), project),
                        new Milestone(2, "spring-core", LocalDate.of(2023, 4, 10), LocalDate.of(2023, 4, 16), project)
                );
                milestoneRepository.saveAll(milestoneList);


            }
        };
    }



}
