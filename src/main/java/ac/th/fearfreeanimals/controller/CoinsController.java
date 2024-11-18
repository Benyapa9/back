package ac.th.fearfreeanimals.controller;

import ac.th.fearfreeanimals.entity.Coins;
import ac.th.fearfreeanimals.service.CoinsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/coins")
public class CoinsController {

    private final CoinsService coinsService;

    @Autowired
    public CoinsController(CoinsService coinsService) {
        this.coinsService = coinsService;
    }

    // Get all coins
    @GetMapping
    public ResponseEntity<List<Coins>> getAllCoins() {
        try {
            List<Coins> coins = coinsService.getAllCoins();
            return ResponseEntity.ok(coins);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Create new coins entry
    @PostMapping
    public ResponseEntity<Coins> createCoins(@RequestBody Coins coins) {
        try {
            Coins createdCoins = coinsService.createCoins(coins);
            return ResponseEntity.ok(createdCoins);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Get coins by ID
    @GetMapping("/{id}")
    public ResponseEntity<Coins> getCoinsById(@PathVariable Long id) {
        try {
            Coins coins = coinsService.getCoinsById(id);
            return ResponseEntity.ok(coins);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Update coins by ID
    @PutMapping("/{id}")
    public ResponseEntity<Coins> updateCoins(@PathVariable Long id, @RequestBody Coins coinsDetails) {
        try {
            Coins updatedCoins = coinsService.updateCoins(id, coinsDetails);
            return ResponseEntity.ok(updatedCoins);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Delete coins by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCoins(@PathVariable Long id) {
        try {
            coinsService.deleteCoins(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
