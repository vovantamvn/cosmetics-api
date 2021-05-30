//package com.app.cosmetics.api;
//
//import com.app.cosmetics.application.data.StockResponse;
//import com.app.cosmetics.application.StockService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//import java.util.List;
//
//@RestController
//@RequestMapping(value = "stocks")
//@RequiredArgsConstructor
//public class StockController {
//
//    private final StockService stockService;
//
//    @PostMapping
//    public ResponseEntity<StockResponse> create(@Valid @RequestBody StockRequest request) {
//        return ResponseEntity
//                .status(HttpStatus.CREATED)
//                .body(stockService.create(request));
//    }
//
//    @GetMapping
//    public ResponseEntity<List<StockResponse>> findAll() {
//        return ResponseEntity.ok(stockService.findAll());
//    }
//
//    @GetMapping(value = "/{id}")
//    public ResponseEntity<StockResponse> findById(@PathVariable long id) {
//        return ResponseEntity.ok(stockService.findById(id));
//    }
//}
