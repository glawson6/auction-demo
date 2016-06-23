package com.ttis.auction.repository;

import com.ttis.auction.entity.AuctionItem;
import com.ttis.auction.entity.AuctionItemCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * Created by tap on 6/6/16.
 */
@PreAuthorize("isAuthenticated()")
public interface AuctionItemCategoryRepository extends CrudRepository<AuctionItemCategory, String> {
}
