package hu.arnoldfarkas.pot.service;

import java.util.Map;

public interface MovingService {

    void init(Map<String, String> config);
    
    void start();

    void stop();
}
