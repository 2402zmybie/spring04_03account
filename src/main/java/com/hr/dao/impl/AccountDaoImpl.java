package com.hr.dao.impl;

import com.hr.dao.IAccountDao;
import com.hr.domain.Account;
import com.hr.utils.ConnectionUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Repository("accountDao")
public class AccountDaoImpl implements IAccountDao {

    @Autowired
    private QueryRunner runner;
    @Autowired
    private ConnectionUtils connectionUtils;


    public List<Account> findAllAccount() {
        try {
            return runner.query(connectionUtils.getThreadConnection(),"select * from account",new BeanListHandler<Account>(Account.class));
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
    }

    public Account findAccountById(Integer accountId) {
        try {
            return runner.query(connectionUtils.getThreadConnection(),"select * from account where id = ?", new BeanHandler<Account>(Account.class),accountId);
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
    }

    public void saveAccount(Account account) {
        try {
            runner.update(connectionUtils.getThreadConnection(),"insert into account(name,money) values(?,?)",account.getName(),account.getMoney());
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
    }

    public void updateAccount(Account account) {
        try {
            runner.update(connectionUtils.getThreadConnection(),"update account set name=?,money=? where id=?",account.getName(),account.getMoney(),account.getId());
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
    }

    public void deleteAccount(Integer accountId) {
        try {
            runner.update(connectionUtils.getThreadConnection(),"delete account where id=?",accountId);
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
    }

    public Account findAccountByName(String accountName) {
        try {
            List<Account> accounts =  runner.query(connectionUtils.getThreadConnection(),"select * from account where name = ?", new BeanListHandler<Account>(Account.class),accountName);
            if(accounts == null || accounts.size() == 0) {
                return null;
            }
            if(accounts.size() > 1) {
                throw new RuntimeException("结果集不唯一,数据有问题");
            }
            return accounts.get(0);
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
    }
}
