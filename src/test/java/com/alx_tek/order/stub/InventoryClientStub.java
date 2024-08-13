package com.alx_tek.order.stub;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class InventoryClientStub {
    public static void checkStock(String skuCode, Integer quantity) {
        stubFor(get(urlEqualTo("/api/inventory?skuCode=" + skuCode + "&quantity=" + quantity))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(200)
                        .withBody("true")));
    }
}