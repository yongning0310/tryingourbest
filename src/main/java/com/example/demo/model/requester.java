package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Builder
@EnableAutoConfiguration
public class requester {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;

    private String password;

    private Boolean designated_officer;

    @JsonIgnore
    @OneToMany(mappedBy = "id")
    private List<task> tasks;

    public requesterDto toDto(){
        return new requesterDto(this.id,this.username,this.designated_officer);
    }


}
