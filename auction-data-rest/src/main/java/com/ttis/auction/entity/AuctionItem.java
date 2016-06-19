/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttis.auction.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author tap
 */
@Entity
//@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "auction_item")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AuctionItem.findAll", query = "SELECT a FROM AuctionItem a"),
    @NamedQuery(name = "AuctionItem.findById", query = "SELECT a FROM AuctionItem a WHERE a.id = :id"),
    @NamedQuery(name = "AuctionItem.findByAuthorId", query = "SELECT a FROM AuctionItem a WHERE a.authorId = :authorId"),
    @NamedQuery(name = "AuctionItem.findByName", query = "SELECT a FROM AuctionItem a WHERE a.name = :name"),
    @NamedQuery(name = "AuctionItem.findByDescription", query = "SELECT a FROM AuctionItem a WHERE a.description = :description"),
    @NamedQuery(name = "AuctionItem.findByCreatedBy", query = "SELECT a FROM AuctionItem a WHERE a.createdBy = :createdBy"),
    @NamedQuery(name = "AuctionItem.findByCreatedOn", query = "SELECT a FROM AuctionItem a WHERE a.createdOn = :createdOn"),
    @NamedQuery(name = "AuctionItem.findBySoldTo", query = "SELECT a FROM AuctionItem a WHERE a.soldTo = :soldTo"),
    @NamedQuery(name = "AuctionItem.findBySoldOn", query = "SELECT a FROM AuctionItem a WHERE a.soldOn = :soldOn"),
    @NamedQuery(name = "AuctionItem.findByBidClosure", query = "SELECT a FROM AuctionItem a WHERE a.bidClosure = :bidClosure")})
public class AuctionItem implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private String id;
    @Basic(optional = false)
    @Column(name = "AUTHOR_ID")
    private String authorId;
    @Basic(optional = false)
    @Column(name = "NAME")
    private String name;
    @Column(name = "DESCRIPTION")
    private String description;
    @Basic(optional = false)
    @Column(name = "CREATED_BY")
    private String createdBy;
    @Basic(optional = false)
    @Column(name = "CREATED_ON")
    private long createdOn;
    @Column(name = "SOLD_TO")
    private String soldTo;
    @Column(name = "SOLD_ON")
    private BigInteger soldOn;
    @Column(name = "BID_CLOSURE")
    private BigInteger bidClosure;
    @JoinColumn(name = "ITEM_CATEGORY_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private AuctionItemCategory itemCategoryId;
    @JoinColumn(name = "ITEM_STATUS_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private AuctionItemStatus itemStatusId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "auctionItemId")
    private Collection<Bids> bidsCollection;

    public AuctionItem() {
    }

    public AuctionItem(String id) {
        this.id = id;
    }

    public AuctionItem(String id, String authorId, String name, String createdBy, long createdOn) {
        this.id = id;
        this.authorId = authorId;
        this.name = name;
        this.createdBy = createdBy;
        this.createdOn = createdOn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public long getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(long createdOn) {
        this.createdOn = createdOn;
    }

    public String getSoldTo() {
        return soldTo;
    }

    public void setSoldTo(String soldTo) {
        this.soldTo = soldTo;
    }

    public BigInteger getSoldOn() {
        return soldOn;
    }

    public void setSoldOn(BigInteger soldOn) {
        this.soldOn = soldOn;
    }

    public BigInteger getBidClosure() {
        return bidClosure;
    }

    public void setBidClosure(BigInteger bidClosure) {
        this.bidClosure = bidClosure;
    }

    public AuctionItemCategory getItemCategoryId() {
        return itemCategoryId;
    }

    public void setItemCategoryId(AuctionItemCategory itemCategoryId) {
        this.itemCategoryId = itemCategoryId;
    }

    public AuctionItemStatus getItemStatusId() {
        return itemStatusId;
    }

    public void setItemStatusId(AuctionItemStatus itemStatusId) {
        this.itemStatusId = itemStatusId;
    }

    @XmlTransient
    public Collection<Bids> getBidsCollection() {
        return bidsCollection;
    }

    public void setBidsCollection(Collection<Bids> bidsCollection) {
        this.bidsCollection = bidsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AuctionItem)) {
            return false;
        }
        AuctionItem other = (AuctionItem) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ttis.auction.entity.AuctionItem[ id=" + id + " ]";
    }

}
