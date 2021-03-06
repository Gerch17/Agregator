package ru.gerch.agregator.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Type;
import ru.gerch.agregator.enums.EnumStatus;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "request")
@Getter
@Setter
@NoArgsConstructor
public class Request {
    @Id
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "img")
    private String img;

    @Column(name = "price")
    private int price;

    @Column(name = "status", columnDefinition = "REVIEW")
    @Enumerated(EnumType.STRING)
    private EnumStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;



    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        Request request = (Request) o;
        return getId() != null && Objects.equals(getId(), request.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
