/**
 * 
 */
package com.chinarewards.gwt.elt.server.gift;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;
import org.slf4j.Logger;
import com.chinarewards.elt.domain.gift.Gift;
import com.chinarewards.elt.service.gift.GiftService;
import com.chinarewards.gwt.elt.client.gift.request.SearchGiftByIdRequest;
import com.chinarewards.gwt.elt.client.gift.request.SearchGiftByIdResponse;
import com.chinarewards.gwt.elt.client.gift.model.GiftVo;
import com.chinarewards.gwt.elt.server.BaseActionHandler;
import com.chinarewards.gwt.elt.server.logger.InjectLogger;
import com.google.inject.Inject;

/**
 * @author yanrui
 */
public class SearchGiftByIdHandler extends
		BaseActionHandler<SearchGiftByIdRequest, SearchGiftByIdResponse> {
	@InjectLogger
	Logger logger;
	GiftService giftService;

	@Inject
	public SearchGiftByIdHandler(GiftService giftService) {
		this.giftService = giftService;
	}

	@Override
	public SearchGiftByIdResponse execute(SearchGiftByIdRequest request,
			ExecutionContext context) throws DispatchException {
		logger.debug(" parameters:{}", request.getId());
		Gift gift = giftService.findGiftById(request.getId());
		return new SearchGiftByIdResponse(adapter(giftService, gift));

	}

	private GiftVo adapter(GiftService giftService, Gift gift) {
		GiftVo giftVo = new GiftVo();
		giftVo.setId(gift.getId());
		giftVo.setName(gift.getName());

		return giftVo;
	}

	@Override
	public Class<SearchGiftByIdRequest> getActionType() {
		return SearchGiftByIdRequest.class;
	}

	@Override
	public void rollback(SearchGiftByIdRequest arg0,
			SearchGiftByIdResponse arg1, ExecutionContext arg2)
			throws DispatchException {
	}

}
