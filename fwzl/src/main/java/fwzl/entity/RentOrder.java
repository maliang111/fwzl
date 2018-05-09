package fwzl.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * 账目信息实体类
 * @author 马亮
 */
public class RentOrder implements Serializable {
    private static final long serialVersionUID = -6610303229938847477L;
    private Integer orderId;
    private Integer houseId;
    private Integer ownerId;
    private Integer tenantId;
    private Date leaseTime;
    private Integer leaseLength;
    private double deposit;
    private double yjje;
    private Double sjje;
    private double wjje;
    private Integer sort;
    private Date dueDate;
    private Date createdTime;
    private String createdUser;
    private Date modifiedTime;
    private String modifiedUser;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getHouseId() {
        return houseId;
    }

    public void setHouseId(Integer houseId) {
        this.houseId = houseId;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getTenantId() {
        return tenantId;
    }

    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }

    public Date getLeaseTime() {
        return leaseTime;
    }

    public void setLeaseTime(Date leaseTime) {
        this.leaseTime = leaseTime;
    }

    public Integer getLeaseLength() {
        return leaseLength;
    }

    public void setLeaseLength(Integer leaseLength) {
        this.leaseLength = leaseLength;
    }

    public double getDeposit() {
        return deposit;
    }

    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }

    public double getYjje() {
        return yjje;
    }

    public void setYjje(double yjje) {
        this.yjje = yjje;
    }

    public double getWjje() {
        return wjje;
    }

    public void setWjje(double wjje) {
        this.wjje = wjje;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getModifiedUser() {
        return modifiedUser;
    }

    public void setModifiedUser(String modifiedUser) {
        this.modifiedUser = modifiedUser;
    }


    public Double getSjje() {
        return sjje;
    }

    public void setSjje(Double sjje) {
        this.sjje = sjje;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
