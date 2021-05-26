package com.eugene.demo.model;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApplicationResponse {
  List<String> matchLocations;
}
