package fr.it_akademy.sneakers.service.mapper;

import org.junit.jupiter.api.BeforeEach;

class CommandeMapperTest {

    private CommandeMapper commandeMapper;

    @BeforeEach
    public void setUp() {
        commandeMapper = new CommandeMapperImpl();
    }
}
