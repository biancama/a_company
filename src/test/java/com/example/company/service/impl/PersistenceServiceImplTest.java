package com.example.company.service.impl;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.company.model.Entity;
import com.example.company.model.Village;
import com.example.company.model.Weapon;
import com.example.company.service.PersistenceService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;

/**
 * Tests for {@link PersistenceServiceImpl}.F
 */
@RunWith(MockitoJUnitRunner.class)
public class PersistenceServiceImplTest {

    @InjectMocks
    private PersistenceService persistenceService = new PersistenceServiceImpl();
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void whenSaveAndResumeAGameThenVillageIsRestored() throws IOException {
        Village village = new Village(5, 4);
        village.setCharacter(new Entity(0, 0, Weapon.CROSSBOW));
        village.setupEnemies(3);
        File fileCreated = tempFolder.newFile("test.txt");
        persistenceService.saveGame(fileCreated.getAbsolutePath(), village);

        Village resumedVillage = persistenceService.resume(fileCreated.getAbsolutePath());

        assertThat(resumedVillage).isEqualTo(village);

    }
}
