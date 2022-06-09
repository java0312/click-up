package uz.pdp.clickup.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.pdp.clickup.entity.enums.SystemRoleName;
import uz.pdp.clickup.entity.template.AbstractUUIDEntity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
public class User extends AbstractUUIDEntity implements UserDetails {
    public User(String fullName, String email, String password, SystemRoleName systemRoleName) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.systemRoleName = systemRoleName;
    }

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String color;

    private String initialLetter;

    @OneToOne(fetch = FetchType.LAZY)
    private Attachment avatar;

    private boolean enabled;

    private boolean accountNonExpired = true;

    private boolean accountNonLocked = true;

    private boolean credentialsNonExpired = true;

    @Enumerated(EnumType.STRING)
    private SystemRoleName systemRoleName;

    private String emailCode;


    private Timestamp lastActive;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(systemRoleName.name()));
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
