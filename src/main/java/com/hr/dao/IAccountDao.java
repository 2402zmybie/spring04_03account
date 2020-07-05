package com.hr.dao;

import com.hr.domain.Account;

import java.util.List;

/**
 * 账户的持久层接口
 */
public interface IAccountDao {

    List<Account> findAllAccount();

    Account findAccountById(Integer accountId);

    void saveAccount(Account account);

    void updateAccount(Account account);

    void deleteAccount(Integer accountId);

    /**
     * 根据名字查询账户
     * @param accountName
     * @return 有唯一的结果返回, 没有结果返回null, 结果超过一个抛异常
     */
    Account findAccountByName(String accountName);
}
