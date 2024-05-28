package com.example.forutec_pt1.Usuario;

import com.example.forutec_pt1.Publicacion.Publicacion;
import com.example.forutec_pt1.Suscripcion.Suscripcion;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id", scope = Usuario.class)
@Entity
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id", scope = Usuario.class)
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Role Role;
    private String firstname;
    private String lastname;

    @Column(unique = true)
    private String email;
    @NonNull
    private String contrasena;
    /*
    @Column(name = "created_at", nullable = false)
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
*/
    @OneToMany(mappedBy = "usuario")
    private List<Publicacion> publicaciones;

    @OneToMany(mappedBy = "usuario")
    private List<Suscripcion> suscripciones;

    @Transient
    String role_prefix = "ROLE_";
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role_prefix + Role.name()));
    }

    @Override
    public String getPassword() {
        return this.contrasena;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }



}
