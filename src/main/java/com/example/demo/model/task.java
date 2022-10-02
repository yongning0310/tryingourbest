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
public class task {
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
    private requester createdBy;

    @ManyToOne
    private requester validatedBy;

    @ManyToOne
    private requester authorisedBy;

    @ManyToOne
    private aetos verifiedBy;

    public taskDto toDto(){
        if(this.validatedBy == null) {
            return new taskDto(this.id,
                    this.asset_id,
                    this.company_name,
                    this.vehicle_no,
                    this.driver_name,
                    this.driver_psa_pass_no,
                    this.description,
                    this.attachments,
                    this.validated,
                    this.time_validated,
                    this.authorised,
                    this.time_authorised,
                    this.verified,
                    this.time_verified,
                    this.createdBy.toDto(),
                    null,
                    null,
                    null);
        }else if(this.authorisedBy == null){
            return new taskDto(this.id,
                    this.asset_id,
                    this.company_name,
                    this.vehicle_no,
                    this.driver_name,
                    this.driver_psa_pass_no,
                    this.description,
                    this.attachments,
                    this.validated,
                    this.time_validated,
                    this.authorised,
                    this.time_authorised,
                    this.verified,
                    this.time_verified,
                    this.createdBy.toDto(),
                    this.validatedBy.toDto(),
                    null,
                    null);
        }else if(this.verifiedBy == null){
            return new taskDto(this.id,
                    this.asset_id,
                    this.company_name,
                    this.vehicle_no,
                    this.driver_name,
                    this.driver_psa_pass_no,
                    this.description,
                    this.attachments,
                    this.validated,
                    this.time_validated,
                    this.authorised,
                    this.time_authorised,
                    this.verified,
                    this.time_verified,
                    this.createdBy.toDto(),
                    this.validatedBy.toDto(),
                    this.authorisedBy.toDto(),
                    null);
        }else{
            return new taskDto(this.id,
                    this.asset_id,
                    this.company_name,
                    this.vehicle_no,
                    this.driver_name,
                    this.driver_psa_pass_no,
                    this.description,
                    this.attachments,
                    this.validated,
                    this.time_validated,
                    this.authorised,
                    this.time_authorised,
                    this.verified,
                    this.time_verified,
                    this.createdBy.toDto(),
                    this.validatedBy.toDto(),
                    this.authorisedBy.toDto(),
                    this.verifiedBy.toDto());
        }
    }

}
