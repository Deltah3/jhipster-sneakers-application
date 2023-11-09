package fr.it_akademy.sneakers.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Details.
 */
@Entity
@Table(name = "details")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Details implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "reference")
    private String reference;

    @JsonIgnoreProperties(value = { "produits", "commandes" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "produits")
    private Sneakers sneakers;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Details id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return this.description;
    }

    public Details description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReference() {
        return this.reference;
    }

    public Details reference(String reference) {
        this.setReference(reference);
        return this;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Sneakers getSneakers() {
        return this.sneakers;
    }

    public void setSneakers(Sneakers sneakers) {
        if (this.sneakers != null) {
            this.sneakers.setProduits(null);
        }
        if (sneakers != null) {
            sneakers.setProduits(this);
        }
        this.sneakers = sneakers;
    }

    public Details sneakers(Sneakers sneakers) {
        this.setSneakers(sneakers);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Details)) {
            return false;
        }
        return getId() != null && getId().equals(((Details) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Details{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", reference='" + getReference() + "'" +
            "}";
    }
}
