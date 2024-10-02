package model;

import lombok.*;

import java.time.LocalDate;

@ToString
@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    private String itemCode;
    private String description;
    private String packSize;
    private Double unitPrice;
    private Integer qtyOnHand;


    public Item(String itemCode, String description, String packSize, double unitPrice, int qtyOnHand, Object o) {
    }
}