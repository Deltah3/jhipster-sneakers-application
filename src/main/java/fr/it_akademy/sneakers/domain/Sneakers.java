package fr.it_akademy.sneakers.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Sneakers.
 */
@Entity
@Table(name = "sneakers")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Sneakers implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "stock")
    private Long stock;

    @Column(name = "nom")
    private String nom;

    @Column(name = "taille")
    private Long taille;

    @Column(name = "couleur")
    private String couleur;

    @Column(name = "prix")
    private Float prix;

    @JsonIgnoreProperties(value = { "sneakers" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Details produits;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "sneakersses")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "sneakersses", "client" }, allowSetters = true)
    private Set<Commande> commandes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Sneakers id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStock() {
        return this.stock;
    }

    public Sneakers stock(Long stock) {
        this.setStock(stock);
        return this;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public String getNom() {
        return this.nom;
    }

    public Sneakers nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Long getTaille() {
        return this.taille;
    }

    public Sneakers taille(Long taille) {
        this.setTaille(taille);
        return this;
    }

    public void setTaille(Long taille) {
        this.taille = taille;
    }

    public String getCouleur() {
        return this.couleur;
    }

    public Sneakers couleur(String couleur) {
        this.setCouleur(couleur);
        return this;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public Float getPrix() {
        return this.prix;
    }

    public Sneakers prix(Float prix) {
        this.setPrix(prix);
        return this;
    }

    public void setPrix(Float prix) {
        this.prix = prix;
    }

    public Details getProduits() {
        return this.produits;
    }

    public void setProduits(Details details) {
        this.produits = details;
    }

    public Sneakers produits(Details details) {
        this.setProduits(details);
        return this;
    }

    public Set<Commande> getCommandes() {
        return this.commandes;
    }

    public void setCommandes(Set<Commande> commandes) {
        if (this.commandes != null) {
            this.commandes.forEach(i -> i.removeSneakerss(this));
        }
        if (commandes != null) {
            commandes.forEach(i -> i.addSneakerss(this));
        }
        this.commandes = commandes;
    }

    public Sneakers commandes(Set<Commande> commandes) {
        this.setCommandes(commandes);
        return this;
    }

    public Sneakers addCommande(Commande commande) {
        this.commandes.add(commande);
        commande.getSneakersses().add(this);
        return this;
    }

    public Sneakers removeCommande(Commande commande) {
        this.commandes.remove(commande);
        commande.getSneakersses().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Sneakers)) {
            return false;
        }
        return getId() != null && getId().equals(((Sneakers) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Sneakers{" +
            "id=" + getId() +
            ", stock=" + getStock() +
            ", nom='" + getNom() + "'" +
            ", taille=" + getTaille() +
            ", couleur='" + getCouleur() + "'" +
            ", prix=" + getPrix() +
            "}";
    }
}
