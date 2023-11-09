package fr.it_akademy.sneakers.service.mapper;

import fr.it_akademy.sneakers.domain.Client;
import fr.it_akademy.sneakers.service.dto.ClientDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Client} and its DTO {@link ClientDTO}.
 */
@Mapper(componentModel = "spring")
public interface ClientMapper extends EntityMapper<ClientDTO, Client> {}
