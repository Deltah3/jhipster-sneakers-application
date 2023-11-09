package fr.it_akademy.sneakers.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.it_akademy.sneakers.domain.Details} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DetailsDTO implements Serializable {

    private Long id;

    private String description;

    private String reference;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DetailsDTO)) {
            return false;
        }

        DetailsDTO detailsDTO = (DetailsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, detailsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DetailsDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", reference='" + getReference() + "'" +
            "}";
    }
}
