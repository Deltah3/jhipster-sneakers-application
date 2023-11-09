package fr.it_akademy.sneakers.service.impl;

import fr.it_akademy.sneakers.domain.Details;
import fr.it_akademy.sneakers.repository.DetailsRepository;
import fr.it_akademy.sneakers.service.DetailsService;
import fr.it_akademy.sneakers.service.dto.DetailsDTO;
import fr.it_akademy.sneakers.service.mapper.DetailsMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link fr.it_akademy.sneakers.domain.Details}.
 */
@Service
@Transactional
public class DetailsServiceImpl implements DetailsService {

    private final Logger log = LoggerFactory.getLogger(DetailsServiceImpl.class);

    private final DetailsRepository detailsRepository;

    private final DetailsMapper detailsMapper;

    public DetailsServiceImpl(DetailsRepository detailsRepository, DetailsMapper detailsMapper) {
        this.detailsRepository = detailsRepository;
        this.detailsMapper = detailsMapper;
    }

    @Override
    public DetailsDTO save(DetailsDTO detailsDTO) {
        log.debug("Request to save Details : {}", detailsDTO);
        Details details = detailsMapper.toEntity(detailsDTO);
        details = detailsRepository.save(details);
        return detailsMapper.toDto(details);
    }

    @Override
    public DetailsDTO update(DetailsDTO detailsDTO) {
        log.debug("Request to update Details : {}", detailsDTO);
        Details details = detailsMapper.toEntity(detailsDTO);
        details = detailsRepository.save(details);
        return detailsMapper.toDto(details);
    }

    @Override
    public Optional<DetailsDTO> partialUpdate(DetailsDTO detailsDTO) {
        log.debug("Request to partially update Details : {}", detailsDTO);

        return detailsRepository
            .findById(detailsDTO.getId())
            .map(existingDetails -> {
                detailsMapper.partialUpdate(existingDetails, detailsDTO);

                return existingDetails;
            })
            .map(detailsRepository::save)
            .map(detailsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DetailsDTO> findAll() {
        log.debug("Request to get all Details");
        return detailsRepository.findAll().stream().map(detailsMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the details where Sneakers is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<DetailsDTO> findAllWhereSneakersIsNull() {
        log.debug("Request to get all details where Sneakers is null");
        return StreamSupport
            .stream(detailsRepository.findAll().spliterator(), false)
            .filter(details -> details.getSneakers() == null)
            .map(detailsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DetailsDTO> findOne(Long id) {
        log.debug("Request to get Details : {}", id);
        return detailsRepository.findById(id).map(detailsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Details : {}", id);
        detailsRepository.deleteById(id);
    }
}
