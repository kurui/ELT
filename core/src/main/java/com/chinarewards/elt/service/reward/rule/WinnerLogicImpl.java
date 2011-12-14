package com.chinarewards.elt.service.reward.rule;

import java.util.Date;
import java.util.List;

import com.chinarewards.elt.dao.reward.PreWinnerDao;
import com.chinarewards.elt.dao.reward.PreWinnerLotDao;
import com.chinarewards.elt.dao.reward.RewardDao;
import com.chinarewards.elt.dao.reward.WinnerDao;
import com.chinarewards.elt.domain.reward.person.PreWinner;
import com.chinarewards.elt.domain.reward.person.PreWinnerLot;
import com.chinarewards.elt.domain.reward.person.Winner;
import com.chinarewards.elt.domain.user.SysUser;
import com.chinarewards.elt.model.reward.base.PreWinnerLotStatus;
import com.chinarewards.elt.model.reward.base.WinnerProcessFlag;
import com.chinarewards.elt.tx.exception.BalanceLackException;
import com.chinarewards.elt.tx.service.TransactionService;
import com.chinarewards.elt.util.DateUtil;
import com.google.inject.Inject;

/**
 * The implementation of {@link WinnerLogic}
 * 
 * @author yanxin
 * @since 1.0
 */
public class WinnerLogicImpl implements WinnerLogic {

	PreWinnerLotDao preWinnerLotDao;
	PreWinnerDao preWinnerDao;
	WinnerDao winnerDao;
	RewardDao rewardDao;
	TransactionService transactionService;

	@Inject
	public WinnerLogicImpl(PreWinnerLotDao preWinnerLotDao,
			PreWinnerDao preWinnerDao, WinnerDao winnerDao,
			RewardDao rewardDao, TransactionService transactionService) {
		this.preWinnerLotDao = preWinnerLotDao;
		this.preWinnerDao = preWinnerDao;
		this.winnerDao = winnerDao;
		this.rewardDao = rewardDao;
		this.transactionService = transactionService;
	}

	@Override
	public void approveWinnerFromEffectivePreWinnerLotOfReward(SysUser caller,
			String lotId, String reason) {
		Date now = DateUtil.getTime();
		PreWinnerLot lot = preWinnerLotDao.findById(PreWinnerLot.class, lotId);
		List<PreWinner> preWinners = preWinnerDao
				.findPreWinnerByPreWinnerLotId(lotId);
		for (PreWinner preWinner : preWinners) {
			Winner winner = new Winner();
			winner.setPreWinner(preWinner);
			winner.setReward(lot.getReward());
			winner.setAmt(preWinner.getAmt());
			winner.setReason(preWinner.getReason());
			winner.setProcessFlag(WinnerProcessFlag.UNPROCESS);
			winner.setUnit(preWinner.getUnit());
			winner.setReward(preWinner.getPreWinnerLot().getReward());
			winner.setStaff(preWinner.getStaff());
			winner.setWinTime(now);
			winner.setCreatedAt(now);
			winner.setCreatedBy(caller);
			winner.setLastModifiedAt(now);
			winner.setLastModifiedBy(caller);
			winnerDao.save(winner);
		}

		lot.setStatus(PreWinnerLotStatus.PASS);
		lot.setReason(reason);
		lot.setLastModifiedAt(now);
		lot.setLastModifiedBy(caller);
		preWinnerLotDao.update(lot);
	}

	@Override
	public void processWinnerAward(String rewardId) {
		List<Winner> untreatedWinners = winnerDao
				.findUntreatedWinnersByRewardId(rewardId);
		String fromAccountId = rewardDao
				.findCorporationTxIdByRewardId(rewardId);
		for (Winner w : untreatedWinners) {
			String toAccountId = w.getStaff().getTxAccountId();
			String unitCode = w.getUnit().toString();
			double amt = w.getAmt();
			try {
				String txId = transactionService.transaction(fromAccountId,
						toAccountId, unitCode, amt);
				w.setProcessFlag(WinnerProcessFlag.PROCESS_SUCCESS);
				w.setRefTransactionId(txId);
			} catch (BalanceLackException e) {
				w.setProcessFlag(WinnerProcessFlag.PROCESS_FAIL);
			} finally {
				winnerDao.update(w);
			}
		}
	}

}
