package com.ttis.auction.repository;

import com.ttis.auction.entity.Author;
import com.ttis.auction.entity.Contact;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * Created by tap on 6/6/16.
 */
@PreAuthorize("isAuthenticated()")
public interface ContactRepository extends CrudRepository<Contact, String> {
}
