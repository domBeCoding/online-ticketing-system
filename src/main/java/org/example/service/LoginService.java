package org.example.service;

import org.example.model.Account;
import org.example.model.Credentials;
import org.example.reader.FileReader;

import java.util.List;

public class LoginService<T> extends FileReader<T> {

    private Account account;

    public LoginService(Class<T> type) {
        super(type);
    }

    public boolean login(Credentials credentials) {

        boolean doesAccountExist;

        try {
            account = getAccountByCredentials(credentials);
            doesAccountExist = true;
        } catch (RuntimeException e) {
            doesAccountExist = false;
        }

        return doesAccountExist;
    }

    private Account getAccountByCredentials(Credentials credentials) {

        List<Account> accounts = (List<Account>) getAll();

        return accounts.stream()
                .filter(x -> x.getUserId().equals(credentials.userId()))
                .filter(x -> x.getPassword().equals(credentials.password()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException());
    }

    public Account getAccount() {
        return account;
    }
}
