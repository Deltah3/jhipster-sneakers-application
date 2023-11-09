package fr.it_akademy.sneakers.service.mapper;

import fr.it_akademy.sneakers.domain.Details;
import fr.it_akademy.sneakers.domain.Sneakers;
import fr.it_akademy.sneakers.service.dto.DetailsDTO;
import fr.it_akademy.sneakers.service.dto.SneakersDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Sneakers} and its DTO {@link SneakersDTO}.
 */
@Mapper(componentModel = "spring")
public interface SneakersMapper extends EntityMapper<SneakersDTO, Sneakers> {
    @Mapping(target = "produits", source = "produits", qualifiedByName = "detailsId")
    SneakersDTO toDto(Sneakers s);

    @Named("detailsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DetailsDTO toDtoDetailsId(Details details);
}
