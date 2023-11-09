package fr.it_akademy.sneakers.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.it_akademy.sneakers.domain.Sneakers} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SneakersDTO implements Serializable {

    private Long id;

    private String nom;

    private String couleur;

    private Long stock;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SneakersDTO)) {
            return false;
        }

        SneakersDTO sneakersDTO = (SneakersDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, sneakersDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SneakersDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", couleur='" + getCouleur() + "'" +
            ", stock=" + getStock() +
            "}";
    }
}
