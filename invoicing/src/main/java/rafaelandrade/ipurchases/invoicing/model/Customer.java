package rafaelandrade.ipurchases.invoicing.model;

public record Customer(
        String name,
        String cpf,
        String address,
        String number,
        String district,
        String email,
        String telephone
) {

}
