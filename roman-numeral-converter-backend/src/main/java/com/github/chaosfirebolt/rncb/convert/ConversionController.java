package com.github.chaosfirebolt.rncb.convert;

import com.github.chaosfirebolt.converter.RomanInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConversionController {

  private final ConversionService conversionService;

  @Autowired
  public ConversionController(ConversionService conversionService) {
    this.conversionService = conversionService;
  }

  @GetMapping("/convert")
  public ResponseEntity<RomanInteger> convert(@RequestParam("val") ConversionValue valueToParse) {
    return ResponseEntity.ok(conversionService.convert(valueToParse));
  }
}
