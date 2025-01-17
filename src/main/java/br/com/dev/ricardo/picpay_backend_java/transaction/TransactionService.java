package br.com.dev.ricardo.picpay_backend_java.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.dev.ricardo.picpay_backend_java.exeption.InvalidTransactionException;
import br.com.dev.ricardo.picpay_backend_java.wallet.Wallet;
import br.com.dev.ricardo.picpay_backend_java.wallet.WalletRepository;
import br.com.dev.ricardo.picpay_backend_java.wallet.WalletType;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;
    private WalletRepository walletRepository;

    @Transactional
    public Transaction create(Transaction transaction) {
        // 1- validar
        validate(transaction);

        // 2- criara trasação
        var newTransaction = transactionRepository.save(transaction);

        // 3- debitar da carteira
        var wallet = walletRepository.findById(transaction.payer()).get();
        walletRepository.save(wallet.debit(transaction.value()));

        // 4- chamar serviços externos

        return newTransaction;
    }

    /*
     * - the payer has a common wallet
     * - the payer has enough balance
     * - the payer is not the payee
     */
    private void validate(Transaction transaction) {
        walletRepository.findById(transaction.payee())
            .map(payee -> walletRepository.findById(transaction.payer())
                .map(payer -> isTransactionValid(transaction, payer) ? transaction : null)
                .orElseThrow(() -> new InvalidTransactionException("Invalid Trasaction - %s".formatted(transaction))))
            .orElseThrow(() -> new InvalidTransactionException("Invalid Trasaction - %s".formatted(transaction)));
    }

    private boolean isTransactionValid(Transaction transaction, Wallet payer) {
        return payer.type() == WalletType.COMUM.getValue() && payer.balance().compareTo(transaction.value()) >= 0 &&
        !payer.id().equals(transaction.payee());
    }
}
