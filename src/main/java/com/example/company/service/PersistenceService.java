package com.example.company.service;


import com.example.company.model.Village;

import java.io.IOException;

public interface PersistenceService {
    void saveGame (String fileName, Village village) throws IOException;

    Village resume(String fileName) throws IOException;

}
