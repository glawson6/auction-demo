/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttis.auction.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author tap
 */
@Entity
@Table(name = "bids")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Bids.findAll", query = "SELECT b FROM Bids b"),
    @NamedQuery(name = "Bids.findById", query = "SELECT b FROM Bids b WHERE b.id = :id"),
    @NamedQuery(name = "Bids.findByBidDate", query = "SELECT b FROM Bids b WHERE b.bidDate = :bidDate"),
    @NamedQuery(name = "Bids.findByBidAmount", query = "SELECT b FROM Bids b WHERE b.bidAmount = :bidAmount")})
public class Bids implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private String id;
    @Basic(optional = false)
    @Column(name = "BID_DATE")
    private long bidDate;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "BID_AMOUNT")
    private BigDecimal bidAmount;
    @JoinColumn(name = "AUCTION_ITEM_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private AuctionItem auctionItemId;
    @JoinColumn(name = "BIDDER_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Bidder bidderId;

    public Bids() {
    }

    public Bids(String id) {
        this.id = id;
    }

    public Bids(String id, long bidDate) {
        this.id = id;
        this.bidDate = bidDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getBidDate() {
        return bidDate;
    }

    public void setBidDate(long bidDate) {
        this.bidDate = bidDate;
    }

    public BigDecimal getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(BigDecimal bidAmount) {
        this.bidAmount = bidAmount;
    }

    public AuctionItem getAuctionItemId() {
        return auctionItemId;
    }

    public void setAuctionItemId(AuctionItem auctionItemId) {
        this.auctionItemId = auctionItemId;
    }

    public Bidder getBidderId() {
        return bidderId;
    }

    public void setBidderId(Bidder bidderId) {
        this.bidderId = bidderId;
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
        if (!(object instanceof Bids)) {
            return false;
        }
        Bids other = (Bids) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ttis.auction.entity.Bids[ id=" + id + " ]";
    }
    
}
