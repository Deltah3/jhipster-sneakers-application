package fr.it_akademy.sneakers.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link fr.it_akademy.sneakers.domain.Commande} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CommandeDTO implements Serializable {

    private Long id;

    private Long quantite;

    private Instant dateCommande;

    private Instant dateLivraison;

    private Boolean status;

    private Set<SneakersDTO> sneakersses = new HashSet<>();

    private ClientDTO client;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuantite() {
        return quantite;
    }

    public void setQuantite(Long quantite) {
        this.quantite = quantite;
    }

    public Instant getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(Instant dateCommande) {
        this.dateCommande = dateCommande;
    }

    public Instant getDateLivraison() {
        return dateLivraison;
    }

    public void setDateLivraison(Instant dateLivraison) {
        this.dateLivraison = dateLivraison;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Set<SneakersDTO> getSneakersses() {
        return sneakersses;
    }

    public void setSneakersses(Set<SneakersDTO> sneakersses) {
        this.sneakersses = sneakersses;
    }

    public ClientDTO getClient() {
        return client;
    }

    public void setClient(ClientDTO client) {
        this.client = client;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CommandeDTO)) {
            return false;
        }

        CommandeDTO commandeDTO = (CommandeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, commandeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CommandeDTO{" +
            "id=" + getId() +
            ", quantite=" + getQuantite() +
            ", dateCommande='" + getDateCommande() + "'" +
            ", dateLivraison='" + getDateLivraison() + "'" +
            ", status='" + getStatus() + "'" +
            ", sneakersses=" + getSneakersses() +
            ", client=" + getClient() +
            "}";
    }
}
