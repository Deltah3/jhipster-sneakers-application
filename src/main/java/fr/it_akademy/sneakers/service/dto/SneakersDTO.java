package fr.it_akademy.sneakers.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.it_akademy.sneakers.domain.Sneakers} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SneakersDTO implements Serializable {

    private Long id;

    private Long stock;

    private String nom;

    private Long taille;

    private String couleur;

    private Float prix;

    private DetailsDTO produits;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Long getTaille() {
        return taille;
    }

    public void setTaille(Long taille) {
        this.taille = taille;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public Float getPrix() {
        return prix;
    }

    public void setPrix(Float prix) {
        this.prix = prix;
    }

    public DetailsDTO getProduits() {
        return produits;
    }

    public void setProduits(DetailsDTO produits) {
        this.produits = produits;
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
            ", stock=" + getStock() +
            ", nom='" + getNom() + "'" +
            ", taille=" + getTaille() +
            ", couleur='" + getCouleur() + "'" +
            ", prix=" + getPrix() +
            ", produits=" + getProduits() +
            "}";
    }
}
