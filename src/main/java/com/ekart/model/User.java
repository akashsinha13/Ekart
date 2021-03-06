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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @NotNull
    @Size(min = 1)
    private String name;

    @NotNull
    private String password;

    @NotNull
    @Column(unique = true)
    private String email;

    @ManyToMany(fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    private Set<Role> roles = new HashSet<>();

    private boolean active;

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

    @NotNull
    @Column(name = "primary_mobile")
    private Long primaryMobile;

    @Column(name = "secondary_mobile")
    private Long secondaryMobile;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    public User(@NotNull @Size(min = 1) String name, @NotNull String password, @NotNull String email, Set<Role> roles, boolean active, @NotNull Long primaryMobile) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.roles = roles;
        this.active = active;
        this.primaryMobile = primaryMobile;
    }
}
