package com.facturacion.facturacion.request;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class InvoiceRequest {

    //private Long invoice_id;
    private Long clientId;
    private List<InvoiceDetail> detailsList;
    public Long getClientId() {
        return clientId;
    }
    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
    public List<InvoiceDetail> getDetailsList() {
        return detailsList;
    }
    public void setDetailsList(List<InvoiceDetail> detailsList) {
        this.detailsList = detailsList;
    }

    public static class InvoiceDetail {
        private Long amounts;
        private Long productId;
        private Long price;

        public Long getAmounts() {
            return amounts;
        }
        public void setAmounts(Long amounts) {
            this.amounts = amounts;
        }
        public Long getProductId() {
            return productId;
        }
        public void setProductId(Long productId) {
            this.productId = productId;
        }
        public Long getPrice() {
            return price;
        }
        public void setPrice(Long price) {
            this.price = price;
        }
    }

    public static class InvoiceProduct {

        private Long id;
        private String description;
        private String code;
        private Long stock;
        private Double price;

        public InvoiceProduct(){}

        public InvoiceProduct(String description, String code, Long stock, Double price) {
            this.description = description;
            this.code = code;
            this.stock = stock;
            this.price = price;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public Long getStock() {
            return stock;
        }

        public void setStock(Long stock) {
            this.stock = stock;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }
    }
}