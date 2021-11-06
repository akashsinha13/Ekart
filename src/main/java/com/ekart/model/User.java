package com.ekart.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
@TypeDefs({
        @TypeDef(
                name = "json",
                typeClass = JsonType.class
        )
})
public class User {
    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private byte[] password;

    private String email;

    private UserRole role;

    @Column(name = "is_active")
    private boolean isActive;

    @Type(type = "json")
    @Column(columnDefinition = "jsonb")
    private List<Address> addresses;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_date")
    private LocalDateTime createdDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Column(name = "primary_mobile")
    private Long primaryMobile;

    @Column(name = "secondary_mobile")
    private Long secondaryMobile;

    private Long telephone;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id")
    private Cart cart;
}
