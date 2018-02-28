package com.example.amarlubovac.cryptocurrency;

/**
 * Created by amar.lubovac on 28.2.2018.
 */

public class Currency {
    int rank;
    String id;
    String price;
    String price_bitcoin;
    String name;
    String symbol;
    String percent_change_24h;
    String percent_change_1h;
    String percent_change_7d;
    String volume_24;
    String market_cap;
    String available_supply;
    String total_supply;

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getPrice() {
        return price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrice_bitcoin() {
        return price_bitcoin;
    }

    public void setPrice_bitcoin(String price_bitcoin) {
        this.price_bitcoin = price_bitcoin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getPercent_change_24h() {
        return percent_change_24h;
    }

    public void setPercent_change_24h(String percent_change_24h) {
        this.percent_change_24h = percent_change_24h;
    }

    public String getPercent_change_1h() {
        return percent_change_1h;
    }

    public void setPercent_change_1h(String percent_change_1h) {
        this.percent_change_1h = percent_change_1h;
    }

    public String getPercent_change_7d() {
        return percent_change_7d;
    }

    public void setPercent_change_7d(String percent_change_7d) {
        this.percent_change_7d = percent_change_7d;
    }

    public String getVolume_24() {
        return volume_24;
    }

    public void setVolume_24(String volume_24) {
        this.volume_24 = volume_24;
    }

    public String getMarket_cap() {
        return market_cap;
    }

    public void setMarket_cap(String markec_cap) {
        this.market_cap = markec_cap;
    }

    public String getAvailable_supply() {
        return available_supply;
    }

    public void setAvailable_supply(String available_supply) {
        this.available_supply = available_supply;
    }

    public String getTotal_supply() {
        return total_supply;
    }

    public void setTotal_supply(String total_supply) {
        this.total_supply = total_supply;
    }
}
