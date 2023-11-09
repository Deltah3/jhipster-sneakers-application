package fr.it_akademy.sneakers.service.impl;

import fr.it_akademy.sneakers.domain.Sneakers;
import fr.it_akademy.sneakers.repository.SneakersRepository;
import fr.it_akademy.sneakers.service.SneakersService;
import fr.it_akademy.sneakers.service.dto.SneakersDTO;
import fr.it_akademy.sneakers.service.mapper.SneakersMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link fr.it_akademy.sneakers.domain.Sneakers}.
 */
@Service
@Transactional
public class SneakersServiceImpl implements SneakersService {

    private final Logger log = LoggerFactory.getLogger(SneakersServiceImpl.class);

    private final SneakersRepository sneakersRepository;

    private final SneakersMapper sneakersMapper;

    public SneakersServiceImpl(SneakersRepository sneakersRepository, SneakersMapper sneakersMapper) {
        this.sneakersRepository = sneakersRepository;
        this.sneakersMapper = sneakersMapper;
    }

    @Override
    public SneakersDTO save(SneakersDTO sneakersDTO) {
        log.debug("Request to save Sneakers : {}", sneakersDTO);
        Sneakers sneakers = sneakersMapper.toEntity(sneakersDTO);
        sneakers = sneakersRepository.save(sneakers);
        return sneakersMapper.toDto(sneakers);
    }

    @Override
    public SneakersDTO update(SneakersDTO sneakersDTO) {
        log.debug("Request to update Sneakers : {}", sneakersDTO);
        Sneakers sneakers = sneakersMapper.toEntity(sneakersDTO);
        sneakers = sneakersRepository.save(sneakers);
        return sneakersMapper.toDto(sneakers);
    }

    @Override
    public Optional<SneakersDTO> partialUpdate(SneakersDTO sneakersDTO) {
        log.debug("Request to partially update Sneakers : {}", sneakersDTO);

        return sneakersRepository
            .findById(sneakersDTO.getId())
            .map(existingSneakers -> {
                sneakersMapper.partialUpdate(existingSneakers, sneakersDTO);

                return existingSneakers;
            })
            .map(sneakersRepository::save)
            .map(sneakersMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SneakersDTO> findAll() {
        log.debug("Request to get all Sneakers");
        return sneakersRepository.findAll().stream().map(sneakersMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SneakersDTO> findOne(Long id) {
        log.debug("Request to get Sneakers : {}", id);
        return sneakersRepository.findById(id).map(sneakersMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Sneakers : {}", id);
        sneakersRepository.deleteById(id);
    }
}
