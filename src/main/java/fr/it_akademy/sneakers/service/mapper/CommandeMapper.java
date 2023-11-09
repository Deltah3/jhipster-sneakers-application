package fr.it_akademy.sneakers.service.mapper;

import fr.it_akademy.sneakers.domain.Client;
import fr.it_akademy.sneakers.domain.Commande;
import fr.it_akademy.sneakers.domain.Sneakers;
import fr.it_akademy.sneakers.service.dto.ClientDTO;
import fr.it_akademy.sneakers.service.dto.CommandeDTO;
import fr.it_akademy.sneakers.service.dto.SneakersDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Commande} and its DTO {@link CommandeDTO}.
 */
@Mapper(componentModel = "spring")
public interface CommandeMapper extends EntityMapper<CommandeDTO, Commande> {
    @Mapping(target = "sneakersses", source = "sneakersses", qualifiedByName = "sneakersIdSet")
    @Mapping(target = "client", source = "client", qualifiedByName = "clientId")
    CommandeDTO toDto(Commande s);

    @Mapping(target = "removeSneakerss", ignore = true)
    Commande toEntity(CommandeDTO commandeDTO);

    @Named("sneakersId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SneakersDTO toDtoSneakersId(Sneakers sneakers);

    @Named("sneakersIdSet")
    default Set<SneakersDTO> toDtoSneakersIdSet(Set<Sneakers> sneakers) {
        return sneakers.stream().map(this::toDtoSneakersId).collect(Collectors.toSet());
    }

    @Named("clientId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ClientDTO toDtoClientId(Client client);
}
