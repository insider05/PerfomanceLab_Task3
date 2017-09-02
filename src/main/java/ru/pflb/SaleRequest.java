package ru.pflb;

public class SaleRequest {
    public String seller;
    public Float price;
    public Integer id;
    public static SaleRequest parseSaleRequest(String str) throws IllegalArgumentException{
        SaleRequest saleRequest;
        String[] props = str.split(",");
        if (props.length != 3) throw new IllegalArgumentException();
        saleRequest = new SaleRequest();
        saleRequest.seller = props[0];
        saleRequest.id = Integer.parseInt(props[1]);
        saleRequest.price = Float.parseFloat(props[2]);
        return saleRequest;
    }
}
