package com.bzpayroll.login.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bca_usermapping")
public class BcaUsermapping {
	
	    @Id
	    @GeneratedValue(strategy=GenerationType.IDENTITY)
	    @Column(name = "mappingid")
         private Long mappingId;
	    
	    @Column(name = "companyid")
	    private int companyId;
	    
	    @Column(name = "usergroupid")
	    private int userGroupId;
	    
	    @Column(name = "userid")
	    private int userId;
	    
	    @Column(name = "Role")
	    private String role;
	
	    @Column(name = "Active")
	    private int active;
	    
	    @Column(name = "Deleted")
	    private int deleted;

		public Long getMappingId() {
			return mappingId;
		}

		public void setMappingId(Long mappingId) {
			this.mappingId = mappingId;
		}

		public int getCompanyId() {
			return companyId;
		}

		public void setCompanyId(int companyId) {
			this.companyId = companyId;
		}

		public int getUserGroupId() {
			return userGroupId;
		}

		public void setUserGroupId(int userGroupId) {
			this.userGroupId = userGroupId;
		}

		public int getUserId() {
			return userId;
		}

		public void setUserId(int userId) {
			this.userId = userId;
		}

		public String getRole() {
			return role;
		}

		public void setRole(String role) {
			this.role = role;
		}

		public int getActive() {
			return active;
		}

		public void setActive(int active) {
			this.active = active;
		}

		public int getDeleted() {
			return deleted;
		}

		public void setDeleted(int deleted) {
			this.deleted = deleted;
		}
	    
	    
	    

}
