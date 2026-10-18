package com.shop.web;

import com.shop.catalog.CatalogStats;

public record CatalogStatsResponse(int productCount, long totalPriceInCents) {
    public static CatalogStatsResponse from(CatalogStats stats) {
        return new CatalogStatsResponse(stats.productCount(), stats.totalPriceInCents());
    }
}
