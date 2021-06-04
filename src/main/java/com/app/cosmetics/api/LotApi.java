package com.app.cosmetics.api;

import com.app.cosmetics.application.LotService;
import com.app.cosmetics.application.data.LotData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "lots")
@RequiredArgsConstructor
public class LotApi {

    private final LotService lotService;

    @GetMapping(path = "/expiry")
    public ResponseEntity<List<LotData>> getExpiryLots() {
        return ResponseEntity.ok(lotService.getLotExpired());
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        lotService.delete(id);
        return ResponseEntity.ok().build();
    }
}
