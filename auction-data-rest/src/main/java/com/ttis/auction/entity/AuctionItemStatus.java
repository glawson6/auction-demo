/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttis.auction.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author tap
 */
@Entity
@Table(name = "auction_item_status")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AuctionItemStatus.findAll", query = "SELECT a FROM AuctionItemStatus a"),
    @NamedQuery(name = "AuctionItemStatus.findById", query = "SELECT a FROM AuctionItemStatus a WHERE a.id = :id"),
    @NamedQuery(name = "AuctionItemStatus.findByName", query = "SELECT a FROM AuctionItemStatus a WHERE a.name = :name"),
    @NamedQuery(name = "AuctionItemStatus.findByDescription", query = "SELECT a FROM AuctionItemStatus a WHERE a.description = :description")})
public class AuctionItemStatus implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private String id;
    @Basic(optional = false)
    @Column(name = "NAME")
    private String name;
    @Column(name = "DESCRIPTION")
    private String description;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "itemStatusId")
    private Collection<AuctionItem> auctionItemCollection;

    public AuctionItemStatus() {
    }

    public AuctionItemStatus(String id) {
        this.id = id;
    }

    public AuctionItemStatus(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    @XmlTransient
    public Collection<AuctionItem> getAuctionItemCollection() {
        return auctionItemCollection;
    }

    public void setAuctionItemCollection(Collection<AuctionItem> auctionItemCollection) {
        this.auctionItemCollection = auctionItemCollection;
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
        if (!(object instanceof AuctionItemStatus)) {
            return false;
        }
        AuctionItemStatus other = (AuctionItemStatus) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ttis.auction.entity.AuctionItemStatus[ id=" + id + " ]";
    }
    
}
