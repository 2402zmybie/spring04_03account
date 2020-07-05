package com.hr.service.impl;

import com.hr.dao.IAccountDao;
import com.hr.domain.Account;
import com.hr.service.IAccountService;
import com.hr.utils.TransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("accountService")
public class AccountServiceImpl implements IAccountService {

    @Autowired
    private IAccountDao accountDao;


    public List<Account> findAllAccount() {
        List<Account> accounts = accountDao.findAllAccount();
        return accounts;
    }

    public Account findAccountById(Integer accountId) {

        Account account = accountDao.findAccountById(accountId);
        return account;
    }

    public void saveAccount(Account account) {
        accountDao.saveAccount(account);

    }

    public void updateAccount(Account account) {
        accountDao.updateAccount(account);
    }

    public void deleteAccount(Integer accountId) {
        accountDao.deleteAccount(accountId);
    }

    //由于每次获取一个连接 导致无法事务控制
    //需要使用ThreadLocal对象把Connection和当前线程邦定,从而使一个线程中只有一个能控制事务的对象
    //转账
    public void transfer(String sourceName, String targetName, Float money) {
        //查询转出账户
        Account source = accountDao.findAccountByName(sourceName);
        //查询转入账户
        Account target = accountDao.findAccountByName(targetName);
        //转出账户减钱
        source.setMoney(source.getMoney() - money);
        //转入账户加钱
        target.setMoney(target.getMoney() + money);
        //更新转出账户
        accountDao.updateAccount(source);
//        int i = 1 / 0;
        //更新转入账户
        accountDao.updateAccount(target);


    }
}
