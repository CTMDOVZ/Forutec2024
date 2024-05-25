package com.example.forutec_pt1.Usuario;

import com.example.forutec_pt1.Publicacion.Publicacion;
import com.example.forutec_pt1.Suscripcion.Suscripcion;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellido;

    @Column(unique = true)
    private String correoInstitucional;

    private String contrasena;

    @OneToMany(mappedBy = "usuario")
    @JsonManagedReference
    private List<Publicacion> publicaciones;

    @OneToMany(mappedBy = "usuario")
    private List<Suscripcion> suscripciones;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreoInstitucional() {
        return correoInstitucional;
    }

    public void setCorreoInstitucional(String correoInstitucional) {
        this.correoInstitucional = correoInstitucional;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public List<Publicacion> getPublicaciones() {return publicaciones; }

    public void setPublicaciones(List<Publicacion> publicaciones) {this.publicaciones = publicaciones; }

    public List<Suscripcion> getSuscripciones() {return suscripciones; }

    public void setSuscripciones(List<Suscripcion> suscripciones) {this.suscripciones = suscripciones; }
}
