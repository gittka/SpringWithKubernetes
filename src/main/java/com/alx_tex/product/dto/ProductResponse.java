package com.alx_tex.product.dto;

import java.math.BigDecimal;

public record ProductResponse(String id,
                              String name,
                              String description,
                              BigDecimal price) {
}
