package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
//@Table(name="task")
@Builder
@EnableAutoConfiguration
public class taskDto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //@Column(name = "id", nullable = false)
    private Long id;

    //@Column(name="asset_id")
    private String asset_id;

    //@Column(name="company_name")
    private String company_name;

    //@Column(name="vehicle_no")
    private String vehicle_no;

    //@Column(name="driver_name")
    private String driver_name;

    //@Column(name="driver_psa_pass_no")
    private String driver_psa_pass_no;

    //@Column(name="description")
    private String description;

    //@Column(name="attachments")
    private String attachments;

    //@Column(name="validated")
    private Boolean validated;

    //@Column(name="time_validated")
    private String time_validated;

    //@Column(name="authorised")
    private Boolean authorised;

    //@Column(name="time_authorised")
    private String time_authorised;

    //@Column(name="verified")
    private Boolean verified;

    //@Column(name="time_verified")
    private String time_verified;

    @ManyToOne
    private requesterDto createdBy;

    @ManyToOne
    private requesterDto validatedBy;

    @ManyToOne
    private requesterDto authorisedBy;

    @ManyToOne
    private aetosDto verifiedBy;

}

