package com.bzpayroll.register.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bzpayroll.login.entity.BcaUsermapping;

@Repository
@Transactional
public interface UserMappingRepository extends JpaRepository<BcaUsermapping, String> {

}
