package com.shoplite.order.gateway;

import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

@Component
public class InventoryGateway {

    private final RestClient inventoryRestClient;

    // RestClient est fourni par RestClientConfig (injecté par Spring)
    public InventoryGateway(RestClient inventoryRestClient) {
        this.inventoryRestClient = inventoryRestClient;
    }

    /**
     * Appelle inventory-api pour réserver du stock.
     * S'il n'y a pas de produit → 404 → InventoryProductNotFoundException
     * S'il n'y a pas assez de stock → 409 → InventoryInsufficientStockException
     * Sinon, succès (on ignore le corps de réponse).
     */
    public void reserveStock(long productId, int quantity) {
        try {
            inventoryRestClient.post()
                    .uri("/api/inventory/reservations")
                    .body(new ReserveRequest(productId, quantity))
                    .retrieve()
                    .toBodilessEntity(); // pas besoin du body, juste que ça passe
        } catch (RestClientResponseException ex) {
            HttpStatusCode status = ex.getStatusCode();
            int code = status.value();
            if (code == 404) {
                throw new InventoryProductNotFoundException("Product not found: " + productId);
            } else if (code == 409) {
                throw new InventoryInsufficientStockException("Insufficient stock for product: " + productId);
            } else {
                // Re-propage en runtime pour les autres cas (500, 400 inattendu, etc.)
                throw new RuntimeException("Inventory call failed with status " + code + ": " + ex.getResponseBodyAsString(), ex);
            }
        }
    }

    // Petit DTO interne uniquement pour l'appel HTTP
    static class ReserveRequest {
        private long productId;
        private int quantity;

        public ReserveRequest(long productId, int quantity) {
            this.productId = productId;
            this.quantity = quantity;
        }

        public long getProductId() { return productId; }
        public int getQuantity() { return quantity; }
    }
}
