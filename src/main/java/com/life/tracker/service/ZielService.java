package com.life.tracker.service;

import com.life.tracker.mapper.ZielMapper;
import com.life.tracker.model.UserEntity;
import com.life.tracker.model.ZielEntity;
import com.life.tracker.repo.UserRepository;
import com.life.tracker.repo.ZielRepository;
import org.openapitools.model.Ziel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class ZielService {

    private final ZielRepository zielRepository;
    private final UserRepository userRepository;
    private final ZielMapper zielMapper;

    @Autowired
    public ZielService(ZielRepository zielRepository, UserRepository userRepository, ZielMapper zielMapper) {
        this.zielRepository = zielRepository;
        this.userRepository = userRepository;
        this.zielMapper = zielMapper;
    }

    public ResponseEntity<Ziel> saveGoal(Ziel ziel) {
        List<ZielEntity> ziele = zielRepository.findByEmail(ziel.getEmail());
        boolean conflict = ziele.stream().anyMatch(zielEntity -> ziel.getTitle().equals(zielEntity.getTitel()));
        Optional<UserEntity> user = userRepository.findByEmail(ziel.getEmail());

        if (user.isEmpty()) {
            return ResponseEntity.badRequest().build(); // oder throw new RuntimeException
        }
        if(conflict){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }{
            // 2. ZielEntity erstellen
            ZielEntity zielEntity = new ZielEntity();
            zielEntity.setTitel(ziel.getTitle());
            zielEntity.setBeschreibung(ziel.getDescription());
            zielEntity.setEmail(ziel.getEmail());

            // 3. Beziehung setzen (WICHTIG!)
            zielEntity.setUserEntity(user.get());

            // 4. Ziel speichern
            zielRepository.save(zielEntity);

            // 5. Ziel in User-Liste eintragen (optional, falls bidirektional)
            user.get().getZielEntityList().add(zielEntity);

            // 6. User speichern (optional, nur wenn bidirektional)
            userRepository.save(user.get());
        }



        return ResponseEntity.ok(ziel);
    }

    public ResponseEntity<List<Ziel>> getGoal(String email) {
        List<ZielEntity> zielEntityList = zielRepository.findByEmail(email);
        List<Ziel> zielList = new ArrayList<>();
        for (ZielEntity zielEntity : zielEntityList) {
            Ziel ziel = zielMapper.toModel(zielEntity);
            zielList.add(ziel);
        }

        return ResponseEntity.ok(zielList);
    }
}

