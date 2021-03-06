package com.example.trello.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

// id bigint generated by default as identity primary key,
//        title varchar(255),
//        archived bool,
//        description text,
//        deadline timestamp,
//        created timestamp,
//        list_id bigint not null,
//        foreign key (list_id) references lists(id)
@Entity
@Table(name = "cards")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private boolean archived;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date deadline;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    @CreationTimestamp
    private Date created;

    @JsonIgnore
    @ManyToOne
    private ListEntity list;
}
