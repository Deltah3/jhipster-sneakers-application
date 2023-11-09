package fr.it_akademy.sneakers.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Commande.
 */
@Entity
@Table(name = "commande")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Commande implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "quantite")
    private Long quantite;

    @Column(name = "date_commande")
    private Instant dateCommande;

    @Column(name = "date_livraison")
    private Instant dateLivraison;

    @Column(name = "status")
    private Boolean status;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_commande__sneakerss",
        joinColumns = @JoinColumn(name = "commande_id"),
        inverseJoinColumns = @JoinColumn(name = "sneakerss_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "commandes" }, allowSetters = true)
    private Set<Sneakers> sneakersses = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "commandes" }, allowSetters = true)
    private Client client;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Commande id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuantite() {
        return this.quantite;
    }

    public Commande quantite(Long quantite) {
        this.setQuantite(quantite);
        return this;
    }

    public void setQuantite(Long quantite) {
        this.quantite = quantite;
    }

    public Instant getDateCommande() {
        return this.dateCommande;
    }

    public Commande dateCommande(Instant dateCommande) {
        this.setDateCommande(dateCommande);
        return this;
    }

    public void setDateCommande(Instant dateCommande) {
        this.dateCommande = dateCommande;
    }

    public Instant getDateLivraison() {
        return this.dateLivraison;
    }

    public Commande dateLivraison(Instant dateLivraison) {
        this.setDateLivraison(dateLivraison);
        return this;
    }

    public void setDateLivraison(Instant dateLivraison) {
        this.dateLivraison = dateLivraison;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public Commande status(Boolean status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Set<Sneakers> getSneakersses() {
        return this.sneakersses;
    }

    public void setSneakersses(Set<Sneakers> sneakers) {
        this.sneakersses = sneakers;
    }

    public Commande sneakersses(Set<Sneakers> sneakers) {
        this.setSneakersses(sneakers);
        return this;
    }

    public Commande addSneakerss(Sneakers sneakers) {
        this.sneakersses.add(sneakers);
        return this;
    }

    public Commande removeSneakerss(Sneakers sneakers) {
        this.sneakersses.remove(sneakers);
        return this;
    }

    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Commande client(Client client) {
        this.setClient(client);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Commande)) {
            return false;
        }
        return getId() != null && getId().equals(((Commande) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Commande{" +
            "id=" + getId() +
            ", quantite=" + getQuantite() +
            ", dateCommande='" + getDateCommande() + "'" +
            ", dateLivraison='" + getDateLivraison() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
