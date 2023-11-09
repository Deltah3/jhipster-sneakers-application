package fr.it_akademy.sneakers.service.mapper;

import fr.it_akademy.sneakers.domain.Details;
import fr.it_akademy.sneakers.service.dto.DetailsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Details} and its DTO {@link DetailsDTO}.
 */
@Mapper(componentModel = "spring")
public interface DetailsMapper extends EntityMapper<DetailsDTO, Details> {}
