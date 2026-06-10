package rafaelandrade.ipurchases.orders.client.representation;

public record CustomerRepresentation(
        Long code,
        String name,
        String cpf,
        String address,
        String number,
        String district,
        String email,
        String telephone
) {
}
