package com.modak.notification.entities;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class NotificationRateLimit {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "notification_type_id", referencedColumnName = "id")
    private NotificationType type;

    private Integer limit;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "period_time_id", referencedColumnName = "id")
    private PeriodTime periodTime;
}
