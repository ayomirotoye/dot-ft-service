/** Created by
 * @author Ayomide.Akinrotoye 
 *  on Mar 30, 2022
 */
package africa.dotpay.ftservice.services;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import africa.dotpay.ftservice.configs.DotPayFTProperties;
import africa.dotpay.ftservice.entities.SummaryOfTransaction;
import africa.dotpay.ftservice.entities.Transaction;
import africa.dotpay.ftservice.enums.TransactionStatusEnum;
import africa.dotpay.ftservice.models.TransactionSummary;
import africa.dotpay.ftservice.repositories.TransactionRepository;
import africa.dotpay.ftservice.repositories.TransactionSummaryRepo;
import africa.dotpay.ftservice.tools.HelperUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Ayomide.Akinrotoye
 *
 */
@Service
@Slf4j
public class AnalyticsService {
	private TransactionRepository transactionRepository;
	private TransactionSummaryRepo transactionSummaryRepo;
	private DotPayFTProperties dotPayFTProperties;

	@Autowired
	public AnalyticsService(TransactionRepository transactionRepository, DotPayFTProperties dotPayFTProperties,
			TransactionSummaryRepo transactionSummaryRepo) {
		this.transactionRepository = transactionRepository;
		this.transactionSummaryRepo = transactionSummaryRepo;
		this.dotPayFTProperties = dotPayFTProperties;
	}

	@Scheduled(cron = "${dotpay.config.cron-for-running-analysis}")
	public void checkIfTransactionIsCommissionWorthyAndProcessAccordingly() {
		List<Transaction> getTransactions = transactionRepository.findByIsProcessedFalse();

		if (!getTransactions.isEmpty()) {
			log.info("== STARTED PROCESSING TRANSACTIONS @ ===" + LocalDateTime.now());
			List<Transaction> processedTnxs = getTransactions.stream()
					.map(tnx -> defineIfCommissionWorthyAndProcess(tnx)).collect(Collectors.toList());
			transactionRepository.saveAll(processedTnxs);
			log.info("== ENDED PROCESSING TRANSACTIONS @===" + LocalDateTime.now());
			return;
		}
		log.info("=== THERE ARE NO TRANSACTIONS TO PROCESS @ ===" + LocalDateTime.now());
	}

	@Scheduled(cron = "${dotpay.config.cron-for-running-summary}")
	public void runSummaryOnTransactions() {
		Date nowDate = new Date();
		List<Transaction> getTransactions = transactionRepository.findByCreatedOnLessThanEqual(nowDate);

		TransactionSummary summary = TransactionSummary.builder().build();

		try {
			if (!getTransactions.isEmpty()) {
				log.info("== STARTED SUMMARY PROCESS @ ===" + LocalDateTime.now());
				summary = HelperUtils.summarize(getTransactions, summary);
				summary.setTotalTransactions(getTransactions.size());
				SummaryOfTransaction summaryOfTransaction = new SummaryOfTransaction();
				BeanUtils.copyProperties(summary, summaryOfTransaction, "id");

				transactionSummaryRepo.save(summaryOfTransaction);

				log.info("== ENDED SUMMARY PROCESS @ ===" + LocalDateTime.now());
				return;
			}
		} catch (Exception e) {
			log.info("== ERROR OCCURRED WHILE SAVING SUMMARY @ ===" + LocalDateTime.now());
			e.printStackTrace();
			return;
		}
		log.info("=== THERE ARE NO TRANSACTIONS TO SUMMARIZE @ ===" + LocalDateTime.now());
	}

	private Transaction defineIfCommissionWorthyAndProcess(Transaction transaction) {
		if (transaction.getStatus().equalsIgnoreCase(TransactionStatusEnum.SUCCESSFUL.name())) {
			transaction.setIsProcessed(Boolean.TRUE);
			transaction.setIsCommissionWorthy(Boolean.TRUE);

			Double commissionAmount = dotPayFTProperties.getCommissionPercent() * transaction.getAmount();
			Double feeAmount = calculateFeeAmount(commissionAmount);

			transaction.setFeeAmount(feeAmount);
			transaction.setCommissionAmount(commissionAmount);
			transaction.setIsCommissionWorthy(Boolean.TRUE);
			transaction.setTransactionProcessTime(new Date());
			return transaction;
		}
		transaction.setIsProcessed(Boolean.TRUE);
		transaction.setIsCommissionWorthy(Boolean.FALSE);
		return transaction;
	}

	private Double calculateFeeAmount(Double commissionAmount) {
		Double feeAmount = dotPayFTProperties.getFeePercent() * commissionAmount;
		Double feeAmountCap = dotPayFTProperties.getFeeAmountCap();

		if (feeAmount > feeAmountCap) {
			feeAmount = feeAmountCap;
		}

		return feeAmount;
	}
}
