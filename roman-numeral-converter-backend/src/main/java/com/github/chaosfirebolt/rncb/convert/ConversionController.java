package com.github.chaosfirebolt.rncb.convert;

import com.github.chaosfirebolt.converter.RomanInteger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConversionController {

  @GetMapping("/convert")
  public ResponseEntity<RomanInteger> convert(@RequestParam("val") String valueToParse) {
    return ResponseEntity.ok(RomanInteger.parse(valueToParse));
  }
}
