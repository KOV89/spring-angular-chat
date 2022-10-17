package ru.klimov.backend.model.entity;

import lombok.Data;
import ru.klimov.backend.model.entity.marker.IEntity;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
@Data
public class Message implements IEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "text")
    private String text;

    @Column(name = "time", updatable = false)
    private Instant time;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", updatable = false)
    private User user;
}