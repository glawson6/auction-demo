package com.ttis.auction.repository;

import com.ttis.auction.entity.Bidder;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by tap
 */
public interface BidderRepository extends CrudRepository<Bidder, String> {
}
